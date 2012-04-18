package org.openstack.ui.client.compute.server;

import org.openstack.portal.client.PortalActivity;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ServersActivity extends PortalActivity {
	
	public static final ServersView VIEW = new ServersView();

	public ServersActivity(PortalPlace place) {
		super(place);
	}

	@Override
	public void startOnPanel(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(VIEW);
		VIEW.refresh();
	}

}
