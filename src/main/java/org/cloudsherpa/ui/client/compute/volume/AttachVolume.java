package org.cloudsherpa.ui.client.compute.volume;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AttachVolume extends Composite {

	private static AttachVolumeUiBinder uiBinder = GWT
			.create(AttachVolumeUiBinder.class);

	interface AttachVolumeUiBinder extends UiBinder<Widget, AttachVolume> {
	}

	public AttachVolume() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
