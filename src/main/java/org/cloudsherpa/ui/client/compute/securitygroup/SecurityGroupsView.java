package org.cloudsherpa.ui.client.compute.securitygroup;

import java.util.List;
import java.util.Set;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupRule;

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
					update();
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
		Set<SecurityGroup> users = selectionModel.getSelectedSet();
		Integer[] ids = Collections2.transform(users, new Function<SecurityGroup, Integer>() {

			@Override
			public Integer apply(SecurityGroup user) {
				return user.getId();
			}
			
		}).toArray(new Integer[0]);
		Portal.CLOUD.deleteSecurityGroups(ids, new AsyncCallback<Void>() {
			
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
		grid = new DataGrid<SecurityGroup>();
		selectionModel = new MultiSelectionModel<SecurityGroup>();
		Column<SecurityGroup, Boolean> checkboxColumn = new Column<SecurityGroup, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(SecurityGroup object) {
				return selectionModel.isSelected(object);
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
		details.name.setText(securityGroup.getName());
		details.description.setText(securityGroup.getDescription());
		
		int row = 0;
		for(SecurityGroupRule rule : securityGroup.getRules()) {
			details.rules.setText(row, 0, rule.getIpProtocol());
			details.rules.setText(row, 1, String.valueOf(rule.getFromPort()));
			details.rules.setText(row, 2, String.valueOf(rule.getToPort()));
			details.rules.setText(row, 3, rule.getIpRange().getCidr());
			row++;
		}
	}
	
}


