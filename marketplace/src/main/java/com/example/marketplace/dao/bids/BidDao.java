/**
 * 
 */
package com.example.marketplace.dao.bids;

import java.util.List;

import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 * <p>Defines all the Bid DAO methods</p>
 */
public interface BidDao {

	public static final String BID_INSERT_SQL = "INSERT INTO " +
	        "BID(PROJECT_ID,BUYER,CURRENCY,BID_AMOUNT,STATUS,CREATED_DATE) " +
	        "VALUES (:projectId,:buyer,:currency,:bidAmount,:status,:createdDate)";

	public static final String BID_UPDATE_SQL = "UPDATE BID  " +
	        "SET PROJECT_ID = :projectId, CURRENCY = :currency, BID_AMOUNT = :bidAmount, STATUS = :status, CREATED_DATE = :createdDate " +
	        "WHERE BID_ID = :bidId";

	public static final String GET_BID_BY_ID = "SELECT * FROM BID WHERE BID_ID = :bidId";

	public static final String GET_BID_BY_PROJECT_ID = "SELECT * FROM BID WHERE PROJECT_ID = :projectId";

	public static final String GET_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE PROJECT_ID = :projectId";

	/**
	 * Gets project for the provided projectId
	 * @param projectId
	 * @return Project
	 */
	Project getProject(int projectId);

	/**
	 * Creates Bid
	 * @param bid
	 * @return bidId
	 */
	int createBid(Bid bid);

	/**
	 * Gets Bid for the provided bidId
	 * @param bidId
	 * @return Bid
	 */
	Bid getBid(int bidId);

	/**
	 * Updates bid 
	 * @param bid
	 */
	void updateBid(Bid bid);

	/**
	 * Gets bids for the provided projectId
	 * @param projectId
	 * @return list of bids
	 */
	List<Bid> getBidsByProjectId(int projectId);

}
