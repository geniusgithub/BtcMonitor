package com.geniusgithub.bcoinmonitor;


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.geniusgithub.bcoinmonitor.activity.MainActivity;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.datacenter.IPriceObser;
import com.geniusgithub.bcoinmonitor.datastore.LocalConfigSharePreference;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.util.TipHelper;




public class MonitorApplication extends Application implements IPriceObser{

	private static final CommonLog log = LogFactory.createLog();
	private static final int MSG_SHOW_PRICE_INTERVAL = 0x0001;
	
	private static MonitorApplication mInstance;
	private ConinMarketManager mConinMarketManager;
		
	private Handler mHandler;
	
	private boolean mEnterMain = false;
	
	private int mWarnType = IBCointType.BTC_CHINA;
	private int mLowPrice = 0;
	private int mHightPrice = 0;
	
	
	private int mComparePF1 = IBCointType.BTC_CHINA;
	private int mComparePF2 = IBCointType.BTC_CHINA;
	private int mPriceInterval = 0;
	
	private int mNotifyPriceType = -1;
	
	public synchronized static MonitorApplication getInstance(){
		return mInstance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		log.e("MonitorApplication  onCreate!!!");
		mInstance = this;
		mConinMarketManager = ConinMarketManager.getInstance(this);
		mConinMarketManager.registerPriceObser(this);
		
		mWarnType = LocalConfigSharePreference.getPriceWarningType(this);
		mLowPrice = LocalConfigSharePreference.getLowPrice(this);
		mHightPrice = LocalConfigSharePreference.getHightPrice(this);
		
		mComparePF1 = LocalConfigSharePreference.getComparePF1(this);
		mComparePF2 = LocalConfigSharePreference.getComparePF2(this);
		mPriceInterval = LocalConfigSharePreference.getPriceInterval(this);
		
		mNotifyPriceType = LocalConfigSharePreference.getPriceNoticeType(this);
		
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
					case MSG_SHOW_PRICE_INTERVAL:
						String title = getResources().getString(R.string.notify_title_compare);
						sendNotifycation(title, notifyPriceIntervalString);
						TipHelper.Vibrate(MonitorApplication.this, 2000);
						break;
				}
			}
			
		};
		startBackgroundService();
	}
	
	private void startBackgroundService(){
		Intent intent = new Intent();
		intent.setClass(this, BackgroundService.class);
		startService(intent);
	}
	
	public void setStatus(boolean flag){
		mEnterMain = flag;
	}
	
	public boolean getEnterFlag(){
		return mEnterMain;
	}
	
	public void exitProcess(){
		log.e("MonitorApplication  exitProcess!!!");
		Intent intent = new Intent();
		intent.setClass(this, BackgroundService.class);
		stopService(intent);
		System.exit(0);
	}

	public void setWarnPlatiformInfo(int type, int lowPrice, int hightPrice){
		mWarnType = type;
		mLowPrice = lowPrice;
		mHightPrice = hightPrice;
		log.e("setNoticeInfo type = "  + type + ", lowPrice = " + lowPrice + ", hightPrice = " + hightPrice);
	}
	
	public void setCompareInfo(int comparePF1, int comparePF2, int priceInterval){
		mComparePF1 = comparePF1;
		mComparePF2 = comparePF2;
		mPriceInterval = priceInterval;
		log.e("setCompareInfo  mComparePF1  = "  + mComparePF1 + ", mComparePF2 = " + mComparePF2 + ", mPriceInterval = " + mPriceInterval);
	}

	public void setNotifyPlatiformInfo(int type){
		mNotifyPriceType = type;
		log.e("setNotifyPlatiformInfo type = "  + mNotifyPriceType);
	}
	
	@Override
	public void onPriceUpdate(int type, String price) {
		
		if (mNotifyPriceType != -1 && mNotifyPriceType == type){
			double priDouble = Double.valueOf(price);
			int iprice = (int)priDouble;
			sendLastPrice(mNotifyPriceType, iprice);
		}
		
		if (type == mComparePF1 || type == mComparePF2){	
		
			if (mComparePF1 != mComparePF2 && mPriceInterval != 0){
				int price1 = mConinMarketManager.getLastPrice(mComparePF1);
				int price2 = mConinMarketManager.getLastPrice(mComparePF2);
				
				if (price1 != 0 && price2 != 0){
					if (Math.abs(price1 - price2) >= mPriceInterval){
						log.e("price1 = " + price1 + ", price2 = " + price2 + ", mPriceInterval = " + mPriceInterval + ", warning!!!");
						sendPriceCompareWarning(mComparePF1, mComparePF2, Math.abs(price1 - price2));
					}
				}
			}
		}
		
		

		if (type == mWarnType){
			double priDouble = Double.valueOf(price);
			int priInter = (int)priDouble;
			
			if (priInter >= mLowPrice && priInter <= mHightPrice){
				log.e("priInter = " + priInter + ", mLowPrice = " + mLowPrice + ", mHightPrice = " + mHightPrice + ", warning!!!");
				sendPriceUpdateWarning(mWarnType, priInter);
			}
		}

	}

	private String notifyPriceIntervalString = "";
	private void sendPriceCompareWarning(int comparePF1,int comparePF2, int priceInterval){
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getPfName(comparePF1) + "和" + getPfName(comparePF2) + ", 价格差在" + String.valueOf(priceInterval));
		
		notifyPriceIntervalString = stringBuffer.toString();
		mHandler.removeMessages(MSG_SHOW_PRICE_INTERVAL);
		mHandler.sendEmptyMessageDelayed(MSG_SHOW_PRICE_INTERVAL, 3000);
		
	}
	
	private void sendPriceUpdateWarning(int pfType, int newPrice){
		String title = getResources().getString(R.string.notify_title_newprice);
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getPfName(pfType) + "最新成交价:" + String.valueOf(newPrice));
		
		sendNotifycation(title, stringBuffer.toString());
		
		TipHelper.Vibrate(this, 2000);
	}
	
	private void sendLastPrice(int pfType, int newPrice){
		String title = getResources().getString(R.string.notify_title_lastprice);
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getPfName(pfType) + "最新成交价:" + String.valueOf(newPrice));
		
		sendNotifycation(title, stringBuffer.toString());
	}
	
	
	private void sendNotifycation(String title, String content){
		NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);               
		Notification n = new Notification(R.drawable.icon, content, System.currentTimeMillis());             
		n.flags = Notification.FLAG_AUTO_CANCEL;                
		Intent i = new Intent(this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);           
		//PendingIntent
		PendingIntent contentIntent = PendingIntent.getActivity(
		        this,
		        R.string.app_name, 
		        i, 
		        PendingIntent.FLAG_UPDATE_CURRENT);
		                 
		n.setLatestEventInfo(
				this,
				title, 
				content, 
		        contentIntent);
		nm.notify(R.string.app_name, n);
		
		
	}
	
	private String getPfName(int type){
		String[] names = getResources().getStringArray(R.array.platiform_name);
		return names[type];
	}
}
