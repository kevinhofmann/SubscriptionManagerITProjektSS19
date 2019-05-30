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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.LeftMenu;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;

public class RemoveSubscriptionFromGroupDialogBox extends DialogBox {


    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel vPanel = new VerticalPanel();
    private HorizontalPanel buttonPanel = new HorizontalPanel();

    private Button submitButton = new Button("Bestätigen");
    private Button abortButton = new Button("Abbrechen");

    private Subscription subscription = new Subscription();
    private SubscriptionGroup subscriptionGroup = new SubscriptionGroup();

    private ListBox subscriptionGroupListBox = new ListBox();
    private ArrayList<SubscriptionGroup> subscriptionGroupArrayList = new ArrayList<>();
    private ArrayList<Subscription> selectedSubscriptionsInTable = new ArrayList<>();

    private HTML nameLabel;

    public void onLoad() {
	this.subscriptionGroup = LeftMenu.getSelectedSubscriptionGroup();
	selectedSubscriptionsInTable = ViewAllSubscriptionsOfGroup.getSelectedSubscriptionsOfTable();
	if(selectedSubscriptionsInTable.size() > 0) {
	    showDialogPanel(); 
	} else {
	    Window.alert("Es muss zuerst eine Auswahl getroffen werden");
	    hide();
	}

    }

    public void showDialogPanel() {
	submitButton.addClickHandler(new RemoveSubscriptionFromGroupClickHandler());
	abortButton.addClickHandler(new AbortClickHandler());
	this.setText("Abo aus Gruppe entfernen");
	this.setGlassEnabled(true);
	this.setAnimationEnabled(true);
	this.setAutoHideEnabled(true);

	buttonPanel.add(submitButton);
	buttonPanel.add(abortButton);
	
	nameLabel = new HTML("Soll die Auswahl aus der Gruppe " + subscriptionGroup.getName() + " entfernt werden?");
	vPanel.add(nameLabel);
	vPanel.add(buttonPanel);

	this.add(vPanel);
    }


    class RemoveSubscriptionFromGroupClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    for(Subscription sub : selectedSubscriptionsInTable) {
		subscriptionManagerAdmin.removeSubscriptionFromGroup(sub, subscriptionGroup, new RemoveSubscriptionFromGroupCallback());
	    }
	}
    }


    class RemoveSubscriptionFromGroupCallback implements AsyncCallback<Void> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Fehler beim Entfernen!");

	}

	@Override
	public void onSuccess(Void result) {
	    hide();
	    ViewAllSubscriptionsOfGroup viewAll = new ViewAllSubscriptionsOfGroup();
	    RootPanel.get("content").clear();
	    RootPanel.get("content").add(viewAll);
	}
    }

    class AbortClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    hide();
	}

    }
}


