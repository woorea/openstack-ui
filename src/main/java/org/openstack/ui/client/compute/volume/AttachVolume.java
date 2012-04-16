package org.openstack.ui.client.compute.volume;

import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerList;
import org.openstack.model.compute.Volume;
import org.openstack.portal.client.Portal;

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
	
	public interface Listener {

		void onAttach(Volume volume);
		
	}
	
	private Listener listener;
	
	Volume volume;
	@UiField ListBox serverId;

	public AttachVolume() {
		Portal.CLOUD.listServers(0, 50, new AsyncCallback<ServerList>() {
			
			@Override
			public void onSuccess(ServerList result) {
				for(Server s : result) {
					serverId.addItem(s.getName() + " " + s.getId(), s.getId());
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		
		Portal.CLOUD.attachVolume(volume.getId(), serverId.getValue(serverId.getSelectedIndex()), new AsyncCallback<Volume>() {
			
			@Override
			public void onSuccess(Volume result) {
				Portal.MODAL.hide();
				listener.onAttach(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
