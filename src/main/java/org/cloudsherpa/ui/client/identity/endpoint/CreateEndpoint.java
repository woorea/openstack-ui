package org.cloudsherpa.ui.client.identity.endpoint;

import org.cloudsherpa.admin.client.Administration;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.keystone.KeystoneEndpoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateEndpoint extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateEndpoint> {
	}

	public CreateEndpoint() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Administration.MODAL.hide();
	}
	
	public interface Listener {

		void onSave(Endpoint tenant);
		
	}
	
	private Listener listener;
	
	String serviceId;
	@UiField TextBox region;
	@UiField TextBox publicURL;
	@UiField TextBox internalURL;
	@UiField TextBox adminURL;
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		Endpoint tenant = new KeystoneEndpoint();
		tenant.setServiceId(serviceId);
		tenant.setRegion(region.getValue());
		tenant.setPublicURL(publicURL.getValue());
		tenant.setInternalURL(internalURL.getValue());
		tenant.setAdminURL(adminURL.getValue());
		Administration.CLOUD.create(tenant, new AsyncCallback<Endpoint>() {
			
			@Override
			public void onSuccess(Endpoint result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
