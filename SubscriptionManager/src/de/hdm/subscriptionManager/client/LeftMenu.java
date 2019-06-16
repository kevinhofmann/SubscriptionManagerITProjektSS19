package de.hdm.subscriptionManager.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.subscriptionManager.client.gui.SubscriptionAndGroupOverview;
import de.hdm.subscriptionManager.client.gui.SubscriptionView;
import de.hdm.subscriptionManager.client.gui.ViewAllSubscriptionsOfGroup;
import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;
import de.hdm.subscriptionManager.shared.bo.User;


/*
 * Klasse, welche das Selektionsmenü im linken Bereich der Seite umsetzt. Über zwei Toggle-Buttons wird
 * entweder die Subscription Ansicht (einzelne Abos in der CellList) oder die Sub-Group View
 * (Abogruppen in der CellList) angezeigt. Ein Klick auf ein Objekt löst dann die Anzeige eines Abos 
 * oder einer Gruppe aus.
 */
public class LeftMenu extends VerticalPanel {


    private VerticalPanel menuContainerPanel = new VerticalPanel();
    private HorizontalPanel subButtonPanel = new HorizontalPanel();
    private ToggleButton subButton = new ToggleButton();
    private ToggleButton subGroupButton = new ToggleButton();

    private Subscription subscription = new Subscription();
    private static Subscription selectedSubscription = new Subscription();
    private SubscriptionGroup subscriptionGroup = new SubscriptionGroup();
    private static SubscriptionGroup selectedSubscriptionGroup = new SubscriptionGroup();

    private ScrollPanel subCellListPanel = new ScrollPanel();
    private User user = new User();
    private CellList<Subscription> subscriptionCellList = new CellList<Subscription>(new SubscriptionCell(), CellListResources.INSTANCE);
    private ArrayList<Subscription> subscriptionList = new ArrayList<Subscription>();
    private CellList<SubscriptionGroup> subscriptionGroupCellList = new CellList<SubscriptionGroup>(new SubscriptionGroupCell(), CellListResources.INSTANCE);
    private ArrayList<SubscriptionGroup> subscriptionGroupList = new ArrayList<SubscriptionGroup>();

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();


