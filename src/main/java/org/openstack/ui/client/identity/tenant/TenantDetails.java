package org.openstack.ui.client.identity.tenant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TenantDetails extends Composite {

	private static ServerDetailsUiBinder uiBinder = GWT
			.create(ServerDetailsUiBinder.class);

	interface ServerDetailsUiBinder extends UiBinder<Widget, TenantDetails> {
	}
	
	@UiField Label id;
	@UiField Label name;
	@UiField Label description;
	@UiField Label enabled;

	public TenantDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
