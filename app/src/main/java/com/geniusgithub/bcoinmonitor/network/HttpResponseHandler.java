package com.geniusgithub.bcoinmonitor.network;

import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class HttpResponseHandler extends AsyncHttpResponseHandler{

	private static final CommonLog log = LogFactory.createLog();
	
	private int mAction = 0;
	private IRequestContentCallback mContentCallback;
	
	private Object mExtra;
	
	public HttpResponseHandler(int action, IRequestContentCallback callback2, Object extra){
		mAction = action;
		mContentCallback = callback2;
		mExtra = extra;
	}
	
	@Override
	public void onStart() {
		super.onStart();
	//	log.e("mAction = " + mAction + ", onStart!");
	}

	@Override
	public void onFinish() {
		super.onFinish();
	//	log.e("mAction = " + mAction + ", onFinish!");
	}

	@Override
	public void onSuccess(int statusCode, String content) {
		
	//	log.d("mAction = " + mAction + ", onSuccess! statusCode = " + statusCode + "\ncontent = " + content);

		if (mContentCallback == null){
			return ;
		}
		
		mContentCallback.onResult(mAction, true, content, mExtra);

	}

	@Override
	public void onFailure(Throwable error, String content) {
		
		log.d("mAction = " + mAction + ", onFailure! error = " + error.getMessage() + "\ncontent = " + content);
		if (mContentCallback == null){
			return ;
		}
		
		mContentCallback.onResult(mAction, false, content, mExtra);
	
	}

}
