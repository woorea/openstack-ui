package org.openstack.ui.client.compute.keypair;

import org.openstack.portal.client.Portal;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
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
