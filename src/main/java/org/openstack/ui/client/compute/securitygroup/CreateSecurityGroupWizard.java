package org.openstack.ui.client.compute.securitygroup;

import org.openstack.model.compute.SecurityGroup;
import org.openstack.portal.client.Portal;
import org.openstack.ui.client.common.DefaultAsyncCallback;

import com.google.gwt.core.client.GWT;
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
	
	@UiField
	Button cancel;
	@UiField
	Button previous;
	@UiField
	Button next;
	@UiField
	Button finish;
	
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
		step(0);
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
		
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
		final int currentIndex = deck.getVisibleWidgetIndex();
		Portal.CLOUD.create(securityGroup.flush(), new DefaultAsyncCallback<SecurityGroup>() {

			@Override
			public void onSuccess(SecurityGroup result) {
				if(currentIndex < deck.getWidgetCount()) {
					GWT.log(""+result);
					securityGroupRules.refresh(result.getId());
					step(currentIndex + 1);
				}
			}
		
		});
		
	}
	
	@UiHandler("finish")
	void onFinishClick(ClickEvent event) {
		Portal.MODAL.hide();
		listener.onFinish(null);
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
		
	}

}
