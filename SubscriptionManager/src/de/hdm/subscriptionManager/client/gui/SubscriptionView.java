package de.hdm.subscriptionManager.client.gui;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
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

public class SubscriptionView extends VerticalPanel {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private FlexTable subscriptionViewTable = new FlexTable();
    private VerticalPanel subscriptionViewPanel = new VerticalPanel();
    private Subscription subscriptionToDisplay = null;
    private Cancellation cancellationInfo = null;

    private int userId;

    private Label name = new Label();

    private Label price = new Label();

    private Label note = new Label();

    private Label startDate = new Label();
    
    private Label expenses = new Label();

    private Label cancellationRelevance = new Label();

    private Label expirationDate = new Label();

    private Label cancellationPeriod = new Label();

    private Label cancellationDate = new Label();

    private HTML headline= new HTML("Ein neues Abo anlegen<br>");
    private Label nameLabel = new Label("Aboname");
    private Label priceLabel = new Label("Monatspreis");
    private Label expensesLabel = new Label("Gesamtausgaben");
    private Label noteLabel = new Label("Anmerkung");
    private Label startDateLabel = new Label("Startdatum");
    private Label expirationDateLabel = new Label("Auslaufdatum");
    private Label cancellationRelevanceLabel = new Label("Kündigungsrelevant");
    private Label cancellationPeriodLabel = new Label("Kündigungsfrist (MM)");
    private Label cancellationDateLabel = new Label("Kündigungstag");

    private Date today = new Date();
    private DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd.MM.yyyy");

    public SubscriptionView() {

    }

    public SubscriptionView(Subscription subscription) {
	this.subscriptionToDisplay = subscription;
	name.setText(subscriptionToDisplay.getName());
	price.setText(Float.toString(subscription.getPrice()) + " €");
	note.setText(subscription.getNote());
	startDate.setText(dateTimeFormat.format(subscription.getStartDate()));

	if(subscriptionToDisplay.getCancellationRelevance()) {
	    cancellationRelevance.setText("Ja");
	    subscriptionManagerAdmin.getCancellationBySubscriptionId(subscription.getId(), new GetCancellationBySubscriptionCallback());
	} else {
	    cancellationRelevance.setText("Nein");
	}
	calculateCosts(subscription);
    }

    protected void onLoad() {

	subscriptionViewTable.setWidget(0, 0, nameLabel);
	subscriptionViewTable.setWidget(1, 0, priceLabel);
	subscriptionViewTable.setWidget(2, 0, expensesLabel);
	subscriptionViewTable.setWidget(3, 0, noteLabel);
	subscriptionViewTable.setWidget(4, 0, startDateLabel);
	subscriptionViewTable.setWidget(5, 0, cancellationRelevanceLabel);


	subscriptionViewTable.setWidget(0, 1, name);
	subscriptionViewTable.setWidget(1, 1, price);
	subscriptionViewTable.setWidget(2, 1, expenses);
	subscriptionViewTable.setWidget(3, 1, note);
	subscriptionViewTable.setWidget(4, 1, startDate);
	subscriptionViewTable.setWidget(5, 1, cancellationRelevance);

	if(subscriptionToDisplay.getCancellationRelevance()) {
	    subscriptionViewTable.setWidget(6, 0, new Label(" "));
	    
	    subscriptionViewTable.setWidget(7, 0, expirationDateLabel);
	    subscriptionViewTable.setWidget(8, 0, cancellationPeriodLabel);
	    subscriptionViewTable.setWidget(9, 0, cancellationDateLabel);
	    subscriptionViewTable.setWidget(7, 1, expirationDate);
	    subscriptionViewTable.setWidget(8, 1, cancellationPeriod);
	    subscriptionViewTable.setWidget(9, 1, cancellationDate);
	}

	name.setStylePrimaryName("subscriptionViewTableFont");
	name.setStylePrimaryName("subscriptionViewTableFont");
	subscriptionViewTable.getFlexCellFormatter().setHeight(6, 0, "20px");
	subscriptionViewPanel.add(subscriptionViewTable);
	subscriptionViewTable.setStylePrimaryName("subscriptionViewTable");
	subscriptionViewTable.getColumnFormatter().setWidth(0, "220px");
	RootPanel.get("content").clear();
	RootPanel.get("content").add(subscriptionViewPanel);
    }
    
	public void calculateCosts(Subscription subscription) {
	    subscription.setExpensesSinceStart((subscription.getPrice() / 30) * (Math.abs(CalendarUtil.getDaysBetween(subscription.getStartDate(), today))));
	    expenses.setText(Math.round(100.0 * subscription.getExpensesSinceStart()) / 100.0 + " €");
	}

    public class GetCancellationBySubscriptionCallback implements AsyncCallback<Cancellation> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(Cancellation result) {
	    cancellationInfo = result;
	    expirationDate.setText(dateTimeFormat.format(cancellationInfo.getExpirationDate()));
	    cancellationPeriod.setText(Integer.toString(cancellationInfo.getCancellationPeriod()));
	    cancellationDate.setText(dateTimeFormat.format(cancellationInfo.getCancellationDate()));

	}

    }

}
