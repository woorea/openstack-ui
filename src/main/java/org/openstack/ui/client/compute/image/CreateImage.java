package org.openstack.ui.client.compute.image;

import org.openstack.portal.client.Portal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;

public class CreateImage extends Composite {
	
	private static final String UPLOAD_ACTION_URL = GWT.getHostPageBaseURL() + "image-upload";

	private static CreateVolumeUiBinder uiBinder = GWT.create(CreateVolumeUiBinder.class);

	interface CreateVolumeUiBinder extends UiBinder<Widget, CreateImage> {
	}
	
	private static final String[] dfs = new String[]{"aki", "ari", "ami", "raw", "iso", "vhd", "vdi", "qcow2", "vmdk"};
	private static final String[] cfs = new String[]{"aki", "ari", "ami", "bare", "ovf"};
	
	@UiField FormPanel form;
	
	@UiField TextBox name;
	
	@UiField ListBox diskFormat;
	
	@UiField ListBox containerFormat;
	
	@UiField FileUpload file;
	
	@UiField Button upload;

	public CreateImage() {
		initWidget(uiBinder.createAndBindUi(this));
		form.setAction(UPLOAD_ACTION_URL);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		for(String df : dfs) {
			diskFormat.addItem(df);
		}
		for(String cf : cfs) {
			containerFormat.addItem(cf);
		}
		upload.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				form.submit();
				
			}
		});
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			
			@Override
			public void onSubmit(SubmitEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	
	
	@UiHandler({"close","cancel"})
	void onCloseClick(ClickEvent event) {
		Portal.MODAL.hide();
	}

}
