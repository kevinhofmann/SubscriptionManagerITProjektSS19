package de.hdm.subscriptionManager.client;

import com.google.gwt.core.client.GWT;

/*
 * Durch das Interface wird es ermöglicht, die standardmäßige GWT style theme
 * durch CSS Anweisungen abzuändern. Dadurch kann das Aussehen des CellTables verändert 
 * werden. In der CellTableResources.css sind alle für den CellTable definierten
 * CSS Befehle hinterlegt, die dann abgeändert werden können.
 * 
 * @author Kevin
 * 
 */
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Style;

public interface CellTableResources extends CellTable.Resources {

    interface TableStyle extends CellTable.Style {
}
	    @Override
	    @Source({ CellTable.Style.DEFAULT_CSS, "CellTableStyle.css" })
	    public Style cellTableStyle();
}
