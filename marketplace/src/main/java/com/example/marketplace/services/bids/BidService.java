/**
 * 
 */
package com.example.marketplace.services.bids;

import java.util.List;

import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 * <p>Service definition for Bid</p>
 */
public interface BidService {

	/**
	 * Gets project for the provided projectId
	 * @param projectId
	 * @return Project
	 */
	Project getProject(int projectId);

	/**
	 * Creates Bid
	 * @param bid
	 * @return
	 */
	int createBid(Bid bid);

	/**
	 * Gets Bid for the provided bidId
	 * @param bidId
	 * @return
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
	 * @return
	 */
	List<Bid> getBidsByProjectId(int projectId);

}
