package org.openstack.ui.client.identity.role;

import java.util.List;
import java.util.Set;

import org.openstack.admin.client.Administration;
import org.openstack.model.identity.Role;

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

public class RolesView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, RolesView> {
	}
	
	public interface Presenter extends CreateRole.Listener {
		void onDelete();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField Button refresh;
	
	@UiField(provided = true) DataGrid<Role> grid;
	
	MultiSelectionModel<Role> selectionModel;
	
	DefaultSelectionEventManager<Role> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<Role> asyncDataProvider = new AsyncDataProvider<Role>() {

		@Override
		protected void onRangeChanged(HasData<Role> display) {
			
			final Range range = display.getVisibleRange();
			
			Administration.CLOUD.listRoles(new AsyncCallback<List<Role>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<Role> result) {
					update();
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					
				}
			});
			
		}

	};
	
	Presenter presenter;

	public RolesView() {
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
		CreateRole widget = new CreateRole();
		widget.setListener(presenter);
		Administration.MODAL.setWidget(widget);
		Administration.MODAL.center();
	}
	
	@UiHandler("delete")
	void onDeleteClick(ClickEvent event) {
		Set<Role> users = selectionModel.getSelectedSet();
		String[] ids = Collections2.transform(users, new Function<Role, String>() {

			@Override
			public String apply(Role user) {
				return user.getId();
			}
			
		}).toArray(new String[0]);
		Administration.CLOUD.deleteRoles(ids, new AsyncCallback<Void>() {
			
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
		grid = new DataGrid<Role>();
		selectionModel = new MultiSelectionModel<Role>();
		Column<Role, Boolean> checkboxColumn = new Column<Role, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(Role object) {
				return selectionModel.isSelected(object);
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		TextColumn<Role> nameColumn = new TextColumn<Role>() {
			@Override
			public String getValue(Role object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(nameColumn, "120px");
		grid.addColumn(nameColumn, "Name");
		TextColumn<Role> descriptionColumn = new TextColumn<Role>() {
			@Override
			public String getValue(Role object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(descriptionColumn, "120px");
		grid.addColumn(descriptionColumn, "Description");
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


