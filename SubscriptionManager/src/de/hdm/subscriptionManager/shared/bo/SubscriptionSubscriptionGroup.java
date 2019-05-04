package de.hdm.subscriptionManager.shared.bo;

public class SubscriptionSubscriptionGroup extends BusinessObject {
    
    /**
	 * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Fremdschlüsselbeziehung zur Subscription 
	 */
	private int subscriptionID = 0;
	
	/**
	 * Fremdschlüsselbeziehung zum SubscriptionGroup 
	 */
	private int subscriptionGroupID = 0;
	
	private int userID = 0;

	/**
	 * Auslesen der Fremdschlüsselbeziehung zur Kontaktliste 
	 * @return kontaktlisteID
	 */
	public int getSubscriptionGroupID() {
		return subscriptionGroupID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zur Kontaktliste 
	 * @param kontaktlisteID
	 */
	public void setSubscriptionGroupID(int subscriptionGroupID) {
		this.subscriptionGroupID = subscriptionGroupID;
	}

	/**
	 * Auslesen der Fremdschlüsselbeziehung zum Kontakt 
	 * @return kontaktID
	 */
	public int getSubscriptionID() {
		return subscriptionID;
	}

	/**
	 * Setzen der Fremdschlüsselbeziehung zum Kontakt 
	 * @param kontaktID
	 */
	public void setSubscriptionID(int subscriptionID) {
		this.subscriptionID = subscriptionID;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
	    return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
	    this.userID = userID;
	}
	
	
}