package com.geniusgithub.bcoinmonitor.datacenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.datastore.LocalConfigSharePreference;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.model.PublicType;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinDetail;
import com.geniusgithub.bcoinmonitor.network.BaseRequestPacket;
import com.geniusgithub.bcoinmonitor.network.ClientEngine;
import com.geniusgithub.bcoinmonitor.network.IRequestContentCallback;
import com.geniusgithub.bcoinmonitor.network.ServerUrlBuilder;
import com.geniusgithub.bcoinmonitor.timer.BaseTimer;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ConinDetailManager implements IRequestContentCallback {

	private static final CommonLog log = LogFactory.createLog();
	private static final int MSG_REQUEST_DATA = 0x0001;
	private static ConinDetailManager mInstance;
	private Context mContext;
	
	private Map<Integer, ConinDetail> mConinMap = new HashMap<Integer, PublicType.ConinDetail>();
	private Handler mEListenerHandler;
	private int mMsgID = 0;
	
	private ClientEngine mClientEngine;
	private long updateTime = 0;
	
	private Handler mHandler;
	private BaseTimer mBaseTimer;
	
	
	private int mCurPlatiformType = IBCointType.BTC_CHINA;
	
	public static synchronized ConinDetailManager getInstance(Context context) {
		if (mInstance == null){
			mInstance  = new ConinDetailManager(context);
		}
		return mInstance;
	}
	
	private ConinDetailManager(Context context){
		mContext = context.getApplicationContext();
		init();
	}
	
	private void init(){		
		mClientEngine = ClientEngine.getInstance(MonitorApplication.getInstance());
		
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
					case MSG_REQUEST_DATA:
						requestBTCData(false);
						break;
				}
			}
			
		};
		
		mBaseTimer = new BaseTimer(mContext);
		mBaseTimer.setHandler(mHandler, MSG_REQUEST_DATA);
		int interval = LocalConfigSharePreference.getDetailInterval(mContext);
		mBaseTimer.setTimeInterval(interval * 1000);
	}
	
	public void setDetailType(int type){
		mCurPlatiformType = type;
	}

	public int getDetailType(){
		return mCurPlatiformType;
	}

	public ConinDetail getConinDetail(int type){
		return mConinMap.get(type);
	}
	
	public ConinDetail getCurConinDetail(){
		return mConinMap.get(mCurPlatiformType);
	}
	
	public void setDetailData(int platiformType, ConinDetail object){

		switch(platiformType){
				case IBCointType.BTC_CHINA:
				case IBCointType.OK_COIN:
				case IBCointType.FIRE_COIN:
					break;
				default:
					return ;
		}
		
		mConinMap.put(platiformType, object);
		updateTime = System.currentTimeMillis();
	}

	public void registerHandler(Handler handler, int MsgID){
		mEListenerHandler = handler;
		mMsgID = MsgID;
	}
	
	public void unregisterHandler(){
		mEListenerHandler = null;
	}
	
	public void startRequestTimer(){
		mBaseTimer.stopTimer();
		mBaseTimer.startTimer();
	}
	
	public void stopRequestTimer(){
		mBaseTimer.stopTimer();
	}
	
	public void setTimeInterval(int timeSec){
		log.e("ConinDetailManager setTime = " + timeSec);
		mBaseTimer.setTimeInterval(timeSec * 1000);
	}
	
	@Override
	public void onResult(int requestAction, Boolean isSuccess, String content,
			Object extra) {

		log.e("requestAction = " + requestAction + ", isSuccess = " + isSuccess + "\ncontent = " + content);
		
		if (isSuccess){
			switch(requestAction){
			case PublicType.MSG_GET_BTC_CHINA_DETAIL:
				onDetailResult(content, IBCointType.BTC_CHINA);
				break;
			case PublicType.MSG_GET_OK_COIN_DETAIL:
				onDetailResult(content, IBCointType.OK_COIN);
				break;
			case PublicType.MSG_GET_FIRE_COIN_DETAIL:
				onDetailResult(content, IBCointType.FIRE_COIN);
			}
		}
			
		if (mEListenerHandler != null){
			mEListenerHandler.sendEmptyMessage(mMsgID);
		}
	}
	
	
	private void onDetailResult(String content, int platiformType){
		
		PublicType.ConinDetail detail = new PublicType.ConinDetail();
		try {
			detail.parseJson(content);
			String showString = detail.getShowString();
		//	log.e("platiformType = " + platiformType + ", onDetailResult -->\n" + showString);
			
			setDetailData(platiformType, detail);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void requestBTCData(boolean restartTimer){		
		int action = 0;
		switch(mCurPlatiformType){
			case IBCointType.BTC_CHINA:
				action = PublicType.MSG_GET_BTC_CHINA_DETAIL;
				break;
			case IBCointType.OK_COIN:
				action = PublicType.MSG_GET_OK_COIN_DETAIL;
				break;
			case IBCointType.FIRE_COIN:
				action = PublicType.MSG_GET_FIRE_COIN_DETAIL;
				break;
			default:
				return ;
		}
		
		if (restartTimer){
			mBaseTimer.stopTimer();
			mBaseTimer.startTimer();
		}
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = action;
		packet.url = ServerUrlBuilder.getDepth(mCurPlatiformType);	
		mClientEngine.httpGetRequest(packet, this);

	}

	public long getUpdateTime(){
		return updateTime;
	}
}
