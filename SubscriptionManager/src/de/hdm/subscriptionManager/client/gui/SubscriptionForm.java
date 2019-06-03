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


/*
 * Klasse, welche als Formular zum Befüllen von Subscription-Objekt Variablen dient. Die einzelnen Objektvariablen
 * werden in einem FlexTable aufgelistet und mittels TextBoxen können Werte für die Variablen festgelegt werden, welche
 * dann bei der Speicherung eines neuen Sub-Objektes per RPC-Call an die DB übermittelt werden.
 */

public class SubscriptionForm extends VerticalPanel {


    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private Subscription subscription = null;
    private Subscription selectedSubscription = null;
    private Cancellation cancellationInfo = null;
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

    private HTML headline= new HTML("");
    private Label nameLabel = new Label("Aboname");
    private Label priceLabel = new Label("Monatspreis");
    private Label noteLabel = new Label("Anmerkung");
    private Label startDateLabel = new Label("Startdatum");
    private Label expirationDateLabel = new Label("Auslaufdatum");
    private Label cancellationRelevanceLabel = new Label("Kuendigungsrelevant");
    private Label cancellationPeriodLabel = new Label("Kuendigungsfrist (MM)");
    private Label cancellationDateLabel = new Label("Kuendigungstag");



    /*
     * Standardkonstruktor, welcher aufgerufen wird wenn ein neues Abo erstellt werden soll. Das anzuzeigende Formular
     * (TextBoxen) sind somit leer.
     */
    public SubscriptionForm() {
	headline.setHTML("Ein neues Abo anlegen<br>");
	cancellationRelevanceSelector.addItem("Ja");
	cancellationRelevanceSelector.addItem("Nein");
	submit.addClickHandler(new submitFormClickHandler());
    }


    /*
     * Parametrisierter Konstruktor welcher ein Abo Objekt erwartet. Dieser Konstruktor wird verwendet, wenn ein bereits 
     * angelegtes Abo bearbeitet werden soll. Die bereits vorhandenen Werte der Objektvariablen werden ausgelesen und als 
     * vordefinierter Text in den TextBoxen zur Anzeige gebracht. 
     */
    public SubscriptionForm(Subscription subscription) {
	headline.setHTML("Ausgewähltes Abo bearbeiten<br>");
	cancellationRelevanceSelector.addItem("Ja");
	cancellationRelevanceSelector.addItem("Nein");
	submit.addClickHandler(new updateFormClickHandler());
	submit.setText("Update");
	this.selectedSubscription = subscription;
	name.setText(selectedSubscription.getName());
	price.setText(Float.toString(selectedSubscription.getPrice()));
	note.setText(selectedSubscription.getNote());
	String extractedDay = DateTimeFormat.getFormat("dd-MM-yyyy").format(selectedSubscription.getStartDate()).split("-")[0];
	String extractedMonth = DateTimeFormat.getFormat("dd-MM-yyyy").format(selectedSubscription.getStartDate()).split("-")[1];
	String extractedYear = DateTimeFormat.getFormat("dd-MM-yyyy").format(selectedSubscription.getStartDate()).split("-")[2];
	day.setText(extractedDay);
	month.setText(extractedMonth);
	year.setText(extractedYear);
	subscriptionManagerAdmin.getCancellationBySubscriptionId(selectedSubscription.getId(), new GetCancellationInfoCallback());
	if(selectedSubscription.getCancellationRelevance() == true) {
	    cancellationRelevanceSelector.setItemSelected(0, true);
	} else {
	    cancellationRelevanceSelector.setItemSelected(1, true);
	    cancellationDay.setEnabled(false);
	    cancellationMonth.setEnabled(false);
	    cancellationYear.setEnabled(false);
	    cancellationPeriod.setEnabled(false);
	    calculateCancellationDateButton.setEnabled(false);
	    expirationDay.setEnabled(false);
	    expirationMonth.setEnabled(false);
	    expirationYear.setEnabled(false);
	}
    }


    /*
     * Methode, welche den FlexTable zur Anzeige des Formulares generiert. Hier wird lediglich die Basis des Formulares
     * erzeugt, die TextBoxen werden separat erzeugt.
     */
    public FlexTable constructTable() {

	formTable.setWidget(0, 0, headline);

	formTable.setWidget(1, 0, nameLabel);

	formTable.setWidget(2, 0, priceLabel);

	formTable.setWidget(3, 0, noteLabel);

	datePicker.add(day);
	datePicker.add(month);
	datePicker.add(year);
	day.setWidth("20px");
	month.setWidth("20px");
	year.setWidth("40px");
	formTable.setWidget(4, 0, startDateLabel);

	formTable.setWidget(5, 0, cancellationRelevanceLabel);
	formTable.setWidget(5, 1, cancellationRelevanceSelector);

	formTable.setWidget(6, 0, expirationDateLabel);
	expirationDatePicker.add(expirationDay);
	expirationDatePicker.add(expirationMonth);
	expirationDatePicker.add(expirationYear);
	expirationDay.setWidth("20px");
	expirationMonth.setWidth("20px");
	expirationYear.setWidth("40px");

	formTable.setWidget(7, 0, cancellationPeriodLabel);

	formTable.setWidget(8, 0, cancellationDateLabel);
	cancellationDatePicker.add(cancellationDay);
	cancellationDatePicker.add(cancellationMonth);
	cancellationDatePicker.add(cancellationYear);
	cancellationDay.setWidth("20px");
	cancellationMonth.setWidth("20px");
	cancellationYear.setWidth("40px");

	formTable.setWidget(8, 2, calculateCancellationDateButton);

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

	return formTable;
    }


