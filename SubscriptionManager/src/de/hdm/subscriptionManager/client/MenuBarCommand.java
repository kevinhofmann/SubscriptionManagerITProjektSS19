package de.hdm.subscriptionManager.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.subscriptionManager.client.gui.AddSubscriptionToGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.CreateSubscriptionGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.DeleteSubscriptionDialogBox;
import de.hdm.subscriptionManager.client.gui.DeleteSubscriptionGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.EditSubscriptionGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.RemoveSubscriptionFromGroupDialogBox;
import de.hdm.subscriptionManager.client.gui.SubscriptionAndGroupOverview;
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
	    if(subscription.getId() > 0) {
		SubscriptionForm sf = new SubscriptionForm(subscription);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(sf);
	    } else {
		Window.alert("Bitte wähle zunächst ein Abo aus");
	    }
	}
    }

    public static class EditSubscriptionGroupCommand implements Command {

	@Override
	public void execute() {
	    if(subGroup.getId() > 0) {
		EditSubscriptionGroupDialogBox editGroup = new EditSubscriptionGroupDialogBox();
		editGroup.center();
	    } else {
		Window.alert("Bitte wähle zunächst eine Abogruppe aus");
	    }
	}
    }


    public static class DeleteSubscriptionCommand implements Command {

	@Override
	public void execute() {
	    if(subscription.getId() > 0) {
		DeleteSubscriptionDialogBox deleteSubscription = new DeleteSubscriptionDialogBox();
		deleteSubscription.center();   
	    } else {
		Window.alert("Bitte wähle zunächst ein Abo aus");
	    }
	}
    }

    public static class DeleteSubscriptionGroupCommand implements Command {

	@Override
	public void execute() {
	    if(subGroup.getId() > 0) {
		DeleteSubscriptionGroupDialogBox deleteGroup = new DeleteSubscriptionGroupDialogBox();
		deleteGroup.center();
	    } else {
		Window.alert("Bitte wähle zunächst eine Abogruppe aus");
	    }
	}
    }

    public static class AddSubscriptionToGroupCommand implements Command {

	@Override
	public void execute() {
	    if(subscription.getId() > 0) {
		AddSubscriptionToGroupDialogBox addSubscriptionToGroup = new AddSubscriptionToGroupDialogBox();
		addSubscriptionToGroup.center();
	    } else {
		Window.alert("Bitte wähle zunächst ein Abo aus");
	    }
	}
    }


    public static class RemoveSubscriptionFromGroupCommand implements Command {

	@Override
	public void execute() {
	    if(subGroup.getId() > 0) {
		RemoveSubscriptionFromGroupDialogBox removeSubscriptionFromGroup = new RemoveSubscriptionFromGroupDialogBox();
		removeSubscriptionFromGroup.center();
	    } else {
		Window.alert("Bitte wähle zunächst eine Abogruppe aus");
	    }
	}
    }
    
    public static class DisplayStatisticOverview implements Command {

	@Override
	public void execute() {
		SubscriptionAndGroupOverview subscriptionOverview = new SubscriptionAndGroupOverview(subscription);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(subscriptionOverview);
	}
	
    }

}
