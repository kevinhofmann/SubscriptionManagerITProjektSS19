package de.hdm.subscriptionManager.server;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.subscriptionManager.shared.SubscriptionManagerAdmin;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.User;

@SuppressWarnings("serial")
public class SubscriptionManagerImpl extends RemoteServiceServlet implements SubscriptionManagerAdmin {

    
    public SubscriptionManagerImpl () throws IllegalArgumentException {
	
    }
    
    public void init() throws IllegalArgumentException {
	
    }
    
    
    @Override
    public User createUser(String mail) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Subscription createSubscription(String name, float price, String note, Date startMonth, Date endMonth,
	    int userID) throws IllegalArgumentException {
	Subscription sub = new Subscription();
	sub.setName(name);
	sub.setPrice(price);
	sub.setNote(note);
	sub.setStartMonth(startMonth);
	sub.setEndMonth(endMonth);
	sub.setUserID(userID);
	//MAPPER AUFRUFEN
	return null;
    }

    @Override
    public SubscriptionGroup createSubscriptionGroup(String name, int userID) throws IllegalArgumentException {
	SubscriptionGroup subgroup = new SubscriptionGroup();
	subgroup.setName(name);
	subgroup.setUserID(userID);
	//MAPPER
	return null;
    }

    @Override
    public void updateSubscription(Subscription s) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void updateSubscriptionGroup(SubscriptionGroup sg) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void deleteSubscription(Subscription s) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void deleteSubscriptionGroup(SubscriptionGroup sg) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void addSubscriptionToGroup(Subscription s, SubscriptionGroup sg) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void removeSubscriptionFromGroup(Subscription s, SubscriptionGroup sg) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
    }

    @Override
    public ArrayList<Subscription> getAllSubscriptions(int userId) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ArrayList<SubscriptionGroup> getAllSubscriptionGroups(int userId) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ArrayList<Subscription> getAllSubscriptionsWithinGroup(SubscriptionGroup sg)
	    throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
    }

}
