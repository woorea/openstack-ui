package org.cloudsherpa.ui.client.compute.securitygroup;

import org.cloudsherpa.ui.client.compute.common.PortalPlace;
import org.openstack.model.compute.SecurityGroup;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SecurityGroupsActivity extends AbstractActivity implements SecurityGroupsView.Presenter {
	
	public static final SecurityGroupsView VIEW = new SecurityGroupsView();

	private PortalPlace place;

	public SecurityGroupsActivity(PortalPlace place) {
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		VIEW.setPresenter(this);
		panel.setWidget(VIEW);
	}

	@Override
	public void onFinish(SecurityGroup securityGroup) {
		VIEW.refresh();
	}

	@Override
	public void onDelete() {
		onRefresh();
		
	}

	@Override
	public void onRefresh() {
		
	}

}
