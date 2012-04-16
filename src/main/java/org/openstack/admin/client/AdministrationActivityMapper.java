package org.openstack.admin.client;

import org.openstack.ui.client.compute.common.AdministrationPlace;
import org.openstack.ui.client.identity.endpoint.EndpointsActivity;
import org.openstack.ui.client.identity.role.RolesActivity;
import org.openstack.ui.client.identity.service.ServicesActivity;
import org.openstack.ui.client.identity.tenant.TenantsActivity;
import org.openstack.ui.client.identity.user.UsersActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AdministrationActivityMapper implements ActivityMapper {

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AdministrationPlace) {
			AdministrationPlace pp = (AdministrationPlace) place;
			if ("tenants".equals(pp.getToken())) {
				return new TenantsActivity(pp);
			} else if ("users".equals(pp.getToken())) {
				return new UsersActivity(pp);
			} else if ("roles".equals(pp.getToken())) {
				return new RolesActivity(pp);
			} else if ("services".equals(pp.getToken())) {
				return new ServicesActivity(pp);
			} else if ("endpoints".equals(pp.getToken())) {
				return new EndpointsActivity(pp);
			}
		}
		return null;
	}

}
