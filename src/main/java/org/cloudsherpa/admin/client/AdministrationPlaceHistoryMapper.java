package org.cloudsherpa.admin.client;

import org.cloudsherpa.ui.client.compute.common.AdministrationPlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({AdministrationPlace.Tokenizer.class})
public interface AdministrationPlaceHistoryMapper extends PlaceHistoryMapper {

}
