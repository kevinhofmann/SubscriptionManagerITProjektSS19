package de.hdm.subscriptionManager.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LeftMenu extends VerticalPanel {
    
	
	private VerticalPanel menuContainerPanel = new VerticalPanel();
	private HorizontalPanel subButtonPanel = new HorizontalPanel();
	private Button subButton = new Button();
	private Button subGroupButton = new Button();

    public LeftMenu() {
	subButton.setText("Abos");
	subGroupButton.setText("Abogruppen");
	subButton.setStylePrimaryName("subSelectionButton");
	subGroupButton.setStylePrimaryName("subSelectionButton");
	subButtonPanel.add(subButton);
	subButtonPanel.add(subGroupButton);
	subButtonPanel.setStylePrimaryName("subSelectionButtonPanel");

	
	menuContainerPanel.add(subButtonPanel);
	RootPanel.get("leftmenu").add(menuContainerPanel);
    }
}
