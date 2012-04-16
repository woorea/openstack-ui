package org.openstack.ui.client.compute.server;

import java.io.Serializable;

import org.openstack.model.compute.Server;
import org.openstack.model.compute.nova.server.actions.GetVncConsoleAction;
import org.openstack.model.compute.nova.server.actions.RebootAction;
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

public class Restore extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, Restore> {
	}
	
	public interface Listener {
		void onRestore(Server server);
	}
	
	private Listener listener;
	
	Server server;

	@UiField ListBox type;

	public Restore() {
		initWidget(uiBinder.createAndBindUi(this));
		type.addItem("SOFT");
		type.addItem("HARD");
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
	void onRebootClick(ClickEvent event) {
		final org.openstack.model.compute.ServerAction action = new RebootAction(type.getValue(type.getSelectedIndex()));
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
