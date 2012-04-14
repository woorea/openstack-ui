package org.cloudsherpa.ui.client.compute.snapshot;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.SnapshotForCreate;
import org.openstack.model.compute.nova.snapshot.NovaSnapshotForCreate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateSnapshot extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateSnapshot> {
	}

	public CreateSnapshot() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public interface Listener {

		void onSave(Snapshot service);
		
	}
	
	private Listener listener;
	
	Integer volumeId;
	@UiField TextBox name;
	@UiField TextBox description;
	@UiField CheckBox force;
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		SnapshotForCreate item = new NovaSnapshotForCreate();
		item.setVolumeId(volumeId);
		item.setName(name.getValue());
		item.setDescription(description.getValue());
		item.setForce(force.getValue());
		Portal.CLOUD.create(item, new AsyncCallback<Snapshot>() {
			
			@Override
			public void onSuccess(Snapshot result) {
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
