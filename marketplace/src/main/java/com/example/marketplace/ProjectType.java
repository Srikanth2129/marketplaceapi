/**
 * 
 */
package com.example.marketplace;

/**
 * @author srikanthgummula
 * 
 * <p>Enum class for Project Type</p>
 */
public enum ProjectType {
	/**
	 * Project type for Fixed Bid
	 */
	FIXED("FIXED"),
	/**
	 * Project type for Hourly Bid
	 */
	HOURLY("HOURLY");
	
	private String type;
	
	ProjectType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
	
	/**
	 * Returns ProjectType from string value.
	 * @param str
	 * @return
	 */
	public static ProjectType fromValue(String str) {
		for(ProjectType projectType: ProjectType.values()) {
			if(projectType.toString().equalsIgnoreCase(str)) {
				return projectType;
			}
		}
		return null;
	}
}
