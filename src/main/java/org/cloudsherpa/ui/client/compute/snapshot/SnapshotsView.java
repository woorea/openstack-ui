package org.cloudsherpa.ui.client.compute.snapshot;

import java.util.List;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.Snapshot;

import com.google.gwt.cell.client.CheckboxCell;
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

public class SnapshotsView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, SnapshotsView> {
	}
	
	public interface Presenter {
		void onCreate();
		void onDelete();
		void onCreateVolume();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField Button createVolume;
	@UiField Button refresh;
	
	@UiField(provided = true) DataGrid<Snapshot> grid;
	
	MultiSelectionModel<Snapshot> selectionModel;
	
	DefaultSelectionEventManager<Snapshot> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<Snapshot> asyncDataProvider = new AsyncDataProvider<Snapshot>() {

		@Override
		protected void onRangeChanged(HasData<Snapshot> display) {
			
			final Range range = display.getVisibleRange();
			
			Portal.CLOUD.listSnapshots(new AsyncCallback<List<Snapshot>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<Snapshot> result) {
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					
				}
			});
			
		}

	};
	
	Presenter presenter;

	public SnapshotsView() {
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
	
	@UiHandler("createVolume")
	void onAtachClick(ClickEvent event) {
		presenter.onCreateVolume();
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
		grid = new DataGrid<Snapshot>();
		selectionModel = new MultiSelectionModel<Snapshot>();
		Column<Snapshot, Boolean> checkboxColumn = new Column<Snapshot, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(Snapshot object) {
				return selectionModel.isSelected(object);
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		TextColumn<Snapshot> nameColumn = new TextColumn<Snapshot>() {

			@Override
			public String getValue(Snapshot object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(nameColumn, "60px");
		grid.addColumn(nameColumn,"name");
		TextColumn<Snapshot> statusColumn = new TextColumn<Snapshot>() {
			@Override
			public String getValue(Snapshot object) {
				return object.getStatus();
			}
		};
		grid.setColumnWidth(statusColumn, "120px");
		grid.addColumn(statusColumn,"status");
		TextColumn<Snapshot> descriptionColumn = new TextColumn<Snapshot>() {

			@Override
			public String getValue(Snapshot object) {
				return object.getName();
			}
		};
		grid.addColumn(descriptionColumn,"description");
		grid.setSelectionModel(selectionModel, selectionEventManager);
		selectionModel.addSelectionChangeHandler(new Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				update();
				
			}
		});
		asyncDataProvider.addDataDisplay(grid); 
	}
	
}


