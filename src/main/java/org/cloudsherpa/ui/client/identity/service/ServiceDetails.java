package org.cloudsherpa.ui.client.identity.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ServiceDetails extends Composite {

	private static ServerDetailsUiBinder uiBinder = GWT
			.create(ServerDetailsUiBinder.class);

	interface ServerDetailsUiBinder extends UiBinder<Widget, ServiceDetails> {
	}
	
	@UiField Label id;
	@UiField Label name;
	@UiField Label description;
	@UiField Label type;

	public ServiceDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