    /*
     * OnLoad Methode ruft die constructTable Methode zunächst auf um den davon zurückgegebenen FlexTable um die
     * TextBoxen für die Nutzereingabe zu erzeugen und dem Table hinzuzufügen. 
     */
    protected void onLoad() {
	constructTable();

	formTable.setWidget(1, 1, name);

	formTable.setWidget(2, 1, price);

	formTable.setWidget(3, 1, note);

	formTable.setWidget(4, 1, datePicker);

	formTable.setWidget(6, 1, expirationDatePicker);

	formTable.setWidget(7, 1, cancellationPeriod);
	cancellationPeriod.setWidth("20px");

	formTable.setWidget(8, 1, cancellationDatePicker);
	formTable.setWidget(8, 2, calculateCancellationDateButton);

	calculateCancellationDateButton.addClickHandler(new calculateCancellationDateClickHandler());

	reset.addClickHandler(new resetFormClickHandler());
	RootPanel.get("content").clear();
	RootPanel.get("content").add(textBoxPanel);

    }


    /*
     * Innere Klasse um den Kündigungstag anhand der Kündigungsfrist und des Auslaufdatums zu berechnen
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


    /*
     * Innere Klasse, welche auf Basis der Nutzereingaben einen RPC-Call auslöst, welcher das Subscription
     * Objekt in der Implementierungsklasse erstellt und in der Datenbank abspeichert.
     */
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


