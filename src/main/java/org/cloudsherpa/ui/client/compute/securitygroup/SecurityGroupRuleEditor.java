package org.cloudsherpa.ui.client.compute.securitygroup;

import java.io.IOException;
import java.util.Arrays;

import org.cloudsherpa.portal.client.Portal;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupRule;
import org.openstack.model.compute.SecurityGroupRuleForCreate;
import org.openstack.model.compute.nova.securitygroup.NovaSecurityGroupRuleForCreate;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SecurityGroupRuleEditor extends Composite implements Editor<SecurityGroupRuleForCreate> {
	
	VerticalPanel panel = new VerticalPanel();
	
	FlexTable table;

	ValueListBox<String> ipProtocol;

	IntegerBox fromPort;

	IntegerBox toPort;
	
	TextBox cidr;

	public SecurityGroupRuleEditor() {
		
		panel.setHeight("100px");
		panel.setWidth("100%");

		ipProtocol = new ValueListBox<String>(new Renderer<String>() {

			@Override
			public String render(String object) {
				return object;
			}

			@Override
			public void render(String object, Appendable appendable)
					throws IOException {
				appendable.append(object);

			}
		});
		ipProtocol.setAcceptableValues(Arrays.asList("", "TCP","UDP","ICMP"));
		ipProtocol.setWidth("100px");

		fromPort = new IntegerBox();
		fromPort.setWidth("60px");
		fromPort.getElement().setAttribute("type", "number");

		toPort = new IntegerBox();
		toPort.setWidth("60px");
		toPort.getElement().setAttribute("type", "number");

		cidr = new TextBox();
		cidr.getElement().setAttribute("placeholder", "0.0.0.0/24");
		
		table = new FlexTable();
		table.setWidth("100%");
		
		initWidget(panel);
	}
	
	public void refresh(final Integer securityGroupId) {
		panel.clear();
		table.removeAllRows();
		
		Grid form = new Grid(1,5);
		form.setWidth("100%");
		form.setWidget(0, 0, ipProtocol);
		form.setWidget(0, 1, fromPort);
		form.setWidget(0, 2, toPort);
		form.setWidget(0, 3, cidr);
		form.setWidget(0, 4, new Button("Add", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				SecurityGroupRuleForCreate rule = new NovaSecurityGroupRuleForCreate();
				rule.setParentGroupId(securityGroupId);
				rule.setIpProtocol(ipProtocol.getValue());
				rule.setFromPort(fromPort.getValue());
				rule.setToPort(toPort.getValue());
				rule.setCidr(cidr.getValue());
				Portal.CLOUD.create(rule, new AsyncCallback<SecurityGroupRule>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
						
					}

					@Override
					public void onSuccess(SecurityGroupRule result) {
						refresh(securityGroupId);
					}
				
				});
				
			}
		}));
		
		panel.add(form);
		
		ScrollPanel scroll = new ScrollPanel();
		scroll.setHeight("300px");
		scroll.add(table);
		panel.add(scroll);
		
		Portal.CLOUD.showSecurityGroup(securityGroupId, new AsyncCallback<SecurityGroup>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				
			}

			@Override
			public void onSuccess(SecurityGroup result) {
				
				int row = 0;
				for(final SecurityGroupRule r : result.getRules()) {
					table.setText(row, 0, r.getIpProtocol());
					table.setText(row, 1, r.getFromPort().toString());
					table.setText(row, 2, r.getToPort().toString());
					table.setText(row, 3, r.getIpRange().getCidr());
					table.setWidget(row++, 4, new Button("x", new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							Portal.CLOUD.deleteSecurityGroupRule(r.getId(), new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert(caught.getMessage());
									
								}

								@Override
								public void onSuccess(Void result) {
									refresh(securityGroupId);	
								}
							
							});
						}
					}));
				}	
			}
		});
		
	}
	
}
