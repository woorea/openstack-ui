package org.cloudsherpa.ui.client.compute.image;

import com.google.gwt.dom.client.Style.TableLayout;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		
		for(int row = 0; row < grid.getRowCount(); row++) {
			int col = 0;
			grid.setText(row, col++, "image."+col);
			grid.setText(row, col++, "image."+col);
			grid.setText(row, col++, "image."+col);
			Button b = new Button("select",new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					value = "1234";
					listener.onImageSelected();
					
				}
			});
			grid.setWidget(row, col++, b);
		}
		initWidget(grid);
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
