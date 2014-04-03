package com.geniusgithub.bcoinmonitor.network;


import android.content.Context;

import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class ClientEngine {


	private static final CommonLog log = LogFactory.createLog();
	private static ClientEngine mInstance;
	
	private Context mContext;
	private AsyncHttpClient client = new AsyncHttpClient();

	
	public static synchronized ClientEngine getInstance(Context context) {
		if (mInstance == null){
			mInstance  = new ClientEngine(context);
		}
		return mInstance;
	}

	private ClientEngine(Context context)
	{	
		mContext = context;
		client.setTimeout(20 * 1000);
	}
	
	public void cancelTask(Context context){
		client.cancelRequests(context, true);
	}
		

	public boolean httpGetRequest(BaseRequestPacket packet,  IRequestContentCallback callback)
	{
		String url = packet.url;
		if (url.equals("")){
			log.e("can't get serverURL by action : " + packet.action);
			return false;
		}
		
		log.e("httpGetRequest url = " + url);
		RequestParams param = null;
		if (packet.object != null){
			param = new RequestParams(packet.object.toStringMap());
		}

		
		try {
			HttpResponseHandler handler = new HttpResponseHandler(packet.action, callback, packet.extra);	
			client.get(packet.context, url,  param, handler);
		} catch (Exception e) {
			e.printStackTrace();
			if (callback != null){
				callback.onResult(packet.action, false, "", null);
			}
		}
	
		
		return true;
	}
	
	public boolean httpPostRequest(BaseRequestPacket packet,  IRequestContentCallback callback)
	{
		String url = packet.url;
		if (url.equals("")){
			log.e("can't get serverURL by action : " + packet.action);
			return false;
		}
		

		log.e("httpPostRequestEx url = " + url);
		RequestParams param = null;
		if (packet.object != null){
			param = new RequestParams(packet.object.toStringMap());
		}

		try {
			HttpResponseHandler handler = new HttpResponseHandler(packet.action, callback, packet.extra);	
			client.post(packet.context, url,  param, handler);
		} catch (Exception e) {
			e.printStackTrace();
			if (callback != null){
				callback.onResult(packet.action, false, "", null);
			}
		}
		
		
		
		return true;
	}

}
