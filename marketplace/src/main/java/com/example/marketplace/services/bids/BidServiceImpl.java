/**
 * 
 */
package com.example.marketplace.services.bids;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplace.dao.bids.BidDao;
import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 */
@Service
public class BidServiceImpl implements BidService {

	@Autowired
	BidDao bidDao;
	
	@Override
	public Project getProject(int projectId) {
		return bidDao.getProject(projectId);
	}

	@Override
	public int createBid(Bid bid) {
		return bidDao.createBid(bid);
	}

	@Override
	public Bid getBid(int bidId) {
		return bidDao.getBid(bidId);
	}

	@Override
	public void updateBid(Bid bid) {
		bidDao.updateBid(bid);
	}

	@Override
	public List<Bid> getBidsByProjectId(int projectId) {
		return bidDao.getBidsByProjectId(projectId);
	}
	
}
