package de.hdm.subscriptionManager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;

public interface CellListResources extends CellList.Resources {

    public static CellListResources INSTANCE = GWT.create(CellListResources.class);

    @Override
    @Source("CellListStyle.css")
    public Style cellListStyle();
}
