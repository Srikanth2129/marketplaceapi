/**
 * 
 */
package com.example.marketplace.dao.projects;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.marketplace.dao.bids.BidMapper;
import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;
import com.example.marketplace.util.ProjectHelper;

/**
 * @author srikanthgummula
 *
 */
@Repository
public class ProjectDaoImpl implements ProjectDao {
	
	@Autowired
	private DataSource dataSource;
	
	private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public int createProject(Project project) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		project.setCreatedDate(new Date());
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(project);
		getNamedParameterJdbcTemplate().update(PROJECT_INSERT_SQL, parameterSource,keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void updateProject(Project project) {
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(project);
		getNamedParameterJdbcTemplate().update(PROJECT_UPDATE_SQL, parameterSource);
	}

	@Override
	public Project getProject(int projectId) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("projectId",projectId);
		List<Project> projects = getNamedParameterJdbcTemplate().query(GET_PROJECT_BY_ID, parameterSource, new ProjectMapper());
		return (projects == null || projects.isEmpty()) ? null : projects.get(0);
	}

	@Override
	public List<Project> getProjects(String status, String type) {
		StringBuilder sb = new StringBuilder();
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		if(!ProjectHelper.isEmpty(status)) {
			sb.append(" WHERE STATUS = :status");
			parameterSource.addValue("status", status);
		}
		if(!ProjectHelper.isEmpty(type)) {
			if(ProjectHelper.isEmpty(sb.toString())) 
				sb.append(" WHERE PROJECT_TYPE = :type");
			else
				sb.append(" AND PROJECT_TYPE = :type");
			parameterSource.addValue("type", type);
		}
		return getNamedParameterJdbcTemplate().query(GET_PROJECTS+sb.toString(), parameterSource, new ProjectMapper());
	}
	
	@Override
	public List<Bid> getLowestBids(int projectId) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("projectId",projectId);
		return getNamedParameterJdbcTemplate().query(GET_LOWEST_BID, parameterSource, new BidMapper());
	}

}
