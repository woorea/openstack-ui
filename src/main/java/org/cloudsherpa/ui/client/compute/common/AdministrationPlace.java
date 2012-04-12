package org.cloudsherpa.ui.client.compute.common;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AdministrationPlace extends Place {

	private String token;
	
	public AdministrationPlace(String token) {
		this.token = token;
	}
	
	public Object getToken() {
		return token;
	}

	@Prefix("a")
	public static class Tokenizer implements PlaceTokenizer<AdministrationPlace> {

		@Override
		public AdministrationPlace getPlace(String token) {
			return new AdministrationPlace(token);
		}

		@Override
		public String getToken(AdministrationPlace place) {
			return place.token;
		}
		
	}

}
