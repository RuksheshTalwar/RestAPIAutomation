package com.api.rest.api.helper;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Features {

	private List<String> feature;
	
	@XmlElement(name="Feature")
	public List<String> getFeature() {
		return feature;
	}

	public void setFeature(List<String> feature) {
		this.feature = feature;
	}
}
