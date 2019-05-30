package de.hdm.subscriptionManager.client.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;

public class SubscriptionAndGroupOverview extends VerticalPanel {


    private VerticalPanel vPanel = new VerticalPanel();
    private ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();
    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();
    private Double sumOfExpenses = 0.0;
    private Label headline = new Label("Kostenübersicht");
    private FlexTable overviewFlexTable = new FlexTable();
    private HTML expensesLabel = new HTML();


    public SubscriptionAndGroupOverview(Subscription subscription) {
	subscriptionManagerAdmin.getAllSubscriptions(1, new GetAllSubscriptionsCallback());

    }
    
    public void constructOverviewTable() {

	expensesLabel = new HTML("Für deine Abos sind bisher Kosten von insgesamt " + sumOfExpenses + " € entstanden.");
	overviewFlexTable.setWidget(0, 0, headline);
	overviewFlexTable.setWidget(2, 0, new Label("Das teuerste Abo: "));
	overviewFlexTable.setWidget(2, 1, new Label(getMostExpensiveSubscription(subscriptionArrayList).getName()));
	overviewFlexTable.setWidget(3, 0, new Label("Das günstigste Abo: "));
	overviewFlexTable.setWidget(3, 1, new Label(getCheapestSubscription(subscriptionArrayList).getName()));
	
	vPanel.add(expensesLabel);
	vPanel.add(overviewFlexTable);
	RootPanel.get("content").clear();
	RootPanel.get("content").add(vPanel);
    }

    class GetAllSubscriptionsCallback implements AsyncCallback<ArrayList<Subscription>> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(ArrayList<Subscription> result) {
	    for(Subscription sub : result) {
		subscriptionArrayList.add(sub);
	    }
	    calculateCosts(subscriptionArrayList);
	    constructOverviewTable();
	}

	public Double calculateCosts(ArrayList<Subscription> subscriptionArrayList) {
	    Date today = new Date();

	    for(Subscription sub : subscriptionArrayList) {
		Date subscriptionStartDate = sub.getStartDate();
		long diff = today.getTime() - subscriptionStartDate.getTime();
		long numberOfDays = diff / (1000*60*60*24);
		double dailyPrice = 0.0;
		dailyPrice = sub.getPrice() / 30;
		sub.setExpensesSinceStart(dailyPrice * numberOfDays);
		sumOfExpenses += dailyPrice * numberOfDays;
	    }
	    sumOfExpenses = Math.round(100.0 * sumOfExpenses) / 100.0; 
	    return sumOfExpenses;
	}
    }
    
    public Subscription getMostExpensiveSubscription(ArrayList<Subscription> subscriptionArrayList) {
	   double max = 0.0;
	   int index = 0;
	for(Subscription sub : subscriptionArrayList) {
	    for(int i = 0; i<subscriptionArrayList.size(); i++) {
		if(subscriptionArrayList.get(i).getExpensesSinceStart() > max) {
		    max = subscriptionArrayList.get(i).getExpensesSinceStart();
		    index = i;
		}
	    }
	}
	return subscriptionArrayList.get(index);
    }
    
    public Subscription getCheapestSubscription(ArrayList<Subscription> subscriptionArrayList) {
	   double min = 1000.0;
	   int index = 0;
	for(Subscription sub : subscriptionArrayList) {
	    for(int i = 0; i<subscriptionArrayList.size(); i++) {
		if(subscriptionArrayList.get(i).getExpensesSinceStart() < min) {
		    min = subscriptionArrayList.get(i).getExpensesSinceStart();
		    index = i;
		}
	    }
	}
	return subscriptionArrayList.get(index);
    }
}
