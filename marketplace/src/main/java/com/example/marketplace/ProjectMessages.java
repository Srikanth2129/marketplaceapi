/**
 * 
 */
package com.example.marketplace;

/**
 * @author srikanthgummula
 *
 * <p>Contains all the messages for Project Resource</p>
 */
public interface ProjectMessages {
	
	public static final String PROJECT_REQUEST_INVALID = "Project request is invalid.";
	public static final String PROJECT_TITLE_INVALID = "Project title is null or empty.";
	public static final String CREATE_PROJECT_ID_INVALID = "Cannot contain projectId.";
	public static final String PROJECT_ID_INVALID = "Project Id is invalid in the request.";
	public static final String PROJECT_DESCRIPTION_INVALID = "Project description is null or empty.";
	public static final String PROJECT_REQUIREMENTS_INVALID = "Project requirements are invalid.";
	public static final String CURRENCY_INVALID = "Invalid Currency.";
	public static final String BUDGET_INVALID = "Budget should be greater than zero.";
	public static final String LASTDATE_INVALID = "Last date is invalid.";	
	public static final Object PROJECT_STATUS_INVALID = "Project status is invalid.";
	public static final Object PROJECT_TYPE_INVALID = "Project type is invalid.";
	
	public static final String PROCESSING_ERROR = "Error encountered while processing the request.";
	public static final String PROJECT_NOT_FOUND = "Requested project does not exist.";

}
