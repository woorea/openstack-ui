package org.openstack.ui.client.common;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UIException extends Exception implements IsSerializable {

	private static final long serialVersionUID = 1;

	public UIException() {
		super();
	}

	public UIException(String message, Throwable cause) {
		super(message, cause);
	}

	public UIException(String message) {
		super(message);
	}

	public UIException(Throwable cause) {
		super(cause);
	}
	
}
