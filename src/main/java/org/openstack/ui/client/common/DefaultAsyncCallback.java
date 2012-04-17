package org.openstack.ui.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DefaultAsyncCallback<T> implements AsyncCallback<T>{

	@Override
	public void onFailure(Throwable caught) {
		if(caught instanceof UISecurityException) {
			Window.Location.replace(GWT.getHostPageBaseURL() + "index.jsp");
		} else {
			caught.printStackTrace();
			GWT.log("1: " + caught.getMessage());
			if(caught.getCause() != null) {
				GWT.log("2: " + caught.getCause().getMessage());
			}
		}
		
	}

	@Override
	public void onSuccess(T result) {
		//do nothing
	}

}
