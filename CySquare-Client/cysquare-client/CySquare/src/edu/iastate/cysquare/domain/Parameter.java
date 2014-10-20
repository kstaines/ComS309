package edu.iastate.cysquare.domain;

import org.apache.http.NameValuePair;

public class Parameter implements NameValuePair {

	private String name;
	
	private String value;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
