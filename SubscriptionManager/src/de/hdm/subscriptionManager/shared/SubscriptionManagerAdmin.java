package de.hdm.subscriptionManager.shared;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.User;

@RemoteServiceRelativePath("subscriptionManager")
public interface SubscriptionManagerAdmin extends RemoteService {
    
	/**
	 * Initialisierung des Objekts. Diese ist hauptsächlich
	 * zum No Argument Constructor der implementierenden Klasse notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;
	
	public User createUser(String mail) throws IllegalArgumentException;
	
	public Subscription createSubscription(String name,float price, String note, Date startMonth, Date endMonth, int userID) throws IllegalArgumentException;
	
	public SubscriptionGroup createSubscriptionGroup(String name, int userID) throws IllegalArgumentException;

	public void updateSubscription(Subscription s) throws IllegalArgumentException;
	
	public void updateSubscriptionGroup(SubscriptionGroup sg) throws IllegalArgumentException;
	
	public void deleteSubscription(Subscription s) throws IllegalArgumentException;
	
	public void deleteSubscriptionGroup(SubscriptionGroup sg) throws IllegalArgumentException;
	
	public void addSubscriptionToGroup(Subscription s, SubscriptionGroup sg) throws IllegalArgumentException;
	
	public void removeSubscriptionFromGroup(Subscription s, SubscriptionGroup sg) throws IllegalArgumentException;
	
	public ArrayList<Subscription> getAllSubscriptions(int userId) throws IllegalArgumentException;
	
	public ArrayList<SubscriptionGroup> getAllSubscriptionGroups(int userId) throws IllegalArgumentException;
	
	public ArrayList<Subscription> getAllSubscriptionsWithinGroup(SubscriptionGroup sg) throws IllegalArgumentException;
}
