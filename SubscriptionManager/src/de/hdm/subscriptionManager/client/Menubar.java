package de.hdm.subscriptionManager.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;

/*
 * Die Klasse Menubar erzeugt die Aktionsbuttons über dem content div Element. Die Buttons dienen dazu,
 * Aktionen wie das hinzufügen, bearbeiten, löschen und verwalten von Subscription + SubGroup zu 
 * ermöglichen.In der separaten Klasse MenuBarCommand werden die auszuführenden Befehle, die beim Klick
 * auf den Button ausgeführt werden sollen, implementiert. 
 */
public class Menubar extends MenuBar {
  
    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();
    
    private Subscription subscription = new Subscription();
    private SubscriptionGroup subGroup = new SubscriptionGroup();
    
    private MenuBar addMenu = new MenuBar();
    private MenuBar editMenu = new MenuBar();
    private MenuBar deleteMenu = new MenuBar();
    private MenuBar manageGroupMenu = new MenuBar();
    private MenuBar statisticsOverview = new MenuBar();
    
    private HorizontalPanel menuBarHorizontalPanel = new HorizontalPanel();
    
    final String statsImage = "<img src=\"images/statistic.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    final String addSubImage = "<img src=\"images/add-sub.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    final String editSubImage = "<img src=\"images/edit-sub.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    final String deleteSubImage = "<img src=\"images/delete-sub.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    final String addGroup = "<img src=\"images/add-group.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    final String editGroup = "<img src=\"images/edit-group.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    final String deleteGroup = "<img src=\"images/delete-group.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    final String intoGroup = "<img src=\"images/intoGroup.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    final String outOfGroup = "<img src=\"images/outOfGroup.png\" height='25px' width='24px' style=\"display: block; margin: auto\">";
    /*
     * Über verschiedene Konstruktoren, auch parametrisierte, werden die zugrundeliegenden Commands gesteuert.
     * Ist im LeftMenu der ToggleButton auf Subscription true, so wird entsprechend ein Subscription
     * Objekt an die Klasse Menubar übergeben, was dann zur Initialisierung der Subscription Commands dient.
     */
    public Menubar() {
	RootPanel.get("menubar").clear();
	run();
    }
    
    public Menubar(Subscription sub) {
	this.subscription = sub;
	MenuBarCommand mbc = new MenuBarCommand(subscription);
	addMenu.addItem(addSubImage, true, new MenuBarCommand.CreateSubscriptionCommand());
	editMenu.addItem(editSubImage, true, new MenuBarCommand.EditSubscriptionCommand());
	deleteMenu.addItem(deleteSubImage, true, new MenuBarCommand.DeleteSubscriptionCommand());
	manageGroupMenu.addItem(intoGroup, true, new MenuBarCommand.AddSubscriptionToGroupCommand());
	run();
    }
    
    public Menubar(SubscriptionGroup subGroup) {
	this.subGroup = subGroup;
	MenuBarCommand mbc = new MenuBarCommand(subGroup);
	addMenu.addItem(addGroup, true, new MenuBarCommand.CreateSubscriptionGroupCommand());
	editMenu.addItem(editGroup, true, new MenuBarCommand.EditSubscriptionGroupCommand());
	deleteMenu.addItem(deleteGroup, true, new MenuBarCommand.DeleteSubscriptionGroupCommand());
	manageGroupMenu.addItem(outOfGroup, true, new MenuBarCommand.RemoveSubscriptionFromGroupCommand());
	run();
    }
    
    private void run() {
	menuBarHorizontalPanel.add(addMenu);
	menuBarHorizontalPanel.add(editMenu);
	menuBarHorizontalPanel.add(deleteMenu);
	menuBarHorizontalPanel.add(manageGroupMenu);
	statisticsOverview.addItem(statsImage, true, new MenuBarCommand.DisplayStatisticOverview());
	//statisticsOverview.addItem("Statistik", new MenuBarCommand.DisplayStatisticOverview());
	menuBarHorizontalPanel.add(statisticsOverview);
	RootPanel.get("menubar").clear();
	RootPanel.get("menubar").add(menuBarHorizontalPanel);
    }
    

    
}