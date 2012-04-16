package org.openstack.portal.client;

import org.openstack.ui.client.compute.common.PortalPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({PortalPlace.Tokenizer.class})
public interface PortalPlaceHistoryMapper extends PlaceHistoryMapper {

}