    /*
     * Standardkonstruktor der als Aktivator für den Toggle-Modus der Subscription dient. RPC-Call
     * wird ausgeführt um alle angelegten Abos eines Nutzers zu erhalten sowie ein SingleSelectionModel
     * implementiert, durch welches die Selektion eines NUtzers registriert werden kann und besagtes 
     * Abo zur Anzeige gebracht werden kann.
     */
    public LeftMenu() {
	subButton.setValue(true);
	subGroupButton.setValue(false);

	user.setId(1);
	subscriptionManagerAdmin.getAllSubscriptions(user.getId(), new getAllSubscriptionsCallback());


	subscriptionCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

	final SingleSelectionModel<Subscription> selectionModel = new SingleSelectionModel<Subscription>();
	subscriptionCellList.setSelectionModel(selectionModel);
	selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	    public void onSelectionChange(SelectionChangeEvent event) {
		selectedSubscription = selectionModel.getSelectedObject();
		if(selectedSubscription != null) {
		    SubscriptionView subscriptionView = new SubscriptionView(selectedSubscription);
		    Menubar menubar = new Menubar(selectedSubscription);
		    RootPanel.get("content").add(subscriptionView);
		}
	    }
	});

	subCellListPanel.add(subscriptionCellList);
	displayMenu();
    }


    /*
     * Getter-Methode um das selektierte Subscription Objekt aus dem SingleSelectionModel auszulesen
     */
    public static Subscription getSelectedSubscription() {
	return selectedSubscription;
    }


    /*
     * Konstruktor mit SubGroup Parameter, durch welchen im linken Men� dann die einzelnen
     * Gruppen angezeigt werden anstatt einzelner Abos
     */
    public LeftMenu(SubscriptionGroup subGroup) {
	subButton.setValue(false);
	subGroupButton.setValue(true);

	user.setId(1);
	subscriptionManagerAdmin.getAllSubscriptionGroups(user.getId(), new getAllSubscriptionGroupsCallback());


	subscriptionGroupCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

	final SingleSelectionModel<SubscriptionGroup> selectionModel = new SingleSelectionModel<SubscriptionGroup>();
	subscriptionGroupCellList.setSelectionModel(selectionModel);
	selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	    public void onSelectionChange(SelectionChangeEvent event) {
		selectedSubscriptionGroup = selectionModel.getSelectedObject();
		if(selectedSubscriptionGroup != null) {
		    MenuBarCommand mbc = new MenuBarCommand(selectedSubscriptionGroup);
		    ViewAllSubscriptionsOfGroup viewSubscriptions = new ViewAllSubscriptionsOfGroup();
		    RootPanel.get("content").add(viewSubscriptions);
		}
	    }
	});

	subCellListPanel.add(subscriptionGroupCellList);
	displayMenu();
    }



    /*
     * Getter-Methode um das selektierte SubGroup Objekt aus dem SingleSelectionModel auszulesen
     */
    public static SubscriptionGroup getSelectedSubscriptionGroup() {
	return selectedSubscriptionGroup;
    }


    /*
     * Methode zur Erstellung und Zuordnung von Panels und ClickHandlern, welche G�ltigkeit
     * sowohl bei Sub als auch bei der SubGroup Anzeige haben
     */
    public void displayMenu() {
	subButton.setText("Abos");
	subGroupButton.setText("Abogruppen");
	subButtonPanel.add(subButton);
	subButtonPanel.add(subGroupButton);
	subButtonPanel.setStylePrimaryName("subSelectionButtonPanel");


	subButton.addClickHandler(new ClickHandler() {

	    @Override
	    public void onClick(ClickEvent event) {
		Menubar menuBar = new Menubar(subscription);
		LeftMenu leftMenuSub = new LeftMenu();

	    }
	});

	subGroupButton.addClickHandler(new ClickHandler() {

	    @Override
	    public void onClick(ClickEvent event) {
		Menubar menuBar = new Menubar(subscriptionGroup);
		LeftMenu leftMenuSubGroup = new LeftMenu(subscriptionGroup); 
//		SubscriptionAndGroupOverview subGroupOverview = new SubscriptionAndGroupOverview(subscriptionGroup);
//		RootPanel.get("content").clear();
//		RootPanel.get("content").add(subGroupOverview);
	    }
	});

	menuContainerPanel.add(subButtonPanel);
	subCellListPanel.setStylePrimaryName("leftMenuScrollPanel");
	menuContainerPanel.add(subCellListPanel);
	RootPanel.get("leftmenu").clear();
	RootPanel.get("leftmenu").add(menuContainerPanel);

    }

    /*
     * Async Callback für die Abfrage der Subscriptions. Die zurückgegebene ArrayList mit Subscription
     * Objekten wird in eine lokale ArrayListe übernommen und der CellList als Datenquelle für
     * die Anzeige der einzelnen Abos zugewiesen
     */
    class getAllSubscriptionsCallback implements AsyncCallback<ArrayList<Subscription>> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(ArrayList<Subscription> result) {
	    for(Subscription sub : result) {
		subscriptionList.add(sub);
		List<Subscription> list = subscriptionList;
		subscriptionCellList.setRowData(0, list);
	    }
	}
    }

    /*
     * Async Callback f�r die Abfrage der SubGroups.  Die zurückgegebene ArrayList mit SubGroup
     * Objekten wird in eine lokale ArrayListe übernommen und der CellList als Datenquelle für
     * die Anzeige der einzelnen Abogruppen zugewiesen
     */
    class getAllSubscriptionGroupsCallback implements AsyncCallback<ArrayList<SubscriptionGroup>> {

	@Override
	public void onFailure(Throwable caught) {
	    Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(ArrayList<SubscriptionGroup> result) {
	    for(SubscriptionGroup subGroup : result) {
		subscriptionGroupList.add(subGroup);
		List<SubscriptionGroup> list = subscriptionGroupList;
		subscriptionGroupCellList.setRowData(0, list);
	    }
	}
    }


    /*
     * Statische Klasse für die Anzeige des entsprechenden Wertes aus dem Subscription Objekt
     * in einer Zelle. 
     */
    static class SubscriptionCell extends AbstractCell<Subscription> {

	@Override
	public void render(Context context, Subscription value, SafeHtmlBuilder sb) {

	    if (value == null) {
		return;
	    }
	    sb.appendEscaped(value.getName());
	}
    }


    /*
     * Statische Klasse für die Anzeige des entsprechenden Wertes aus dem SubGroup Objekt
     * in einer Zelle. 
     */
    static class SubscriptionGroupCell extends AbstractCell<SubscriptionGroup> {

	@Override
	public void render(Context context, SubscriptionGroup value, SafeHtmlBuilder sb) {
	    if (value == null) {
		return;
	    }
	    sb.appendEscaped(value.getName());
	}
    }
}
