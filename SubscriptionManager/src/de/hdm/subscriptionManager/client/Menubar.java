package de.hdm.subscriptionManager.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
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
    
    private MenuBar menubar = new MenuBar();
    private MenuBar addMenu = new MenuBar();
    private MenuBar editMenu = new MenuBar();
    private MenuBar deleteMenu = new MenuBar();
    private MenuBar manageGroupMenu = new MenuBar();
    
    private HorizontalPanel menuBarHorizontalPanel = new HorizontalPanel();
    
    private MenuItem createSubscription = new MenuItem("Neues Abo anlegen", new MenuBarCommand.CreateSubscriptionCommand());
    private MenuItem createSubscriptionGroup = new MenuItem("Neue Abogruppe anlegen", new MenuBarCommand.CreateSubscriptionGroupCommand());
    private MenuItem editSubscription = new MenuItem("Abo bearbeiten", new MenuBarCommand.EditSubscriptionCommand());
    private MenuItem editSubscriptionGroup = new MenuItem("Abogruppe bearbeiten", new MenuBarCommand.EditSubscriptionGroupCommand());
    private MenuItem deleteSubscription = new MenuItem("Abo entfernen", new MenuBarCommand.DeleteSubscriptionCommand());
    private MenuItem deleteSubscriptionGroup = new MenuItem("Abogruppe löschen", new MenuBarCommand.DeleteSubscriptionGroupCommand());
    private MenuItem addSubscriptionToGroup = new MenuItem("Abo zur Gruppe", new MenuBarCommand.AddSubscriptionToGroupCommand());
    private MenuItem removeSubscriptionFromGroup = new MenuItem("Abo aus Gruppe entfernen", new MenuBarCommand.RemoveSubscriptionFromGroupCommand());

    
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
	addMenu.addItem("Neu", new MenuBarCommand.CreateSubscriptionCommand());
	editMenu.addItem("Bearbeiten", new MenuBarCommand.EditSubscriptionCommand());
	deleteMenu.addItem("Löschen", new MenuBarCommand.DeleteSubscriptionCommand());
	manageGroupMenu.addItem("Hinzufügen", new MenuBarCommand.AddSubscriptionToGroupCommand());
	run();
    }
    
    public Menubar(SubscriptionGroup subGroup) {
	addMenu.addItem("Neu", new MenuBarCommand.CreateSubscriptionGroupCommand());
	editMenu.addItem("Bearbeiten", new MenuBarCommand.EditSubscriptionGroupCommand());
	deleteMenu.addItem("Löschen", new MenuBarCommand.DeleteSubscriptionGroupCommand());
	manageGroupMenu.addItem("Entfernen", new MenuBarCommand.RemoveSubscriptionFromGroupCommand());
	run();
    }
    
    private void run() {
	menuBarHorizontalPanel.add(addMenu);
	menuBarHorizontalPanel.add(editMenu);
	menuBarHorizontalPanel.add(deleteMenu);
	menuBarHorizontalPanel.add(manageGroupMenu);
	menuBarHorizontalPanel.add(menubar);
	RootPanel.get("menubar").clear();
	RootPanel.get("menubar").add(menuBarHorizontalPanel);
    }
    

    
}