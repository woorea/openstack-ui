package org.openstack.ui.client.compute.volume;

import java.util.List;

import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.Volume;
import org.openstack.model.compute.nova.volume.NovaVolumeForCreate;
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.portal.client.Portal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateVolume extends Composite {

	private static CreateVolumeUiBinder uiBinder = GWT
			.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateVolume> {
	}
	
	@UiField ListBox snapshots;
	@UiField IntegerBox sizeInGB;
	@UiField TextBox name;
	@UiField TextArea description;

	public CreateVolume(final Integer snapshotId) {
		Portal.CLOUD.listSnapshots(new AsyncCallback<List<Snapshot>>() {
			
			@Override
			public void onSuccess(List<Snapshot> result) {
				int index = 0;
				for(Snapshot s : result) {
					snapshots.addItem(s.getName() + " " + s.getId(), String.valueOf(s.getId()));
					if(snapshotId != null && s.getId().equals(snapshotId)) {
						snapshots.setSelectedIndex(index);
					}
					index++;
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}
	
	public interface Listener {

		void onSave(Volume service);
		
	}
	
	private Listener listener;
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@UiHandler({"save"})
	void onSaveClick(ClickEvent event) {
		VolumeForCreate item = new NovaVolumeForCreate();
		item.setName(name.getValue());
		item.setDescription(description.getValue());
		Portal.CLOUD.create(item, new AsyncCallback<Volume>() {
			
			@Override
			public void onSuccess(Volume result) {
				Portal.MODAL.hide();
				listener.onSave(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
	}

}
