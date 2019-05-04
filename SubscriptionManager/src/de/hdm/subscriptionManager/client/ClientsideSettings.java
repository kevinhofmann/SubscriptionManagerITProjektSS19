package de.hdm.subscriptionManager.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;

import de.hdm.subscriptionManager.shared.CommonSettings;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdmin;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;


public class ClientsideSettings extends CommonSettings {

    /**
     * Deklarieren der Instanzvariablen der Proxy Objekte
     */
    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = null;


    private static final String LOGGER_NAME = "SubscriptionManager Web Client";

    /**
     * Instanz des Client-seitigen Loggers.
     */
    private static final Logger log = Logger.getLogger(LOGGER_NAME);
    /**
     * Methode zum Instanziieren des Proxy Objektes
     * 
     * @return subscriptionManagerAdmin: Instanzvariable des Proxy Objekts
     */
    public static SubscriptionManagerAdminAsync getSubscriptionManagerAdmin() {
	if (subscriptionManagerAdmin == null) {
	    subscriptionManagerAdmin = GWT.create(SubscriptionManagerAdmin.class);
	}
	return subscriptionManagerAdmin;
    }

    public static Logger getLogger() {
	return log;
    }
}

