package com.geniusgithub.bcoinmonitor.datacenter;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.datastore.LocalConfigSharePreference;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.model.PublicType;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinTradeGroup;
import com.geniusgithub.bcoinmonitor.network.BaseRequestPacket;
import com.geniusgithub.bcoinmonitor.network.ClientEngine;
import com.geniusgithub.bcoinmonitor.network.IRequestContentCallback;
import com.geniusgithub.bcoinmonitor.network.ServerUrlBuilder;
import com.geniusgithub.bcoinmonitor.timer.BaseTimer;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

public class ConinTradeManager implements IRequestContentCallback {

	private static final CommonLog log = LogFactory.createLog();
	private static final int MSG_REQUEST_DATA = 0x0001;
	private static ConinTradeManager mInstance;
	
	private Map<Integer, ConinTradeGroup> mConinMap = new HashMap<Integer, ConinTradeGroup>();
	private Handler mEListenerHandler;
	private int mMsgID = 0;
	
	private ClientEngine mClientEngine;
	private long updateTime = 0;
	private String lastBusiness = "";
	
	private Handler mHandler;
	private BaseTimer mBaseTimer;
	
	private Context mContext;
	
	private int mCurTradeType = IBCointType.BTC_CHINA;
	
	public static synchronized ConinTradeManager getInstance(Context context) {
		if (mInstance == null){
			mInstance  = new ConinTradeManager(context);
		}
		return mInstance;
	}
	
	private ConinTradeManager(Context context){
		mContext = context;
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
	
	public void setTradeType(int type){
		mCurTradeType = type;
	}
	
	public ConinTradeGroup getTradeGroup(int type){
		return mConinMap.get(type);
	}
	
	public ConinTradeGroup getCurTradeGroup(){
		return mConinMap.get(mCurTradeType);
	}
	
	public String getLastBusiness(){

		ConinTradeGroup group = mConinMap.get(mCurTradeType);
		if (group != null){
			return group.lastBusiness;
		}else{
			return "";
		}
	}
	
	public void setTradeGroupData(int platiformType, ConinTradeGroup object){

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
		mBaseTimer.setTimeInterval(timeSec * 1000);
	}
	
	@Override
	public void onResult(int requestAction, Boolean isSuccess, String content,
			Object extra) {

		log.e("requestAction = " + requestAction + ", isSuccess = " + isSuccess + "\ncontent = " + content);
		
		if (isSuccess){
			switch(requestAction){
			case PublicType.MSG_GET_BTC_CHINA_TRADE:
				onTradeResult(content, IBCointType.BTC_CHINA);
				break;
			case PublicType.MSG_GET_OK_COIN_TRADE:
				onTradeResult(content, IBCointType.OK_COIN);
				break;
			case PublicType.MSG_GET_FIRE_COIN_TRADE:
				onTradeResult(content, IBCointType.FIRE_COIN);
			}
		}
			
		if (mEListenerHandler != null){
			mEListenerHandler.sendEmptyMessage(mMsgID);
		}
	}
	
	
	private void onTradeResult(String content, int platiformType){
		
		PublicType.ConinTradeGroup tradeGroup = new PublicType.ConinTradeGroup();
		try {
			tradeGroup.parseJson(content);
			String showString = tradeGroup.getShowString();
			
			setTradeGroupData(platiformType, tradeGroup);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void requestBTCData(boolean restartTimer){
		
			int action = 0;
			switch(mCurTradeType){
				case IBCointType.BTC_CHINA:
					action = PublicType.MSG_GET_BTC_CHINA_TRADE;
					break;
				case IBCointType.OK_COIN:
					action = PublicType.MSG_GET_OK_COIN_TRADE;
					break;
				case IBCointType.FIRE_COIN:
					action = PublicType.MSG_GET_FIRE_COIN_TRADE;
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
			packet.url = ServerUrlBuilder.getTrade(mCurTradeType);	
			mClientEngine.httpGetRequest(packet, this);

	}

	public long getUpdateTime(){
		return updateTime;
	}
}