    /*
     * Innere Klasse, welche das Ergebnis des RPC Calls zurückliefert. Ist für die Subscription eine cancellationRelevance
     * selektiert, wird zugleich ein neues Cancellation Objekt erzeugt welches die nötigen Informationen zu 
     * Ablauf und Kündigungstag enthält. Zuletzt wird eine neue Instanz von LeftMenu aufgerufen sowie die Ansicht
     * des neu erstellten Abos über die SubscriptionView Klasse instantiiert. 
     */
    public class CreateSubscriptionCallback implements AsyncCallback<Subscription> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Subscription result) {
	    selectedSubscription = result;
	    int subscriptionID = result.getId();
	    if(result.getCancellationRelevance() == true) {
		Date cancellationDate = DateTimeFormat.getFormat("yyyy-MM-dd")
			.parse(Integer.parseInt(cancellationYear.getText()) + "-" + Integer.parseInt(cancellationMonth.getText()) + "-" + Integer.parseInt(day.getText()));
		java.sql.Date cancellationDateSql = new java.sql.Date(cancellationDate.getTime());

		Date expirationDate = DateTimeFormat.getFormat("yyyy-MM-dd")
			.parse(Integer.parseInt(expirationYear.getText()) + "-" + Integer.parseInt(expirationMonth.getText()) + "-" + Integer.parseInt(day.getText()));
		java.sql.Date expirationDateSql = new java.sql.Date(expirationDate.getTime());

		subscriptionManagerAdmin.createCancellation(expirationDateSql, cancellationDateSql, Integer.parseInt(cancellationPeriod.getText()), subscriptionID, new CreateCancellationCallback());
	    } else {
		LeftMenu lm = new LeftMenu();
		SubscriptionView sv = new SubscriptionView(result);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(sv);
	    }
	}
    }


    /*
     * Callback zur Erstellung des Cancellation Objektes. Neue Instanz von LeftMenu wird aufgerufen sowie die Ansicht
     * des neu erstellten Abos über die SubscriptionView Klasse instantiiert. 
     */
    public class CreateCancellationCallback implements AsyncCallback<Cancellation> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Cancellation result) {
	    LeftMenu lm = new LeftMenu();
	    SubscriptionView sv = new SubscriptionView(selectedSubscription);
	    RootPanel.get("content").clear();
	    RootPanel.get("content").add(sv);
	}
    }


    /*
     * Diese innere Klasse wird durch den RPC-Call aufgerufen, der ein bereits vorhandenes Cancellation Objekt nutzt, 
     * um Informationen über dieses Objekt aus der DB auszulesen. Dies ist der Fall, wenn die Klasse mittels 
     * Subscription Objekt als Parameter instantiiert wird. Infolgedessen werden die Werte des bestehenden Abos in die 
     * FlexTable TextBoxen geladen
     */
    public class GetCancellationInfoCallback implements AsyncCallback<Cancellation> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Error");
	}

	@Override
	public void onSuccess(Cancellation result) {
	    cancellationInfo = new Cancellation();
	    cancellationInfo = result;
	    String extractedExpirationDay = DateTimeFormat.getFormat("dd-MM-yyyy").format(cancellationInfo.getExpirationDate()).split("-")[0];
	    String extractedExpirationMonth = DateTimeFormat.getFormat("dd-MM-yyyy").format(cancellationInfo.getExpirationDate()).split("-")[1];
	    String extractedExpirationYear = DateTimeFormat.getFormat("dd-MM-yyyy").format(cancellationInfo.getExpirationDate()).split("-")[2];
	    expirationDay.setText(extractedExpirationDay);
	    expirationMonth.setText(extractedExpirationMonth);
	    expirationYear.setText(extractedExpirationYear);

	    String extractedCancellationDay = DateTimeFormat.getFormat("dd-MM-yyyy").format(cancellationInfo.getCancellationDate()).split("-")[0];
	    String extractedCancellationMonth = DateTimeFormat.getFormat("dd-MM-yyyy").format(cancellationInfo.getCancellationDate()).split("-")[1];
	    String extractedCancellationYear = DateTimeFormat.getFormat("dd-MM-yyyy").format(cancellationInfo.getCancellationDate()).split("-")[2];
	    cancellationDay.setText(extractedCancellationDay);
	    cancellationMonth.setText(extractedCancellationMonth);
	    cancellationYear.setText(extractedCancellationYear);

	    cancellationPeriod.setText(Integer.toString(cancellationInfo.getCancellationPeriod()));
	}
    }


    /*
     * Diese innere Klasse mit dem Interface ClickHandler wird instantiiert wenn der parametrisierte Konstruktor 
     * aufgerufen wurde. Ein Abo wird in diesem Fall geupdatet und nicht neu erstellt, somit ist der zugrundeliegende
     * RPC-Call auch ein anderer. 
     */
    class updateFormClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {

	    Date startDate = DateTimeFormat.getFormat("yyyy-MM-dd")
		    .parse(Integer.parseInt(year.getText()) + "-" + Integer.parseInt(month.getText()) + "-" + Integer.parseInt(day.getText()));
	    java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());

	    if(cancellationRelevanceSelector.getSelectedItemText() == "Ja") {
		cancellationRelevance = true;
	    }

	    selectedSubscription.setName(name.getText());
	    selectedSubscription.setPrice(Float.parseFloat(price.getText()));
	    selectedSubscription.setNote(note.getText());
	    selectedSubscription.setStartDate(startDateSql);
	    selectedSubscription.setCancellationRelevance(cancellationRelevance);

	    subscriptionManagerAdmin.updateSubscription(selectedSubscription, new UpdateSubscriptionCallback());
	}
    }


    /*
     * Ergebnis des UpdateSubscription RPC. Entsprechend werden die Werte des Cancellation Objektes aktualisiert so wie
     * der Nutzer diese im Formular eingegeben hat. Die Subscription wird im Anschluss zur Anzeige gebracht.
     */
    class UpdateSubscriptionCallback implements AsyncCallback<Void> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Void result) {

	    Date cancellationDate = DateTimeFormat.getFormat("yyyy-MM-dd")
		    .parse(Integer.parseInt(cancellationYear.getText()) + "-" + Integer.parseInt(cancellationMonth.getText()) + "-" + Integer.parseInt(cancellationDay.getText()));
	    java.sql.Date cancellationDateSql = new java.sql.Date(cancellationDate.getTime());

	    Date expirationDate = DateTimeFormat.getFormat("yyyy-MM-dd")
		    .parse(Integer.parseInt(expirationYear.getText()) + "-" + Integer.parseInt(expirationMonth.getText()) + "-" + Integer.parseInt(expirationDay.getText()));
	    java.sql.Date expirationDateSql = new java.sql.Date(expirationDate.getTime());

	    cancellationInfo.setExpirationDate(expirationDateSql);
	    cancellationInfo.setCancellationDate(cancellationDateSql);
	    cancellationInfo.setCancellationPeriod(Integer.parseInt(cancellationPeriod.getText()));
	    subscriptionManagerAdmin.updateCancellation(cancellationInfo, new UpdateCancellationInfoCallback());
	}
    }


    class UpdateCancellationInfoCallback implements AsyncCallback<Void> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Void result) {
	    LeftMenu lm = new LeftMenu();
	    SubscriptionView sv = new SubscriptionView(selectedSubscription);
	    RootPanel.get("content").clear();
	    RootPanel.get("content").add(sv);
	}

    }


    /*
     * Über den ClickHandler werden die Eingaben in die TextBoxen wieder zurückgesetzt.
     */
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
