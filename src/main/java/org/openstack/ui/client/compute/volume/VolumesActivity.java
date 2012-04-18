package org.openstack.ui.client.compute.volume;

import org.openstack.model.compute.Volume;
import org.openstack.portal.client.Portal;
import org.openstack.portal.client.PortalActivity;
import org.openstack.ui.client.compute.common.PortalPlace;
import org.openstack.ui.client.compute.snapshot.CreateSnapshot;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class VolumesActivity extends PortalActivity implements VolumesView.Presenter {
	
	public static final VolumesView VIEW = new VolumesView();

	public VolumesActivity(PortalPlace place) {
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
		
		
	}

	@Override
	public void onDelete() {
		onRefresh();
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onAttach(Volume volume) {
		VIEW.refresh();
	}

	@Override
	public void onDetach() {
		
	}

	@Override
	public void onCreateSnapshot() {
		CreateSnapshot widget = new CreateSnapshot();
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}

}
