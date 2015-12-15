package com.geniusgithub.bcoinmonitor.datastore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.geniusgithub.bcoinmonitor.model.IBCointType;

public class LocalConfigSharePreference {

	public static final String preference_name = "LocalConfigSharePreference";
	public static final String KEY_MARKET = "KEY_MARKET";
	public static final String KEY_DETAIL = "KEY_DETAIL";
	public static final String KEY_WARNPLATIFORM = "KEY_WARNPLATIFORM";
	public static final String KEY_LOWRRICE = "KEY_LOWRRICE";
	public static final String KEY_HIGHTPRICE = "KEY_HIGHTPRICE";
	public static final String KEY_COMPAREPF1 = "KEY_COMPAREPF1";
	public static final String KEY_COMPAREPF2 = "KEY_COMPAREPF2";
	public static final String KEY_COMPAREPRICE = "KEY_COMPAREPRICE";
	public static final String KEY_NOTIFYPLATIFORM = "KEY_NOTIFYPLATIFORM";
	
	public static boolean commintMarketInterval(Context context, int timeSec){
	
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_MARKET, timeSec);
		editor.commit();
		return true;
	}
	
	public static int getMarketInterval(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_MARKET, 60);
		return value;
	}
	
	public static boolean commintDetailInterval(Context context, int timeSec){
		
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_DETAIL, timeSec);
		editor.commit();
		return true;
	}
	
	public static int getDetailInterval(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_DETAIL, 60);
		return value;
	}
	
	public static boolean commitPriceNoticeType(Context context, int types){
		
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_NOTIFYPLATIFORM, types);
		editor.commit();
		return true;
	}
	
	public static int getPriceNoticeType(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_NOTIFYPLATIFORM, -1);
		return value;
	}
	
	
	public static boolean commitPriceWarningType(Context context, int types){
		
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_WARNPLATIFORM, types);
		editor.commit();
		return true;
	}
	
	public static int getPriceWarningType(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_WARNPLATIFORM, IBCointType.BTC_CHINA);
		return value;
	}


	public static boolean commitLowPrice(Context context, int price){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_LOWRRICE, price);
		return editor.commit();
	}

	public static int getLowPrice(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_LOWRRICE, 0);
		return value;
	}

	public static boolean commitHightPrice(Context context, int price){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_HIGHTPRICE, price);
		return editor.commit();
	}

	public static int getHightPrice(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_HIGHTPRICE, 0);
		return value;
	}
	
	public static boolean commitComparePF1(Context context, int type){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_COMPAREPF1, type);
		return editor.commit();
	}
	
	public static int getComparePF1(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_COMPAREPF1, 0);
		return value;
	}
	
	
	public static boolean commitComparePF2(Context context, int type){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_COMPAREPF2, type);
		return editor.commit();
	}
	
	public static int getComparePF2(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_COMPAREPF2, 0);
		return value;
	}
	
	public static boolean commitPriceInterval(Context context, int priceInterval){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt(KEY_COMPAREPRICE, priceInterval);
		return editor.commit();
	}

	public static int getPriceInterval(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		int value = sharedPreferences.getInt(KEY_COMPAREPRICE, 0);
		return value;
	}
	
	public static void clearData(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(preference_name, 0);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}
	
}
