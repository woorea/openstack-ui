package org.cloudsherpa.ui.client.identity.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UserDetails extends Composite {

	private static ServerDetailsUiBinder uiBinder = GWT
			.create(ServerDetailsUiBinder.class);

	interface ServerDetailsUiBinder extends UiBinder<Widget, UserDetails> {
	}
	
	@UiField Label id;
	@UiField Label name;
	@UiField Label email;
	@UiField Label enabled;

	public UserDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
