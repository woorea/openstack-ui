package org.cloudsherpa.ui.client.compute.volume;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateVolume extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateVolume> {
	}

	public CreateVolume() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
