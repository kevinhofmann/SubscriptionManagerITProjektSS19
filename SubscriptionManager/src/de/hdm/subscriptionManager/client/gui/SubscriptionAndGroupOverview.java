package de.hdm.subscriptionManager.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;


/*
 * Diese Klasse dient als Übersichtsansicht. Sie zeigt, je nach dem welcher ToggleButton im linken Menü gedrückt wird
 * Informationen zu den bestehenden Abos (bisher entstandene Gesamtkosten, günstigstes und teuerstes Abo), zu anstehenden
 * Kündigungsterminen (welches Abo läuft als nächstes aus bzw. welcher Kündigungstag rückt näher) sowie Infos über Abogruppen
 * (entstandene Kosten in der Gruppe). Über parametrisierte Konstruktoren, die als Parameter ein Subscription Objekt oder ein
 * SGroup Objekt erwarten, wird jeweils erkannt, was zur Anzeige gebracht werden soll. 
 */
public class SubscriptionAndGroupOverview extends VerticalPanel {


    private VerticalPanel vPanel = new VerticalPanel();
    private ArrayList<Subscription> subscriptionArrayList = new ArrayList<>();
    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();
    private Double sumOfExpenses = 0.0;
    private Label cheapestSub = new Label("");
    private Label mostExpensiveSub = new Label("");
    private Label otherSubsToBeCancelledLabel = new Label("Verbleibende Tage bis zum Kündigungstag aller anderen Abos:");
    private FlexTable overviewFlexTable = new FlexTable();
    private FlexTable cancellationOverviewFlexTable = new FlexTable();
    private HTML detailInformationLabel = new HTML();
    private HTML cancellationLabel = new HTML();
    private ArrayList<Cancellation> cancellationInfoArrayList = new ArrayList<>();

    private Date today = new Date();
    private ArrayList<Subscription> sortedSubscriptionList = new ArrayList<>();

    /*
     * Parametrisierter Konstruktor, der als Parameter ein Sub-Objekt erwartet. Alle vom User angelegten Subscriptions werden
     * anschließend mittels RPC-Call abgefragt.
     */    
    public SubscriptionAndGroupOverview(Subscription subscription) {
	if(subscriptionArrayList.size() > 0) {
	    constructSubscriptionInfoTable();
	} else {
		subscriptionManagerAdmin.getAllSubscriptions(1, new GetAllSubscriptionsCallback());
    }
    }
    
    
    public SubscriptionAndGroupOverview(SubscriptionGroup subGroup) {
	subscriptionManagerAdmin.getAllSubscriptionGroups(1, new GetAllSubscriptionGroupsCallback());
    }

    /*
     * Methode, um den FlexTable, der die Informationen zu Abo und Kündigung enthält, zu erzeugen und zur Anzeige zu bringen.
     * Diese Methode wird aufgerufen, wenn die vorherigen RPC-Calls abgeschlossen wurden.
     */
    public void constructSubscriptionInfoTable() {
	detailInformationLabel = new HTML("Für deine Abos sind bisher Kosten von insgesamt " + sumOfExpenses + " € entstanden.");
	detailInformationLabel.setStylePrimaryName("statisticOverview");
	cheapestSub.setText(getCheapestSubscription(subscriptionArrayList).getName());
	mostExpensiveSub.setText(getMostExpensiveSubscription(subscriptionArrayList).getName());
	cheapestSub.setStylePrimaryName("statisticOverviewItalic");
	mostExpensiveSub.setStylePrimaryName("statisticOverviewItalic");
	overviewFlexTable.setWidget(2, 0, new Label("Das teuerste Abo: "));
	overviewFlexTable.setWidget(2, 1, mostExpensiveSub);
	overviewFlexTable.setWidget(3, 0, new Label("Das günstigste Abo: "));
	overviewFlexTable.setWidget(3, 1, cheapestSub);
	overviewFlexTable.setStylePrimaryName("statisticOverviewFlexTable");
	overviewFlexTable.getColumnFormatter().setWidth(0, "130px");
	/*
	 * For-Schleife um aus zwei ArrayLists (eins enthält Subscription Objekte, das andere Cancellation Objekte) 
	 * die Informationen zu den verbleibenden Tagen bis zum Kündigungstag anzuzeigen.
	 */
	int row = 0;
	for(int i = 1; i<cancellationInfoArrayList.size(); i++) {
	    int column = 0;
	    cancellationOverviewFlexTable.setWidget(row, column, new Label(sortedSubscriptionList.get(i).getName()));
	    cancellationOverviewFlexTable.setWidget(row, column+1, new Label(Long.toString(cancellationInfoArrayList.get(i).getDaysRemainingCancellationDate())));
	    row++;
	}
	
	cancellationLabel = new HTML("Dein Abo " + sortedSubscriptionList.get(0).getName() + " muss bis in " + cancellationInfoArrayList.get(0).getDaysRemainingCancellationDate() + " Tagen gekündigt sein.");
	cancellationLabel.setStylePrimaryName("statisticOverview");
	cancellationOverviewFlexTable.setStylePrimaryName("statisticCancellationOverviewFlexTable");
	cancellationOverviewFlexTable.getColumnFormatter().setWidth(0, "130px");
	addWidgetsToPanel();
    }
    
    
    /*
     * Methode um die Widgets dem zugrundeliegenden Panel zuzuweisen und ins das RootPanel einzufügen
     */
    public void addWidgetsToPanel() {
	vPanel.add(detailInformationLabel);
	vPanel.add(overviewFlexTable);
	vPanel.add(cancellationLabel);
	if(subscriptionArrayList.size() > 0) {
	    vPanel.add(otherSubsToBeCancelledLabel);
	}
	vPanel.add(cancellationOverviewFlexTable);
	vPanel.setStylePrimaryName("statisticOverviewPanel");
	RootPanel.get("content").clear();
	RootPanel.get("content").add(vPanel);
    }


