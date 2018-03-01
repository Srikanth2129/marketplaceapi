/**
 * 
 */
package com.example.marketplace.services.projects;

import java.util.List;

import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 * <p>Service definition for Project</p>
 */
public interface ProjectService {

	/**
	 * Creates project
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
	 * @return list of projects
	 */
	List<Project> getProjects(String status, String type);

	/**
	 * Gets lowest bid for the provided projectId
	 * @param projectId
	 * @return list of bids
	 */
	List<Bid> getLowestBid(int projectId);
}
