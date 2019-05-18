package de.hdm.subscriptionManager.client.gui;



import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.LeftMenu;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Cancellation;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.User;

public class SubscriptionForm extends VerticalPanel {


    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private Subscription subscription = null;
    private Subscription selectedSubscription = null;
    private TextBox name = new TextBox();
    private TextBox price = new TextBox();
    private TextBox note = new TextBox();
    private FlexTable formTable = new FlexTable();
    private Date startDate = new Date();

    private HorizontalPanel datePicker = new HorizontalPanel();
    private TextBox day = new TextBox();
    private TextBox month = new TextBox();
    private TextBox year = new TextBox();

    private TextBox cancellationPeriod = new TextBox();
    private HorizontalPanel cancellationDatePicker = new HorizontalPanel();
    private HorizontalPanel expirationDatePicker = new HorizontalPanel();
    private TextBox cancellationDay = new TextBox();
    private TextBox cancellationMonth = new TextBox();
    private TextBox cancellationYear = new TextBox();
    private TextBox expirationDay = new TextBox();
    private TextBox expirationMonth = new TextBox();
    private TextBox expirationYear = new TextBox();
    private Button calculateCancellationDateButton = new Button("Berechnen");

    private HorizontalPanel formButtonPanel = new HorizontalPanel();
    private Button submit = new Button("Absenden");
    private Button reset = new Button("Reset");
    private ListBox cancellationRelevanceSelector= new ListBox();
    private Boolean cancellationRelevance = false;
    private VerticalPanel textBoxPanel = new VerticalPanel();

    private HTML headline= new HTML("Ein neues Abo anlegen<br>");
    private Label nameLabel = new Label("Aboname");
    private Label priceLabel = new Label("Monatspreis");
    private Label noteLabel = new Label("Anmerkung");
    private Label startDateLabel = new Label("Startdatum");
    private Label expirationDateLabel = new Label("Auslaufdatum");
    private Label cancellationRelevanceLabel = new Label("Kuendigungsrelevant");
    private Label cancellationPeriodLabel = new Label("Kuendigungsfrist (MM)");
    private Label cancellationDateLabel = new Label("Kuendigungstag");

    
    public SubscriptionForm() {
	
    }
    
    public SubscriptionForm(Subscription subscription) {
	this.selectedSubscription = subscription;
	name.setText(subscription.getName());
	price.setText(Float.toString(subscription.getPrice()));
    }

    protected void onLoad() {

	super.onLoad();
	
	formTable.setWidget(0, 0, headline);

	formTable.setWidget(1, 0, nameLabel);
	formTable.setWidget(1, 1, name);

	formTable.setWidget(2, 0, priceLabel);
	formTable.setWidget(2, 1, price);

	formTable.setWidget(3, 0, noteLabel);
	formTable.setWidget(3, 1, note);

	datePicker.add(day);
	datePicker.add(month);
	datePicker.add(year);
	day.setWidth("20px");
	month.setWidth("20px");
	year.setWidth("40px");
	formTable.setWidget(4, 0, startDateLabel);
	formTable.setWidget(4, 1, datePicker);

	formTable.setWidget(5, 0, cancellationRelevanceLabel);
	formTable.setWidget(5, 1, cancellationRelevanceSelector);

	formTable.setWidget(6, 0, expirationDateLabel);
	expirationDatePicker.add(expirationDay);
	expirationDatePicker.add(expirationMonth);
	expirationDatePicker.add(expirationYear);
	expirationDay.setWidth("20px");
	expirationMonth.setWidth("20px");
	expirationYear.setWidth("40px");
	formTable.setWidget(6, 1, expirationDatePicker);

	formTable.setWidget(7, 0, cancellationPeriodLabel);
	formTable.setWidget(7, 1, cancellationPeriod);
	cancellationPeriod.setWidth("20px");

	formTable.setWidget(8, 0, cancellationDateLabel);
	cancellationDatePicker.add(cancellationDay);
	cancellationDatePicker.add(cancellationMonth);
	cancellationDatePicker.add(cancellationYear);
	cancellationDay.setWidth("20px");
	cancellationMonth.setWidth("20px");
	cancellationYear.setWidth("40px");
	formTable.setWidget(8, 1, cancellationDatePicker);
	formTable.setWidget(8, 2, calculateCancellationDateButton);



	cancellationRelevanceSelector.addItem("Ja");
	cancellationRelevanceSelector.addItem("Nein");
	cancellationRelevanceSelector.addChangeHandler(new ChangeHandler() {

	    @Override
	    public void onChange(ChangeEvent event) {
		if(cancellationRelevanceSelector.getSelectedIndex() == 1) {
		    cancellationDay.setEnabled(false);
		    cancellationMonth.setEnabled(false);
		    cancellationYear.setEnabled(false);
		    cancellationPeriod.setEnabled(false);
		    calculateCancellationDateButton.setEnabled(false);
		    expirationDay.setEnabled(false);
		    expirationMonth.setEnabled(false);
		    expirationYear.setEnabled(false);
		} else {
		    cancellationDay.setEnabled(true);
		    cancellationMonth.setEnabled(true);
		    cancellationYear.setEnabled(true);
		    cancellationPeriod.setEnabled(true);
		    calculateCancellationDateButton.setEnabled(true);
		    expirationDay.setEnabled(true);
		    expirationMonth.setEnabled(true);
		    expirationYear.setEnabled(true);
		}
	    } 
	});


	formButtonPanel.add(submit);
	formButtonPanel.add(reset);
	formTable.setWidget(11, 1, formButtonPanel);

	textBoxPanel.add(formTable);
	calculateCancellationDateButton.addClickHandler(new calculateCancellationDateClickHandler());
	submit.addClickHandler(new submitFormClickHandler());
	reset.addClickHandler(new resetFormClickHandler());
	RootPanel.get("content").add(textBoxPanel);

    }

    
    /*
     * Klasse um den K�ndigungstag anhand der K�ndigungsfrist und des Auslaufdatums zu berechnen
     */
    class calculateCancellationDateClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    int cancellationMonthCalc = Integer.parseInt(expirationMonth.getText());
	    int cancellationYearCalc = Integer.parseInt(expirationYear.getText());
	    int cancellationPeriodCalc = Integer.parseInt(cancellationPeriod.getText());

