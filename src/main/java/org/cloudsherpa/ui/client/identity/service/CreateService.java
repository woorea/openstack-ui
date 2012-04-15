package org.cloudsherpa.ui.client.identity.service;

import org.cloudsherpa.admin.client.Administration;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.keystone.KeystoneService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateService extends Composite {

	private static Binder uiBinder = GWT
			.create(Binder.class);

	interface Binder extends UiBinder<Widget, CreateService> {
	}

	public CreateService() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Administration.MODAL.hide();
	}
	
	public interface Listener {

		void onSave(Service service);
		
	}
	
	private Listener listener;
	
	@UiField TextBox type;
	@UiField TextBox name;
	@UiField TextArea description;
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		Service service = new KeystoneService();
		service.setType(type.getValue());
		service.setName(name.getValue());
		service.setDescription(description.getValue());
		Administration.CLOUD.create(service, new AsyncCallback<Service>() {
			
			@Override
			public void onSuccess(Service result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
