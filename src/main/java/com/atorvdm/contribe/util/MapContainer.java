package com.atorvdm.contribe.util;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class MapContainer<T, V> implements Serializable {
	private static final long serialVersionUID = 553648637879905186L;
	private Map<T, V> map;
	
	public MapContainer(Map<T, V> map) {
		super();
		this.map = map;
	}

	@JsonSerialize(using = MapToCoupleArraySerializer.class)
	public Map<T, V> getMap() {
		return map;
	}

	public void setMap(Map<T, V> map) {
		this.map = map;
	}
}
