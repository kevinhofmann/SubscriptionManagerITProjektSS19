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

public class AddSubscriptionToGroupDialogBox extends DialogBox {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel vPanel = new VerticalPanel();
    private HorizontalPanel buttonPanel = new HorizontalPanel();

    private Button submitButton = new Button("Bestätigen");
    private Button abortButton = new Button("Abbrechen");

    private Subscription subscription = new Subscription();

    private ListBox subscriptionGroupListBox = new ListBox();
    private ArrayList<SubscriptionGroup> subscriptionGroupArrayList = new ArrayList<>();

    private HTML nameLabel;

    public void onLoad() {
	this.subscription = LeftMenu.getSelectedSubscription();
	if(subscription.getId() > 0) {
	    subscriptionManagerAdmin.getAllSubscriptionGroups(1, new GetAllSubscriptionGroupsCallback());
	} else {
	    Window.alert("Bitte wähle zunächst eine Abogruppe aus");
	    hide();
	}
    }

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
	vPanel.add(nameLabel);
	vPanel.add(buttonPanel);

	this.add(vPanel);
    }


    class GetAllSubscriptionGroupsCallback implements AsyncCallback<ArrayList<SubscriptionGroup>> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub

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

    class AddSubscriptionToGroupClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {

	    for(SubscriptionGroup sg:subscriptionGroupArrayList) {
		if(sg.getName() == subscriptionGroupListBox.getSelectedItemText()) {
		    subscriptionManagerAdmin.addSubscriptionToGroup(subscription, sg, new AddSubscriptionToGroupCallback());
		}
	    }
	}

    }
    
    
   class AddSubscriptionToGroupCallback implements AsyncCallback<SubscriptionSubscriptionGroup> {

    @Override
    public void onFailure(Throwable caught) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void onSuccess(SubscriptionSubscriptionGroup result) {
	hide();
	
    }
       
   }

    class AbortClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    hide();
	}
    }
}