    /*
     * Callback der eine ArrayListe der bestehenden Abos des Users als Ergebnis zurückgibt. Die einzelnen Abos werden über eine
     * for-each Schleife in eine lokale ArrayListe übernommen. Zudem werden die Cancellation Informationen über einen RPC-Call
     * mittels UserID aus der Datenbank abgefragt. Für die Abos wird dann die Methode calculateCosts aufgerufen und das 
     * ArrayList Objekt welches die Abos enthält darin übergeben. 
     */
    class GetAllSubscriptionsCallback implements AsyncCallback<ArrayList<Subscription>> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(ArrayList<Subscription> result) {
	    for(Subscription sub : result) {
		subscriptionArrayList.add(sub);
	    }
	    subscriptionManagerAdmin.getAllCancellationInfoByUserId(1, new CancellationCallback());

	    calculateCosts(subscriptionArrayList);
	}
    }
	
    
    /*
     * Callback der eine ArrayListe an bestehenden AboGruppen des Users als Ergebnis zurückgibt.
     */
	class GetAllSubscriptionGroupsCallback implements AsyncCallback<ArrayList<SubscriptionGroup>> {

	    @Override
	    public void onFailure(Throwable caught) {
		Window.alert(caught.getMessage());
		
	    }

	    @Override
	    public void onSuccess(ArrayList<SubscriptionGroup> result) {
		detailInformationLabel = new HTML("Es sind insgesamt " + result.size() + " Gruppen angelegt.");
		detailInformationLabel.setStylePrimaryName("statisticOverview");
		addWidgetsToPanel();
	    } 
	}


	/*
	 * Methode um die entstandenen Kosten eines jeden Abos zu kalkulieren. Hierfür wird das Startdatum jedes Abos
	 * mit einer for-each Schleife ausgelesen, eine Differenz zum heutigen Tag erstellt und die Tage mit dem runtergerechneten
	 * Tagespreis des Abos multipliziert. Das Ergebnis wird in der lokalen Variable sumOfExpenses festgehalten und aufsummiert.
	 */
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


    /*
     * Methode, mit welcher über eine for-Schleife das Sub-Objekt herausgefunden wird, welches die höchsten Ausgaben seit
     * dessen Startdatum erzeugt hat. Dazu wird auf die Objektvariable expensesSinceStart zugegriffen. 
     */
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


    /*
     * Pendant zur obigen Methode um das günstigste Abo herauszufinden.
     */
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


    /*
     * Methode um die verbleibenden Tage bis zum Kündigungstag zu berechnen. Differenz des heutigen Tages mit dem Tag des
     * Kündigungsdatums wird berechnet für jedes Cancellation Objekt in der ArrayList. Im Anschluss wird die Liste nach Tagen
     * aufsteigend geordnet. Über einen RPC-Call der als Parameter die SubscriptionID übergibt welche in jedem Cancellation
     * Objekt als Objektvariable gespeichert ist, wird das zugehörige Subscription Objekt abgefragt.
     */
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


    /*
     * Callback, der das Ergebnis in eine lokale ArrayListe speichert und dann, wenn die Größe beider ArrayLists (Cancellation
     * und Subscription) gleich ist, die Methode constructInfoTable() ausführt, mit welcher letztendlich die gesammelten
     * Infos angezeigt werden.
     */
    class GetSubscriptionBySubscriptionIDCallback implements AsyncCallback<Subscription> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Fehler beim Auslesen der Subscription Info!");

	}

	@Override
	public void onSuccess(Subscription result) {
	    sortedSubscriptionList.add(result);
	    if(sortedSubscriptionList.size() == cancellationInfoArrayList.size()) {
		constructSubscriptionInfoTable();
	    }
	}
    }


    /*
     * Callback, der als Ergebnis eine ArrayListe von Cancellation-Objekten zurückliefert. Diese werden in eine lokale 
     * ArrayListe übernommen und für diese Objekte wird dann die Methode calculateDaysUntilCancellation() aufgerufen, um die
     * Tage bis zum Kündigungstag zu berechnen.
     */
    class CancellationCallback implements AsyncCallback<ArrayList<Cancellation>> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Fehler beim Auslesen der Cancellation Info!");

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






