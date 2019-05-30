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

public class EditSubscriptionGroupDialogBox extends DialogBox {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel vPanel = new VerticalPanel();
    private HorizontalPanel buttonPanel = new HorizontalPanel();
    private FlexTable dialogFlexTable = new FlexTable();

    private Button submitButton = new Button("Übernehmen");
    private Button abortButton = new Button("Abbrechen");
    private TextBox nameOfGroup = new TextBox();

    private Label infoLabel = new Label();

    private SubscriptionGroup sGroup = new SubscriptionGroup();



    public void onLoad() {
	this.sGroup = LeftMenu.getSelectedSubscriptionGroup();
	if(sGroup.getId() > 0) {
	    showDialogPanel();
	} else {
	    Window.alert("Bitte wähle zunächst eine Abogruppe aus");
	    hide();
	}
    }

    public void showDialogPanel() {
	this.sGroup = LeftMenu.getSelectedSubscriptionGroup();
	submitButton.addClickHandler(new SubmitSubscriptionGroupClickHandler());
	abortButton.addClickHandler(new AbortEditingSubscriptionGroupClickHandler());
	this.setText("Abogruppe umbenennen");
	this.setGlassEnabled(true);
	this.setAnimationEnabled(true);
	this.setAutoHideEnabled(true);

	infoLabel.setText("Neue Bezeichnung: ");
	buttonPanel.add(submitButton);
	buttonPanel.add(abortButton);

	dialogFlexTable.setWidget(0, 0, infoLabel);
	dialogFlexTable.setWidget(0, 1, nameOfGroup);
	nameOfGroup.setText(sGroup.getName());

	vPanel.add(dialogFlexTable);
	vPanel.add(buttonPanel);

	this.add(vPanel);
    }


    class SubmitSubscriptionGroupClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    if(nameOfGroup.getText().length() > 3) {
		sGroup.setName(nameOfGroup.getText());
		subscriptionManagerAdmin.updateSubscriptionGroup(sGroup, new EditSubscriptionGroupCallback());
	    } else {
		Window.alert("Bitte wähle einen anderen Namen (länger als 3 Zeichen)");
	    }
	}
    }

    class EditSubscriptionGroupCallback implements AsyncCallback<Void> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(Void result) {
	    LeftMenu leftMenu = new LeftMenu(sGroup);
	    hide();
	}
    }

    class AbortEditingSubscriptionGroupClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    hide();
	}

    }
}

