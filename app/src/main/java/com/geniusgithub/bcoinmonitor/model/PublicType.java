package com.geniusgithub.bcoinmonitor.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.util.StringUtil;
import com.geniusgithub.bcoinmonitor.util.TimeUtil;

public class PublicType {

	private static final CommonLog log = LogFactory.createLog();
	
	
	public static final int MSG_GET_BTC_CHINA_MARKET = 0x0001;
	public static final int MSG_GET_OK_COIN_MARKET = 0x0002;
	public static final int MSG_GET_FIRE_COIN_MARKET = 0x0003;
	public static class ConinMarket implements IParseJson, IToPrintString{

		public final static String KEY_TICKER = "ticker";		
		
		public final static String KEY_HIGH = "high";
		public final static String KEY_LOW = "low";
		public final static String KEY_BUY = "buy";
		public final static String KEY_SELL = "sell";
		public final static String KEY_LAST = "last";
		public final static String KEY_VOL = "vol";
	
		public String mHigh = "";
		public String mLow = "";
		public String mBuy = "";
		public String mSell = "";
		public String mLast = "";
		public String mVol = "";

		@Override
		public boolean parseJson(String content) throws JSONException {
			
			JSONObject jsonObject = new JSONObject(content);	
			JSONObject object = jsonObject.getJSONObject(KEY_TICKER);
			
			mHigh = object.getString(KEY_HIGH);
			mLow = object.getString(KEY_LOW);
			mBuy = object.getString(KEY_BUY);
			mSell = object.getString(KEY_SELL);
			mLast = object.getString(KEY_LAST);
			mVol = object.getString(KEY_VOL);
			
			return true;
		}

		@Override
		public String getShowString() {
	
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("---------------------------");
			stringBuffer.append("mHigh = " + mHigh + "\n");
			stringBuffer.append("mLow = " + mLow + "\n");
			stringBuffer.append("mBuy = " + mBuy + "\n");
			stringBuffer.append("mSell = " + mSell + "\n");
			stringBuffer.append("mLast = " + mLast + "\n");
			stringBuffer.append("mVol = " + mVol + "\n");
			stringBuffer.append("---------------------------");
			
			return stringBuffer.toString();
		}
	}
	

	
	
	public static final int MSG_GET_BTC_CHINA_DETAIL = 0x0004;
	public static final int MSG_GET_OK_COIN_DETAIL = 0x0005;
	public static final int MSG_GET_FIRE_COIN_DETAIL = 0x0006;
	public static class ConinDetail implements IParseJson, IToPrintString{

		public final static String KEY_ASKS = "asks";
		public final static String KEY_BIDS = "bids";
		
		public List<BaseType.ConinInst> mAsksList = new ArrayList<BaseType.ConinInst>();
		public List<BaseType.ConinInst> mBidsList = new ArrayList<BaseType.ConinInst>();
		
		public double maxAmount = 0;
		
		@Override
		public boolean parseJson(String content) throws JSONException {

			JSONObject jsonObject = new JSONObject(content);	
			
			mAsksList.clear();
			mBidsList.clear();
			
			JSONArray asksJsonArray = jsonObject.getJSONArray(KEY_ASKS);
			int size1 = asksJsonArray.length();
			for(int i = 0; i < size1; i++){
				JSONArray array = asksJsonArray.getJSONArray(i);
				double price = array.getDouble(0);
				double count = array.getDouble(1);
				BaseType.ConinInst inst = new BaseType.ConinInst(price, count);
				mAsksList.add(inst);
				if (maxAmount < count){
					maxAmount = count;
				}
			}
			
			JSONArray bidsJsonArray = jsonObject.getJSONArray(KEY_BIDS);
			int size2 = bidsJsonArray.length();
			for(int i = 0; i < size2; i++){
				JSONArray array = bidsJsonArray.getJSONArray(i);
				double price = array.getDouble(0);
				double count = array.getDouble(1);
				BaseType.ConinInst inst = new BaseType.ConinInst(price, count);
				mBidsList.add(inst);
				if (maxAmount < count){
					maxAmount = count;
				}
			}	
			
			autoCalRate();
			
			return true;
			
		}
		
		
		private void autoCalRate(){
			log.e("ConinDetail maxAmount = " + maxAmount);
			
			if (maxAmount != 0){
				int size1 = mAsksList.size();
				for(int i = 0; i < size1; i++){			
					BaseType.ConinInst inst = mAsksList.get(i);
					inst.mRate = inst.mCount / maxAmount;
				}
				
				int size2 = mBidsList.size();
				for(int i = 0; i < size2; i++){			
					BaseType.ConinInst inst = mBidsList.get(i);
					inst.mRate = inst.mCount / maxAmount;
				}
			}
		}
		
