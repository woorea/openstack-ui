package org.openstack.portal.client;

import org.openstack.ui.client.UIService;
import org.openstack.ui.client.UIServiceAsync;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
	
	public void onModuleLoad() {
		
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
	
}
