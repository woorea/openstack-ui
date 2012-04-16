package org.openstack.ui.client.identity.user;

import java.util.List;
import java.util.Set;

import org.openstack.admin.client.Administration;
import org.openstack.model.identity.User;

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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

public class UsersView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, UsersView> {
	}
	
	public interface Presenter {
		void onCreate();
		void onDelete();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField Button refresh;
	
	@UiField UserDetails details;
	
	@UiField(provided = true) DataGrid<User> grid;
	
	MultiSelectionModel<User> selectionModel;
	
	DefaultSelectionEventManager<User> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<User> asyncDataProvider = new AsyncDataProvider<User>() {

		@Override
		protected void onRangeChanged(HasData<User> display) {
			
			final Range range = display.getVisibleRange();
			
			Administration.CLOUD.listUsers(new AsyncCallback<List<User>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<User> result) {
					update();
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
				}
			});
			
		}

	};
	
	Presenter presenter;

	public UsersView() {
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
		presenter.onCreate();
	}
	
	@UiHandler("delete")
	void onDeleteClick(ClickEvent event) {
		Set<User> users = selectionModel.getSelectedSet();
		String[] ids = Collections2.transform(users, new Function<User, String>() {

			@Override
			public String apply(User user) {
				return user.getId();
			}
			
		}).toArray(new String[0]);
		Administration.CLOUD.deleteUsers(ids, new AsyncCallback<Void>() {
			
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
		grid = new DataGrid<User>();
		selectionModel = new MultiSelectionModel<User>();
		Column<User, Boolean> checkboxColumn = new Column<User, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(User object) {
				return selectionModel.isSelected(object);
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		TextColumn<User> nameColumn = new TextColumn<User>() {
			@Override
			public String getValue(User object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(nameColumn, "120px");
		grid.addColumn(nameColumn, "Name");
		TextColumn<User> descriptionColumn = new TextColumn<User>() {
			@Override
			public String getValue(User object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(descriptionColumn, "120px");
		grid.addColumn(descriptionColumn, "Description");
		ButtonCell previewButton = new ButtonCell();
		Column<User,String> preview = new Column<User,String>(previewButton) {
		  public String getValue(User object) {
		    return "Preview";
		  }
		};
		preview.setFieldUpdater(new FieldUpdater<User, String>() {
		  @Override
		  public void update(int index, User server, String value) {
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
	
	private void onPreview(User server) {
		details.id.setText(server.getId());
		details.name.setText(server.getName());
		details.email.setText(server.getEmail());
		details.enabled.setText(String.valueOf(server.isEnabled()));
	}
	
}


