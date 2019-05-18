package de.hdm.subscriptionManager.client;

import de.hdm.subscriptionManager.client.gui.SubscriptionForm;
import de.hdm.subscriptionManager.server.db.DBConnection;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdmin;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@SuppressWarnings("deprecation")
public class SubscriptionManager implements EntryPoint {

    
    private Label headline = new Label("Willkommen beim Subscription Manager");
    private VerticalPanel containerPanel = new VerticalPanel();
    private Subscription subscription = new Subscription();

   private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
	
	containerPanel.add(headline);
	LeftMenu lm = new LeftMenu();
	Menubar menuBar = new Menubar(subscription);
	
	RootPanel.get("content").clear();
	RootPanel.get("content").add(containerPanel);



    }
}

