package org.cloudsherpa.ui.client.compute.volume;

import org.cloudsherpa.portal.client.Portal;
import org.cloudsherpa.ui.client.compute.common.PortalPlace;
import org.cloudsherpa.ui.client.compute.snapshot.CreateSnapshot;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class VolumesActivity extends AbstractActivity implements VolumesView.Presenter {
	
	public static final VolumesView VIEW = new VolumesView();

	private PortalPlace place;

	public VolumesActivity(PortalPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
	}

	@Override
	public void onCreate() {
		CreateVolume widget = new CreateVolume();
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
	public void onAttach() {
		AttachVolume widget = new AttachVolume();
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
		
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
