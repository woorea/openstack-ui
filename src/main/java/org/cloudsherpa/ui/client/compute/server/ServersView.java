package org.cloudsherpa.ui.client.compute.server;

import java.util.ArrayList;
import java.util.List;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.nova.NovaServer;

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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class ServersView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ServersView> {
	}
	
	public interface Presenter {
		void onCreate();
		void onDelete();
		void onRefresh();
		void onFilters();
	}
	
	@UiField Button create;
	@UiField Button delete;
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
			
			Portal.CLOUD.listServers(range.getStart(), range.getLength(),  new AsyncCallback<List<Server>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<Server> result) {
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					
				}
			});
			
		}

	};
	
	Presenter presenter;

	public ServersView() {
		createGrid();
		initWidget(uiBinder.createAndBindUi(this));
		update();
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("create")
	void onCreateClick(ClickEvent event) {
		presenter.onCreate();
	}
	
	@UiHandler("delete")
	void onDeleteClick(ClickEvent event) {
		presenter.onDelete();
	}
	
	@UiHandler("refresh")
	void onRefreshClick(ClickEvent event) {
		presenter.onRefresh();
	}
	
	@UiHandler("filters")
	void onFiltersClick(ClickEvent event) {
		presenter.onFilters();
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
	}
	
}


