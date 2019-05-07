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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ibm.icu.util.Calendar;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.MainContentFrame;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Cancellation;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.User;

public class SubscriptionForm extends MainContentFrame {


    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private Subscription subscription = null;
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
    private TextBox cancellationDay = new TextBox();
    private TextBox cancellationMonth = new TextBox();
    private TextBox cancellationYear = new TextBox();
    private Button calculateCancellationDateButton = new Button("Berechnen");

    private HorizontalPanel formButtonPanel = new HorizontalPanel();
    private Button submit = new Button("Absenden");
    private Button reset = new Button("Reset");
    private ListBox cancellationRelevanceSelector= new ListBox();
    private Boolean cancellationRelevance = false;
    private VerticalPanel textBoxPanel = new VerticalPanel();


    public SubscriptionForm() {
	super.onLoad();
    }

    @Override
    protected void run() {

	formTable.setWidget(0, 0, new Label("Ein neues Abo anlegen"));

	formTable.setWidget(1, 0, new Label("Aboname"));
	formTable.setWidget(1, 1, name);

	formTable.setWidget(2, 0, new Label("Monatspreis"));
	formTable.setWidget(2, 1, price);

	formTable.setWidget(3, 0, new Label("Anmerkung"));
	formTable.setWidget(3, 1, note);

	datePicker.add(day);
	datePicker.add(month);
	datePicker.add(year);
	day.setWidth("20px");
	month.setWidth("20px");
	year.setWidth("40px");
	formTable.setWidget(4, 0, new Label("Startdatum"));
	formTable.setWidget(4, 1, datePicker);

	formTable.setWidget(5, 0, new Label("Kuendigungsrelevant"));
	formTable.setWidget(5, 1, cancellationRelevanceSelector);

	formTable.setWidget(6, 0, new Label("Kuendigungsfrist (MM)"));
	formTable.setWidget(6, 1, cancellationPeriod);
	cancellationPeriod.setWidth("20px");

	formTable.setWidget(7, 0, new Label("Kuendigungstag"));
	cancellationDatePicker.add(cancellationDay);
	cancellationDatePicker.add(cancellationMonth);
	cancellationDatePicker.add(cancellationYear);
	cancellationDay.setWidth("20px");
	cancellationMonth.setWidth("20px");
	cancellationYear.setWidth("40px");
	formTable.setWidget(7, 1, cancellationDatePicker);
	formTable.setWidget(7, 2, calculateCancellationDateButton);

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
		} else {
		    cancellationDay.setEnabled(true);
		    cancellationMonth.setEnabled(true);
		    cancellationYear.setEnabled(true);
		    cancellationPeriod.setEnabled(true);
		    calculateCancellationDateButton.setEnabled(true);
		}
	    } 
	});
	

	formButtonPanel.add(submit);
	formButtonPanel.add(reset);
	formTable.setWidget(8, 1, formButtonPanel);

	textBoxPanel.add(formTable);
	calculateCancellationDateButton.addClickHandler(new calculateCancellationDateClickHandler());
	submit.addClickHandler(new submitFormClickHandler());
	reset.addClickHandler(new resetFormClickHandler());
	RootPanel.get("content").add(textBoxPanel);

    }

    class calculateCancellationDateClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    int cancellationMonthCalc = Integer.parseInt(month.getText());
	    int cancellationYearCalc = Integer.parseInt(year.getText());
	    int cancellationPeriodCalc = Integer.parseInt(cancellationPeriod.getText());

	    while(cancellationPeriodCalc > 0) {
		if(cancellationMonthCalc < 12) {
		    cancellationMonthCalc ++;
		    cancellationPeriodCalc --;    
		} if(cancellationMonthCalc == 12) {
		    cancellationMonthCalc = 1;
		    cancellationYearCalc ++;
		    cancellationPeriodCalc --;   
		}
	    }
	    
	    cancellationDay.setText(day.getText());
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
		    Date endDate = DateTimeFormat.getFormat("yyyy-MM-dd")
			    .parse(Integer.parseInt(cancellationYear.getText()) + "-" + Integer.parseInt(cancellationMonth.getText()) + "-" + Integer.parseInt(day.getText()));
		    java.sql.Date endDateSql = new java.sql.Date(endDate.getTime());
		    
		subscriptionManagerAdmin.createCancellation(endDateSql, Integer.parseInt(cancellationPeriod.getText()), subscriptionID, new CreateCancellationCallback());
	    }
	}
    }
    
    public class CreateCancellationCallback implements AsyncCallback<Cancellation> {
	
	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("no");
	}

	@Override
	public void onSuccess(Cancellation result) {
	    Window.alert("Kündigung Erfolg");
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


	}

    }

}
