package org.cloudsherpa.ui.client.identity.tenant;

import org.cloudsherpa.admin.client.Administration;
import org.cloudsherpa.ui.client.compute.common.AdministrationPlace;
import org.cloudsherpa.ui.client.compute.server.CreateServerWizard;
import org.openstack.model.compute.nova.NovaServerForCreate;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class TenantsActivity extends AbstractActivity implements TenantsView.Presenter {
	
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
		CreateServerWizard widget = new CreateServerWizard();
		widget.edit(new NovaServerForCreate());
		Administration.MODAL.setWidget(widget);
		Administration.MODAL.center();
		
	}

	@Override
	public void onDelete() {
		onRefresh();
		
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onAttach() {
		CreateServerWizard widget = new CreateServerWizard();
		widget.edit(new NovaServerForCreate());
		Administration.MODAL.setWidget(widget);
		Administration.MODAL.center();
		
	}

	@Override
	public void onDetach() {
		CreateServerWizard widget = new CreateServerWizard();
		widget.edit(new NovaServerForCreate());
		Administration.MODAL.setWidget(widget);
		Administration.MODAL.center();
	}

}
