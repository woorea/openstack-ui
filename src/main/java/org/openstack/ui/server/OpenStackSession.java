package org.openstack.ui.server;

import java.io.Serializable;
import java.util.Properties;

import org.openstack.model.identity.Access;

public class OpenStackSession implements Serializable {

	private Properties properties;
	
	private Access access;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Access getAccess() {
		return this.access;
	}

	public void setAccess(Access access) {
		System.out.println("BEFORE " + this.access);
		this.access = access;
		System.out.println("AFTER " + this.access);
	}

}
