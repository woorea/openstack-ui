package org.cloudsherpa.ui.client.identity.tenant;

import org.cloudsherpa.admin.client.Administration;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.keystone.KeystoneTenant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateTenant extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateTenant> {
	}
	
	public interface Listener {

		void onSave(Tenant tenant);
		
	}
	
	private Listener listener;
	
	@UiField TextBox name;
	@UiField TextArea description;
	@UiField CheckBox enabled;

	public CreateTenant(Listener listener) {
		this.listener = listener;
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Administration.MODAL.hide();
	}
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		Tenant tenant = new KeystoneTenant();
		tenant.setName(name.getValue());
		tenant.setDescription(description.getValue());
		tenant.setEnabled(enabled.getValue());
		Administration.CLOUD.create(tenant, new AsyncCallback<Tenant>() {
			
			@Override
			public void onSuccess(Tenant result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
