package de.hdm.subscriptionManager.shared.bo;

import java.sql.Date;

public class Cancellation extends BusinessObject {

    /**
     * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
     */
    private static final long serialVersionUID = 1L;

    private Date dateOfNotice = null;
    
    private int cancellationPeriod = 0;


    /**
     * @return the dateOfNotice
     */
    public Date getDateOfNotice() {
	return dateOfNotice;
    }

    /**
     * @param dateOfNotice the dateOfNotice to set
     */
    public void setDateOfNotice(Date dateOfNotice) {
	this.dateOfNotice = dateOfNotice;
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

}
