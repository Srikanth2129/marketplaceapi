/**
 * 
 */
package com.example.marketplace.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.example.marketplace.BidMessages;
import com.example.marketplace.BidStatus;
import com.example.marketplace.Currency;
import com.example.marketplace.ErrorCode;
import com.example.marketplace.domain.ErrorBean;
import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;

/**
 * @author srikanthgummula
 *
 * <p>Helper class for Bid resources</p>
 */
@Component
public class BidHelper {

	private static final Logger LOGGER = Logger.getLogger(BidHelper.class.getName());
	
	/**
	 * Validates create bid request
	 * @param projectId
	 * @param bid
	 * @return ErrorBean
	 */
	public ErrorBean validateCreateBid(int projectId, Bid bid) {
		ErrorBean errorBean = null;
		if(bid == null) {
			LOGGER.log(Level.WARNING,BidMessages.BID_REQUEST_INVALID);
			errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST, BidMessages.BID_REQUEST_INVALID);
		}
		else {
			StringBuilder sb = new StringBuilder();
			if(bid.getBidId() != 0) {
				sb.append(BidMessages.CREATE_BID_ID_INVALID);
			}
			if(bid.getProjectId() != 0 && bid.getProjectId() != projectId) {
				sb.append(BidMessages.PROJECT_ID_INVALID);
			}
			sb.append(validateBidDetails(bid));
			String msg = sb.toString();
			if(!isEmpty(msg)) {
				LOGGER.log(Level.WARNING,msg);
				errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST,msg);
			}
		}
		return errorBean;
	}
	
	/**
	 * Validates bid info with project info
	 * @param bid
	 * @param bidProject
	 * @return ErrorBean
	 */
	public ErrorBean validateBidAndProject(Bid bid, Project bidProject) {
		ErrorBean errorBean = null;
		StringBuilder sb = new StringBuilder();			
		if(bid.getCurrency() == null || !bid.getCurrency().equalsIgnoreCase(bidProject.getCurrency())) {
			sb.append(BidMessages.CURRENCY_INVALID);
		}
		if(!isEmpty(sb.toString()))
			errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST, sb.toString());
		return errorBean;
	}
	
	/**
	 * Validates update bid request
	 * @param bidId
	 * @param bid
	 * @return ErrorBean
	 */
	public ErrorBean validateUpdateBid(int bidId, Bid bid) {
		ErrorBean errorBean = null;
		if(bid == null) {
			LOGGER.log(Level.WARNING,BidMessages.BID_REQUEST_INVALID);
			errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST, BidMessages.BID_REQUEST_INVALID);
		}
		else {
			StringBuilder sb = new StringBuilder();
			if(bid.getBidId() != 0 && bid.getBidId() != bidId) {
				sb.append(BidMessages.BID_ID_INVALID);
			}
			if(bid.getProjectId() == 0) {
				sb.append(BidMessages.PROJECT_ID_INVALID);
			}
			sb.append(validateBidDetails(bid));
			String msg = sb.toString();
			if(!isEmpty(msg)) {
				LOGGER.log(Level.WARNING,msg);
				errorBean = new ErrorBean(ErrorCode.INVALID_REQUEST,msg);
			}
		}
		return errorBean;
	}
	
	/**
	 * validates bid details
	 * @param bid
	 * @return String
	 */
	private String validateBidDetails(Bid bid) {
		StringBuilder sb = new StringBuilder();
		if(isEmpty(bid.getBuyer())) {
			sb.append(BidMessages.BUYER_INVALID);
		}
		if(bid.getCurrency() == null || Currency.fromValue(bid.getCurrency()) == null) {
			sb.append(BidMessages.CURRENCY_INVALID);
		}
		if(bid.getBidAmount() <= 0) {
			sb.append(BidMessages.BID_AMOUNT_INVALID);
		}
		if(bid.getStatus() != null && BidStatus.fromValue(bid.getStatus()) == null) {
			sb.append(BidMessages.BID_STATUS_INVALID);
		}
		return sb.toString();
	}

	/**
	 * Checks if the string is null or empty
	 * @param inputStr
	 * @return boolean
	 */
	public static boolean isEmpty(String inputStr) {
		return (inputStr == null || inputStr.trim().length() == 0);
	}
}
