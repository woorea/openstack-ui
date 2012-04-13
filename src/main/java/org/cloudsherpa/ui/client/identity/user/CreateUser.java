package org.cloudsherpa.ui.client.identity.user;

import org.cloudsherpa.admin.client.Administration;
import org.openstack.model.identity.User;
import org.openstack.model.identity.UserForCreate;
import org.openstack.model.identity.keystone.KeystoneUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateUser extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateUser> {
	}
	
	public interface Listener {

		void onSave(UserForCreate tenant);
		
	}
	
	private Listener listener;
	
	@UiField TextBox name;
	@UiField TextBox username;
	@UiField PasswordTextBox password;
	@UiField TextBox email;
	@UiField CheckBox enabled;

	public CreateUser(Listener listener) {
		this.listener = listener;
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Administration.MODAL.hide();
	}
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		UserForCreate user = new KeystoneUser();
		user.setName(name.getValue());
		user.setUsername(username.getValue());
		user.setPassword(password.getValue());
		user.setEmail(email.getValue());
		user.setEnabled(enabled.getValue());
		Administration.CLOUD.createUser(user, new AsyncCallback<User>() {
			
			@Override
			public void onSuccess(User result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
