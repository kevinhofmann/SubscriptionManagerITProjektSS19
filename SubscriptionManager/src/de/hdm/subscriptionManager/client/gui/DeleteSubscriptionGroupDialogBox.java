package de.hdm.subscriptionManager.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.LeftMenu;
import de.hdm.subscriptionManager.client.SubscriptionManager;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;

public class DeleteSubscriptionGroupDialogBox extends DialogBox {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel vPanel = new VerticalPanel();
    private HorizontalPanel buttonPanel = new HorizontalPanel();

    private Button submitButton = new Button("Löschen");
    private Button abortButton = new Button("Abbrechen");

    private SubscriptionGroup sGroup = new SubscriptionGroup();

    private HTML nameLabel;


    
    public void onLoad() {
	this.sGroup = LeftMenu.getSelectedSubscriptionGroup();
	submitButton.addClickHandler(new DeleteSubscriptionGroupClickHandler());
	abortButton.addClickHandler(new AbortDeletingSubscriptionGroupClickHandler());
	this.setText("Abogruppe umbenennen");
	this.setGlassEnabled(true);
	this.setAnimationEnabled(true);
	this.setAutoHideEnabled(true);

	buttonPanel.add(submitButton);
	buttonPanel.add(abortButton);

	nameLabel = new HTML("Soll die Abogruppe " + sGroup.getName() + " gelöscht werden?");
	vPanel.add(nameLabel);
	vPanel.add(buttonPanel);

	this.add(vPanel);
    }

    class DeleteSubscriptionGroupClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    subscriptionManagerAdmin.deleteSubscriptionGroup(sGroup, new DeleteSubscriptionGroupCallback());
	}
    }


	class DeleteSubscriptionGroupCallback implements AsyncCallback<Void> {

	    @Override
	    public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onSuccess(Void result) {
		LeftMenu leftMenu = new LeftMenu(sGroup);
		RootPanel.get("content").clear();
		hide();
	    }
	}

	class AbortDeletingSubscriptionGroupClickHandler implements ClickHandler {

	    @Override
	    public void onClick(ClickEvent event) {
		hide();
	    }
	}
}

