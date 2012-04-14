package org.cloudsherpa.ui.client.compute.securitygroup;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.SecurityGroup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateSecurityGroup extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateSecurityGroup> {
	}
	
	public interface Listener {
		void onSave(SecurityGroup securityGroup);
	}
	
	private Listener listener;
	
	public CreateSecurityGroup() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
		
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}

	

}
