package de.hdm.subscriptionManager.shared.bo;

import java.util.ArrayList;

public class SubscriptionGroup extends BusinessObject {

    /**
     * Dient zum Serialisieren von Objekten f�r eine RPC f�higen Austausch zwischen Server und Client.
     */
    private static final long serialVersionUID = 1L;

    private String name = "";
    
    private int userID = 0;

    // ArrayList welche die einzelnen Subscriptions einer Gruppe enth�lt
    private ArrayList<Subscription> subscriptionArrayList = null;


    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    // Alle in der ArrayList enthaltenen Subscriptions werden zur�ckgegeben
    public ArrayList<Subscription> getAllSubcriptionsOfGroup() {
	return subscriptionArrayList;
    }

    /* Gesamtausgaben einer SubscriptionGruppe berechnen, indem f�r jede einzelne
     * Subscription innerhalb des Array die Funktion getTotalSubscriptionExpeneses
     * in der Subscription Klasse aufgerufen wird, welche die Kosten f�r ein einzelnes
     * Abo berechnet. Diese Kosten pro Abo werden dann durch die Variable amount
     * zusammenaddiert um die Gesamtkosten zur�ckzugeben
     */
    public float getTotalGroupExpenses() {
	float amount = 0;
	if(getAllSubcriptionsOfGroup().size() > 0) {
	    for(Subscription subscription : subscriptionArrayList) {
		amount += subscription.getTotalSubscriptionExpenses();
	    }
	}
	return amount;
    }
    
    public int getUserID() {
	return userID;
    }
    
    public void setUserID(int userID) {
	this.userID = userID;
    }
}
