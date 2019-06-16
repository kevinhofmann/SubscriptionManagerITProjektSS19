package de.hdm.subscriptionManager.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.LeftMenu;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;

public class DeleteSubscriptionDialogBox extends DialogBox {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel vPanel = new VerticalPanel();
    private HorizontalPanel buttonPanel = new HorizontalPanel();

    private Button submitButton = new Button("Löschen");
    private Button abortButton = new Button("Abbrechen");

    private Subscription subscription = new Subscription();

    private HTML nameLabel;
    
    
    public void onLoad() {
	this.subscription = LeftMenu.getSelectedSubscription();
	submitButton.addClickHandler(new DeleteSubscriptionClickHandler());
	abortButton.addClickHandler(new AbortDeletingSubscriptionClickHandler());
	this.setText("Abo löschen");
	this.setGlassEnabled(true);
	this.setAnimationEnabled(true);
	this.setAutoHideEnabled(true);
	
	buttonPanel.add(submitButton);
	buttonPanel.add(abortButton);
	buttonPanel.setStylePrimaryName("buttonPanelBox");

	nameLabel = new HTML("Soll das Abo " + subscription.getName() + " gelöscht werden?");
	vPanel.add(nameLabel);
	vPanel.add(buttonPanel);

	this.add(vPanel);
    }
    
    class DeleteSubscriptionClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    subscriptionManagerAdmin.deleteSubscription(subscription, new DeleteSubscriptionCallback());
	    
	}
	
    }
    
    class DeleteSubscriptionCallback implements AsyncCallback<Void> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Fehler beim Löschen");
	    
	}

	@Override
	public void onSuccess(Void result) {
	    LeftMenu leftMenu = new LeftMenu();
	    hide();
	}
    }
    
    class AbortDeletingSubscriptionClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    hide();
	    
	}
    }
}
