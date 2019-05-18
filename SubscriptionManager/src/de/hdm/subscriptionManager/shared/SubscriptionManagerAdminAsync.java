package de.hdm.subscriptionManager.shared;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.subscriptionManager.shared.bo.Cancellation;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.User;

public interface SubscriptionManagerAdminAsync {

    void init(AsyncCallback<Void> callback);

    void createUser(String firstName, String lastName, String mail, AsyncCallback<User> callback);

    void deleteSubscription(Subscription s, AsyncCallback<Void> callback);

    void updateSubscriptionGroup(SubscriptionGroup sg, AsyncCallback<Void> callback);

    void createSubscriptionGroup(String name, int userID, AsyncCallback<SubscriptionGroup> callback);

    void updateSubscription(Subscription s, AsyncCallback<Void> callback);

    void createSubscription(String name, float price, String note, java.util.Date startDate, Boolean cancellationRelevance, int userID,
	    AsyncCallback<Subscription> callback);
    
    void getCancellationBySubscriptionId(int subscriptionID, AsyncCallback<Cancellation> callback) throws IllegalArgumentException;

    void createCancellation(Date expirationDate, Date cancellationDate, int cancellationPeriod, int subscriptionID, AsyncCallback<Cancellation> callback);
    
    void deleteSubscriptionGroup(SubscriptionGroup sg, AsyncCallback<Void> callback);

    void addSubscriptionToGroup(Subscription s, SubscriptionGroup sg, AsyncCallback<Void> callback);

    void getAllSubscriptions(int userId, AsyncCallback<ArrayList<Subscription>> callback);

    void removeSubscriptionFromGroup(Subscription s, SubscriptionGroup sg, AsyncCallback<Void> callback);

    void getAllSubscriptionGroups(int userId, AsyncCallback<ArrayList<SubscriptionGroup>> callback);

    void getAllSubscriptionsWithinGroup(SubscriptionGroup sg, AsyncCallback<ArrayList<Subscription>> callback);
}
