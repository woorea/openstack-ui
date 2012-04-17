package org.openstack.ui.client.common;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class PreviewButtonCell extends ButtonCell {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, SafeHtml data, SafeHtmlBuilder sb) {
		sb.appendHtmlConstant("<button type=\"button\" class=\"btn btn-inverse\" tabindex=\"-1\">");
	    if (data != null) {
	    	sb.appendHtmlConstant("<i class=\"icon-search icon-white\"></i> ");
	    	sb.append(data);
	    }
	    sb.appendHtmlConstant("</button>");
	}

}