		public void clear(){
			mAsksList = new ArrayList<BaseType.ConinInst>();
			mBidsList = new ArrayList<BaseType.ConinInst>();
			
			maxAmount = 0;
		}

		@Override
		public String getShowString() {

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("asks------------------>\n");
			int size1 = mAsksList.size();
			for(int i = 0;i < size1; i++){
				stringBuffer.append(mAsksList.get(i).getShowString() + "\n");
			}
			stringBuffer.append("bids------------------>\n");
			int size2 = mBidsList.size();
			for(int i = 0;i < size2; i++){
				stringBuffer.append(mBidsList.get(i).getShowString() + "\n");
			}
			
			return stringBuffer.toString();
			
		}
		
	}
	

	
	public static final int MSG_GET_BTC_CHINA_TRADE = 0x0007;
	public static final int MSG_GET_OK_COIN_TRADE = 0x0008;
	public static final int MSG_GET_FIRE_COIN_TRADE = 0x0009;
	public static class ConinTradeGroup implements IParseJson, IToPrintString{

		public List<ConinTrade> mConinTradeList = new ArrayList<PublicType.ConinTrade>();
		public double maxAmount = 0;
		public String lastBusiness = "";
		
		@Override
		public boolean parseJson(String content) throws JSONException {
		
			JSONArray jsonArray = new JSONArray(content);
			int size = jsonArray.length();
			for(int i = 0; i < size; i++){
				JSONObject object = jsonArray.getJSONObject(i);
				ConinTrade trade = new ConinTrade();
				trade.parseJson(object.toString());
				mConinTradeList.add(trade);
				
				double value = Double.valueOf(trade.mAmount);
				if (maxAmount < value){
					maxAmount = value;
				}
				
				if (i == 0){
					lastBusiness = trade.mPrice;
				}
				
			}
			
			autoCalRateAndUpDown();
			
			return true;
		}
		
		private void autoCalRateAndUpDown(){
			log.e("ConinTradeGroup maxAmount = " + maxAmount);
			
			if (maxAmount != 0){
				int size = mConinTradeList.size();
				for(int i = 0; i < size; i++){			
					ConinTrade object = mConinTradeList.get(i);
					object.mRate =  Double.valueOf(object.mAmount) / maxAmount;
					if (i < size - 1){
						ConinTrade nextTrade = mConinTradeList.get(i+1);
						object.mIsUp = StringUtil.compareDoubleStr(object.mPrice, nextTrade.mPrice);
					}
				}
			}
		}

		


		@Override
		public String getShowString() {

			StringBuffer stringBuffer = new StringBuffer();

			int size = mConinTradeList.size();
			for(int i = 0; i < size; i++){
				ConinTrade object = mConinTradeList.get(i);
				stringBuffer.append(object.getShowString());
			}
			
			return stringBuffer.toString();
		}
		
	}
	
	public static class ConinTrade implements IParseJson, IToPrintString{
		
		public final static String KEY_DATE = "date";
		public final static String KEY_PRICE = "price";
		public final static String KEY_AMOUNT = "amount";
		public final static String KEY_TID = "tid";
		public final static String KEY_TYPE = "type";
		
		public String mDate = "";
		public String mPrice = "";
		public String mAmount = "";
		public String mTid = "";
		public String mType = "";
		
		public String mDataEx = "";
		public double mRate = 0.01;
		public boolean mIsUp = true;
		
		@Override
		public boolean parseJson(String content) throws JSONException {

			JSONObject jsonObject = new JSONObject(content);	
			
			mDate = jsonObject.getString(KEY_DATE);
			mPrice = jsonObject.getString(KEY_PRICE);
			mAmount = jsonObject.getString(KEY_AMOUNT);
			mTid = jsonObject.getString(KEY_TID);
			mType = jsonObject.getString(KEY_TYPE);
			
			mDataEx = TimeUtil.getTimeShort(mDate);
			
			return true;
		}

		@Override
		public String getShowString() {

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("---------------------------\n");
			stringBuffer.append("mDate = " + mDate + "\n");
			stringBuffer.append("mPrice = " + mPrice + "\n");
			stringBuffer.append("mAmount" + mAmount + "\n");
			stringBuffer.append("mTid = " + mTid + "\n");
			stringBuffer.append("mType = " + mType + "\n");
			stringBuffer.append("---------------------------\n");
			
			return stringBuffer.toString();
			
		}

	}

	
	
}
