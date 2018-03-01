/**
 * 
 */
package com.example.marketplace.dao.projects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 * <p>Row mapper class for Project</p>
 */
public class ProjectMapper implements RowMapper<Project>{

	@Override
	public Project mapRow(ResultSet resultSet, int i) throws SQLException {
		Project project = null;
		if(resultSet != null) {
			project = new Project();
			project.setProjectId((Integer) resultSet.getObject("PROJECT_ID"));
			project.setTitle((String)resultSet.getObject("TITLE"));
			project.setDescription((String)resultSet.getObject("DESCRIPTION"));
			project.setRequirements((String)resultSet.getObject("REQUIREMENTS"));
			project.setSkills((String)resultSet.getObject("SKILLS"));
			project.setCurrency((String)resultSet.getObject("CURRENCY"));
			project.setMaxBudget((Double)resultSet.getObject("MAXBUDGET"));
			project.setLastDate((Date) resultSet.getObject("LASTDATE"));
			project.setStatus((String)resultSet.getObject("STATUS"));
			project.setType((String)resultSet.getObject("PROJECT_TYPE"));
			project.setCreatedDate((Date) resultSet.getObject("CREATED_DATE"));
		}
		return project;
	}

}
