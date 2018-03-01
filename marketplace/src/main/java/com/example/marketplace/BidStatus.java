/**
 * 
 */
package com.example.marketplace;

/**
 * @author srikanthgummula
 *
 * <p>Enum class for Bid Status</p>
 */
public enum BidStatus {
	/**
	 * No action on bid 
	 */
	OPEN("OPEN"),
	/**
	 * Bid accepted by bid owner
	 */
	ACCEPTED("ACCEPTED"),
	/**
	 * Bid denied by bid owner
	 */
	DENIED("DENIED"),
	/**
	 * Bid selected by project owner
	 */
	SELECTED("SELECTED");
	
	private String status;
	
	BidStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return status;
	}
	
	/**
	 * Returns BidStatus from string value.
	 * @param str
	 * @return
	 */
	public static BidStatus fromValue(String str) {
		for(BidStatus bidStatus: BidStatus.values()) {
			if(bidStatus.toString().equalsIgnoreCase(str)) {
				return bidStatus;
			}
		}
		return null;
	}
	
}
