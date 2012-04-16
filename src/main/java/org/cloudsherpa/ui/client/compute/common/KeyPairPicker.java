package org.cloudsherpa.ui.client.compute.common;

import java.util.List;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.KeyPair;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ValueListBox;

public class KeyPairPicker extends ValueListBox<String> {

	public KeyPairPicker() {
		super(new AbstractRenderer<String>() {

			@Override
			public String render(String object) {
				return object;
			}
		});
		refresh();
	}
	
	public void refresh() {
		Portal.CLOUD.listKeyPairs(new AsyncCallback<List<KeyPair>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(List<KeyPair> result) {
				setAcceptableValues(Lists.transform(result, new Function<KeyPair, String>() {

					@Override
					public String apply(KeyPair kp) {
						return kp.getName();
					}
				}));
			}

		});
	}

}
