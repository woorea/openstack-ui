package org.cloudsherpa.ui.client.compute.securitygroup;

import org.openstack.model.compute.SecurityGroupForCreate;
import org.openstack.model.compute.nova.securitygroup.NovaSecurityGroupForCreate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SecurityGroupEditor extends Composite {

	private static Binder uiBinder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, SecurityGroupEditor> {
	}
	
	@UiField TextBox name;
	@UiField TextArea description;

	public SecurityGroupEditor() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void edit(SecurityGroupForCreate securityGroup) {
		
	}
	
	public SecurityGroupForCreate flush() {
		SecurityGroupForCreate sg = new NovaSecurityGroupForCreate();
		sg.setName(name.getValue());
		sg.setDescription(description.getValue());
		return sg;
	}

}
