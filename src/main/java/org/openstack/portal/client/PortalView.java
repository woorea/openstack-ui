package org.openstack.portal.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalView extends Composite {

	private static PortalViewUiBinder uiBinder = GWT.create(PortalViewUiBinder.class);

	interface PortalViewUiBinder extends UiBinder<Widget, PortalView> {
	}
	
	@UiField SimpleLayoutPanel main;

	public PortalView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
