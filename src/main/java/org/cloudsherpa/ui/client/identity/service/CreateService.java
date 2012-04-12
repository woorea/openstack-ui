package org.cloudsherpa.ui.client.identity.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateService extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateService> {
	}

	public CreateService() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
