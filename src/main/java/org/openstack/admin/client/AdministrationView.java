package org.openstack.admin.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class AdministrationView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, AdministrationView> {
	}
	
	@UiField SimpleLayoutPanel main2;

	public AdministrationView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
