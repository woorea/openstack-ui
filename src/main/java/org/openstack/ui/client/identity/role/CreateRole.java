package org.openstack.ui.client.identity.role;

import org.openstack.admin.client.Administration;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.keystone.KeystoneRole;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateRole extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateRole> {
	}

	public CreateRole() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Administration.MODAL.hide();
	}
	
	public interface Listener {

		void onSave(Role tenant);
		
	}
	
	private Listener listener;
	
	@UiField TextBox name;
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		Role role = new KeystoneRole();
		role.setName(name.getValue());
		Administration.CLOUD.create(role, new AsyncCallback<Role>() {
			
			@Override
			public void onSuccess(Role result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
