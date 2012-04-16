package org.openstack.ui.client.compute.floatingip;

import java.util.List;
import java.util.Set;

import org.openstack.model.compute.FloatingIp;
import org.openstack.portal.client.Portal;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
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
	
	public interface Presenter extends AssociateFloatingIp.Listener {
		void onCreate();
		void onDelete();
		void onDisassociate();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField Button associate;
	@UiField Button disassociate;
	@UiField Button refresh;
	
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
					selectionModel.clear();
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					update();
				}
			});
			
		}

	};
	
	Presenter presenter;

	public FloatingIpsView() {
		createGrid();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	public void refresh() {
		grid.setVisibleRangeAndClearData(grid.getVisibleRange(), true);
		//RangeChangeEvent.fire(grid, grid.getVisibleRange());
	}

	@UiHandler("create")
	void onCreateClick(ClickEvent event) {
		Portal.CLOUD.createFloatingIp(new AsyncCallback<FloatingIp>() {
			
			@Override
			public void onSuccess(FloatingIp result) {
				refresh();
				presenter.onCreate();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
			
		});
	}
	
	@UiHandler("delete")
	void onDeleteClick(ClickEvent event) {
		Set<FloatingIp> users = selectionModel.getSelectedSet();
		Integer[] ids = Collections2.transform(users, new Function<FloatingIp, Integer>() {

			@Override
			public Integer apply(FloatingIp user) {
				return user.getId();
			}
			
		}).toArray(new Integer[0]);
		Portal.CLOUD.deleteFloatingIps(ids, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				refresh();
				presenter.onDelete();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
			
		});
	}
	
	@UiHandler("associate")
	void onAssociateClick(ClickEvent event) {
		FloatingIp floatingIp = selectionModel.getSelectedSet().iterator().next();
		AssociateFloatingIp widget = new AssociateFloatingIp();
		widget.floatingIp = floatingIp;
		widget.setListener(presenter);
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	@UiHandler("disassociate")
	void onDisassociateClick(ClickEvent event) {
		FloatingIp floatingIp = selectionModel.getSelectedSet().iterator().next();
		Portal.CLOUD.disassociateFloatingIp(floatingIp.getIp(), new AsyncCallback<FloatingIp>() {
			
			@Override
			public void onSuccess(FloatingIp result) {
				refresh();
				presenter.onDisassociate();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
			
		});
	}
	
	@UiHandler("refresh")
	void onRefreshClick(ClickEvent event) {
		//grid.setVisibleRangeAndClearData(grid.getVisibleRange(), true);
		RangeChangeEvent.fire(grid, grid.getVisibleRange());
		presenter.onRefresh();
	}
	
	private void update() {
		Set<FloatingIp> selected = selectionModel.getSelectedSet();
		switch (selected.size()) {
		case 0:
			delete.setEnabled(false);
			associate.setEnabled(false);
			disassociate.setEnabled(false);
			break;
		case 1:
			delete.setEnabled(true);
			FloatingIp floatingIp = selected.iterator().next();
			if(floatingIp.getInstanceId() == null) {
				associate.setEnabled(true);
				disassociate.setEnabled(false);
			} else {
				associate.setEnabled(false);
				disassociate.setEnabled(true);
			}
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
				return selectionModel.isSelected(object);
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
		TextColumn<FloatingIp> instanceIdColumn = new TextColumn<FloatingIp>() {
			@Override
			public String getValue(FloatingIp object) {
				return object.getInstanceId();
			}
		};
		grid.setColumnWidth(instanceIdColumn, "120px");
		grid.addColumn(instanceIdColumn, "Instance Id");
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


