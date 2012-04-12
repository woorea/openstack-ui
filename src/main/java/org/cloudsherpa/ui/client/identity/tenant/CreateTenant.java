package org.cloudsherpa.ui.client.identity.tenant;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateTenant extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateTenant> {
	}

	public CreateTenant() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
