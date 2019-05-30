package de.hdm.subscriptionManager.client.gui;

import java.sql.Date;
import java.util.ArrayList;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.subscriptionManager.client.ClientsideSettings;
import de.hdm.subscriptionManager.client.LeftMenu;
import de.hdm.subscriptionManager.client.SubscriptionCellTable;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;

import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;

public class ViewAllSubscriptionsOfGroup extends SubscriptionCellTable {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private VerticalPanel cellTableContainerPanel = new VerticalPanel();
    private ClickableTextCell clickCell = new ClickableTextCell();
    private CheckboxCell checkBoxCell = new CheckboxCell(true, false);
    private ArrayList<Subscription> allSubscriptionsByGroupArrayList = new ArrayList<>();
    private static ArrayList<Subscription> selectedSubscriptionsInTableArrayList = new ArrayList<>();

    private SubscriptionGroup subGroup = new SubscriptionGroup();

    private SubscriptionCellTable allSubscriptionsCellTable = null;
    private SubscriptionCellTable.CheckColumn checkColumn = null;
    private SubscriptionCellTable.NameColumn nameColumn = null;
    private SubscriptionCellTable.PriceColumn priceColumn = null;
    private SubscriptionCellTable.NoteColumn noteColumn = null;
    private SubscriptionCellTable.StartDateColumn startDateColumn = null;
    private SubscriptionCellTable.EndDateColumn endDateColumn = null;
    private SubscriptionCellTable.TotalCostColumn totalCostColumn = null;

    public ViewAllSubscriptionsOfGroup() {
	this.subGroup = LeftMenu.getSelectedSubscriptionGroup();
	subscriptionManagerAdmin.getAllSubscriptionsWithinGroup(subGroup.getId(), new GetAllSubscriptionsOfGroup());
    }
    
    public static ArrayList<Subscription> getSelectedSubscriptionsOfTable() {
	  return selectedSubscriptionsInTableArrayList;
    }

    public void onLoad() {
	allSubscriptionsCellTable = new SubscriptionCellTable();
	checkColumn = allSubscriptionsCellTable.new CheckColumn(checkBoxCell);
	nameColumn = allSubscriptionsCellTable.new NameColumn(clickCell);
	priceColumn = allSubscriptionsCellTable.new PriceColumn(clickCell);
	noteColumn = allSubscriptionsCellTable.new NoteColumn(clickCell);
	startDateColumn = allSubscriptionsCellTable.new StartDateColumn(clickCell);
	endDateColumn = allSubscriptionsCellTable.new EndDateColumn(clickCell);
	totalCostColumn = allSubscriptionsCellTable.new TotalCostColumn(clickCell);
	

	allSubscriptionsCellTable.addColumn(checkColumn);
	allSubscriptionsCellTable.setColumnWidth(checkColumn, 2, Unit.EM);
	allSubscriptionsCellTable.addColumn(nameColumn, "Name");
	allSubscriptionsCellTable.setColumnWidth(nameColumn, 12, Unit.EM);
	allSubscriptionsCellTable.addColumn(priceColumn, "Preis");
	allSubscriptionsCellTable.setColumnWidth(priceColumn, 4, Unit.EM);
	allSubscriptionsCellTable.addColumn(noteColumn, "Bemerkung");
	allSubscriptionsCellTable.setColumnWidth(noteColumn, 14, Unit.EM);
	allSubscriptionsCellTable.addColumn(startDateColumn, "Beginn Laufzeit");
	allSubscriptionsCellTable.setColumnWidth(startDateColumn, 14, Unit.EM);
	allSubscriptionsCellTable.addColumn(endDateColumn, "Ende");
	allSubscriptionsCellTable.setColumnWidth(endDateColumn, 14, Unit.EM);
	allSubscriptionsCellTable.addColumn(totalCostColumn, "Gesamtkosten");
	allSubscriptionsCellTable.setColumnWidth(totalCostColumn, 2, Unit.EM);
	
	allSubscriptionsCellTable.getSubscriptionSelectionModel().addSelectionChangeHandler(new SelectionChangeHandlerCellTable());

	cellTableContainerPanel.add(allSubscriptionsCellTable);
	RootPanel.get("content").clear();
	RootPanel.get("content").add(cellTableContainerPanel);
    }


    public class GetAllSubscriptionsOfGroup implements AsyncCallback<ArrayList<Subscription>> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert("Beim Laden der Daten ist ein Fehler aufgetreten" + caught.getMessage());

	}

	@Override
	public void onSuccess(ArrayList<Subscription> result) {
	    Range range = new Range(0, result.size());
	    allSubscriptionsCellTable.setVisibleRangeAndClearData(range, true);
	    allSubscriptionsByGroupArrayList.clear();
	    for(Subscription sub : result) {
		allSubscriptionsByGroupArrayList.add(sub);
	    }
	    allSubscriptionsCellTable.setRowCount(allSubscriptionsByGroupArrayList.size(), true);
	    allSubscriptionsCellTable.setRowData(0, allSubscriptionsByGroupArrayList);
	}
    }

    public class SelectionChangeHandlerCellTable implements SelectionChangeEvent.Handler {

	@Override
	public void onSelectionChange(SelectionChangeEvent event) {
	    selectedSubscriptionsInTableArrayList.clear();
	    selectedSubscriptionsInTableArrayList.addAll(allSubscriptionsCellTable.getSubscriptionSelectionModel().getSelectedSet());
	}
    }
    

    
}
