package org.cloudsherpa.ui.client.identity.service;

import java.util.List;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.identity.Service;

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
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class ServicesView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, ServicesView> {
	}
	
	public interface Presenter {
		void onCreate();
		void onDelete();
		void onAttach();
		void onDetach();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField Button attach;
	@UiField Button detach;
	@UiField Button refresh;
	
	@UiField ServiceDetails details;
	
	@UiField(provided = true) DataGrid<Service> grid;
	
	MultiSelectionModel<Service> selectionModel;
	
	DefaultSelectionEventManager<Service> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<Service> asyncDataProvider = new AsyncDataProvider<Service>() {

		@Override
		protected void onRangeChanged(HasData<Service> display) {
			
			final Range range = display.getVisibleRange();
			
			Portal.CLOUD.listServices(new AsyncCallback<List<Service>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<Service> result) {
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					
				}
			});
			
		}

	};
	
	Presenter presenter;

	public ServicesView() {
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
	
	@UiHandler("attach")
	void onAtachClick(ClickEvent event) {
		presenter.onAttach();
	}
	
	@UiHandler("detach")
	void onDetachClick(ClickEvent event) {
		presenter.onDetach();
	}
	
	@UiHandler("refresh")
	void onRefreshClick(ClickEvent event) {
		//grid.setVisibleRangeAndClearData(grid.getVisibleRange(), true);
		RangeChangeEvent.fire(grid, grid.getVisibleRange());
		presenter.onRefresh();
	}
	
	private void update() {
		switch (selectionModel.getSelectedSet().size()) {
		case 0:
			delete.setEnabled(false);
			attach.setEnabled(false);
			detach.setEnabled(false);
			break;
		case 1:
			delete.setEnabled(true);
			attach.setEnabled(true);
			detach.setEnabled(true);
			break;
		default:
			delete.setEnabled(true);
			attach.setEnabled(false);
			detach.setEnabled(false);
			break;
		}
	}
	
	private void createGrid() {
		grid = new DataGrid<Service>();
		selectionModel = new MultiSelectionModel<Service>();
		Column<Service, Boolean> checkboxColumn = new Column<Service, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(Service object) {
				return false;
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		TextColumn<Service> idColumn = new TextColumn<Service>() {
			@Override
			public String getValue(Service object) {
				return object.getId();
			}
		};
		grid.setColumnWidth(idColumn, "120px");
		grid.addColumn(idColumn, "Id");
		TextColumn<Service> typeColumn = new TextColumn<Service>() {
			@Override
			public String getValue(Service object) {
				return object.getType();
			}
		};
		grid.setColumnWidth(typeColumn, "120px");
		grid.addColumn(typeColumn, "Type");
		TextColumn<Service> nameColumn = new TextColumn<Service>() {
			@Override
			public String getValue(Service object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(nameColumn, "120px");
		grid.addColumn(nameColumn, "Name");
		TextColumn<Service> descriptionColumn = new TextColumn<Service>() {
			@Override
			public String getValue(Service object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(descriptionColumn, "120px");
		grid.addColumn(descriptionColumn, "Description");
		ButtonCell previewButton = new ButtonCell();
		Column<Service,String> preview = new Column<Service,String>(previewButton) {
		  public String getValue(Service object) {
		    return "Preview";
		  }
		};
		preview.setFieldUpdater(new FieldUpdater<Service, String>() {
		  @Override
		  public void update(int index, Service service, String value) {
		    onPreview(service);
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
	
	private void onPreview(Service server) {
		details.id.setText(server.getId());
	}
	
}


