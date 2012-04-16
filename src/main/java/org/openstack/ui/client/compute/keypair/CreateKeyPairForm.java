package org.openstack.ui.client.compute.keypair;

import java.util.List;

import org.openstack.model.compute.KeyPair;
import org.openstack.portal.client.Portal;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateKeyPairForm extends Composite {

	private static CreateKeyPairFormUiBinder uiBinder = GWT
			.create(CreateKeyPairFormUiBinder.class);

	interface CreateKeyPairFormUiBinder extends
			UiBinder<Widget, CreateKeyPairForm> {
	}

	public interface Listener {
		void onKeyPairCreated(KeyPair keyPair);
	}

	private Listener listener;

	@UiField
	FormPanel formPanel;
	@UiField
	TextBox keyPairName;
	@UiField
	Anchor createKeyPair;

	public CreateKeyPairForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	@UiHandler("createKeyPair")
	void onCreateKeyPairClick(ClickEvent event) {
		formPanel.addSubmitHandler(new FormPanel.SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				Timer t = new Timer() {

					private static final int MAX = 5;

					private int current = 0;

					public void run() {
						GWT.log("search cookie " + current);
						if (current == MAX) {
							cancel();
						}

						Portal.CLOUD.listKeyPairs(new AsyncCallback<List<KeyPair>>() {

							@Override
							public void onFailure(Throwable caught) {
								cancel();
								Window.alert(caught.getMessage());
							}

							@Override
							public void onSuccess(List<KeyPair> result) {
								
								try {
									KeyPair found = Iterables.find(result, new Predicate<KeyPair>() {

										@Override
										public boolean apply(KeyPair current) {
											return keyPairName.getValue().equals(current.getName());
										}
									});
									keyPairName.setValue(null);
									listener.onKeyPairCreated(found);
									cancel();
								} catch (Exception e) {
									GWT.log("not found" + current);
								}
							}

						});
						current++;
					}
					
				};
				t.scheduleRepeating(1000);

			}
		});
		formPanel.submit();

	}

}
