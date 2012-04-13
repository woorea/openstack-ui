package org.cloudsherpa.ui.client.compute.server;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ServerDetails extends Composite {

	private static ServerDetailsUiBinder uiBinder = GWT
			.create(ServerDetailsUiBinder.class);

	interface ServerDetailsUiBinder extends UiBinder<Widget, ServerDetails> {
	}
	
	@UiField Label id;
	@UiField Label configDrive;
	@UiField Label created;
	@UiField Label updated;
	@UiField Label fault;
	@UiField Label flavor;
	@UiField Label hostId;
	@UiField Label keyName;
	@UiField Label progress;
	@UiField Label status;
	@UiField Label tenantId;
	@UiField Label userId;
	
	@UiField FlexTable metadata;
	@UiField VerticalPanel networks;

	public ServerDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
