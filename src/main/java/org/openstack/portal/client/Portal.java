package org.openstack.portal.client;

import java.util.HashMap;
import java.util.Map;

import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FlavorList;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.TenantList;
import org.openstack.model.images.Image;
import org.openstack.model.images.ImageList;
import org.openstack.ui.client.UIService;
import org.openstack.ui.client.UIServiceAsync;
import org.openstack.ui.client.common.DefaultAsyncCallback;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Portal implements EntryPoint {
	
	public static final EventBus EVENT_BUS = new SimpleEventBus();
	
	public static final PlaceController PLACE_CONTROLLER = new PlaceController(EVENT_BUS);
	
	public static final PopupPanel MODAL = new PopupPanel(true, true);
	
	public static final UIServiceAsync CLOUD = GWT.create(UIService.class);
	
	public static final Map<String, Flavor> flavors = new HashMap<String, Flavor>();
	
	public static final Map<String, Image> images = new HashMap<String, Image>();
	
	public static String TENANT_ID;
	
	public static final PortalView VIEW = new PortalView();
	
	public void onModuleLoad() {
		
		
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				
				Portal.MODAL.setGlassEnabled(true);
				//CloudSherpa.MODAL.setAnimationEnabled(true);
				
				
				
				ActivityMapper activityMapper = new PortalActivityMapper();
				
				ActivityManager activityManager = new ActivityManager(activityMapper, EVENT_BUS);
				activityManager.setDisplay(VIEW.main);
				
				Portal.CLOUD.listUserTenants(new DefaultAsyncCallback<TenantList>() {

					@Override
					public void onSuccess(TenantList result) {
						
						for(Tenant t : result.getList()) {
							VIEW.tenants.addItem(t.getName(), t.getId());
						}
					
						PlaceHistoryMapper historyMapper = GWT.create(PortalPlaceHistoryMapper.class);
						PlaceHistoryHandler historyManager = new PlaceHistoryHandler(historyMapper);
						historyManager.register(Portal.PLACE_CONTROLLER, Portal.EVENT_BUS, new PortalPlace(TENANT_ID,"servers"));
						
						historyManager.handleCurrentHistory();
						
						RootLayoutPanel.get().add(VIEW);
						
					}
					
				});
				
			}
		});
		
	}
	
}
