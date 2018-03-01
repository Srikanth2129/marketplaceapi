/**
 * 
 */
package com.example.marketplace.dao.bids;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.example.marketplace.domain.bids.Bid;

/**
 * @author srikanthgummula
 *
 * <p>Row mapper class for Bid</p>
 */
public class BidMapper implements RowMapper<Bid>{

	@Override
	public Bid mapRow(ResultSet resultSet, int i) throws SQLException {
		Bid bid = null;
		if(resultSet != null) {
			bid = new Bid();
			bid.setBidId((Integer)resultSet.getObject("BID_ID"));
			bid.setProjectId((Integer) resultSet.getObject("PROJECT_ID"));
			bid.setCurrency((String)resultSet.getObject("CURRENCY"));
			bid.setBidAmount((Double)resultSet.getObject("BID_AMOUNT"));
			bid.setStatus((String)resultSet.getObject("STATUS"));
			bid.setCreatedDate((Date) resultSet.getObject("CREATED_DATE"));
			bid.setBuyer((String)resultSet.getObject("BUYER"));
		}		
		return bid;
	}

}
