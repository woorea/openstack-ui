package org.cloudsherpa.ui.client.compute.floatingip;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.Snapshot;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class AssociateFloatingIp extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, AssociateFloatingIp> {
	}

	public AssociateFloatingIp() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	public interface Listener {

		void onSave(FloatingIp service);
		
	}
	
	private Listener listener;
	
	String ip;
	@UiField ListBox serverId;
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		
		Portal.CLOUD.associateFloatingIp(ip, serverId.getItemText(serverId.getSelectedIndex()), new AsyncCallback<FloatingIp>() {
			
			@Override
			public void onSuccess(FloatingIp result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
