package de.hdm.subscriptionManager.client;

import de.hdm.subscriptionManager.client.gui.CreateUserDialogBox;
import de.hdm.subscriptionManager.client.gui.SubscriptionAndGroupOverview;
import de.hdm.subscriptionManager.client.gui.SubscriptionForm;
import de.hdm.subscriptionManager.server.db.DBConnection;
import de.hdm.subscriptionManager.shared.LoginService;
import de.hdm.subscriptionManager.shared.LoginServiceAsync;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdmin;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.User;

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
import com.google.gwt.user.client.Cookies;
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

    private LoginInfo loginInfo = null;
    private static Label headline = new Label("Willkommen beim Subscription Manager");
    private static VerticalPanel containerPanel = new VerticalPanel();
    private static Subscription subscription = new Subscription();

   private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
	//LoginServiceAsync loginService = GWT.create(LoginService.class);
//	loginService.login(GWT.getHostPageBaseURL() + "SubscriptionManager.html", new LoginCallback());
	headline.setStylePrimaryName("welcomeFont");
	containerPanel.add(headline);
	LeftMenu lm = new LeftMenu();
	Menubar menuBar = new Menubar(subscription);
	
	RootPanel.get("content").clear();
	RootPanel.get("content").add(containerPanel);
    }
    
    public static void loadSubscriptionManager(LoginInfo loginInfo) {

    }
    
    
    public class LoginCallback implements AsyncCallback<LoginInfo> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void onSuccess(LoginInfo result) {
	    loginInfo = result;
	    if(loginInfo.isLoggedIn()) {
		subscriptionManagerAdmin.checkEmail(loginInfo.getEmailAddresss(), new FindUserCallback());
	    }
	    
	}
	
    }
    
    public class FindUserCallback implements AsyncCallback<User> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void onSuccess(User result) {
	    if(result != null) {
		Cookies.setCookie("email", result.getMail());
		Cookies.setCookie("id", result.getId() + "");
		Cookies.setCookie("signout", loginInfo.getLogoutUrl());
	    } else {
		CreateUserDialogBox dialogBox = new CreateUserDialogBox(loginInfo.getEmailAddresss());
		dialogBox.center();
	    }
	    
	}
	
    }
}

