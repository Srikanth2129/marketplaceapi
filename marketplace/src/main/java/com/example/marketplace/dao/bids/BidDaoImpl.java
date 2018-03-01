/**
 * 
 */
package com.example.marketplace.dao.bids;

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

import com.example.marketplace.dao.projects.ProjectMapper;
import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 */
@Repository
public class BidDaoImpl implements BidDao {

	@Autowired
	private DataSource dataSource;
	
	private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Project getProject(int projectId) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("projectId",projectId);
		List<Project> projects = getNamedParameterJdbcTemplate().query(GET_PROJECT_BY_ID, parameterSource, new ProjectMapper());
		return (projects == null || projects.isEmpty()) ? null : projects.get(0);
	}

	@Override
	public int createBid(Bid bid) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		bid.setCreatedDate(new Date());
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(bid);
		getNamedParameterJdbcTemplate().update(BID_INSERT_SQL, parameterSource, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public Bid getBid(int bidId) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("bidId",bidId);
		List<Bid> bids = getNamedParameterJdbcTemplate().query(GET_BID_BY_ID, parameterSource, new BidMapper());
		return (bids == null || bids.isEmpty()) ? null : bids.get(0);
	}

	@Override
	public void updateBid(Bid bid) {
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(bid);
		getNamedParameterJdbcTemplate().update(BID_UPDATE_SQL, parameterSource);
	}

	@Override
	public List<Bid> getBidsByProjectId(int projectId) {
		SqlParameterSource parameterSource = new MapSqlParameterSource("projectId",projectId);
		List<Bid> bids = getNamedParameterJdbcTemplate().query(GET_BID_BY_PROJECT_ID, parameterSource, new BidMapper());
		return bids;
	}

}
