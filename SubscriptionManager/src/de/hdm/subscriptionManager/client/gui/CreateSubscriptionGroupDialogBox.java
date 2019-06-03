package de.hdm.subscriptionManager.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.LeftMenu;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;


/*
 * Klasse, über welche mittels einer DialogBox eine neue Sub-Group angelegt werden kann.
 */
public class CreateSubscriptionGroupDialogBox extends DialogBox {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel vPanel = new VerticalPanel();
    private HorizontalPanel buttonPanel = new HorizontalPanel();
    private FlexTable dialogFlexTable = new FlexTable();

    private Button submitButton = new Button("Anlegen");
    private Button abortButton = new Button("Abbrechen");
    private TextBox nameOfGroup = new TextBox();

    private Label infoLabel = new Label();

    private SubscriptionGroup sGroup = new SubscriptionGroup();


    /*
     * Die onLoad Methode spezifiziert das anzuzeigende DialogPanel und fügt die einzelnen Elemente dem Panel hinzu.
     */
    public void onLoad() {
	submitButton.addClickHandler(new SubmitSubscriptionGroupClickHandler());
	abortButton.addClickHandler(new AbortCreatingSubscriptionGroupClickHandler());
	this.setText("Neue Abogruppe anlegen");
	this.setGlassEnabled(true);
	this.setAnimationEnabled(true);
	this.setAutoHideEnabled(true);

	infoLabel.setText("Bezeichnung: ");
	buttonPanel.add(submitButton);
	buttonPanel.add(abortButton);

	dialogFlexTable.setWidget(0, 0, infoLabel);
	dialogFlexTable.setWidget(0, 1, nameOfGroup);

	vPanel.add(dialogFlexTable);
	vPanel.add(buttonPanel);

	this.add(vPanel);
    }


    /*
     * ClickHandler um bei einem Klick auf Submit einen RPC Call auszulösen, über welchen der eingegebene Gruppenname 
     * zur Erstellung eines neuen Sub-Group Objektes welches dann in der Datenbank gespeichert wird.
     */
    class SubmitSubscriptionGroupClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    if(nameOfGroup.getText().length() > 3) {
		subscriptionManagerAdmin.createSubscriptionGroup(nameOfGroup.getText(), 1, new CreateSubscriptionGroupCallback());
	    } else {
		Window.alert("Bitte wähle einen anderen Namen (länger als 3 Zeichen)");
	    }
	}
    }


    /*
     * Callback als Ergebnis der Speicherung des Sub-Group Objektes in der DB. Die LeftMenu Klasse wird neu instantiiert mittels
     * parametrisiertem Konstruktor, sodass die Gruppenansicht ausgelöst wird und das neu hinzugefügte Sub-Group Objekt in
     * der CellList angezeigt wird.
     */
    class CreateSubscriptionGroupCallback implements AsyncCallback<SubscriptionGroup> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(SubscriptionGroup result) {
	    LeftMenu leftMenu = new LeftMenu(sGroup);
	    hide();
	}
    }


    class AbortCreatingSubscriptionGroupClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    hide();
	}

    }
}
