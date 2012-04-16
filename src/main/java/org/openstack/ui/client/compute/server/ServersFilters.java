package org.openstack.ui.client.compute.server;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ServersFilters extends Composite {

	private static ServersFiltersUiBinder uiBinder = GWT
			.create(ServersFiltersUiBinder.class);

	interface ServersFiltersUiBinder extends UiBinder<Widget, ServersFilters> {
	}

	public ServersFilters() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
