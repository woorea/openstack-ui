package org.openstack.ui.client.common;

import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.cellview.client.DataGrid;

public interface DefaultGridResources extends DataGrid.Resources {

    interface TableStyle extends DataGrid.Style {
    }

    @NotStrict
    @Source({ DataGrid.Style.DEFAULT_CSS, "org/openstack/ui/client/resources/data_grid.css" })
    DataGrid.Style dataGridStyle();


}