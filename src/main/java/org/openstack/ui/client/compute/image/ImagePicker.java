package org.openstack.ui.client.compute.image;

import org.openstack.model.images.ImageList;
import org.openstack.portal.client.Portal;

import com.google.gwt.dom.client.Style.TableLayout;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

public class ImagePicker extends Composite implements LeafValueEditor<String> {
	
	public interface Listener {
		void onImageSelected();
	}
	
	private Listener listener;
	
	private Grid grid;
	
	private String value;
	
	public ImagePicker() {
		
		grid = new Grid(5,4);
		
		grid.getElement().getStyle().setWidth(100.0, Unit.PCT);
		grid.getElement().getStyle().setTableLayout(TableLayout.FIXED);
		
		initWidget(grid);
		
		Portal.CLOUD.listImages(0, 5,  new AsyncCallback<ImageList>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				
			}

			@Override
			public void onSuccess(final ImageList result) {
				
				
				
				for(int row = 0; row < result.getList().size(); row++) {
					int col = 0;
					grid.setText(row, col++, result.getList().get(row).getId());
					grid.setText(row, col++, result.getList().get(row).getName());
					grid.setText(row, col++, result.getList().get(row).getStatus());
					final int finalRow = row;
					Button b = new Button("select",new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							value = result.getList().get(finalRow).getId();
							listener.onImageSelected();
							
						}
					});
					grid.setWidget(row, col++, b);
					
					
				}
				
			}
		});
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
	
}
