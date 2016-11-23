package com.atorvdm.contribe.util;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This class is a wrapper for any kind of map. Can be used for serialization purposes.
 * @author Maksim Strutinskiy
 * @param <T> Type of the key
 * @param <V> Type of the value
 */
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
