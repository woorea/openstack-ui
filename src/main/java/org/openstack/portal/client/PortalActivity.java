package org.openstack.portal.client;

import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FlavorList;
import org.openstack.model.images.Image;
import org.openstack.model.images.ImageList;
import org.openstack.ui.client.common.DefaultAsyncCallback;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class PortalActivity extends AbstractActivity {
	
	protected PortalPlace place;

	public PortalActivity(PortalPlace place) {
		this.place = place;
	}


	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
		if(!place.getTenantId().equals(Portal.TENANT_ID)) {
			
			Portal.TENANT_ID = place.getTenantId();
			
			for(int i = 0; i < Portal.VIEW.tenants.getItemCount(); i++) {
				if(Portal.TENANT_ID.equals(Portal.VIEW.tenants.getValue(i))) {
					Portal.VIEW.tenants.setSelectedIndex(i);
					break;
				}
			}
			
			Portal.VIEW.nav.setTenantId(Portal.TENANT_ID);
			
			Portal.CLOUD.authenticate(Portal.TENANT_ID, new DefaultAsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					
					
					Portal.CLOUD.listImages(0, 100, new DefaultAsyncCallback<ImageList>() {

						@Override
						public void onSuccess(ImageList result) {
							Portal.images.clear();
							for(Image image : result) {
								Portal.images.put(image.getId(), image);
							}
						}
						
					});
					
					Portal.CLOUD.listFlavors(0, 100, new DefaultAsyncCallback<FlavorList>() {

						@Override
						public void onSuccess(FlavorList result) {
							Portal.flavors.clear();
							for(Flavor flavor : result) {
								Portal.flavors.put(flavor.getId(), flavor);
							}
						}
						
					});
					startOnPanel(panel, eventBus);
				}
			});
		} else {
			startOnPanel(panel, eventBus);
		}
		
	}
	
	public abstract void startOnPanel(AcceptsOneWidget panel, EventBus eventBus);

}
