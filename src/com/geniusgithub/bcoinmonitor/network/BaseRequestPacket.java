package com.geniusgithub.bcoinmonitor.network;

import android.content.Context;

import com.geniusgithub.bcoinmonitor.model.IToStringMap;


public  class BaseRequestPacket {
	public Context context;
	public int action;
	public String url;
	public IToStringMap object;
	public Object extra;
	
	public BaseRequestPacket(){
		
	}
}
