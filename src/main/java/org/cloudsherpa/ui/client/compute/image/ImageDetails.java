package org.cloudsherpa.ui.client.compute.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ImageDetails extends Composite {

	private static ServerDetailsUiBinder uiBinder = GWT
			.create(ServerDetailsUiBinder.class);

	interface ServerDetailsUiBinder extends UiBinder<Widget, ImageDetails> {
	}
	
	@UiField Label id;

	public ImageDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
