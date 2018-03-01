/**
 * 
 */
package com.example.marketplace.resources.bids;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.marketplace.BidMessages;
import com.example.marketplace.BidStatus;
import com.example.marketplace.ErrorCode;
import com.example.marketplace.domain.ErrorBean;
import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;
import com.example.marketplace.services.bids.BidService;
import com.example.marketplace.util.BidHelper;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;

/**
 * @author srikanthgummula
 *
 * <p>Provides all bid resource methods</p>
 */
@Path("/v1/projects")
@Component
public class BidResource {

	private static final Logger LOGGER = Logger.getLogger(BidResource.class.getName());
	
	private static final String HAL_JSON = RepresentationFactory.HAL_JSON;
	
	@Autowired
	BidService bidService;
	
	@Autowired
	BidHelper bidHelper;
	
	@Autowired
	RepresentationFactory representationFactory;
	
	@Context
	UriInfo uriInfo;
	
	private RepresentationFactory getRepresentationFactory() {
		return representationFactory.withFlag(StandardRepresentationFactory.PRETTY_PRINT);
	}
	
	@POST
	@Path("/{projectId}/bids")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(HAL_JSON)
	public Response createBid(@PathParam("projectId") int projectId, Bid bid) {
		Representation representation = getRepresentationFactory().newRepresentation(uriInfo.getAbsolutePath());
		ErrorBean errorBean = bidHelper.validateCreateBid(projectId, bid);
		if (errorBean != null) {
			representation.withBean(errorBean);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		int bidId;
		try {
			Project bidProject = bidService.getProject(projectId);
			if(bidProject == null) {
				representation.withBean(new ErrorBean(ErrorCode.RESOURCE_NOT_FOUND, BidMessages.PROJECT_NOT_FOUND));
				return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
			}
			errorBean = bidHelper.validateBidAndProject(bid, bidProject);
			if(errorBean != null) {
				representation.withBean(errorBean);
				return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
			}
			bid.setCurrency(bidProject.getCurrency());
			BidStatus bidStatus = BidStatus.fromValue(bid.getStatus()) != null ? BidStatus.fromValue(bid.getStatus())
					: BidStatus.OPEN;
			bid.setStatus(bidStatus.toString());
			bid.setProjectId(projectId);

			bidId = bidService.createBid(bid);
		} catch (Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, BidMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}

		if (bidId == 0) {
			LOGGER.log(Level.WARNING, "Unable to create the bid");
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, BidMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		String bidUri = uriInfo.getBaseUri().toString() + "v1/projects/bids/" + bidId;

		representation = getRepresentationFactory().newRepresentation(bidUri);
		return Response.status(Response.Status.CREATED.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
	}

	@PUT
	@Path("/bids/{bidId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(HAL_JSON)
	public Response updatebid(@PathParam("bidId") int bidId, Bid bid) {
		Representation representation = getRepresentationFactory().newRepresentation(uriInfo.getAbsolutePath());
		ErrorBean errorBean = bidHelper.validateUpdateBid(bidId, bid);
		if(errorBean != null) {
			representation.withBean(errorBean);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		try{
			Project bidProject = bidService.getProject(bid.getProjectId());
			if(bidProject == null) {
				representation.withBean(new ErrorBean(ErrorCode.INVALID_REQUEST, BidMessages.PROJECT_ID_INVALID));
				return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
			}
			if(bidService.getBid(bidId) == null) {
				representation.withBean(new ErrorBean(ErrorCode.RESOURCE_NOT_FOUND, BidMessages.BID_NOT_FOUND));
				return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
			}
			errorBean = bidHelper.validateBidAndProject(bid, bidProject);
			if(errorBean != null) {
				representation.withBean(errorBean);
				return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
			}
			bid.setCurrency(bidProject.getCurrency());
			BidStatus bidStatus = BidStatus.fromValue(bid.getStatus()) != null ? BidStatus.fromValue(bid.getStatus())
					: BidStatus.OPEN;
			bid.setStatus(bidStatus.toString());
			bid.setBidId(bidId);
			
			bidService.updateBid(bid);
		}
		catch(Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, BidMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
	}
	
	@GET
	@Path("/bids/{bidId}")
	@Produces(HAL_JSON)
	public Response getBid(@PathParam("bidId") int bidId) {
		Representation representation = getRepresentationFactory().newRepresentation(uriInfo.getAbsolutePath());
		Bid bid=null;
		try{
			bid = bidService.getBid(bidId);
		}
		catch(Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, BidMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		if(bid == null) {
			LOGGER.log(Level.WARNING, "No bids found for bidId : "+bidId);
			representation.withBean(new ErrorBean(ErrorCode.RESOURCE_NOT_FOUND, BidMessages.BID_NOT_FOUND));
			return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		representation.withBean(bid);
		return Response.status(Response.Status.OK.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
	}
	
	@GET
	@Path("/{projectId}/bids")
	@Produces(HAL_JSON)
	public Response getBidsByProjectId(@PathParam("projectId") int projectId) {
		Representation representation = getRepresentationFactory().newRepresentation(uriInfo.getAbsolutePath());
		List<Bid> bids = null;
		try {
			Project bidProject = bidService.getProject(projectId);
			if(bidProject == null) {
				representation.withBean(new ErrorBean(ErrorCode.RESOURCE_NOT_FOUND, BidMessages.PROJECT_NOT_FOUND));
				return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
			}
			bids = bidService.getBidsByProjectId(projectId);
		}
		catch(Exception exception) {
			LOGGER.log(Level.WARNING, exception.getMessage(), exception);
			representation.withBean(new ErrorBean(ErrorCode.SERVER_ERROR, BidMessages.PROCESSING_ERROR));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
		}
		
		if(bids == null || bids.isEmpty()) {
			LOGGER.log(Level.WARNING, "No bids found for projectId : "+projectId);
			return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
		}
		for(Bid bid:bids) {
			if(bid != null) {
				String bidUri = uriInfo.getBaseUri() + "v1/projects/bids/" + bid.getBidId();
				Representation embeddedRepresentation = getRepresentationFactory().newRepresentation(bidUri).withBean(bid);
				representation.withRepresentation("bids", embeddedRepresentation);
			}
		}
		
		return Response.status(Response.Status.OK.getStatusCode()).entity(representation.toString(HAL_JSON)).build();
	}
	
}
