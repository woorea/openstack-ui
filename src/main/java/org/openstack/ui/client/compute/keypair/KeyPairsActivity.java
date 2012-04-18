package org.openstack.ui.client.compute.keypair;

import org.openstack.portal.client.Portal;
import org.openstack.portal.client.PortalActivity;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class KeyPairsActivity extends PortalActivity implements KeyPairsView.Presenter {
	
	public static final KeyPairsView VIEW = new KeyPairsView();

	public KeyPairsActivity(PortalPlace place) {
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
