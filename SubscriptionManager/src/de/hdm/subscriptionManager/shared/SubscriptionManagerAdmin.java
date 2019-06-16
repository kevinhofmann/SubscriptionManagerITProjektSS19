package de.hdm.subscriptionManager.shared;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.subscriptionManager.shared.bo.Cancellation;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.SubscriptionSubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.User;


@RemoteServiceRelativePath("subscriptionmanagerservice")
public interface SubscriptionManagerAdmin extends RemoteService {
    
	/**
	 * Initialisierung des Objekts. Diese ist haupts�chlich
	 * zum No Argument Constructor der implementierenden Klasse notwendig.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;
	
	public User createUser(String firstName, String lastName, String mail) throws IllegalArgumentException;
	
	public User checkEmail(String eMail) throws IllegalArgumentException;
	
	public Subscription createSubscription(String name,float price, String note, java.util.Date startDate, Boolean cancellationRelevance, int userID) throws IllegalArgumentException;
	
	public SubscriptionGroup createSubscriptionGroup(String name, int userID) throws IllegalArgumentException;

	public Cancellation createCancellation(Date expirationDate, Date cancellationDate, int cancellationPeriod, int subscriptionID) throws IllegalArgumentException;
	
	public void updateCancellation(Cancellation cancellation) throws IllegalArgumentException;
	
	public void deleteCancellation(Cancellation cancellation) throws IllegalArgumentException;
	
	public Cancellation getCancellationBySubscriptionId(int subscriptionID) throws IllegalArgumentException;
	
	public ArrayList<Cancellation> getAllCancellationInfoByUserId(int userId) throws IllegalArgumentException;
	
	public void updateSubscription(Subscription s) throws IllegalArgumentException;
	
	public void updateSubscriptionGroup(SubscriptionGroup sg) throws IllegalArgumentException;
	
	public void deleteSubscription(Subscription s) throws IllegalArgumentException;
	
	public void deleteSubscriptionGroup(SubscriptionGroup sg) throws IllegalArgumentException;
	
	public SubscriptionSubscriptionGroup addSubscriptionToGroup(Subscription s, SubscriptionGroup sg) throws IllegalArgumentException;
	
	public void removeSubscriptionFromGroup(Subscription s, SubscriptionGroup sg) throws IllegalArgumentException;
	
	public Subscription getSubscriptionBySubscriptionID(int subscriptionID) throws IllegalArgumentException;
	
	public ArrayList<Subscription> getAllSubscriptions(int userId) throws IllegalArgumentException;
	
	public ArrayList<SubscriptionGroup> getAllSubscriptionGroups(int userId) throws IllegalArgumentException;
	
	public ArrayList<Subscription> getAllSubscriptionsWithinGroup(int groupId) throws IllegalArgumentException;

	public SubscriptionSubscriptionGroup checkSubscriptionToGroupBelonging(int subscriptionId, int groupId) throws IllegalArgumentException;
}

