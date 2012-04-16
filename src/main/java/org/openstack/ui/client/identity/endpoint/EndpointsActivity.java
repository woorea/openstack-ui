package org.openstack.ui.client.identity.endpoint;

import org.openstack.admin.client.Administration;
import org.openstack.ui.client.compute.common.AdministrationPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EndpointsActivity extends AbstractActivity implements EndpointsView.Presenter {
	
	public static final EndpointsView VIEW = new EndpointsView();

	private AdministrationPlace place;

	public EndpointsActivity(AdministrationPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
	}

	@Override
	public void onCreate() {
		CreateEndpoint widget = new CreateEndpoint();
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

}
