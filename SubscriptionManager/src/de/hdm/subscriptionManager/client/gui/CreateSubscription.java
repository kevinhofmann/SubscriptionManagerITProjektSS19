package de.hdm.subscriptionManager.client.gui;

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.gui.SubscriptionForm.CreateCancellationCallback;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.client.gui.SubscriptionForm;

public class CreateSubscription extends DialogBox {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();
    
    VerticalPanel vp = new VerticalPanel();
    SubscriptionForm sf = new SubscriptionForm();
    
    
    public void onLoad() {

	FlexTable st = sf.constructTable();

	st.setWidget(12, 1, new Label("test"));
	this.add(st);
	Label txt = new Label("Test");
	this.setText("Neuen Kontakt erstellen");
	this.setGlassEnabled(true);
	this.setAnimationEnabled(true);
	this.setAutoHideEnabled(true);
	this.add(txt);
	this.add(vp);
	this.setGlassEnabled(true);
	
    }
    

}
