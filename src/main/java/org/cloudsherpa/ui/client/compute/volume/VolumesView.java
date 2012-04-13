package org.cloudsherpa.ui.client.compute.volume;

import java.util.List;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.Volume;

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

public class VolumesView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, VolumesView> {
	}
	
	public interface Presenter {
		void onCreate();
		void onDelete();
		void onAttach();
		void onDetach();
		void onCreateSnapshot();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField Button attach;
	@UiField Button detach;
	@UiField Button createSnapshot;
	@UiField Button refresh;
	
	@UiField(provided = true) DataGrid<Volume> grid;
	
	MultiSelectionModel<Volume> selectionModel;
	
	DefaultSelectionEventManager<Volume> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<Volume> asyncDataProvider = new AsyncDataProvider<Volume>() {

		@Override
		protected void onRangeChanged(HasData<Volume> display) {
			
			final Range range = display.getVisibleRange();
			
			Portal.CLOUD.listVolumes(new AsyncCallback<List<Volume>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<Volume> result) {
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					
				}
			});
			
		}

	};
	
	Presenter presenter;

	public VolumesView() {
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
	
	@UiHandler("createSnapshot")
	void onCreateVolumeClick(ClickEvent event) {
		presenter.onCreateSnapshot();
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
			createSnapshot.setEnabled(false);
			break;
		case 1:
			delete.setEnabled(true);
			attach.setEnabled(true);
			detach.setEnabled(true);
			createSnapshot.setEnabled(true);
			break;
		default:
			delete.setEnabled(true);
			attach.setEnabled(false);
			detach.setEnabled(false);
			createSnapshot.setEnabled(false);
			break;
		}
	}
	
	private void createGrid() {
		grid = new DataGrid<Volume>();
		selectionModel = new MultiSelectionModel<Volume>();
		Column<Volume, Boolean> checkboxColumn = new Column<Volume, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(Volume object) {
				return selectionModel.isSelected(object);
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		TextColumn<Volume> statusColumn = new TextColumn<Volume>() {

			@Override
			public String getValue(Volume object) {
				return object.getStatus();
			}
		};
		grid.setColumnWidth(statusColumn, "120px");
		grid.addColumn(statusColumn,"status");
		TextColumn<Volume> nameColumn = new TextColumn<Volume>() {

			@Override
			public String getValue(Volume object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(nameColumn, "120px");
		grid.addColumn(nameColumn, "name");
		TextColumn<Volume> sizeColumn = new TextColumn<Volume>() {
			@Override
			public String getValue(Volume object) {
				return "15"; //object.getSizeInGB().toString();
			}
		};
		grid.setColumnWidth(sizeColumn, "120px");
		grid.addColumn(sizeColumn, "size");
		TextColumn<Volume> descriptionColumn = new TextColumn<Volume>() {

			@Override
			public String getValue(Volume object) {
				return object.getDescription();
			}
		};
		//grid.setColumnWidth(descriptionColumn, "120px");
		grid.addColumn(descriptionColumn, "description");
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


