package org.cloudsherpa.ui.client.compute.securitygroup;

import org.cloudsherpa.portal.client.Portal;
import org.cloudsherpa.ui.client.compute.common.MetadataEditor;
import org.openstack.model.compute.SecurityGroup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateSecurityGroupWizard extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, CreateSecurityGroupWizard> {
	}
	
	public interface Listener {
		void onFinish(SecurityGroup securityGroup);
	}
	
	private Listener listener;
	
	@UiField HorizontalPanel steps;
	
	@UiField DeckLayoutPanel deck;
	
	@UiField SecurityGroupEditor securityGroup;
	
	@UiField SecurityGroupRuleEditor securityGroupRules;
	
	public CreateSecurityGroupWizard() {
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
		deck.showWidget(0);
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
		
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
		
	}
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		if(deck.getVisibleWidgetIndex() == 0) {
			Portal.CLOUD.create(securityGroup.flush(), new AsyncCallback<SecurityGroup>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
					
				}

				@Override
				public void onSuccess(SecurityGroup result) {
					securityGroupRules.refresh(result.getId());
					deck.showWidget(1);
				}
			
			});
		} else {
			Portal.MODAL.hide();
			listener.onFinish(null);
		}
		
	}

}
