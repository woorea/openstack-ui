package org.openstack.ui.client.compute.image;

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
	@UiField Label checksum;
	@UiField Label createdAt;
	@UiField Label deleted;
	@UiField Label deletedAt;
	@UiField Label containerFormat;
	@UiField Label diskFormat;
	@UiField Label isProtected;
	@UiField Label isPublic;
	@UiField Label minDisk;
	@UiField Label minRam;
	@UiField Label name;
	@UiField Label ownerx;
	@UiField Label size;
	@UiField Label status;
	@UiField Label updatedAt;
	@UiField Label uri;
	

	public ImageDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
