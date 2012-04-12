package org.cloudsherpa.ui.client.compute.volume;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class VolumeDetails extends Composite {

	private static ServerDetailsUiBinder uiBinder = GWT
			.create(ServerDetailsUiBinder.class);

	interface ServerDetailsUiBinder extends UiBinder<Widget, VolumeDetails> {
	}
	
	@UiField Label id;

	public VolumeDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
