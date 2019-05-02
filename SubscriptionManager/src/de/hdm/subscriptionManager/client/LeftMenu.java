package de.hdm.subscriptionManager.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
	
	subButton.addClickHandler(new ClickHandler() {
	    
	    @Override
	    public void onClick(ClickEvent event) {
		Window.alert("Hi");
		
	    }
	});

	
	menuContainerPanel.add(subButtonPanel);
	RootPanel.get("leftmenu").add(menuContainerPanel);
    }
}
