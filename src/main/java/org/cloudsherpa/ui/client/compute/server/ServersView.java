package org.cloudsherpa.ui.client.compute.server;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerAction;
import org.openstack.model.compute.ServerList;
import org.openstack.model.compute.nova.NovaAddressList.Network;
import org.openstack.model.compute.nova.NovaAddressList.Network.Ip;
import org.openstack.model.compute.nova.NovaServerForCreate;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class ServersView extends Composite implements CreateServerWizard.Listener, ServerActionPicker.Listener {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ServersView> {
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField ServerActionPicker actions;
	@UiField Button refresh;
	@UiField Button filters;
	
	@UiField ServerDetails details;
	
	@UiField(provided = true) DataGrid<Server> grid;
	
	MultiSelectionModel<Server> selectionModel;
	
	DefaultSelectionEventManager<Server> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<Server> asyncDataProvider = new AsyncDataProvider<Server>() {

		@Override
		protected void onRangeChanged(HasData<Server> display) {
			
			final Range range = display.getVisibleRange();
			
			Portal.CLOUD.listServers(range.getStart(), range.getLength(),  new AsyncCallback<ServerList>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(ServerList result) {
					selectionModel.clear();
					updateRowData(range.getStart(), result.getList());
					updateRowCount(range.getLength(), true);
					update();
					
				}
			});
			
		}

	};

	public ServersView() {
		createGrid();
		initWidget(uiBinder.createAndBindUi(this));
		actions.setSelectionModel(selectionModel);
		actions.setListener(this);
	}
	
	public void refresh() {
		grid.setVisibleRangeAndClearData(grid.getVisibleRange(), true);
		//RangeChangeEvent.fire(grid, grid.getVisibleRange());
	}

	@UiHandler("create")
	void onCreateClick(ClickEvent event) {
		CreateServerWizard widget = new CreateServerWizard();
		widget.setListener(this);
		widget.edit(new NovaServerForCreate());
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	@UiHandler("delete")
	void onDeleteClick(ClickEvent event) {
		Set<Server> users = selectionModel.getSelectedSet();
		String[] ids = Collections2.transform(users, new Function<Server, String>() {

			@Override
			public String apply(Server user) {
				return user.getId();
			}
			
		}).toArray(new String[0]);
		Portal.CLOUD.deleteServers(ids, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				refresh();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
			
		});
	}
	
	@UiHandler("refresh")
	void onRefreshClick(ClickEvent event) {
		refresh();
	}
	
	@UiHandler("filters")
	void onFiltersClick(ClickEvent event) {
		ServersFilters widget = new ServersFilters();
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	private void update() {
		switch (selectionModel.getSelectedSet().size()) {
			case 0:
				delete.setEnabled(false);
				break;
			case 1:
				delete.setEnabled(true);
				break;
			default:
				delete.setEnabled(true);
				break;
		}
		actions.update();
	}
	
	private void createGrid() {
		grid = new DataGrid<Server>();
		selectionModel = new MultiSelectionModel<Server>();
		Column<Server, Boolean> checkboxColumn = new Column<Server, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(Server object) {
				return selectionModel.isSelected(object);
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		/*
		Column<Server, String> logoColumn = new Column<Server, String>(new LogoCell()) {

			@Override
			public String getValue(Server object) {
				return "";
			}
		};
		grid.setColumnWidth(logoColumn, "60px");
		grid.addColumn(logoColumn);
		*/
		TextColumn<Server> statusColumn = new TextColumn<Server>() {
			@Override
			public String getValue(Server object) {
				return object.getStatus();
			}
		};
		grid.setColumnWidth(statusColumn, "100px");
		grid.addColumn(statusColumn, "Name");
		TextColumn<Server> nameColumn = new TextColumn<Server>() {
			@Override
			public String getValue(Server object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(nameColumn, "120px");
		grid.addColumn(nameColumn, "Name");
		TextColumn<Server> imageColumn = new TextColumn<Server>() {
			@Override
			public String getValue(Server object) {
				//return object.getImage().getName();
				return "";
			}
		};
		// grid.setColumnWidth(imageColumn, "120px");
		grid.addColumn(imageColumn, "Image");
		TextColumn<Server> flavorColumn = new TextColumn<Server>() {
			@Override
			public String getValue(Server object) {
				//return object.getFlavor().getName();
				return "";
			}
		};
		grid.setColumnWidth(flavorColumn, "120px");
		grid.addColumn(flavorColumn, "Flavor");
		ButtonCell previewButton = new ButtonCell();
		Column<Server,String> preview = new Column<Server,String>(previewButton) {
		  public String getValue(Server object) {
		    return "Preview";
		  }
		};
		preview.setFieldUpdater(new FieldUpdater<Server, String>() {
		  @Override
		  public void update(int index, Server server, String value) {
			  onPreview(server);
		  }
		});
		grid.setColumnWidth(preview, "100px");
		grid.addColumn(preview);
		grid.setSelectionModel(selectionModel, selectionEventManager);
		selectionModel.addSelectionChangeHandler(new Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				update();
				
			}
		});
		
		asyncDataProvider.addDataDisplay(grid); 
	}
	
	private void onPreview(Server server) {
		details.id.setText(server.getId());
		details.configDrive.setText(server.getConfigDrive());
		details.created.setText(server.getCreated().toString());
		details.updated.setText(server.getUpdated().toString());
		if(server.getFault() != null) {
			details.fault.setText(server.getFault().getMessage());
		}
		details.flavor.setText(server.getFlavor().getId());
		details.hostId.setText(server.getHostId());
		details.keyName.setText(server.getKeyName());
		details.progress.setText(server.getProgress());
		details.status.setText(server.getStatus());
		details.tenantId.setText(server.getTenantId());
		details.userId.setText(server.getUserId());
		details.accessIPv4.setText(server.getAccessIPv4());
		details.accessIPv6.setText(server.getAccessIPv6());
		
		int row = 0;
		for(Map.Entry<String, String> entry : server.getMetadata().entrySet()) {
			details.metadata.setText(row, 0, entry.getKey());
			details.metadata.setText(row, 1, entry.getValue());
			row++;
		}
		
		for(Map.Entry<String, List<Network.Ip>> n : server.getAddresses().entrySet()) {
			VerticalPanel network = new VerticalPanel();
			network.setWidth("100%");
			network.add(new HTML("<h4>" + n.getKey() + "<h4>"));
			FlexTable ips = new FlexTable();
			ips.setStyleName("table table-striped");
			row = 0;
			for(Ip ip : n.getValue()) {
				ips.setText(row, 0, ip.getVersion());
				ips.setText(row, 0, ip.getAddr());
				row++;
			}
			network.add(ips);
			
			details.networks.add(network);			
		}
		
	}
	
	@Override
	public void onServerCreated(Server server) {
		refresh();
		Portal.MODAL.hide();
	}

	@Override
	public void onServerActionSuccess(ServerAction action, Serializable result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerActionFailure(ServerAction action, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}
	
}


