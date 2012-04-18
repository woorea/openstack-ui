package org.openstack.portal.client;

import org.openstack.ui.client.common.DefaultAsyncCallback;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class PortalView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, PortalView> {
	}

	@UiField ListBox tenants;
	
	@UiField Navigation nav;

	@UiField SimpleLayoutPanel main;

	public PortalView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		tenants.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				Portal.PLACE_CONTROLLER.goTo(new PortalPlace(tenants.getValue(tenants.getSelectedIndex()), "servers"));
			}
		});
		
	}

}
