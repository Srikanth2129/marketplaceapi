/**
 * 
 */
package com.example.marketplace.util;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.example.marketplace.Currency;
import com.example.marketplace.ErrorCode;
import com.example.marketplace.ProjectMessages;
import com.example.marketplace.ProjectStatus;
import com.example.marketplace.ProjectType;
import com.example.marketplace.domain.ErrorBean;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 * <p>Helper class for Project Resources</p>
 */
@Component
public class ProjectHelper {
	
	private static final Logger LOGGER = Logger.getLogger(ProjectHelper.class.getName());
	
	/**
	 * Validates Create Project Request
	 * @param project
	 * @return ErrorBean
	 */
	public ErrorBean validateCreateProject(Project project) {
		ErrorBean errorBean = null;
		if(project == null) {
			LOGGER.log(Level.WARNING,ProjectMessages.PROJECT_REQUEST_INVALID);
			errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST, ProjectMessages.PROJECT_REQUEST_INVALID);
		}
		else {
			StringBuilder sb = new StringBuilder();
			if(project.getProjectId() != 0) {
				sb.append(ProjectMessages.CREATE_PROJECT_ID_INVALID);
			}
			sb.append(validateProjectDetails(project));
			String msg = sb.toString();
			if(!isEmpty(msg)) {
				LOGGER.log(Level.WARNING,msg);
				errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST,msg);
			}
		}
		
		return errorBean;
		
	}
	
	/**
	 * Validates Update Project Request
	 * @param project
	 * @return ErrorBean
	 */
	public ErrorBean validateUpdateProject(int projectId, Project project) {
		ErrorBean errorBean = null;
		if(project == null) {
			LOGGER.log(Level.WARNING,ProjectMessages.PROJECT_REQUEST_INVALID);
			errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST, ProjectMessages.PROJECT_REQUEST_INVALID);
		}
		else {
			StringBuilder sb = new StringBuilder();
			if(project.getProjectId() != 0 && projectId != project.getProjectId()) {
				sb.append(ProjectMessages.PROJECT_ID_INVALID);
			}
			sb.append(validateProjectDetails(project));
			String msg = sb.toString();
			if(!isEmpty(msg)) {
				LOGGER.log(Level.WARNING,msg);
				errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST,msg);
			}
		}
		
		return errorBean;
		
	}

	/**
	 * Validates Project Details
	 * @param project
	 * @return
	 */
	private String validateProjectDetails(Project project) {
		StringBuilder sb = new StringBuilder();
		if(isEmpty(project.getTitle())) {
			sb.append(ProjectMessages.PROJECT_TITLE_INVALID);
		}
		if(isEmpty(project.getDescription())) {
			sb.append(ProjectMessages.PROJECT_DESCRIPTION_INVALID);
		}
		if(isEmpty(project.getRequirements())) {
			sb.append(ProjectMessages.PROJECT_REQUIREMENTS_INVALID);
		}
		if(project.getCurrency() == null || Currency.fromValue(project.getCurrency()) == null) {
			sb.append(ProjectMessages.CURRENCY_INVALID);
		}
		if(project.getMaxBudget() <= 0) {
			sb.append(ProjectMessages.BUDGET_INVALID);
		}
		Date date = new Date();
		if(project.getLastDate() == null || project.getLastDate().before(date)) {
			sb.append(ProjectMessages.LASTDATE_INVALID);
		}
		if(project.getStatus() != null && ProjectStatus.fromValue(project.getStatus()) == null) {
			sb.append(ProjectMessages.PROJECT_STATUS_INVALID);
		}
		if(project.getType() != null && ProjectType.fromValue(project.getType()) == null) {
			sb.append(ProjectMessages.PROJECT_TYPE_INVALID);
		}
		return sb.toString();
	}
	
	public static boolean isEmpty(String inputStr) {
		return (inputStr == null || inputStr.trim().length() == 0);
	}

}
