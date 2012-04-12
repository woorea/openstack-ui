package org.cloudsherpa.ui.client.compute.securitygroup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SecurityGroupEditor extends Composite {

	private static SecurityGroupEditorUiBinder uiBinder = GWT
			.create(SecurityGroupEditorUiBinder.class);

	interface SecurityGroupEditorUiBinder extends
			UiBinder<Widget, SecurityGroupEditor> {
	}

	public SecurityGroupEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
