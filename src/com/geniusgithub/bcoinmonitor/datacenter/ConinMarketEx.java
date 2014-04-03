package com.geniusgithub.bcoinmonitor.datacenter;

import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinMarket;

public class ConinMarketEx extends ConinMarket{

	public static interface IStatusType{
		int ICT_NOSTATUS = -1;
		int ICT_NORMAL = 0; 
		int ICT_UP = 1;
		int ICT_DOWN = 2;
	}
	
	public int mPlatiformType = IBCointType.BTC_CHINA;
	public int mStatus = IStatusType.ICT_NOSTATUS;
	
	public ConinMarketEx (int platiformType){
		mPlatiformType = platiformType;
	}
	
	public ConinMarketEx (ConinMarket object, int platiformType){
		setData(object, platiformType);
	}
	
	public void setData (ConinMarket object, int platiformType){
		mHigh = object.mHigh;
		mLow = object.mLow;
		mBuy = object.mBuy;
		mSell = object.mSell;
		mVol = object.mVol;
		mPlatiformType = platiformType;
		if (mStatus == IStatusType.ICT_NOSTATUS){		
			mLast = object.mLast;
			mStatus = IStatusType.ICT_NORMAL;
		}else{
			try {
				boolean ret = comparePrice(object.mLast, mLast);
				if (ret){
					mStatus = IStatusType.ICT_UP;
				}else{
					mStatus = IStatusType.ICT_DOWN;
				}		
			} catch (Exception e) {
				e.printStackTrace();	
				mStatus = IStatusType.ICT_NORMAL;
			}
			
			mLast = object.mLast;
		}

	}
	
	
	
	private boolean comparePrice(String str1, String str2) throws Exception{
	
		double value1 = Double.valueOf(str1);
		double value2 =  Double.valueOf(str2);
		
		return value1 > value2 ? true : false;
	
	}
}

