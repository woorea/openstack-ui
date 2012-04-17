package org.openstack.ui.client.compute.floatingip;

import java.io.Serializable;

import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerList;
import org.openstack.model.compute.nova.floatingip.AssociateFloatingIpAction;
import org.openstack.portal.client.Portal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
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
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	

	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		AssociateFloatingIpAction action = new AssociateFloatingIpAction();
		action.setAddress(floatingIp.getIp());
		Portal.CLOUD.executeServerAction(serverId.getValue(serverId.getSelectedIndex()), action, new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				Portal.MODAL.hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
