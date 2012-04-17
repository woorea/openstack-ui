package org.openstack.portal.client;

import java.util.HashMap;
import java.util.Map;

import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FlavorList;
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
	
	public void onModuleLoad() {
		
		Portal.CLOUD.listFlavors(0, 100, new DefaultAsyncCallback<FlavorList>() {

			@Override
			public void onSuccess(FlavorList result) {
				for(Flavor flavor : result) {
					flavors.put(flavor.getId(), flavor);
				}
			}
			
		});
		
		Portal.CLOUD.listImages(0, 100, new DefaultAsyncCallback<ImageList>() {

			@Override
			public void onSuccess(ImageList result) {
				for(Image image : result) {
					images.put(image.getId(), image);
				}
			}
			
		});
		
		
		
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				
				Portal.MODAL.setGlassEnabled(true);
				//CloudSherpa.MODAL.setAnimationEnabled(true);
				
				PortalView view = new PortalView();
				
				ActivityMapper activityMapper = new PortalActivityMapper();
				
				ActivityManager activityManager = new ActivityManager(activityMapper, EVENT_BUS);
				activityManager.setDisplay(view.main);
				
				PlaceHistoryMapper historyMapper = GWT.create(PortalPlaceHistoryMapper.class);
				PlaceHistoryHandler historyManager = new PlaceHistoryHandler(historyMapper);
				historyManager.register(PLACE_CONTROLLER, EVENT_BUS, new PortalPlace("servers"));
				
				historyManager.handleCurrentHistory();
				
				RootLayoutPanel.get().add(view);
				
			}
		});
		
		
		
	}
	
}
