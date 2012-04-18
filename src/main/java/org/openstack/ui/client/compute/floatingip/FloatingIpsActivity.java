package org.openstack.ui.client.compute.floatingip;

import org.openstack.model.compute.FloatingIp;
import org.openstack.portal.client.PortalActivity;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class FloatingIpsActivity extends PortalActivity implements FloatingIpsView.Presenter {
	
	public static final FloatingIpsView VIEW = new FloatingIpsView();

	public FloatingIpsActivity(PortalPlace place) {
		super(place);
	}

	@Override
	public void startOnPanel(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
		VIEW.refresh();
	}

	@Override
	public void onCreate() {
		
	}

	@Override
	public void onDelete() {
		onRefresh();
		
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onAssociate(FloatingIp floatingIp) {
		VIEW.refresh();
	}

	@Override
	public void onDisassociate() {
		
	}

}
