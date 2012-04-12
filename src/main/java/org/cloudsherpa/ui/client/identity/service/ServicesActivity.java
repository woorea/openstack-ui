package org.cloudsherpa.ui.client.identity.service;

import org.cloudsherpa.admin.client.Administration;
import org.cloudsherpa.ui.client.compute.common.AdministrationPlace;
import org.cloudsherpa.ui.client.compute.server.CreateServerWizard;
import org.openstack.model.compute.nova.NovaServerForCreate;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ServicesActivity extends AbstractActivity implements ServicesView.Presenter {
	
	public static final ServicesView VIEW = new ServicesView();

	private AdministrationPlace place;

	public ServicesActivity(AdministrationPlace place) {
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
