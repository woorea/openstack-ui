package org.openstack.ui.client.compute.keypair;

import java.util.List;
import java.util.Set;

import org.openstack.model.compute.KeyPair;
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

public class KeyPairsView extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, KeyPairsView> {
	}
	
	public interface Presenter {
		void onCreate();
		void onDelete();
		void onImportPublicKey();
		void onRefresh();
	}
	
	@UiField Button create;
	@UiField Button importPublicKey;
	@UiField Button delete;
	@UiField Button refresh;
	
	@UiField(provided = true) DataGrid<KeyPair> grid;
	
	MultiSelectionModel<KeyPair> selectionModel;
	
	DefaultSelectionEventManager<KeyPair> selectionEventManager = DefaultSelectionEventManager.createCheckboxManager(0);
	
	AsyncDataProvider<KeyPair> asyncDataProvider = new AsyncDataProvider<KeyPair>() {

		@Override
		protected void onRangeChanged(HasData<KeyPair> display) {
			
			final Range range = display.getVisibleRange();
			
			Portal.CLOUD.listKeyPairs(new AsyncCallback<List<KeyPair>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(List<KeyPair> result) {
					update();
					updateRowData(range.getStart(), result);
					updateRowCount(range.getLength(), true);
					
				}
			});
			
		}

	};
	
	Presenter presenter;

	public KeyPairsView() {
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
	
	@UiHandler("importPublicKey")
	void onImportPublicKeyClick(ClickEvent event) {
		presenter.onImportPublicKey();
	}

	@UiHandler("delete")
	void onDeleteClick(ClickEvent event) {
		Set<KeyPair> users = selectionModel.getSelectedSet();
		String[] ids = Collections2.transform(users, new Function<KeyPair, String>() {

			@Override
			public String apply(KeyPair user) {
				return user.getName();
			}
			
		}).toArray(new String[0]);
		Portal.CLOUD.deleteKeyPairs(ids, new AsyncCallback<Void>() {
			
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
		grid = new DataGrid<KeyPair>();
		selectionModel = new MultiSelectionModel<KeyPair>();
		Column<KeyPair, Boolean> checkboxColumn = new Column<KeyPair, Boolean>(new CheckboxCell()) {

			@Override
			public Boolean getValue(KeyPair object) {
				return selectionModel.isSelected(object);
			}
		};
		grid.setColumnWidth(checkboxColumn, "40px");
		grid.addColumn(checkboxColumn, "");
		TextColumn<KeyPair> nameColumn = new TextColumn<KeyPair>() {
			@Override
			public String getValue(KeyPair object) {
				return object.getName();
			}
		};
		grid.setColumnWidth(nameColumn, "120px");
		grid.addColumn(nameColumn, "Name");
		TextColumn<KeyPair> userColumn = new TextColumn<KeyPair>() {
			@Override
			public String getValue(KeyPair object) {
				return object.getUserId();
			}
		};
		grid.setColumnWidth(userColumn, "120px");
		grid.addColumn(userColumn, "User");
		TextColumn<KeyPair> fingerprintColumn = new TextColumn<KeyPair>() {
			@Override
			public String getValue(KeyPair object) {
				return object.getFingerprint();
			}
		};
		grid.setColumnWidth(fingerprintColumn, "120px");
		grid.addColumn(fingerprintColumn, "Fingerprint");
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


