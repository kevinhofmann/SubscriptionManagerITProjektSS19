package de.hdm.subscriptionManager.shared.bo;

import java.sql.Date;

public class Cancellation extends BusinessObject {

    /**
     * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
     */
    private static final long serialVersionUID = 1L;

    private Date expirationDate = null;
    
    private int cancellationPeriod = 0;

    private int subscriptionID = 0;

    /**
     * @return the dateOfNotice
     */
    public Date getExpirationDate() {
	return expirationDate;
    }

    /**
     * @param dateOfNotice the dateOfNotice to set
     */
    public void setExpirationDate(Date expirationDate) {
	this.expirationDate = expirationDate;
    }
    
    /**
     * @return the cancellationPeriod
     */
    public int getCancellationPeriod() {
	return cancellationPeriod;
    }

    /**
     * @param cancellationPeriod the cancellationPeriod to set
     */
    public void setCancellationPeriod(int cancellationPeriod) {
	this.cancellationPeriod = cancellationPeriod;
    }

    public int getSubscriptionID() {
	return subscriptionID;
    }

    public void setSubscriptionID(int subscriptionID) {
	this.subscriptionID = subscriptionID;
    }

}
