package org.openstack.ui.client.compute.common;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PortalPlace extends Place {

	private String token;
	
	public PortalPlace(String token) {
		this.token = token;
	}
	
	public Object getToken() {
		return token;
	}

	@Prefix("p")
	public static class Tokenizer implements PlaceTokenizer<PortalPlace> {

		@Override
		public PortalPlace getPlace(String token) {
			return new PortalPlace(token);
		}

		@Override
		public String getToken(PortalPlace place) {
			return place.token;
		}
		
	}

}
