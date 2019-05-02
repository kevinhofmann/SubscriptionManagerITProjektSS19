package de.hdm.subscriptionManager.server;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.subscriptionManager.server.db.SubscriptionGroupMapper;
import de.hdm.subscriptionManager.server.db.SubscriptionMapper;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdmin;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.User;

@SuppressWarnings("serial")
public class SubscriptionManagerImpl extends RemoteServiceServlet implements SubscriptionManagerAdmin {

    
    private SubscriptionMapper subscriptionMapper = null;
    
    private SubscriptionGroupMapper subscriptionGroupMapper = null;
    
    
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
    public Subscription createSubscription(String name, float price, String note, Date startMonth, Boolean cancellationRelevance,
	    int userID) throws IllegalArgumentException {
	Subscription sub = new Subscription();
	sub.setName(name);
	sub.setPrice(price);
	sub.setNote(note);
	sub.setStartMonth(startMonth);
	sub.setCancellationRelevance(cancellationRelevance);
	sub.setUserID(userID);
	return this.subscriptionMapper.createSubscription(sub);
    }

    @Override
    public SubscriptionGroup createSubscriptionGroup(String name, int userID) throws IllegalArgumentException {
	SubscriptionGroup subgroup = new SubscriptionGroup();
	subgroup.setName(name);
	subgroup.setUserID(userID);
	return this.subscriptionGroupMapper.createSubscriptionGroup(subgroup);
    }

    @Override
    public void updateSubscription(Subscription s) throws IllegalArgumentException {
	subscriptionMapper.updateSubscription(s);
    }

    @Override
    public void updateSubscriptionGroup(SubscriptionGroup sg) throws IllegalArgumentException {
	subscriptionGroupMapper.updateSubscriptionGroup(sg);
	
    }

    @Override
    public void deleteSubscription(Subscription s) throws IllegalArgumentException {
	subscriptionMapper.deleteSubscription(s);
	
    }

    @Override
    public void deleteSubscriptionGroup(SubscriptionGroup sg) throws IllegalArgumentException {
	subscriptionGroupMapper.deleteSubscriptionGroup(sg);
	
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
