package org.openstack.ui.client.compute.snapshot;

import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.Volume;
import org.openstack.portal.client.Portal;
import org.openstack.ui.client.compute.common.PortalPlace;

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
	public void onSave(Snapshot snapshot) {
		VIEW.refresh();
	}

	@Override
	public void onDelete() {
		onRefresh();
		
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onSave(Volume service) {
		Portal.PLACE_CONTROLLER.goTo(new PortalPlace("volumes"));
	}

}
