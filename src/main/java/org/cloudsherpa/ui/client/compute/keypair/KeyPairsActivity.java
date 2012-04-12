package org.cloudsherpa.ui.client.compute.keypair;

import org.cloudsherpa.portal.client.Portal;
import org.cloudsherpa.ui.client.compute.common.PortalPlace;
import org.cloudsherpa.ui.client.compute.server.CreateServerWizard;
import org.cloudsherpa.ui.client.compute.server.ServersFilters;
import org.cloudsherpa.ui.client.compute.server.ServersView;
import org.cloudsherpa.ui.client.compute.server.ServersView.Presenter;
import org.cloudsherpa.ui.client.compute.volume.VolumesView;
import org.openstack.model.compute.nova.NovaServerForCreate;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class KeyPairsActivity extends AbstractActivity implements KeyPairsView.Presenter {
	
	public static final KeyPairsView VIEW = new KeyPairsView();

	private PortalPlace place;

	public KeyPairsActivity(PortalPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
	}

	@Override
	public void onCreate() {
		CreateKeyPair widget = new CreateKeyPair();
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
		
	}

	@Override
	public void onDelete() {
		onRefresh();
		
	}

	@Override
	public void onRefresh() {
		
	}
	
	@Override
	public void onImportPublicKey() {
		CreateKeyPair widget = new CreateKeyPair();
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
		
	}

}
