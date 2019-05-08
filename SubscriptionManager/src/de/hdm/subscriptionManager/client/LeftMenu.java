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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.User;

public class LeftMenu extends VerticalPanel {


    private VerticalPanel menuContainerPanel = new VerticalPanel();
    private HorizontalPanel subButtonPanel = new HorizontalPanel();
    private Button subButton = new Button();
    private Button subGroupButton = new Button();

    private ScrollPanel subCellListPanel = new ScrollPanel();
    private User user = new User();
    private CellList<Subscription> subscriptionCellList = new CellList<Subscription>(new SubscriptionCell(), CellListResources.INSTANCE);
    private ArrayList<Subscription> subscriptionList = new ArrayList<Subscription>();

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();


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

	    }
	});

	user.setId(1);
	subscriptionManagerAdmin.getAllSubscriptions(user.getId(), new getAllSubscriptionsCallback());


	subscriptionCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

	final SingleSelectionModel<Subscription> selectionModel = new SingleSelectionModel<Subscription>();
	subscriptionCellList.setSelectionModel(selectionModel);
	selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	    public void onSelectionChange(SelectionChangeEvent event) {
		Subscription selectedSubscription = selectionModel.getSelectedObject();
		if(selectedSubscription != null) {

		    //ANZEIGE DER SUB IM MAINFRAME
		}
	    }
	});
	menuContainerPanel.add(subButtonPanel);
	subCellListPanel.add(subscriptionCellList);
	subCellListPanel.setStylePrimaryName("leftMenuScrollPanel");
	menuContainerPanel.add(subCellListPanel);
	RootPanel.get("leftmenu").clear();
	RootPanel.get("leftmenu").add(menuContainerPanel);
    }

    class getAllSubscriptionsCallback implements AsyncCallback<ArrayList<Subscription>> {

	@Override
	public void onFailure(Throwable caught) {
	    // TODO Auto-generated method stub

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
    
    static class SubscriptionCell extends AbstractCell<Subscription> {

	@Override
	public void render(Context context, Subscription value, SafeHtmlBuilder sb) {
		
	    if (value == null) {
			return;
		}
		sb.appendEscaped(value.getName());
	}
	
    }
    
    

}
