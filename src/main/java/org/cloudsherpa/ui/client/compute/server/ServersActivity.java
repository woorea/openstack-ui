package org.cloudsherpa.ui.client.compute.server;

import org.cloudsherpa.portal.client.Portal;
import org.cloudsherpa.ui.client.compute.common.PortalPlace;
import org.openstack.model.compute.nova.NovaServerForCreate;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ServersActivity extends AbstractActivity implements ServersView.Presenter {
	
	public static final ServersView VIEW = new ServersView();

	private PortalPlace place;

	public ServersActivity(PortalPlace place) {
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
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
		
	}

	@Override
	public void onDelete() {
		onRefresh();
		
	}

	@Override
	public void onRefresh() {
		Window.alert("refresh...");
	}

	@Override
	public void onFilters() {
		ServersFilters widget = new ServersFilters();
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}

}
