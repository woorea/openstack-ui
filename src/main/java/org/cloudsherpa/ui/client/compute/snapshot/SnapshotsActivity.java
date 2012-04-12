package org.cloudsherpa.ui.client.compute.snapshot;

import org.cloudsherpa.portal.client.Portal;
import org.cloudsherpa.ui.client.compute.common.PortalPlace;
import org.cloudsherpa.ui.client.compute.volume.CreateVolume;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SnapshotsActivity extends AbstractActivity implements SnapshotsView.Presenter {
	
	public static final SnapshotsView VIEW = new SnapshotsView();

	private PortalPlace place;

	public SnapshotsActivity(PortalPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
	}

	@Override
	public void onCreate() {
		CreateSnapshot widget = new CreateSnapshot();
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

	@Override
	public void onCreateVolume() {
		CreateVolume widget = new CreateVolume();
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
		
	}

}
