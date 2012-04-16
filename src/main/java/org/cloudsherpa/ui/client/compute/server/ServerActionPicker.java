package org.cloudsherpa.ui.client.compute.server;

import java.io.Serializable;
import java.util.Set;

import org.cloudsherpa.ui.client.compute.common.PopupPicker;
import org.openstack.model.compute.Server;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.view.client.MultiSelectionModel;

public class ServerActionPicker extends PopupPicker<ServerAction> {

	public interface Listener {
		void onServerActionSuccess(org.openstack.model.compute.ServerAction action, Serializable result);
		void onServerActionFailure(org.openstack.model.compute.ServerAction action, Throwable throwable);
	}

	private FlexTable grid;

	private MultiSelectionModel<Server> selectionModel;
	
	Set<Server> servers;

	public ServerActionPicker() {
		dropdown.setText("Server Actions");
		grid = new FlexTable();
		grid.setCellPadding(2);
		grid.setCellSpacing(2);
		grid.setWidth("165px");
		int row = 0;
		for (ServerAction sa : ServerAction.values()) {
			grid.setHTML(row, 0, sa.toString());
			grid.getCellFormatter().getElement(row++, 0).getStyle().setFontSize(10.0, Unit.PX);
		}
		popup.setWidget(grid);

		grid.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(servers != null && servers.size() == 1) {
					Cell cell = grid.getCellForEvent(event);
					if (cell != null) {
						ServerAction sa = ServerAction.values()[cell.getRowIndex()];
						Server server = servers.iterator().next();
						popup.hide(true);
						sa.execute(server, listener);
					}
				}
			}
		});
	}
	
	private Listener listener;

	public void setListener(Listener listener) {
		this.listener = listener;

	}

	@Override
	public void setValue(ServerAction value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ServerAction getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSelectionModel(MultiSelectionModel<Server> selectionModel) {
		this.selectionModel = selectionModel;
	}
	
	public void update() {
		servers = selectionModel.getSelectedSet();
		if(servers.size() == 1) {
			
		} else {
			
		}
	}

}