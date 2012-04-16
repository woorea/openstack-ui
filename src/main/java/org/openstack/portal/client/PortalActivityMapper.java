package org.openstack.portal.client;

import org.openstack.ui.client.compute.common.PortalPlace;
import org.openstack.ui.client.compute.floatingip.FloatingIpsActivity;
import org.openstack.ui.client.compute.image.ImagesActivity;
import org.openstack.ui.client.compute.keypair.KeyPairsActivity;
import org.openstack.ui.client.compute.securitygroup.SecurityGroupsActivity;
import org.openstack.ui.client.compute.server.ServersActivity;
import org.openstack.ui.client.compute.snapshot.SnapshotsActivity;
import org.openstack.ui.client.compute.volume.VolumesActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;

public class PortalActivityMapper implements ActivityMapper {

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof PortalPlace) {
			PortalPlace pp = (PortalPlace) place;
			if ("servers".equals(pp.getToken())) {
				return new ServersActivity(pp);
			} else if ("images".equals(pp.getToken())) {
				return new ImagesActivity(pp);
			} else if ("floatingips".equals(pp.getToken())) {
				return new FloatingIpsActivity(pp);
			} else if ("volumes".equals(pp.getToken())) {
				return new VolumesActivity(pp);
			} else if ("snapshots".equals(pp.getToken())) {
				return new SnapshotsActivity(pp);
			} else if ("keypairs".equals(pp.getToken())) {
				return new KeyPairsActivity(pp);
			} else if ("securitygroups".equals(pp.getToken())) {
				return new SecurityGroupsActivity(pp);
			}
		}
		GWT.log("No place");
		return null;
	}

}
