package com.geniusgithub.bcoinmonitor.model;

import org.json.JSONException;
import org.json.JSONObject;

public interface IParseJson {

	public boolean parseJson(String content) throws JSONException;
}
