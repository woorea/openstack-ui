package org.openstack.ui.client.compute.server;

import java.io.Serializable;
import java.util.List;

import org.openstack.model.compute.KeyPair;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerForCreate;
import org.openstack.model.compute.nova.NovaServerForCreate;
import org.openstack.model.compute.nova.server.actions.RebootAction;
import org.openstack.model.compute.nova.server.actions.RebuildAction;
import org.openstack.portal.client.Portal;
import org.openstack.ui.client.compute.common.FlavorPicker;
import org.openstack.ui.client.compute.common.KeyPairPicker;
import org.openstack.ui.client.compute.common.MapMetadataEditor;
import org.openstack.ui.client.compute.common.SelectMultiple;
import org.openstack.ui.client.compute.image.ImagePicker;
import org.openstack.ui.client.compute.keypair.CreateKeyPairForm;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RebuildServerWizard extends Composite implements Editor<RebuildAction>, ImagePicker.Listener {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, RebuildServerWizard> {
	}
	
	interface CreateServerRequestDriver extends SimpleBeanEditorDriver<RebuildAction, RebuildServerWizard> {

	}
	
	private static final CreateServerRequestDriver createServerRequestDriver = GWT.create(CreateServerRequestDriver.class);
	
	public interface Listener {
		void onServerRebuilt(Server server);
	}
	
	private Listener listener;
	
	private Server server;
	
	@UiField HorizontalPanel steps;
	
	@UiField DeckLayoutPanel deck;
	
	@UiField TextBox name;
	
	@UiField ImagePicker imageRef;
	
	@UiField MapMetadataEditor metadata;
	
	@UiField PersonalityEditor personality;
	
	@UiField
	Button cancel;
	@UiField
	Button previous;
	@UiField
	Button next;
	@UiField
	Button finish;

	public RebuildServerWizard() {
		initWidget(uiBinder.createAndBindUi(this));
		for (int i = 0; i < deck.getWidgetCount(); i++) {
			HTML step = new HTML("<h3>Step" + (i + 1) + "</h3><p>" + deck.getWidget(i).getTitle() + "</p>");
			final int j = i;
			step.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					step(j);
	
				}
			});
			steps.add(step);
		}
		imageRef.setListener(this);
		step(0);
	}
	
	
	
	public void edit(RebuildAction rebuildAction) {
		createServerRequestDriver.initialize(this);
		createServerRequestDriver.edit(rebuildAction);
		
	}
	
	public RebuildAction flush() {
		return createServerRequestDriver.flush();
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	@Override
	public void onImageSelected() {
		step(1);
	}
	
	public void step(int index) {
		previous.setEnabled(index != 0);
		next.setVisible(index < (deck.getWidgetCount() - 1));
		finish.setVisible(!next.isVisible());
		deck.showWidget(index);
		
	}
	
	@UiHandler("previous")
	void onPreviousClick(ClickEvent event) {
		int currentIndex = deck.getVisibleWidgetIndex();
		if(currentIndex > 0) {
			step(--currentIndex);
		}
	}
	
	@UiHandler("next")
	void onNextClick(ClickEvent event) {
		int currentIndex = deck.getVisibleWidgetIndex();
		if(currentIndex < deck.getWidgetCount()) {
			step(++currentIndex);
		}
	}
	
	@UiHandler("finish")
	void onFinishClick(ClickEvent event) {
		Window.alert(flush().toString());
		/*
		Portal.CLOUD.executeServerAction(server.getId(), flush(), new AsyncCallback<Serializable>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(Serializable result) {
				Portal.MODAL.hide();
			}
		});
		
		Portal.CLOUD.create(flush(), new AsyncCallback<Server>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(Server result) {
				listener.onServerCreated(result);
				
			}
			
		});
		*/
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}

}
