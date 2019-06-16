package de.hdm.subscriptionManager.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.LeftMenu;
import de.hdm.subscriptionManager.client.gui.DeleteSubscriptionGroupDialogBox.AbortDeletingSubscriptionGroupClickHandler;
import de.hdm.subscriptionManager.client.gui.DeleteSubscriptionGroupDialogBox.DeleteSubscriptionGroupClickHandler;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.SubscriptionSubscriptionGroup;


/*
 * Klasse, welche eine DialogBox erzeugt mit deren Hilfe ein selektiertes Abo in eine Abogruppe hinzugefügt werden kann.
 * Zuvor muss im LeftMenu in der CellList ein Abo ausgewählt worden sein. 
 */
public class AddSubscriptionToGroupDialogBox extends DialogBox {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel vPanel = new VerticalPanel();
    private HorizontalPanel buttonPanel = new HorizontalPanel();

    private Button submitButton = new Button("Bestätigen");
    private Button abortButton = new Button("Abbrechen");

    private Subscription subscription = new Subscription();
    private SubscriptionGroup selectedSubscriptionGroup = new SubscriptionGroup();

    private ListBox subscriptionGroupListBox = new ListBox();
    private ArrayList<SubscriptionGroup> subscriptionGroupArrayList = new ArrayList<>();

    private HTML nameLabel;


    /*
     * Aus der Klasse LeftMenu wird mittels Getter-Methode das selektierte Subscription Objekt ausgelesen sowie ein
     * RPC-Call eingeleitet, der alle vom User angelegten Sub-Groups abfragt.
     */
    public void onLoad() {
	this.subscription = LeftMenu.getSelectedSubscription();
	    subscriptionManagerAdmin.getAllSubscriptionGroups(1, new GetAllSubscriptionGroupsCallback());
    }


    /*
     * Die Methode showDialogPanel() spezifiziert das anzuzeigende DialogPanel und fügt die einzelnen Elemente dem Panel hinzu.
     */
    public void showDialogPanel() {
	submitButton.addClickHandler(new AddSubscriptionToGroupClickHandler());
	abortButton.addClickHandler(new AbortClickHandler());
	this.setText("Abo zur Gruppe hinzufügen");
	this.setGlassEnabled(true);
	this.setAnimationEnabled(true);
	this.setAutoHideEnabled(true);

	buttonPanel.add(submitButton);
	buttonPanel.add(abortButton);

	nameLabel = new HTML("Abogruppe auswählen, um das Abo " + subscription.getName() + " einzufügen");
	vPanel.add(subscriptionGroupListBox);
	subscriptionGroupListBox.setWidth("200px");
	subscriptionGroupListBox.setMultipleSelect(false);
	vPanel.add(nameLabel);
	vPanel.add(buttonPanel);

	this.add(vPanel);
    }


    /*
     * Callback, der die ArrayListe von angelegten Sub-Groups des Users zurückgibt. Diese werden in eine lokale ArrayList
     * übernommen und zugleich in die ListBox, mittels welcher die Selektion stattfindet, in welche Gruppe das Sub-Objekt eingefügt
     * werden soll. Im Anschluss wird das Panel zur Anzeige gebracht.
     */
    class GetAllSubscriptionGroupsCallback implements AsyncCallback<ArrayList<SubscriptionGroup>> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(ArrayList<SubscriptionGroup> result) {
	    for(SubscriptionGroup sg : result) {
		subscriptionGroupListBox.addItem(sg.getName());
		subscriptionGroupArrayList.add(sg);
	    }
	    subscriptionGroupListBox.setVisibleItemCount(subscriptionGroupListBox.getItemCount());
	    showDialogPanel();

	}
    }


    /*
     * Lokale Klasse die das Interface ClickHandler implementiert. Beim Klick auf den "Submit" Button, wird überprüft,
     * welche Sub-Group vom User in der ListBox ausgewählt wurde und dann über einen RPC-Call wird das entsprechende Abo
     * in die ausgewählte Gruppe übernommen.
     */
    class AddSubscriptionToGroupClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {

	    for(SubscriptionGroup sg:subscriptionGroupArrayList) {
		if(sg.getName() == subscriptionGroupListBox.getSelectedItemText()) {
		    selectedSubscriptionGroup = sg;
		    subscriptionManagerAdmin.checkSubscriptionToGroupBelonging(subscription.getId(), selectedSubscriptionGroup.getId(), new GetSubToGroupBelongingCallback());
		}
	    }
	}

    }
    
    
    class GetSubToGroupBelongingCallback implements AsyncCallback<SubscriptionSubscriptionGroup> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void onSuccess(SubscriptionSubscriptionGroup result) {
	    if(result.getSubscriptionID() > 0) {
		Window.alert("Das Abo " + subscription.getName() + " ist bereits in der Gruppe " + selectedSubscriptionGroup.getName() + "!");
	    } else {
		subscriptionManagerAdmin.addSubscriptionToGroup(subscription, selectedSubscriptionGroup, new AddSubscriptionToGroupCallback());
	    }
	    
	}
	
    }
    

    /*
     * Callback als Ergebnis der Speicherung eines Sub-Objektes in einer Sub-Group.
     */
    class AddSubscriptionToGroupCallback implements AsyncCallback<SubscriptionSubscriptionGroup> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(SubscriptionSubscriptionGroup result) {
	    hide();
	}
    }


    /*
     * ClickHandler, mit welchem die Aktion abgebrochen wird und die DialogBox wieder ausgeblendet wird.
     */
    class AbortClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    hide();
	}
    }
}
