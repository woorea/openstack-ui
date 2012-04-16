package org.cloudsherpa.ui.client.compute.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.TextBox;

public class MapMetadataEditor extends Composite implements LeafValueEditor<Map<String,String>> {
	
	FlexTable table;

	public MapMetadataEditor() {
		FlowPanel panel = new FlowPanel();
		Button add = new Button("Add", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int index = table.getRowCount();
				table.setWidget(index, 0, new TextBox());
				table.setWidget(index, 1, new TextBox());
				Button remove = new Button("&times", new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						Cell cell = table.getCellForEvent(event);
						if(cell != null) {
							table.removeRow(cell.getRowIndex());
						}
					}
				});
				remove.setStyleName("btn");
				table.setWidget(index, 2, remove);
				table.getCellFormatter().getElement(index, 2).getStyle().setVerticalAlign(VerticalAlign.TOP);
			}

		});
		panel.add(add);
		table = new FlexTable();
		table.setWidth("100%");
		panel.add(table);
		initWidget(panel);
	}

	@Override
	public void setValue(Map<String, String> value) {
		int row = 0;
		for(Entry<String, String> entry : value.entrySet()) {
			TextBox k = new TextBox();
			k.setValue(entry.getKey());
			TextBox v = new TextBox();
			v.setValue(entry.getValue());
			table.setWidget(row, 0, k);
			table.setWidget(row, 1, v);
		}
	}

	@Override
	public Map<String, String> getValue() {
		Map<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < table.getRowCount(); i++) {
			TextBox k = (TextBox) table.getWidget(i, 0);
			TextBox v = (TextBox) table.getWidget(i, 1);
			map.put(k.getValue(), v.getValue());
		}
		return map;
	}
	
}
