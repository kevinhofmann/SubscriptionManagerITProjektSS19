package de.hdm.subscriptionManager.client.gui;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
    
    private Label cancellationRelevance = new Label();
    
    private Label expirationDate = new Label();
    
    private Label cancellationPeriod = new Label();
    
    private Label cancellationDate = new Label();
    
    private HTML headline= new HTML("Ein neues Abo anlegen<br>");
    private Label nameLabel = new Label("Aboname");
    private Label priceLabel = new Label("Monatspreis");
    private Label noteLabel = new Label("Anmerkung");
    private Label startDateLabel = new Label("Startdatum");
    private Label expirationDateLabel = new Label("Auslaufdatum");
    private Label cancellationRelevanceLabel = new Label("Kuendigungsrelevant");
    private Label cancellationPeriodLabel = new Label("Kuendigungsfrist (MM)");
    private Label cancellationDateLabel = new Label("Kuendigungstag");
    
    private DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
    
    public SubscriptionView() {
	
    }
    
    public SubscriptionView(Subscription subscription) {
	this.subscriptionToDisplay = subscription;
	name.setText(subscriptionToDisplay.getNote());
	price.setText(Float.toString(subscription.getPrice()));
	note.setText(subscription.getNote());
	startDate.setText(dateTimeFormat.format(subscription.getStartDate()));
	
	if(subscriptionToDisplay.getCancellationRelevance()) {
	    cancellationRelevance.setText("Ja");
	    subscriptionManagerAdmin.getCancellationBySubscriptionId(subscription.getId(), new GetCancellationBySubscriptionCallback());
	} else {
	    cancellationRelevance.setText("Nein");
	}
    }
    
    protected void onLoad() {
	
	subscriptionViewTable.setWidget(0, 0, nameLabel);
	subscriptionViewTable.setWidget(1, 0, priceLabel);
	subscriptionViewTable.setWidget(2, 0, noteLabel);
	subscriptionViewTable.setWidget(3, 0, startDateLabel);
	subscriptionViewTable.setWidget(4, 0, cancellationRelevanceLabel);
	subscriptionViewTable.setWidget(5, 0, expirationDateLabel);
	subscriptionViewTable.setWidget(6, 0, cancellationPeriodLabel);
	subscriptionViewTable.setWidget(7, 0, cancellationDateLabel);
	
	subscriptionViewTable.setWidget(0, 1, name);
	subscriptionViewTable.setWidget(1, 1, price);
	subscriptionViewTable.setWidget(2, 1, note);
	subscriptionViewTable.setWidget(3, 1, startDate);
	subscriptionViewTable.setWidget(4, 1, cancellationRelevance);
	subscriptionViewTable.setWidget(5, 1, expirationDate);
	subscriptionViewTable.setWidget(6, 1, cancellationPeriod);
	subscriptionViewTable.setWidget(7, 1, cancellationDate);
	
	subscriptionViewPanel.add(subscriptionViewTable);
	RootPanel.get("content").clear();
	RootPanel.get("content").add(subscriptionViewPanel);
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
