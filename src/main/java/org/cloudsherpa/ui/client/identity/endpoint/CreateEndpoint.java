package org.cloudsherpa.ui.client.identity.endpoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateEndpoint extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateEndpoint> {
	}

	public CreateEndpoint() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
