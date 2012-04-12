package org.cloudsherpa.ui.client.compute.securitygroup;

import java.util.List;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.SecurityGroup;

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

public class SecurityGroupsView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, SecurityGroupsView> {
	}
	
	public interface Presenter {
		void onCreate();
		void onDelete();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button delete;
	@UiField Button refresh;
	
	@UiField SecurityGroupDetails details;
	
	@UiField(provided = true) DataGrid<SecurityGroup> grid;
	
	MultiSelectionModel<SecurityGroup> selectionModel;
	
	DefaultSelectionEventManager<SecurityGroup> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<SecurityGroup> asyncDataProvider = new AsyncDataProvider<SecurityGroup>() {

		@Override
		protected void onRangeChanged(HasData<SecurityGroup> display) {
			
			final Range range = display.getVisibleRange();
			
			Portal.CLOUD.listSecurityGroups(new AsyncCallback<List<SecurityGroup>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<SecurityGroup> result) {
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					
				}
			});
			
		}

	};
	
	Presenter presenter;

	public SecurityGroupsView() {
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
		grid = new DataGrid<SecurityGroup>();
		selectionModel = new MultiSelectionModel<SecurityGroup>();
		Column<SecurityGroup, Boolean> checkboxColumn = new Column<SecurityGroup, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(SecurityGroup object) {
				return false;
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		TextColumn<SecurityGroup> nameColumn = new TextColumn<SecurityGroup>() {
			@Override
			public String getValue(SecurityGroup object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(nameColumn, "120px");
		grid.addColumn(nameColumn, "");
		ButtonCell previewButton = new ButtonCell();
		Column<SecurityGroup,String> preview = new Column<SecurityGroup,String>(previewButton) {
		  public String getValue(SecurityGroup object) {
		    return "Preview";
		  }
		};
		preview.setFieldUpdater(new FieldUpdater<SecurityGroup, String>() {
		  @Override
		  public void update(int index, SecurityGroup securityGroup, String value) {
		    onPreview(securityGroup);
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
	
	private void onPreview(SecurityGroup securityGroup) {
		details.id.setText(securityGroup.getId().toString());
	}
	
}


