package org.openstack.ui.client.compute.image;

import org.openstack.portal.client.Portal;
import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ImagesActivity extends AbstractActivity implements ImagesView.Presenter {
	
	public static final ImagesView VIEW = new ImagesView();

	private PortalPlace place;

	public ImagesActivity(PortalPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
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
