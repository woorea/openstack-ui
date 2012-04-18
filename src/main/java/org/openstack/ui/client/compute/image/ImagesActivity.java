package org.openstack.ui.client.compute.image;

import org.openstack.portal.client.Portal;
import org.openstack.portal.client.PortalActivity;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ImagesActivity extends PortalActivity implements ImagesView.Presenter {
	
	public static final ImagesView VIEW = new ImagesView();

	public ImagesActivity(PortalPlace place) {
		super(place);
	}

	@Override
	public void startOnPanel(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
		VIEW.refresh();
	}

	@Override
	public void onCreate() {
		CreateImage widget = new CreateImage();
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
		
	}

	@Override
	public void onDelete() {
		onRefresh();
		
	}

	@Override
	public void onRefresh() {
		
	}
	

}
