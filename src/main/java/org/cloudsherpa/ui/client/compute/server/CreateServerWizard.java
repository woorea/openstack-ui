package org.cloudsherpa.ui.client.compute.server;

import org.cloudsherpa.ui.client.compute.common.MetadataEditor;
import org.cloudsherpa.ui.client.compute.image.ImagePicker;
import org.openstack.model.compute.ServerForCreate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateServerWizard extends Composite implements Editor<ServerForCreate>, ImagePicker.Listener {

	private static CreateServerWizardUiBinder uiBinder = GWT
			.create(CreateServerWizardUiBinder.class);

	interface CreateServerWizardUiBinder extends
			UiBinder<Widget, CreateServerWizard> {
	}
	
	interface CreateServerRequestDriver extends SimpleBeanEditorDriver<ServerForCreate, CreateServerWizard> {

	}
	
	private static final CreateServerRequestDriver createServerRequestDriver = GWT.create(CreateServerRequestDriver.class);

	
	@UiField HorizontalPanel steps;
	
	@UiField DeckLayoutPanel deck;
	
	@UiField TextBox name;
	
	@UiField ImagePicker imageRef;
	
	@UiField ListBox flavorRef;
	
	@UiField IntegerBox min;
	
	@UiField IntegerBox max;
	
	@UiField MetadataEditor metadata;
	
	@UiField PersonalityEditor personality;
	
	@UiField ListBox keyName;
	
	@UiField ListBox securityGroups;
	
	@UiField Button save;

	public CreateServerWizard() {
		initWidget(uiBinder.createAndBindUi(this));
		for (int i = 0; i < deck.getWidgetCount(); i++) {
			HTML step = new HTML("<h3>Step" + (i + 1) + "</h3><p>" + deck.getWidget(i).getTitle() + "</p>");
			final int j = i;
			step.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					deck.showWidget(j);
	
				}
			});
			steps.add(step);
		}
		imageRef.setListener(this);
		deck.showWidget(0);
	}
	
	public void edit(ServerForCreate serverForCreate) {
		createServerRequestDriver.initialize(this);
		createServerRequestDriver.edit(serverForCreate);
		
	}
	
	public ServerForCreate flush() {
		return createServerRequestDriver.flush();
	}

	@Override
	public void onImageSelected() {
		deck.showWidget(1);
	}
	
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		Window.alert(flush().toString());
	}

}
