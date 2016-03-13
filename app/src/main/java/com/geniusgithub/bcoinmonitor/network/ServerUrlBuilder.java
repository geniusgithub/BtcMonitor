package com.geniusgithub.bcoinmonitor.network;

import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

public class ServerUrlBuilder {

	private static final CommonLog log = LogFactory.createLog();
	
	public static final String BTC_CHINA_MARKET = "https://data.btcchina.com/data/ticker";
	public static final String BTC_OK_COIN_MARKET = "https://www.okcoin.com/api/ticker.do";
	public static final String BTC_FIRE_COIN_MARKET = "https://www.huobi.com/market/huobi.php?a=ticker";

	public static final String BTC_CHINA_DETAIL = "http://api.chbtc.com/data/depth";
	public static final String BTC_OK_COIN_DETAIL = "https://www.okcoin.com/api/depth.do";
	public static final String BTC_FIRE_COIN_DETAIL = "https://www.huobi.com/market/huobi.php?a=depth";
	
	public static final String BTC_CHINA_TRADE = "http://api.chbtc.com/data/trades";
	public static final String BTC_OK_COIN_TRADE = "https://www.okcoin.com/api/trades.do";
	public static final String BTC_FIRE_COIN_TRADE = "https://www.huobi.com/market/huobi.php?a=trades";
	
	
	public static final String BTC_CHINA_CHART = "http://www.btcc.com/chart/chbtc";
	public static final String BTC_OK_COIN_CHART = "http://www.btcc.com/chart/okcoin";
	public static final String BTC_FIRE_COIN_CHART = "http://www.btcc.com/chart/huobi";
	
	
	public static String getMarket(int type){
		switch(type){
			case IBCointType.BTC_CHINA:
				return BTC_CHINA_MARKET;
			case IBCointType.OK_COIN:
				return BTC_OK_COIN_MARKET;
			case IBCointType.FIRE_COIN:
				return BTC_FIRE_COIN_MARKET;
			default:
				break;
		}
	
			return "";
	}
	
	public static String getDepth(int type){
		switch(type){
			case IBCointType.BTC_CHINA:
				return BTC_CHINA_DETAIL;
			case IBCointType.OK_COIN:
				return BTC_OK_COIN_DETAIL;
			case IBCointType.FIRE_COIN:
				return BTC_FIRE_COIN_DETAIL;
			default:
				break;
		}

			return "";
	}
	
	
	public static String getTrade(int type){
		switch(type){
			case IBCointType.BTC_CHINA:
				return BTC_CHINA_TRADE;
			case IBCointType.OK_COIN:
				return BTC_OK_COIN_TRADE;
			case IBCointType.FIRE_COIN:
				return BTC_FIRE_COIN_TRADE;
			default:
				break;
		}

			return "";
	}

	public static String getChart(int type){
		switch(type){
			case IBCointType.BTC_CHINA:
				return BTC_CHINA_CHART;
			case IBCointType.OK_COIN:
				return BTC_OK_COIN_CHART;
			case IBCointType.FIRE_COIN:
				return BTC_FIRE_COIN_CHART;
			default:
				break;
		}

			return "";
	}

	
}
