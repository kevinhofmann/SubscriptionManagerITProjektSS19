package de.hdm.subscriptionManager.client;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.CellPreviewEvent.Handler;

import de.hdm.subscriptionManager.shared.SubscriptionManagerAdminAsync;
import de.hdm.subscriptionManager.shared.bo.Cancellation;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.User;

public class SubscriptionCellTable extends CellTable<Subscription> {

    private static SubscriptionManagerAdminAsync subscriptionManagerAdmin = ClientsideSettings.getSubscriptionManagerAdmin();

    private List<Subscription> allSelectedSubscriptions = new ArrayList<>();
    private Subscription subscription = null;
    private final MultiSelectionModel<Subscription> subscriptionSelectionModel = new MultiSelectionModel<Subscription>();
    private final Handler<Subscription> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager();
    private CellTable subscriptionCellTable = new CellTable<Subscription>(15, (com.google.gwt.user.cellview.client.CellTable.Resources) GWT.create(CellTableResources.class));
    private User user = new User();
    private DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("dd.MM.yyyy");
    private Cancellation cancellation = new Cancellation();

    public SubscriptionCellTable() {
	this.setSelectionModel(subscriptionSelectionModel, selectionEventManager);
	run();
    }

    public MultiSelectionModel<Subscription> getSubscriptionSelectionModel() {
	return subscriptionSelectionModel;
    }

    public void run() {
	this.setEmptyTableWidget(new Label("Diese Abogruppe ist leer"));
	user.setId(1);
    }

    public class CheckColumn extends Column<Subscription, Boolean> {

	public CheckColumn(Cell<Boolean> cell) {
	    super(cell);
	    // TODO Auto-generated constructor stub
	} public Boolean getValue(Subscription subscription) {
	    allSelectedSubscriptions.add(subscription);
	    return subscriptionSelectionModel.isSelected(subscription);
	}
    }

    public class NameColumn extends Column<Subscription, String> {

	public NameColumn(Cell<String> cell) {
	    super(cell);
	}

	public String getValue(Subscription subscription) {
	    return subscription.getName();
	}
    }

    public class PriceColumn extends Column<Subscription, String> {

	public PriceColumn(Cell<String> numberCell) {
	    super(numberCell);
	}

	public String getValue(Subscription subscription) {
	    return Float.toString(subscription.getPrice()) + " €";
	}
    }

    public class NoteColumn extends Column<Subscription, String> {

	public NoteColumn(Cell<String> cell) {
	    super(cell);
	}

	public String getValue(Subscription subscription) {
	    return subscription.getNote();
	}
    }

    public class StartDateColumn extends Column<Subscription, String> {

	public StartDateColumn(Cell<String> cell) {
	    super(cell);
	}

	public String getValue(Subscription subscription) {
	    return dateTimeFormat.format(subscription.getStartDate());
	}
    }

    public class EndDateColumn extends Column<Subscription, String> {

	public EndDateColumn(Cell<String> cell) {
	    super(cell);
	}

	public String getValue(Subscription subscription) {
	    if(subscription.getCancellationRelevance()) {
		return dateTimeFormat.format(subscription.getEndDate());
	} else {
	    return "-";
	}
    }
    }

    public class TotalCostColumn extends Column<Subscription, String> {

	public TotalCostColumn(Cell<String> cell) {
	    super(cell);
	}

	public String getValue(Subscription subscription) {
	    Date today = new Date();
	    Date subscriptionStartDate = subscription.getStartDate();
	    long diff = today.getTime() - subscriptionStartDate.getTime();
	    long numberOfDays = diff / (1000*60*60*24);
	    double dailyPrice;
	    dailyPrice = subscription.getPrice() / 30;
	    return Double.toString(Math.round(100.0 * dailyPrice * numberOfDays) / 100.0) + " €";

	}
    }




}
