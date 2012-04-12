package org.cloudsherpa.ui.client.compute.snapshot;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SnapshotDetails extends Composite {

	private static ServerDetailsUiBinder uiBinder = GWT
			.create(ServerDetailsUiBinder.class);

	interface ServerDetailsUiBinder extends UiBinder<Widget, SnapshotDetails> {
	}
	
	@UiField Label id;

	public SnapshotDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