	    while(cancellationPeriodCalc > 0) {
		--cancellationMonthCalc;
		cancellationPeriodCalc--;
		if(cancellationMonthCalc == 0) {
		    cancellationMonthCalc = 12;
		    cancellationYearCalc --;
		}
	    }
	    cancellationDay.setText(expirationDay.getText());
	    cancellationMonth.setText(Integer.toString(cancellationMonthCalc));
	    cancellationYear.setText(Integer.toString(cancellationYearCalc));
	}
    }

    class submitFormClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    Date startDate = DateTimeFormat.getFormat("yyyy-MM-dd")
		    .parse(Integer.parseInt(year.getText()) + "-" + Integer.parseInt(month.getText()) + "-" + Integer.parseInt(day.getText()));
	    java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());

	    if(cancellationRelevanceSelector.getSelectedItemText() == "Ja") {
		cancellationRelevance = true;
	    }
	    subscriptionManagerAdmin.createSubscription(name.getText(), Float.parseFloat(price.getText()), note.getText(), startDateSql, cancellationRelevance, 1, new CreateSubscriptionCallback());
	}
    }

    public class CreateSubscriptionCallback implements AsyncCallback<Subscription> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Klappt nicht");

	}

	@Override
	public void onSuccess(Subscription result) {
	    int subscriptionID = result.getId();
	    if(result.getCancellationRelevance() == true) {
		Date cancellationDate = DateTimeFormat.getFormat("yyyy-MM-dd")
			.parse(Integer.parseInt(cancellationYear.getText()) + "-" + Integer.parseInt(cancellationMonth.getText()) + "-" + Integer.parseInt(day.getText()));
		java.sql.Date cancellationDateSql = new java.sql.Date(cancellationDate.getTime());

		Date expirationDate = DateTimeFormat.getFormat("yyyy-MM-dd")
			.parse(Integer.parseInt(expirationYear.getText()) + "-" + Integer.parseInt(expirationMonth.getText()) + "-" + Integer.parseInt(day.getText()));
		java.sql.Date expirationDateSql = new java.sql.Date(expirationDate.getTime());

		subscriptionManagerAdmin.createCancellation(cancellationDateSql, expirationDateSql, Integer.parseInt(cancellationPeriod.getText()), subscriptionID, new CreateCancellationCallback());
	    }
	}
    }

    public class CreateCancellationCallback implements AsyncCallback<Cancellation> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Error");
	}

	@Override
	public void onSuccess(Cancellation result) {
	    LeftMenu leftMenu = new LeftMenu();
	}
    }

    class resetFormClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    name.setText("");
	    price.setText("");
	    note.setText("");
	    day.setText("");
	    month.setText("");
	    year.setText("");
	    cancellationDay.setText("");
	    cancellationMonth.setText("");
	    cancellationYear.setText("");
	    expirationDay.setText("");
	    expirationMonth.setText("");
	    expirationYear.setText("");
	    cancellationPeriod.setText("");
	}

    }

}
