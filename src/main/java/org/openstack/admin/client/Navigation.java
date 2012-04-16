package org.openstack.admin.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Navigation extends Composite {

	private static NavigationUiBinder uiBinder = GWT
			.create(NavigationUiBinder.class);

	interface NavigationUiBinder extends UiBinder<Widget, Navigation> {
	}

	public Navigation() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
