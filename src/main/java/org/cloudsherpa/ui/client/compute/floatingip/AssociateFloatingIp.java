package org.cloudsherpa.ui.client.compute.floatingip;

import java.util.List;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.Server;

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
	
	public interface Listener {

		void onAssociate(FloatingIp service);
		
	}
	
	private Listener listener;
	
	FloatingIp floatingIp;
	@UiField ListBox serverId;

	public AssociateFloatingIp() {
		Portal.CLOUD.listServers(0, 50, new AsyncCallback<List<Server>>() {
			
			@Override
			public void onSuccess(List<Server> result) {
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
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	

	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		Portal.CLOUD.associateFloatingIp(floatingIp.getIp(), serverId.getValue(serverId.getSelectedIndex()), new AsyncCallback<FloatingIp>() {
			
			@Override
			public void onSuccess(FloatingIp result) {
				Portal.MODAL.hide();
				listener.onAssociate(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
