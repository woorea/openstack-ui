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

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, CreateRole> {
	}
	
	public interface Listener {
		void onRoleCreated(Role tenant);
	}
	
	private Listener listener;
	
	@UiField TextBox name;

	public CreateRole() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Administration.MODAL.hide();
	}
	
	
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		Role role = new KeystoneRole();
		role.setName(name.getValue());
		Administration.CLOUD.create(role, new AsyncCallback<Role>() {
			
			@Override
			public void onSuccess(Role result) {
				Administration.MODAL.hide();
				listener.onRoleCreated(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
