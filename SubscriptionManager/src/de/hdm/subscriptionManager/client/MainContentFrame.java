package de.hdm.subscriptionManager.client;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class MainContentFrame extends VerticalPanel {

	
	/**
	 * Automatisch geladene Methode onLoad(). Löscht den Div-Container content und ruft die Methode run() auf.
	 */
	public void onLoad() {
	    /*
	     * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es der
	     * Superklasse eine Initialisierung vorzunehmen.
	     */
	    super.onLoad();
	    RootPanel.get("content").clear();	  
	    this.run();
	}

	protected abstract void run();
	
}