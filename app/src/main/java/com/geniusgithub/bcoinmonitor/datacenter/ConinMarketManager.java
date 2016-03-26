package com.geniusgithub.bcoinmonitor.datacenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.datastore.LocalConfigSharePreference;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.model.PublicType;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinMarket;
import com.geniusgithub.bcoinmonitor.network.BaseRequestPacket;
import com.geniusgithub.bcoinmonitor.network.ClientEngine;
import com.geniusgithub.bcoinmonitor.network.IRequestContentCallback;
import com.geniusgithub.bcoinmonitor.network.ServerUrlBuilder;
import com.geniusgithub.bcoinmonitor.timer.BaseTimer;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ConinMarketManager implements IRequestContentCallback {

	private static final CommonLog log = LogFactory.createLog();
	private static ConinMarketManager mInstance;
	
	private static final int MSG_REQUEST_DATA = 0x0001;
	private Context mContext;
	
	private List<ConinMarketEx> mMaList = new ArrayList<ConinMarketEx>();
	private Handler mEListenerHandler;
	private int mMsgID = 0;
	
	private ClientEngine mClientEngine;
	private long updateTime = 0;
	
	private Handler mHandler;
	private BaseTimer mBaseTimer;
	
	private IPriceObser mIPriceObser;
	
	public static synchronized ConinMarketManager getInstance(Context context) {
		if (mInstance == null){
			mInstance  = new ConinMarketManager(context);
		
		}
		return mInstance;
	}
	
	private ConinMarketManager(Context context){
		mContext = context;
		init();
	}
	
	private void init(){
		ConinMarketEx object1 = new ConinMarketEx(IBCointType.BTC_CHINA);
		ConinMarketEx object2 = new ConinMarketEx(IBCointType.OK_COIN);	
		ConinMarketEx object3 = new ConinMarketEx(IBCointType.FIRE_COIN);
		
		mMaList.add(object1);
		mMaList.add(object2);
		mMaList.add(object3);
		
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
		int interval = LocalConfigSharePreference.getMarketInterval(mContext);
		mBaseTimer.setTimeInterval(interval * 1000);
	}
	
	public List<ConinMarketEx> getMaList(){
		return mMaList;
	}

	public ConinMarketEx getCoinMarket(int type){
		int pos = 0;
		switch(type){
			case IBCointType.BTC_CHINA:
				pos = 0;
				break;
			case IBCointType.OK_COIN:
				pos = 1;
				break;
			case IBCointType.FIRE_COIN:
				pos = 2;
				break;
		}

		ConinMarketEx object = mMaList.get(pos);
		return object;
	}
	public int getLastPrice(int type){
		int pos = 0;
		switch(type){
			case IBCointType.BTC_CHINA:
				pos = 0;
				break;
			case IBCointType.OK_COIN:
				pos = 1;
				break;
			case IBCointType.FIRE_COIN:
				pos = 2;
				break;
		}
			
		ConinMarketEx object = mMaList.get(pos);
		if (object.mStatus != ConinMarketEx.IStatusType.ICT_NOSTATUS){
			double value = Double.valueOf(object.mLast);
			return (int) value;
		}else{
			return 0;
		}
		
	}
	
	public void setMaData(int platiformType, ConinMarket object){
		ConinMarketEx marketEx = null;
		switch(platiformType){
			case IBCointType.BTC_CHINA:
				marketEx = mMaList.get(0);
				break;
			case IBCointType.OK_COIN:
				marketEx = mMaList.get(1);
				break;
			case IBCointType.FIRE_COIN:
				marketEx = mMaList.get(2);
				break;
		}
		
		if (marketEx == null){
			return ;
		}
		
		
		marketEx.setData(object, platiformType);
		updateTime = System.currentTimeMillis();
		
		if (mIPriceObser != null){
			mIPriceObser.onPriceUpdate(platiformType, marketEx.mLast);
		}
	}

	public void registerHandler(Handler handler, int MsgID){
		mEListenerHandler = handler;
		mMsgID = MsgID;
	}
	
	public void unregisterHandler(){
		mEListenerHandler = null;
	}
	

	public void registerPriceObser(IPriceObser object){
		mIPriceObser = object;
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
			case PublicType.MSG_GET_BTC_CHINA_MARKET:
				onMarketResult(content, IBCointType.BTC_CHINA);
				break;
			case PublicType.MSG_GET_OK_COIN_MARKET:
				onMarketResult(content, IBCointType.OK_COIN);
				break;
			case PublicType.MSG_GET_FIRE_COIN_MARKET:
				onMarketResult(content, IBCointType.FIRE_COIN);
			}
		}
			
		if (mEListenerHandler != null){
			mEListenerHandler.sendEmptyMessage(mMsgID);
		}
	}
	
	
	private void onMarketResult(String content, int platiformType){

		PublicType.ConinMarket market = new PublicType.ConinMarket();
		try {
			market.parseJson(content);
			String showString = market.getShowString();
			log.e("platiformType = " + platiformType + ", onMarketResult -->\n" + showString);
			
			setMaData(platiformType, market);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void requestBTCData(boolean restartTimer){

	
		BaseRequestPacket packet1 = new BaseRequestPacket();
		packet1.action = PublicType.MSG_GET_BTC_CHINA_MARKET;
		packet1.url = ServerUrlBuilder.getMarket(IBCointType.BTC_CHINA);
		mClientEngine.httpGetRequest(packet1, this);
		
		BaseRequestPacket packet2 = new BaseRequestPacket();
		packet2.action = PublicType.MSG_GET_OK_COIN_MARKET;
		packet2.url = ServerUrlBuilder.getMarket(IBCointType.OK_COIN);
		mClientEngine.httpGetRequest(packet2, this);
		
		BaseRequestPacket packet3 = new BaseRequestPacket();
		packet3.action = PublicType.MSG_GET_FIRE_COIN_MARKET;
		packet3.url = ServerUrlBuilder.getMarket(IBCointType.FIRE_COIN);
		mClientEngine.httpGetRequest(packet3, this);
		
		if(restartTimer){
			mBaseTimer.stopTimer();
			mBaseTimer.startTimer();
		}
	}
	

	public long getUpdateTime(){
		return updateTime;
	}
	
	
}
