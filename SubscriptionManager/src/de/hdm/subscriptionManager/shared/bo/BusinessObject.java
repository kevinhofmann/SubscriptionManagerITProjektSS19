package de.hdm.subscriptionManager.shared.bo;

import java.io.Serializable;

/**
 * <p>
 * Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt für die Umsetzung des Fachkonzepts relevanten Klassen dar.
 * </p>
 * <p>
 * Zentrales Merkmal ist, dass jedes <code>BusinessObject</code> eine Nummer
 * besitzt, die man in einer relationalen Datenbank auch als Primärschlüssel
 * bezeichnen würde. Fernen ist jedes <code>BusinessObject</code> als
 * {@link Serializable} gekennzeichnet. Durch diese Eigenschaft kann jedes
 * <code>BusinessObject</code> automatisch in eine textuelle Form überführt und
 * z.B. zwischen Client und Server transportiert werden. Bei GWT RPC ist diese
 * textuelle Notation in JSON (siehe http://www.json.org/) kodiert. 
 * Der Import Serializable kann eventuell zu Problemen nach dem Deployement führen. 
 * Die Lösung hierzu ist IsSerializable zu verwenden, welches ein Import aus dem GWT Package ist.  
 * </p>
 * 
 * @author thies
 * @version 1.0
 */


public abstract class BusinessObject implements Serializable {

    
    /**
     * Dient zum Serialisieren von Objekten für eine RPC fähigen Austausch zwischen Server und Client.
     */
    private static final long serialVersionUID = 1L;

    
    /**
     * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse.
     */
    private int id = 0;


    /**
     * Auslesen der ID
     * @return id
     */
    public int getId() {
	return this.id;
    }

    
    /**
     * Setzen der ID
     * @param id
     */
    public void setId(int id) {
	this.id = id;
    }

}
