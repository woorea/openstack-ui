package org.cloudsherpa.admin.client;

import org.cloudsherpa.ui.client.compute.common.AdministrationPlace;

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

public class Administration implements EntryPoint {

public static final EventBus EVENT_BUS = new SimpleEventBus();
	
	public static final PlaceController PLACE_CONTROLLER = new PlaceController(EVENT_BUS);
	
	public static final PopupPanel MODAL = new PopupPanel(true, true);
	
	public void onModuleLoad() {
		
		Administration.MODAL.setGlassEnabled(true);
		//CloudSherpa.MODAL.setAnimationEnabled(true);
		
		AdministrationView view = new AdministrationView();
		
		ActivityMapper activityMapper = new AdministrationActivityMapper();
		
		ActivityManager activityManager = new ActivityManager(activityMapper, EVENT_BUS);
		activityManager.setDisplay(view.main);
		
		PlaceHistoryMapper historyMapper = GWT.create(AdministrationPlaceHistoryMapper.class);
		PlaceHistoryHandler historyManager = new PlaceHistoryHandler(historyMapper);
		historyManager.register(PLACE_CONTROLLER, EVENT_BUS, new AdministrationPlace("tenants"));
		
		historyManager.handleCurrentHistory();
		
		RootLayoutPanel.get().add(view);
		
	}

}
