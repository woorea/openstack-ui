package org.openstack.ui.client.compute.keypair;

import org.openstack.model.compute.KeyPair;
import org.openstack.portal.client.Portal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateKeyPair extends Composite implements CreateKeyPairForm.Listener {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, CreateKeyPair> {
	}
	
	@UiField CreateKeyPairForm createKeyPairForm;

	public CreateKeyPair() {
		initWidget(uiBinder.createAndBindUi(this));
		createKeyPairForm.setListener(this);
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}

	@Override
	public void onKeyPairCreated(KeyPair keyPair) {
		Portal.MODAL.hide();
	}

}
