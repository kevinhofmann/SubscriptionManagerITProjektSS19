package de.hdm.subscriptionManager.shared.bo;

import java.sql.Date;

public class Subscription extends BusinessObject{

    /**
     * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
     */
    private static final long serialVersionUID = 1L;


    /** Attribute einer einzelnen Subscription werden definiert und die
     * getter setter Methoden dazu
     */
    private String name = "";

    private float price = 0;

    private String note = "";

    private Date startMonth = null;

    private Date endMonth = null;
    
    private int userID = 0;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public float getPrice() {
	return price;
    }

    public void setPrice(float price) {
	this.price = price;
    }

    public String getNote() {
	return note;
    }

    public void setNote(String note) {
	this.note = note;
    }

    public Date getStartMonth() {
	return startMonth;
    }

    public void setStartMonth(Date startMonth) {
	this.startMonth = startMonth;
    }

    public Date getEndMonth() {
	return endMonth;
    }

    public void setEndMonth(Date endMonth) {
	this.endMonth = endMonth;
    }


    /* TODO: Methode muss noch geschrieben werden, Differenz zwischen Start und Endmonat berechnen
    und die Anzahl dann mit dem Preis multiplizieren */

    public double getTotalSubscriptionExpenses() {
	if(this.price > 0) {
	    return price * 10;
	};
	return 0;
    }

    public int getUserID() {
	return userID;
    }
    
    public void setUserID(int userID) {
	this.userID = userID;
    }

}
