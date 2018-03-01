/**
 * 
 */
package com.example.marketplace.resources.projects;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.marketplace.Currency;
import com.example.marketplace.ErrorCode;
import com.example.marketplace.ProjectMessages;
import com.example.marketplace.ProjectStatus;
import com.example.marketplace.ProjectType;
import com.example.marketplace.domain.ErrorBean;
import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;
import com.example.marketplace.services.projects.ProjectService;
import com.example.marketplace.util.ProjectHelper;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

/**
 * @author srikanthgummula
 * 
 * <p>provides all project resource methods</p>
 */
@Path("/v1/projects")
@Component
public class ProjectResource {
	private static final Logger LOGGER = Logger.getLogger(ProjectResource.class.getName());

	private static final String HAL_JSON = RepresentationFactory.HAL_JSON;

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectHelper projectHelper;

	@Autowired
	RepresentationFactory representationFactory;

	@Context
	UriInfo uriInfo;

	private RepresentationFactory getRepresentationFactory() {
		return representationFactory.withFlag(StandardRepresentationFactory.PRETTY_PRINT);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(HAL_JSON)
	public Response createProject(Project project) {
		Representation representation = getRepresentationFactory().newRepresentation(uriInfo.getAbsolutePath());
		ErrorBean errorBean = projectHelper.validateCreateProject(project);
		if (errorBean != null) {
			representation.withBean(errorBean);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		int projectId;
		try {
			if (Currency.fromValue(project.getCurrency()) != null)
				project.setCurrency(Currency.fromValue(project.getCurrency()).toString());
			ProjectStatus projectStatus = ProjectStatus.fromValue(project.getStatus()) != null ? ProjectStatus.fromValue(project.getStatus())
					: ProjectStatus.OPEN;
			ProjectType projectType = ProjectType.fromValue(project.getType()) != null ? ProjectType.fromValue(project.getType()) : ProjectType.FIXED;
			
			project.setStatus(projectStatus.toString());
			project.setType(projectType.toString());

			projectId = projectService.createProject(project);
		} catch (Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, ProjectMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}

		if (projectId == 0) {
			LOGGER.log(Level.WARNING, "Unable to create the project");
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, ProjectMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		String projectUri = uriInfo.getAbsolutePath().toString() + "/" + projectId;
		String bidUri = projectUri + "/bids";

		representation = getRepresentationFactory().newRepresentation(projectUri).withLink("bids", bidUri);
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
	}

	@PUT
	@Path("/{projectId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(HAL_JSON)
	public Response updateProject(@PathParam("projectId") int projectId, Project project) {
		Representation representation = getRepresentationFactory().newRepresentation(uriInfo.getAbsolutePath());
		ErrorBean errorBean = projectHelper.validateUpdateProject(projectId, project);
		if (errorBean != null) {
			representation.withBean(errorBean);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		try {
			if (projectService.getProject(projectId) == null) {
				LOGGER.log(Level.WARNING, ProjectMessages.PROJECT_NOT_FOUND + " for ProjectId : " + projectId);
				representation.withBean(new ErrorBean(ErrorCode.RESOURCE_NOT_FOUND, ProjectMessages.PROJECT_NOT_FOUND));
				return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
			}
			project.setProjectId(projectId);
			if (Currency.fromValue(project.getCurrency()) != null)
				project.setCurrency(Currency.fromValue(project.getCurrency()).toString());
			ProjectStatus projectStatus = ProjectStatus.fromValue(project.getStatus()) != null ? ProjectStatus.fromValue(project.getStatus())
					: ProjectStatus.OPEN;
			project.setStatus(projectStatus.toString());
			ProjectType projectType = ProjectType.fromValue(project.getType()) != null ? ProjectType.fromValue(project.getType()) : ProjectType.FIXED;
			project.setType(projectType.toString());

			projectService.updateProject(project);
		} catch (Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, ProjectMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("/{projectId}")
	@Produces(HAL_JSON)
	public Response getProject(@PathParam("projectId") int projectId) {
		Representation representation = getRepresentationFactory().newRepresentation(uriInfo.getAbsolutePath());
		Project project;
		try {
			project = projectService.getProject(projectId);
		} catch (Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, ProjectMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		if (project == null) {
			LOGGER.log(Level.WARNING, ProjectMessages.PROJECT_NOT_FOUND + " for ProjectId : " + projectId);
			representation.withBean(new ErrorBean(ErrorCode.RESOURCE_NOT_FOUND, ProjectMessages.PROJECT_NOT_FOUND));
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		List<Bid> bids = null;
		try {
			bids = projectService.getLowestBid(projectId);
		} catch (Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, ProjectMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}

		representation.withBean(project);
		if (bids != null && !bids.isEmpty()) {
			for (Bid bid : bids) {
				if (bid != null) {
					String bidUri = uriInfo.getBaseUri() + "v1/projects/bids/" + bid.getBidId();
					Representation embeddedRepresentation = getRepresentationFactory().newRepresentation(bidUri);
					embeddedRepresentation.withBean(bid);
					representation.withRepresentation("bids", embeddedRepresentation);
				}
			}
		}

		return Response.status(Response.Status.OK.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
	}

	@GET
	@Path("/all")
	@Produces(HAL_JSON)
	public Response getProjects(@QueryParam("status") String status, @QueryParam("type") String type) {
		Representation representation = getRepresentationFactory().newRepresentation(uriInfo.getAbsolutePath());
		List<Project> projects;
		try {
			String projectStatus = null;
			String projectType = null;
			if(!ProjectHelper.isEmpty(status)) {
				if (ProjectStatus.fromValue(status) == null) {
					LOGGER.log(Level.WARNING, "Invalid status :" + status);
					return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
				}
				projectStatus = ProjectStatus.fromValue(status).toString();
			}
			if(!ProjectHelper.isEmpty(type)) {
				if (ProjectType.fromValue(type) == null) {
					LOGGER.log(Level.WARNING, "Invalid Type : " + type);
					return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
				}
				projectType = ProjectType.fromValue(type).toString();
			}
			
			projects = projectService.getProjects(projectStatus, projectType);
		} catch (Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, ProjectMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		if (projects == null) {
			LOGGER.log(Level.WARNING, "No Projects found for the status :" + status + " and type : " + type);
			return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
		}

		if (projects != null && !projects.isEmpty()) {
			for (Project project : projects) {
				if (project != null) {
					String projectUri = uriInfo.getBaseUri() + "v1/projects/" + project.getProjectId();
					Representation embeddedRepresentation = getRepresentationFactory().newRepresentation(projectUri).withLink("bids",
							projectUri + "/bids");
					embeddedRepresentation.withBean(project);
					representation.withRepresentation("Projects", embeddedRepresentation);
				}
			}
		}

		return Response.status(Response.Status.OK.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
	}

}
