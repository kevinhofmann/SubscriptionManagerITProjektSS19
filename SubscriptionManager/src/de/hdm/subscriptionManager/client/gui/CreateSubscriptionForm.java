package de.hdm.subscriptionManager.client.gui;


import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.MainContentFrame;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.User;

public class CreateSubscriptionForm extends MainContentFrame {

    
    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();
    
    private Subscription subscription = null;
    private TextBox name = new TextBox();
    private TextBox price = new TextBox();
    private TextBox note = new TextBox();
    private Date gebTagSql = new Date();


    private Button submit = new Button("Absenden");
    private ListBox cancellationRelevance = new ListBox();
    private VerticalPanel textBoxPanel = new VerticalPanel();
    
    
    public CreateSubscriptionForm() {
	super.onLoad();
    }
    
    @Override
    protected void run() {

	
	submit.addClickHandler(new submitClickHandler());
	cancellationRelevance.addItem("Ja");
	cancellationRelevance.addItem("Nein");
	textBoxPanel.add(name);
	textBoxPanel.add(price);
	textBoxPanel.add(note);
	textBoxPanel.add(cancellationRelevance);
	textBoxPanel.add(submit);
	
	RootPanel.get("content").add(textBoxPanel);
	
    }
    
    class submitClickHandler implements ClickHandler {

	@Override
	public void onClick(ClickEvent event) {
	 //   subscriptionManagerAdmin.createSubscription(name.getText(), Float.parseFloat(price.getText()), note.getText(), (java.sql.Date) gebTagSql, true, 1, new CreateCallback());
	    subscriptionManagerAdmin.createUser(name.getText(), note.getText(), note.getText(), new userCallback());
	}
    }
    
    public class CreateCallback implements AsyncCallback<Subscription> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Klappt nicht");
	    
	}

	@Override
	public void onSuccess(Subscription result) {
	    Window.alert("Klappt");
	    
	}
	
    }
    
    public class userCallback implements AsyncCallback<User> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void onSuccess(User result) {
	    Window.alert("created");
	}
	
    }
    


}
