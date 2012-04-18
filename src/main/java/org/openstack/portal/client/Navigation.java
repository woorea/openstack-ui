package org.openstack.portal.client;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class Navigation extends Composite {

	private static NavigationUiBinder uiBinder = GWT
			.create(NavigationUiBinder.class);

	interface NavigationUiBinder extends UiBinder<Widget, Navigation> {
	}
	
	@UiField Tree tree;

	public Navigation() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setTenantId(String tenantId) {
		Iterator<TreeItem> items = tree.treeItemIterator();
		while (items.hasNext()) {
			TreeItem item = items.next();
			if (item.getWidget() instanceof Hyperlink) {
				Hyperlink link = (Hyperlink) item.getWidget();
				String[] tokens = link.getTargetHistoryToken().split(":");
				link.setTargetHistoryToken(tokens[0] + ":" + tenantId + ":" + tokens[2]);
			}
		}
	}

}
