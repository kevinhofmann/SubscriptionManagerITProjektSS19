package de.hdm.subscriptionManager.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.LoginInfo;
import de.hdm.subscriptionManager.client.SubscriptionManager;
import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.User;

public class CreateUserDialogBox extends DialogBox {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel vPanel = new VerticalPanel();
    private HorizontalPanel buttonPanel = new HorizontalPanel();
    private FlexTable dialogFlexTable = new FlexTable();

    private Button submitButton = new Button("Anlegen");
    private Button abortButton = new Button("Abbrechen");
    private TextBox firstName = new TextBox();
    private TextBox lastName = new TextBox();

    private Label infoLabel = new Label();

    private SubscriptionGroup sGroup = new SubscriptionGroup();
    
    private String googleMail = "";
    
	private LoginInfo loginInfo = null;


    public CreateUserDialogBox(String email) {
	googleMail = email;
    }
    
    /*
     * Die onLoad Methode spezifiziert das anzuzeigende DialogPanel und fügt die einzelnen Elemente dem Panel hinzu.
     */
    public void onLoad() {
	submitButton.addClickHandler(new SubmitCreatedUserClickHandler());
	abortButton.addClickHandler(new AbortClickHandler());
	this.setText("Neue Abogruppe anlegen");
	this.setGlassEnabled(true);
	this.setAnimationEnabled(true);
	this.setAutoHideEnabled(true);

	infoLabel.setText("Vor- und Nachname:");
	buttonPanel.add(submitButton);
	buttonPanel.add(abortButton);

	dialogFlexTable.setWidget(0, 0, infoLabel);
	dialogFlexTable.setWidget(0, 1, firstName);
	dialogFlexTable.setWidget(0, 2, lastName);

	vPanel.add(dialogFlexTable);
	vPanel.add(buttonPanel);

	this.add(vPanel);
    }
    
    class SubmitCreatedUserClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    subscriptionManagerAdmin.createUser(firstName.getText(), lastName.getText(), googleMail, new CreateUserCallback());
	    
	}
	
    }
    
    class CreateUserCallback implements AsyncCallback<User> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void onSuccess(User result) {
	    Window.alert("User erfolgreich erstellt");
		Cookies.setCookie("signout", loginInfo.getLogoutUrl());
		Cookies.setCookie("email", result.getMail());
		Cookies.setCookie("id", result.getId() + "");
		hide();
		SubscriptionManager.loadSubscriptionManager(loginInfo);
	}
	
    }
    
    class AbortClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	    hide();
	}

    }
}
