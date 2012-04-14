package org.cloudsherpa.ui.client.compute.volume;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.Volume;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class AttachVolume extends Composite {

	private static AttachVolumeUiBinder uiBinder = GWT
			.create(AttachVolumeUiBinder.class);

	interface AttachVolumeUiBinder extends UiBinder<Widget, AttachVolume> {
	}

	public AttachVolume() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	public interface Listener {

		void onSave(Volume volume);
		
	}
	
	private Listener listener;
	
	Integer volumeId;
	@UiField ListBox serverId;
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		
		Portal.CLOUD.attachVolume(volumeId, serverId.getItemText(serverId.getSelectedIndex()), new AsyncCallback<Volume>() {
			
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
