package org.openstack.ui.client.compute.server;

import org.openstack.model.compute.nova.NovaServerForCreate;
import org.openstack.ui.client.compute.server.PersonalityEditor.ItemEditor;

import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.EditorSource;
import com.google.gwt.editor.client.adapters.ListEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.TextBox;

public class PersonalityEditor extends Composite implements IsEditor<ListEditor<NovaServerForCreate.File, ItemEditor>> {

	public static class ItemEditor implements Editor<NovaServerForCreate.File> {

		TextBox path;

		TextBox contents;

		public ItemEditor() {
			path = new TextBox();
			contents = new TextBox();
		}

	}

	FlexTable table = new FlexTable();

	ListEditor<NovaServerForCreate.File, ItemEditor> editor = ListEditor.of(new EditorSource<ItemEditor>() {

		@Override
		public ItemEditor create(int index) {
			ItemEditor srcEditor = new ItemEditor();
			table.setWidget(index, 0, srcEditor.path);
			table.setWidget(index, 1, srcEditor.contents);
			Button remove = new Button("&times", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Cell cell = table.getCellForEvent(event);
					if(cell != null) {
						table.removeRow(cell.getRowIndex());
						editor.getList().remove(cell.getRowIndex());
					}
				}
			});
			remove.setStyleName("btn");
			table.setWidget(index, 2, remove);
			table.getCellFormatter().getElement(index, 2).getStyle().setVerticalAlign(VerticalAlign.TOP);
			return srcEditor;
		}

	});

	public PersonalityEditor() {
		FlowPanel panel = new FlowPanel();
		Button add = new Button("Add", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				editor.getList().add(new NovaServerForCreate.File());
			}

		});
		panel.add(add);
		table = new FlexTable();
		table.setWidth("100%");
		panel.add(table);
		initWidget(panel);
	}

	@Override
	public ListEditor<NovaServerForCreate.File, ItemEditor> asEditor() {
		return editor;
	}
	
}
