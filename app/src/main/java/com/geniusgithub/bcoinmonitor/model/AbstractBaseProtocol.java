package com.geniusgithub.bcoinmonitor.model;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBaseProtocol implements IToStringMap{

	public Map<String, String> mapValue = new HashMap<String, String>();
	
	@Override
	public Map<String, String> toStringMap() {	
		return mapValue;
	}
}
