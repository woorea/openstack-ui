package org.cloudsherpa.ui.client.compute.keypair;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.KeyPair;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateKeyPair extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateKeyPair> {
	}

	public CreateKeyPair() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public interface Listener {

		void onSave(KeyPair service);
		
	}
	
	private Listener listener;
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		/*
		KeyPair item = new NovaKeyPairForCreate();
		item.setVolumeId(volumeId);
		item.setName(name.getValue());
		item.setDescription(description.getValue());
		item.setForce(force.getValue());
		Portal.CLOUD.create(item, new AsyncCallback<KeyPair>() {
			
			@Override
			public void onSuccess(KeyPair result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
		*/
	}

}
