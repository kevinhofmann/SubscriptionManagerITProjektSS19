package de.hdm.subscriptionManager.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.subscriptionManager.client.gui.SubscriptionForm;
import de.hdm.subscriptionManager.shared.bo.Subscription;

public class MenuBarCommand {
    
    private static Subscription subscription = new Subscription();

    public MenuBarCommand(Subscription sub) {
	subscription = sub;
    }
    
    public static class CreateSubscriptionCommand implements Command {

	@Override
	public void execute() {
	    SubscriptionForm sf = new SubscriptionForm();
	    RootPanel.get("content").clear();
	    RootPanel.get("content").add(sf);
	}	
    }
    
    public static class CreateSubscriptionGroupCommand implements Command {

	@Override
	public void execute() {
	    Window.alert("Gruppe");
	    
	}
    }
    
    public static class EditSubscriptionCommand implements Command {

	@Override
	public void execute() {
	    SubscriptionForm sf = new SubscriptionForm(subscription);
	    RootPanel.get("content").clear();
	    RootPanel.get("content").add(sf);
	}
    }
    
    public static class EditSubscriptionGroupCommand implements Command {

	@Override
	public void execute() {
	    // TODO Auto-generated method stub
	    
	}
    }
    
    public static class DeleteSubscriptionCommand implements Command {

	@Override
	public void execute() {
	    // TODO Auto-generated method stub
	    
	}
    }
    
    public static class DeleteSubscriptionGroupCommand implements Command {

	@Override
	public void execute() {
	    // TODO Auto-generated method stub
	    
	}
    }
    
    public static class AddSubscriptionToGroupCommand implements Command {

	@Override
	public void execute() {
	    // TODO Auto-generated method stub
	    
	}
    }
    
    public static class RemoveSubscriptionToGroupCommand implements Command {

	@Override
	public void execute() {
	    // TODO Auto-generated method stub
	    
	}
    }
}
