package org.cloudsherpa.ui.client.compute.volume;

import org.cloudsherpa.portal.client.Portal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
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
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}

}
