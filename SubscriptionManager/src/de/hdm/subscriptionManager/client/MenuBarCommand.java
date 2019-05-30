package de.hdm.subscriptionManager.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.subscriptionManager.client.gui.AddSubscriptionToGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.CreateSubscription;
import de.hdm.subscriptionManager.client.gui.CreateSubscriptionGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.DeleteSubscriptionDialogBox;
import de.hdm.subscriptionManager.client.gui.DeleteSubscriptionGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.EditSubscriptionGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.RemoveSubscriptionFromGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.SubscriptionForm;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;

public class MenuBarCommand {
    
    private static Subscription subscription = new Subscription();
    private static SubscriptionGroup subGroup = new SubscriptionGroup();

    public MenuBarCommand(Subscription sub) {
	this.subscription = sub;
    }
    
    public MenuBarCommand(SubscriptionGroup subGroup) {
	this.subGroup = subGroup;
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
	    CreateSubscriptionGroupDialogBox createGroup = new CreateSubscriptionGroupDialogBox();
	    createGroup.center();
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
	    EditSubscriptionGroupDialogBox editGroup = new EditSubscriptionGroupDialogBox();
	    editGroup.center();
	    
	}
    }
    
    public static class DeleteSubscriptionCommand implements Command {

	@Override
	public void execute() {
	    DeleteSubscriptionDialogBox deleteSubscription = new DeleteSubscriptionDialogBox();
	    deleteSubscription.center();   
	}
    }
    
    public static class DeleteSubscriptionGroupCommand implements Command {

	@Override
	public void execute() {
	    DeleteSubscriptionGroupDialogBox deleteGroup = new DeleteSubscriptionGroupDialogBox();
	    deleteGroup.center();
	    
	}
    }
    
    public static class AddSubscriptionToGroupCommand implements Command {

	@Override
	public void execute() {
	    AddSubscriptionToGroupDialogBox addSubscriptionToGroup = new AddSubscriptionToGroupDialogBox();
	    addSubscriptionToGroup.center();
	}
    }
    
    public static class RemoveSubscriptionFromGroupCommand implements Command {

	@Override
	public void execute() {
	    RemoveSubscriptionFromGroupDialogBox removeSubscriptionFromGroup = new RemoveSubscriptionFromGroupDialogBox();
	    removeSubscriptionFromGroup.center();
	    
	}
    }
}
