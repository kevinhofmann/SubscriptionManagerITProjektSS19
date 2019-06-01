package de.hdm.subscriptionManager.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Cancellation;
import de.hdm.subscriptionManager.shared.bo.Subscription;

public class SubscriptionAndGroupOverview extends VerticalPanel {


    private VerticalPanel vPanel = new VerticalPanel();
    private ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();
    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();
    private Double sumOfExpenses = 0.0;
    private Label headline = new Label("Kostenübersicht");
    private FlexTable overviewFlexTable = new FlexTable();
    private FlexTable cancellationOverviewFlexTable = new FlexTable();
    private HTML expensesLabel = new HTML();
    private HTML cancellationLabel = new HTML();
    private ArrayList<Cancellation> cancellationInfoArrayList = new ArrayList<>();
    
    private Date today = new Date();
    private ArrayList<Subscription> sortedSubscriptionList = new ArrayList<>();

    public SubscriptionAndGroupOverview(Subscription subscription) {

	subscriptionManagerAdmin.getAllSubscriptions(1, new GetAllSubscriptionsCallback());
    }

    public void constructInfoTable() {


	expensesLabel = new HTML("Für deine Abos sind bisher Kosten von insgesamt " + sumOfExpenses + " € entstanden.");
	overviewFlexTable.setWidget(0, 0, headline);
	overviewFlexTable.setWidget(2, 0, new Label("Das teuerste Abo: "));
	overviewFlexTable.setWidget(2, 1, new Label(getMostExpensiveSubscription(subscriptionArrayList).getName()));
	overviewFlexTable.setWidget(3, 0, new Label("Das günstigste Abo: "));
	overviewFlexTable.setWidget(3, 1, new Label(getCheapestSubscription(subscriptionArrayList).getName()));

	int row = 0;
	for(int i = 0; i<cancellationInfoArrayList.size(); i++) {
	    int column = 0;
	    cancellationOverviewFlexTable.setWidget(row, column, new Label(sortedSubscriptionList.get(i).getName()));
	    cancellationOverviewFlexTable.setWidget(row, column+1, new Label(Long.toString(cancellationInfoArrayList.get(i).getDaysRemainingCancellationDate())));
	    row++;
	}
	cancellationLabel = new HTML("Dein Abo " + sortedSubscriptionList.get(0).getName() + " muss bis in " + cancellationInfoArrayList.get(0).getDaysRemainingCancellationDate() + " Tagen gekündigt sein.");
	vPanel.add(expensesLabel);
	vPanel.add(overviewFlexTable);
	vPanel.add(cancellationLabel);
	vPanel.add(cancellationOverviewFlexTable);
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
	    subscriptionManagerAdmin.getAllCancellationInfoByUserId(1, new CancellationCallback());
	    for(Subscription sub : result) {
		subscriptionArrayList.add(sub);
	    }
	    calculateCosts(subscriptionArrayList);
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
    

    public void calculateDaysUntilCancellation() {

	for(Cancellation can : cancellationInfoArrayList) {
	    can.setDaysRemainingCancellationDate(Math.abs(CalendarUtil.getDaysBetween(can.getCancellationDate(), today)));
	}
	
	Collections.sort(cancellationInfoArrayList, new Comparator<Cancellation>() {
	    @Override
	    public int compare(Cancellation o1, Cancellation o2) {
		return (int) (o1.getDaysRemainingCancellationDate() - o2.getDaysRemainingCancellationDate());
	    }	    
	});
	
	
	for(Cancellation can : cancellationInfoArrayList) {
		    subscriptionManagerAdmin.getSubscriptionBySubscriptionID(can.getSubscriptionID(), new GetSubscriptionBySubscriptionIDCallback());
		}
	
	}
    

    class GetSubscriptionBySubscriptionIDCallback implements AsyncCallback<Subscription> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void onSuccess(Subscription result) {
	    sortedSubscriptionList.add(result);
	    if(sortedSubscriptionList.size() == cancellationInfoArrayList.size()) {
		    constructInfoTable();
	    }
	}
    }

    
    class CancellationCallback implements AsyncCallback<ArrayList<Cancellation>> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void onSuccess(ArrayList<Cancellation> result) {

	    for(Cancellation can : result) {		
		cancellationInfoArrayList.add(can); 
	    }
	    calculateDaysUntilCancellation();
	}
    }
}






