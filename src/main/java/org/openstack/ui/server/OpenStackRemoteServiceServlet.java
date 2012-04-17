package org.openstack.ui.server;

import javax.servlet.http.HttpSession;

import org.openstack.client.ComputeClient;
import org.openstack.client.IdentityClient;
import org.openstack.client.ImagesClient;
import org.openstack.client.OpenStackClient;
import org.openstack.ui.client.common.UIException;
import org.openstack.ui.client.common.UISecurityException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class OpenStackRemoteServiceServlet extends RemoteServiceServlet {

	protected OpenStackClient getClient() throws UIException {
		HttpSession httpSession =  perThreadRequest.get().getSession(false);
		if(httpSession == null) {
			throw new UISecurityException("Session expired!");
		}
		OpenStackSession session = (OpenStackSession) httpSession.getAttribute(Constants.OPENSTACK_SESSION);
		return OpenStackClient.authenticate(session.getProperties(), session.getAccess());
		
	}
	
	protected IdentityClient getIdentityClient() throws UIException {
		return getClient().getIdentityClient();
	}
	
	protected ComputeClient getComputeClient() throws UIException {
		return getClient().getComputeClient();
	}
	
	protected ImagesClient getImagesClient() throws UIException {
		return getClient().getImagesClient();
	}
	
}
