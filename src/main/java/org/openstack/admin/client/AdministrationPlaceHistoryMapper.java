package org.openstack.admin.client;

import org.openstack.ui.client.compute.common.AdministrationPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({AdministrationPlace.Tokenizer.class})
public interface AdministrationPlaceHistoryMapper extends PlaceHistoryMapper {

}
