/**
 * 
 */
package com.example.marketplace.services.projects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplace.dao.projects.ProjectDao;
import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDao projectDao;

	@Override
	public int createProject(Project project) {
		return projectDao.createProject(project);
	}

	@Override
	public void updateProject(Project project) {
		projectDao.updateProject(project);
	}

	@Override
	public Project getProject(int projectId) {
		return projectDao.getProject(projectId);
	}

	@Override
	public List<Project> getProjects(String status, String type) {
		return projectDao.getProjects(status,type);
	}

	@Override
	public List<Bid> getLowestBid(int projectId) {
		return projectDao.getLowestBids(projectId);
	}

}
