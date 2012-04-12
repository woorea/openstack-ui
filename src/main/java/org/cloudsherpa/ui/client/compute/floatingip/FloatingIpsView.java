package org.cloudsherpa.ui.client.compute.floatingip;

import java.util.List;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.FloatingIp;

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

public class FloatingIpsView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, FloatingIpsView> {
	}
	
	public interface Presenter {
		void onCreate();
		void onDelete();
		void onAssociate();
		void onDisassociate();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField Button associate;
	@UiField Button disassociate;
	@UiField Button refresh;
	
	@UiField FloatingIpDetails details;
	
	@UiField(provided = true) DataGrid<FloatingIp> grid;
	
	MultiSelectionModel<FloatingIp> selectionModel;
	
	DefaultSelectionEventManager<FloatingIp> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<FloatingIp> asyncDataProvider = new AsyncDataProvider<FloatingIp>() {

		@Override
		protected void onRangeChanged(HasData<FloatingIp> display) {
			
			final Range range = display.getVisibleRange();
			
			Portal.CLOUD.listFloatingIps(new AsyncCallback<List<FloatingIp>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<FloatingIp> result) {
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					
				}
			});
			
		}

	};
	
	Presenter presenter;

	public FloatingIpsView() {
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
	
	@UiHandler("associate")
	void onAtachClick(ClickEvent event) {
		presenter.onAssociate();
	}
	
	@UiHandler("disassociate")
	void onDetachClick(ClickEvent event) {
		presenter.onDisassociate();
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
			associate.setEnabled(false);
			disassociate.setEnabled(false);
			break;
		case 1:
			delete.setEnabled(true);
			associate.setEnabled(true);
			disassociate.setEnabled(true);
			break;
		default:
			delete.setEnabled(true);
			associate.setEnabled(false);
			disassociate.setEnabled(false);
			break;
		}
	}
	
	private void createGrid() {
		grid = new DataGrid<FloatingIp>();
		selectionModel = new MultiSelectionModel<FloatingIp>();
		Column<FloatingIp, Boolean> checkboxColumn = new Column<FloatingIp, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(FloatingIp object) {
				return false;
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		TextColumn<FloatingIp> ipColumn = new TextColumn<FloatingIp>() {
			@Override
			public String getValue(FloatingIp object) {
				return object.getIp();
			}
		};
		grid.setColumnWidth(ipColumn, "120px");
		grid.addColumn(ipColumn, "Name");
		TextColumn<FloatingIp> instanceIdColumn = new TextColumn<FloatingIp>() {
			@Override
			public String getValue(FloatingIp object) {
				return object.getInstanceId();
			}
		};
		grid.setColumnWidth(instanceIdColumn, "120px");
		grid.addColumn(instanceIdColumn, "Instance Id");
		TextColumn<FloatingIp> fixedIpColumn = new TextColumn<FloatingIp>() {
			@Override
			public String getValue(FloatingIp object) {
				return object.getFixedIp();
			}
		};
		grid.setColumnWidth(fixedIpColumn, "120px");
		grid.addColumn(fixedIpColumn, "Fixed Ip");
		TextColumn<FloatingIp> poolColumn = new TextColumn<FloatingIp>() {
			@Override
			public String getValue(FloatingIp object) {
				return object.getPool();
			}
		};
		grid.setColumnWidth(poolColumn, "120px");
		grid.addColumn(poolColumn, "Pool");
		grid.setSelectionModel(selectionModel, selectionEventManager);
		selectionModel.addSelectionChangeHandler(new Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				update();
				
			}
		});
		asyncDataProvider.addDataDisplay(grid); 
	}
	
	private void onPreview(FloatingIp server) {
		details.id.setText(server.getId().toString());
	}
	
}


