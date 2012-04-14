package org.cloudsherpa.ui.client.compute.volume;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.Volume;
import org.openstack.model.compute.nova.volume.NovaVolumeForCreate;
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateVolume extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateVolume> {
	}

	public CreateVolume() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	public interface Listener {

		void onSave(Volume service);
		
	}
	
	private Listener listener;
	
	@UiField TextBox name;
	@UiField TextBox description;
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		VolumeForCreate item = new NovaVolumeForCreate();
		item.setName(name.getValue());
		item.setDescription(description.getValue());
		Portal.CLOUD.create(item, new AsyncCallback<Volume>() {
			
			@Override
			public void onSuccess(Volume result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
