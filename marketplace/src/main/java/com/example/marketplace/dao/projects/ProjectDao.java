/**
 * 
 */
package com.example.marketplace.dao.projects;

import java.util.List;

import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 * <p>Defines all the Project DAO methods</p>
 */
public interface ProjectDao {
	
	public static final String PROJECT_INSERT_SQL = "INSERT INTO " +
	        "PROJECT(TITLE, DESCRIPTION, REQUIREMENTS, SKILLS, CURRENCY, MAXBUDGET, LASTDATE , STATUS, PROJECT_TYPE, CREATED_DATE) " +
	        "VALUES (:title,:description,:requirements,:skills,:currency,:maxBudget,:lastDate,:status,:type,:createdDate)";

	public static final String PROJECT_UPDATE_SQL = "UPDATE PROJECT  " +
	        "SET TITLE = :title, DESCRIPTION = :description, REQUIREMENTS = :requirements, SKILLS = :skills, CURRENCY = :currency, " +
	        "MAXBUDGET = :maxBudget, LASTDATE = :lastDate, STATUS = :status, PROJECT_TYPE = :type, CREATED_DATE = :createdDate " +
	        "WHERE PROJECT_ID = :projectId";

	public static final String GET_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE PROJECT_ID = :projectId";

	public static final String GET_PROJECTS = "SELECT * FROM PROJECT ";

	public static final String GET_LOWEST_BID = "SELECT * FROM BID WHERE BID_AMOUNT = (SELECT MIN(BID_AMOUNT) FROM BID WHERE PROJECT_ID = :projectId) ORDER BY CREATED_DATE";

	/**
	 * Creates Project
	 * @param project
	 * @return projectId
	 */
	int createProject(Project project);

	/**
	 * Updates project
	 * @param project
	 */
	void updateProject(Project project);

	/**
	 * Gets project for the provided projectId
	 * @param projectId
	 * @return Project
	 */
	Project getProject(int projectId);

	/**
	 * Gets projects for the provided status and type
	 * @param status
	 * @param type
	 * @return list of Projects
	 */
	List<Project> getProjects(String status, String type);

	/**
	 * Gets lowest bid for the provided projectId
	 * @param projectId
	 * @return list of Bids
	 */
	List<Bid> getLowestBids(int projectId);

}
