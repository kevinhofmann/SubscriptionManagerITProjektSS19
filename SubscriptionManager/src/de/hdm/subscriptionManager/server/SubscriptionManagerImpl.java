package de.hdm.subscriptionManager.server;

import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

//import de.hdm.partnerboerse.shared.bo.Info;
//import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.subscriptionManager.server.db.CancellationMapper;
import de.hdm.subscriptionManager.server.db.SubscriptionGroupMapper;
import de.hdm.subscriptionManager.server.db.SubscriptionMapper;
import de.hdm.subscriptionManager.server.db.SubscriptionSubscriptionGroupMapper;
import de.hdm.subscriptionManager.server.db.UserMapper;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdmin;
import de.hdm.subscriptionManager.shared.bo.Cancellation;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.SubscriptionSubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.User;

@SuppressWarnings("serial")
public class SubscriptionManagerImpl extends RemoteServiceServlet implements SubscriptionManagerAdmin {

    Logger logger = ServersideSettings.getLogger();
    
    private UserMapper userMapper = null;
    
    private SubscriptionMapper subscriptionMapper = null;
    
    private SubscriptionGroupMapper subscriptionGroupMapper = null;
    
    private CancellationMapper cancellationMapper = null;
    
    private SubscriptionSubscriptionGroupMapper subscriptionSubscriptionGroupMapper = null;
    
    
    public SubscriptionManagerImpl () throws IllegalArgumentException {
	
    }
    
    public void init() throws IllegalArgumentException {
	this.userMapper = UserMapper.userMapper();
	this.subscriptionMapper = SubscriptionMapper.subscriptionMapper();
	this.subscriptionGroupMapper = SubscriptionGroupMapper.subGroupMapper();
	this.cancellationMapper = CancellationMapper.cancellationMapper();
	this.subscriptionSubscriptionGroupMapper = SubscriptionSubscriptionGroupMapper.subscriptionSubscriptionGroupMapper();
    }
    
    
    @Override
    public User createUser(String firstName, String lastName, String mail) throws IllegalArgumentException {
	User user = new User();
	user.setFirstName(firstName);
	user.setLastName(lastName);
	user.setMail(mail);
	return this.userMapper.createUser(user);
    }
    
    public User checkEmail(String eMail) throws IllegalArgumentException {
	User user = new User();
	user.setMail(eMail);
	return null;
	
    }

    @Override
    public Subscription createSubscription(String name, float price, String note, java.util.Date startDate, Boolean cancellationRelevance,
	    int userID) throws IllegalArgumentException {
	Subscription sub = new Subscription();
	sub.setName(name);
	sub.setPrice(price);
	sub.setNote(note);
	sub.setStartDate(startDate);
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
    public Cancellation createCancellation(Date expirationDate, Date cancellationDate, int cancellationPeriod, int subscriptionID) throws IllegalArgumentException {
	Cancellation can = new Cancellation();
	can.setExpirationDate(expirationDate);
	can.setCancellationDate(cancellationDate);
	can.setCancellationPeriod(cancellationPeriod);
	can.setSubscriptionID(subscriptionID);
	return this.cancellationMapper.createCancellation(can);
    }
    
    @Override
    public void updateCancellation(Cancellation cancellation) throws IllegalArgumentException {
	cancellationMapper.updateCancellation(cancellation);
    }
    
    @Override 
    public void deleteCancellation(Cancellation cancellation) throws IllegalArgumentException {
	cancellationMapper.deleteCancellation(cancellation);
    }
    
    @Override
    public Cancellation getCancellationBySubscriptionId(int subscriptionID) throws IllegalArgumentException {
	return this.cancellationMapper.getCancellationBySubscriptionId(subscriptionID);
    }
    
    @Override
    public ArrayList<Cancellation> getAllCancellationInfoByUserId(int userId) throws IllegalArgumentException {
	return this.cancellationMapper.getAllCancellationInfoByUserId(userId);
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

    /**
	 * Aktualisiert eine bearbeitete SubscriptionGroup
	 * @param suchprofil
	 */
    @Override
    public SubscriptionSubscriptionGroup addSubscriptionToGroup(Subscription s, SubscriptionGroup sg) throws IllegalArgumentException {
	SubscriptionSubscriptionGroup sSG = new SubscriptionSubscriptionGroup();
	sSG.setSubscriptionGroupID(sg.getId());
	sSG.setSubscriptionID(s.getId());
	sSG.setUserID(s.getUserID());
	return this.subscriptionSubscriptionGroupMapper.createSubscriptionSubscriptionGroup(sSG);
    }

    @Override
    public void removeSubscriptionFromGroup(Subscription s, SubscriptionGroup sg) throws IllegalArgumentException {
	SubscriptionSubscriptionGroup sSG = new SubscriptionSubscriptionGroup();
	sSG.setSubscriptionGroupID(sg.getId());
	sSG.setSubscriptionID(s.getId());
	sSG.setUserID(s.getUserID());
	subscriptionSubscriptionGroupMapper.removeSubscriptionSubscriptionGroup(sSG);

    }

    @Override
    public Subscription getSubscriptionBySubscriptionID(int subscriptionID) throws IllegalArgumentException {
	return this.subscriptionMapper.getSubscriptionBySubscriptionID(subscriptionID);
    }
    
    @Override
    public ArrayList<Subscription> getAllSubscriptions(int userId) throws IllegalArgumentException {

 	return this.subscriptionMapper.getAllSubscriptions(userId);
    }

    @Override
    public ArrayList<SubscriptionGroup> getAllSubscriptionGroups(int userId) throws IllegalArgumentException {

    	return this.subscriptionGroupMapper.findAll(userId);
    }

    @Override
    public ArrayList<Subscription> getAllSubscriptionsWithinGroup(int groupId) throws IllegalArgumentException {
	
	return this.subscriptionMapper.getAllSubscriptionsWithinGroup(groupId);
    }
    
    @Override
    public SubscriptionSubscriptionGroup checkSubscriptionToGroupBelonging(int subscriptionId, int groupId) throws IllegalArgumentException {
	
	return this.subscriptionSubscriptionGroupMapper.checkSubscriptionBelonging(subscriptionId, groupId);
    }

}
