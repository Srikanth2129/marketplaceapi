/**
 * 
 */
package com.example.marketplace;

/**
 * @author srikanthgummula
 * 
 * <p>Enum class for currencies list</p>
 */
public enum Currency {
	/**
	 * United Arab Emirates dirham
	 */
	AED("AED"),
	/**
	 * Australian dollar
	 */
	AUD("AUD"),
	/**
	 * Canadian dollar
	 */
	CAD("CAD"),
	/**
	 * British pound
	 */
	GBP("GBP"),
	/**
	 * Indian rupee
	 */
	INR("INR"),
	/**
	 * Japanese yen
	 */
	JPY("JPY"),
	/**
	 * Singapore dollar
	 */
	SGD("SGD"),
	/**
	 * United States dollar
	 */
	USD("USD");
	
	private String currency;
	
	Currency(String currency) {
		this.currency = currency;
	}
	
	public String toString() {
		return currency;
	}
	
	/**
	 * Returns Currency from string value.
	 * @param str
	 * @return
	 */
	public static Currency fromValue(String str) {
		for(Currency currency: Currency.values()) {
			if(currency.toString().equalsIgnoreCase(str)) {
				return currency;
			}
		}
		return null;
	}
}
