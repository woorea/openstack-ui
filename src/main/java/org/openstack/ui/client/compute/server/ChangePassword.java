package org.openstack.ui.client.compute.server;

import java.io.Serializable;

import org.openstack.model.compute.Server;
import org.openstack.model.compute.nova.server.actions.ChangePasswordAction;
import org.openstack.portal.client.Portal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;

public class ChangePassword extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ChangePassword> {
	}
	
	public interface Listener {
		void onPasswordChanged(Server server);
	}
	
	private Listener listener;
	
	Server server;

	@UiField PasswordTextBox adminPass;

	public ChangePassword() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	@UiHandler({"execute"})
	void onExecuteClick(ClickEvent event) {
		final org.openstack.model.compute.ServerAction action = new ChangePasswordAction(adminPass.getValue());
		Portal.CLOUD.executeServerAction(server.getId(), action, new AsyncCallback<Serializable>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(Serializable result) {
				Portal.MODAL.hide();
			}
		});
		
	}

}
