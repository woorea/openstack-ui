package org.openstack.ui.client.compute.common;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PortalPlace extends Place {

	private String tenantId;
	
	private String token;
	
	public PortalPlace(String tenantId, String token) {
		this.tenantId = tenantId;
		this.token = token;
	}
	
	public String getTenantId() {
		return tenantId;
	}
	
	public String getToken() {
		return token;
	}

	@Prefix("p")
	public static class Tokenizer implements PlaceTokenizer<PortalPlace> {

		@Override
		public PortalPlace getPlace(String token) {
			String[] tokens = token.split(":");
			return new PortalPlace(tokens[0], tokens[1]);
		}

		@Override
		public String getToken(PortalPlace place) {
			return place.tenantId + ":"+ place.token;
		}
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tenantId == null) ? 0 : tenantId.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortalPlace other = (PortalPlace) obj;
		if (tenantId == null) {
			if (other.tenantId != null)
				return false;
		} else if (!tenantId.equals(other.tenantId))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

}
