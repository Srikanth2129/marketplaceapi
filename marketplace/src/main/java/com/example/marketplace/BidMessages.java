/**
 * 
 */
package com.example.marketplace;

/**
 * @author srikanthgummula
 *
 * <p>Contains all the messages for Bid resource</p>
 */
public interface BidMessages {

	public static final String BID_REQUEST_INVALID = "Bid request is invalid.";
	public static final String CREATE_BID_ID_INVALID = "Cannot contain bidId.";
	public static final String BID_ID_INVALID = "Bid Id is invalid.";
	public static final String PROJECT_ID_INVALID = "Project Id is invalid.";
	public static final String BUYER_INVALID = "Invalid buyer.";
	public static final String CURRENCY_INVALID = "Invalid Currency.";
	public static final String BID_AMOUNT_INVALID = "Bid Amount should be greater than zero.";
	public static final String BID_STATUS_INVALID = "Bid status is invalid.";
	public static final String BID_DATE_INVALID = "Bid date is after is Project last date.";
	
	public static final String PROCESSING_ERROR = "Error encountered while processing the request.";
	public static final String PROJECT_NOT_FOUND = "Requested project does not exist.";
	public static final String BID_NOT_FOUND = "Requested bid does not exsit.";
	
}
