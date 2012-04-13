package org.cloudsherpa.ui.client.compute.securitygroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openstack.model.compute.SecurityGroupRule;
import org.openstack.model.compute.SecurityGroupRuleForCreate;
import org.openstack.model.compute.nova.securitygroup.NovaSecurityGroupRule;
import org.openstack.model.compute.nova.securitygroup.NovaSecurityGroupRule.IpRange;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;

public class SecurityGroupRuleEditor extends Composite implements Editor<SecurityGroupRuleForCreate> {
	
	FlexTable table;

	ValueListBox<String> ipProtocol;

	IntegerBox fromPort;

	IntegerBox toPort;
	
	TextBox cidr;

	public SecurityGroupRuleEditor() {
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
		initWidget(table);
		refresh();
	}
	
	public void refresh() {
		List<SecurityGroupRule> rules = new ArrayList<SecurityGroupRule>();
		for(int i = 0; i < 5; i++) {
			SecurityGroupRule rule = new NovaSecurityGroupRule();
			rule.setIpProtocol("TCP");
			rule.setFromPort(22);
			rule.setToPort(25);
			IpRange range = new IpRange();
			range.setCidr("0.0.0.0/8");
			rule.setIpRange(range);
			rules.add(rule);
		}
		table.removeAllRows();
		int row = 0;
		for(SecurityGroupRule r : rules) {
			table.setText(row, 0, r.getIpProtocol());
			table.setText(row, 1, r.getFromPort().toString());
			table.setText(row, 2, r.getToPort().toString());
			table.setText(row, 3, r.getIpRange().getCidr());
			table.setWidget(row++, 4, new Button("x", new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					//refresh();
				}
			}));
		}
		row = table.getRowCount();
		table.setWidget(row, 0, ipProtocol);
		table.setWidget(row, 1, fromPort);
		table.setWidget(row, 2, toPort);
		table.setWidget(row, 3, cidr);
		table.setWidget(row, 4, new Button("", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				//flush
				
			}
		}));
		
	}
	
}
