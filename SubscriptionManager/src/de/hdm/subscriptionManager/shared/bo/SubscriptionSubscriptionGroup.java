package de.hdm.subscriptionManager.shared.bo;

public class SubscriptionSubscriptionGroup extends BusinessObject {
    
    /**
	 * Dient zum Serialisieren von Objekten f�r eine RPC f�higen Austausch zwischen Server und Client.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Fremdschl�sselbeziehung zur Subscription 
	 */
	private int subscriptionID = 0;
	
	/**
	 * Fremdschl�sselbeziehung zum SubscriptionGroup 
	 */
	private int subscriptionGroupID = 0;
	
	private int userID = 0;

	/**
	 * Auslesen der Fremdschl�sselbeziehung zur Kontaktliste 
	 * @return kontaktlisteID
	 */
	public int getSubscriptionGroupID() {
		return subscriptionGroupID;
	}

	/**
	 * Setzen der Fremdschl�sselbeziehung zur Kontaktliste 
	 * @param kontaktlisteID
	 */
	public void setSubscriptionGroupID(int subscriptionGroupID) {
		this.subscriptionGroupID = subscriptionGroupID;
	}

	/**
	 * Auslesen der Fremdschl�sselbeziehung zum Kontakt 
	 * @return kontaktID
	 */
	public int getSubscriptionID() {
		return subscriptionID;
	}

	/**
	 * Setzen der Fremdschl�sselbeziehung zum Kontakt 
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