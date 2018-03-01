/**
 * 
 */
package com.example.marketplace;

/**
 * @author srikanthgummula
 * 
 * <p>Enum class for Project Status</p>
 */
public enum ProjectStatus {
	/**
	 * Project open for bids
	 */
	OPEN("OPEN"),
	/**
	 * Project closed by owner
	 */
	CLOSED("CLOSED"),	
	/**
	 * Project completed with the selected bid
	 */
	COMPLETE("COMPLETE"),
	/**
	 * Project not completed with the selected bid
	 */
	INCOMPLETE("INCOMPLETE");
	
	private String status;
	
	ProjectStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return status;
	}
	
	/**
	 * Returns ProjectStatus from string value.
	 * @param str
	 * @return
	 */
	public static ProjectStatus fromValue(String str) {
		for(ProjectStatus projectStatus: ProjectStatus.values()) {
			if(projectStatus.toString().equalsIgnoreCase(str)) {
				return projectStatus;
			}
		}
		return null;
	}
	
}
