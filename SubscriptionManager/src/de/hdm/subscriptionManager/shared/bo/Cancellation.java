package de.hdm.subscriptionManager.shared.bo;

import java.sql.Date;

public class Cancellation extends BusinessObject {

    /**
     * Dient zum Serialisieren von Objekten f�r eine RPC f�higen Austausch zwischen Server und Client.
     */
    private static final long serialVersionUID = 1L;
    
    private int cancellationID = 0;

    private Date expirationDate = null;
    
    private Date cancellationDate = null;
    
    private int cancellationPeriod = 0;

    private int subscriptionID = 0;

    /**
     * @return Auslaufdatum des Abos
     */
    public Date getExpirationDate() {
	return expirationDate;
    }

    /**
     * @param Auslaufdatum des Abos
     */
    public void setExpirationDate(Date expirationDate) {
	this.expirationDate = expirationDate;
    }
    
    /**
     * @return des K�ndigungsdatums (Tag an dem gek�ndigt sein muss)
     */
    public Date getCancellationDate() {
	return cancellationDate;
    }

    /**
     * @param Setzen des K�ndigungsdatums (Tag an dem gek�ndigt sein muss)
     */
    public void setCancellationDate(Date cancellationDate) {
	this.cancellationDate = cancellationDate;
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

    /**
     * @return the cancellationID
     */
    public int getCancellationID() {
	return cancellationID;
    }

    /**
     * @param cancellationID the cancellationID to set
     */
    public void setCancellationID(int cancellationID) {
	this.cancellationID = cancellationID;
    }


}
