package org.cloudsherpa.ui.client.identity.tenant;

import org.cloudsherpa.admin.client.Administration;
import org.cloudsherpa.ui.client.compute.common.AdministrationPlace;
import org.openstack.model.identity.Tenant;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.RangeChangeEvent;

public class TenantsActivity extends AbstractActivity implements TenantsView.Presenter, CreateTenant.Listener {
	
	public static final TenantsView VIEW = new TenantsView();

	private AdministrationPlace place;

	public TenantsActivity(AdministrationPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
	}

	@Override
	public void onCreate() {
		CreateTenant widget = new CreateTenant(this);
		Administration.MODAL.setWidget(widget);
		Administration.MODAL.center();
	}

	@Override
	public void onDelete() {
		
	}

	@Override
	public void onRefresh() {
		RangeChangeEvent.fire(VIEW.grid, VIEW.grid.getVisibleRange());
	}

	@Override
	public void onSave(Tenant tenant) {
		Administration.MODAL.hide();
		VIEW.refresh();
	}

}
