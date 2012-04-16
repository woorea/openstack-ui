package org.cloudsherpa.ui.client.compute.server;

import org.cloudsherpa.ui.client.compute.common.PortalPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ServersActivity extends AbstractActivity {
	
	public static final ServersView VIEW = new ServersView();

	private PortalPlace place;

	public ServersActivity(PortalPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(VIEW);
	}

}
