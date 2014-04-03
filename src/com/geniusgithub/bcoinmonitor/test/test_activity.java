package com.geniusgithub.bcoinmonitor.test;



import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.adapter.PfNameSpinnerAdapter;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.model.PublicType;
import com.geniusgithub.bcoinmonitor.network.BaseRequestPacket;
import com.geniusgithub.bcoinmonitor.network.ClientEngine;
import com.geniusgithub.bcoinmonitor.network.IRequestContentCallback;
import com.geniusgithub.bcoinmonitor.network.ServerUrlBuilder;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

public class test_activity extends Activity implements OnClickListener, IRequestContentCallback{

	private static final CommonLog log = LogFactory.createLog();
	
	private Button mButtonChinaMarket;
	private Button mButtonOCoinMarket;
	private Button mButtonFCoinMarket;
	
	private Button mButtonChinaDetail;
	private Button mButtonOCoinDetail;
	private Button mButtonFCoinDetail;
	
	private Button mButtonChinaTrade;
	private Button mButtonOCoinTrade;
	private Button mButtonFCoinTrade;
	
	private ClientEngine mClientEngine;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		setupViews();
		initData();
	}
	
	

	private void setupViews(){
		mButtonChinaMarket = (Button) findViewById(R.id.btnChinaMarket);
		mButtonOCoinMarket = (Button) findViewById(R.id.btnOCoinMarket);
		mButtonFCoinMarket = (Button) findViewById(R.id.btnFCoinMarket);
		
		mButtonChinaMarket.setOnClickListener(this);
		mButtonOCoinMarket.setOnClickListener(this);
		mButtonFCoinMarket.setOnClickListener(this);
		
		mButtonChinaDetail = (Button) findViewById(R.id.btnChinaDetail);
		mButtonOCoinDetail = (Button) findViewById(R.id.btnOCoinDetail);
		mButtonFCoinDetail = (Button) findViewById(R.id.btnFCoinDetail);
		
		mButtonChinaDetail.setOnClickListener(this);
		mButtonOCoinDetail.setOnClickListener(this);
		mButtonFCoinDetail.setOnClickListener(this);
		
		mButtonChinaTrade = (Button) findViewById(R.id.btnChinaTrade);
		mButtonOCoinTrade = (Button) findViewById(R.id.btnOCoinTrade);
		mButtonFCoinTrade = (Button) findViewById(R.id.btnFCoinTrade);
		
		mButtonChinaTrade.setOnClickListener(this);
		mButtonOCoinTrade.setOnClickListener(this);
		mButtonFCoinTrade.setOnClickListener(this);
	}
	
	private PfNameSpinnerAdapter adapter = null;
	private Spinner mSpinnerWarnPlatiform;
	private ArrayAdapter<String> mWarnPfAdapter;
	private void initData(){
		mClientEngine = ClientEngine.getInstance(MonitorApplication.getInstance());
		String []pfName = getResources().getStringArray(R.array.platiform_name);
		mSpinnerWarnPlatiform = (Spinner) findViewById(R.id.sp_warnplatiform);
		//mWarnPfAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, pfName);
		//mSpinnerWarnPlatiform.setAdapter(mWarnPfAdapter);  
		adapter = new PfNameSpinnerAdapter(this, pfName);
		mSpinnerWarnPlatiform.setAdapter(adapter);
	}



	@Override
	public void onClick(View view) {

		switch(view.getId()){
			case R.id.btnChinaMarket:
				btnChinaMarket();
				break;
			case R.id.btnOCoinMarket:
				btnOCoinMarket();
				break;
			case R.id.btnFCoinMarket:
				btnFCoinMarket();
				break;
			case R.id.btnChinaDetail:
				btnChinaDetail();
				break;
			case R.id.btnOCoinDetail:
				btnOCoinDetail();
				break;
			case R.id.btnFCoinDetail:
				btnFCoinDetail();
				break;
			case R.id.btnChinaTrade:
				btnChinaTrade();
				break;
			case R.id.btnOCoinTrade:
				btnOCoinTrade();
				break;
			case R.id.btnFCoinTrade:
				btnFCoinTrade();
				break;
		}
	}
	
	
	private void btnChinaMarket(){
		log.e("btnChinaMarket...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_BTC_CHINA_MARKET;
		packet.url = ServerUrlBuilder.getMarket(IBCointType.BTC_CHINA);
		
		mClientEngine.httpGetRequest(packet, this);
		
	}	
	private void btnOCoinMarket(){
		log.e("btnOCoinMarket...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_OK_COIN_MARKET;
		packet.url = ServerUrlBuilder.getMarket(IBCointType.OK_COIN);
		
	
		mClientEngine.httpGetRequest(packet, this);
	}	
	private void btnFCoinMarket(){
		log.e("btnFCoinMarket...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_FIRE_COIN_MARKET;
		packet.url = ServerUrlBuilder.getMarket(IBCointType.FIRE_COIN);
		
		mClientEngine.httpGetRequest(packet, this);
	}

	
	private void btnChinaDetail(){
		log.e("btnChinaDetail...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_BTC_CHINA_DETAIL;
		packet.url = ServerUrlBuilder.getDepth(IBCointType.BTC_CHINA);
		
		mClientEngine.httpGetRequest(packet, this);
		
	}	
	private void btnOCoinDetail(){
		log.e("btnOCoinDetail...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_OK_COIN_DETAIL;
		packet.url = ServerUrlBuilder.getDepth(IBCointType.OK_COIN);
		
		mClientEngine.httpGetRequest(packet, this);
	}	
	private void btnFCoinDetail(){
		log.e("btnFCoinDetail...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_FIRE_COIN_DETAIL;
		packet.url = ServerUrlBuilder.getDepth(IBCointType.FIRE_COIN);
		
		mClientEngine.httpGetRequest(packet, this);
	}
	
	
	private void btnChinaTrade(){
		log.e("btnChinaTrade...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_BTC_CHINA_TRADE;
		packet.url = ServerUrlBuilder.getTrade(IBCointType.BTC_CHINA);
		
		mClientEngine.httpGetRequest(packet, this);
		
	}	
	private void btnOCoinTrade(){
		log.e("btnOCoinTrade...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_OK_COIN_TRADE;
		packet.url = ServerUrlBuilder.getTrade(IBCointType.OK_COIN);
		
		mClientEngine.httpGetRequest(packet, this);
	}	
	private void btnFCoinTrade(){
		log.e("btnFCoinTrade...");
		
		BaseRequestPacket packet = new BaseRequestPacket();
		packet.action = PublicType.MSG_GET_FIRE_COIN_TRADE;
		packet.url = ServerUrlBuilder.getTrade(IBCointType.FIRE_COIN);
		
		mClientEngine.httpGetRequest(packet, this);
	}
	
	@Override
	public void onResult(int requestAction, Boolean isSuccess, String content,
			Object extra) {
		log.e("requestAction = " + requestAction + "\ncontent = " + content);
		
		
		switch(requestAction){
			case PublicType.MSG_GET_BTC_CHINA_MARKET:
				onMarketResult(content);
				break;
			case PublicType.MSG_GET_OK_COIN_MARKET:
				onMarketResult(content);
				break;
			case PublicType.MSG_GET_FIRE_COIN_MARKET:
				onMarketResult(content);
				break;
			case PublicType.MSG_GET_BTC_CHINA_DETAIL:
				onDetailResult(content);
				break;
			case PublicType.MSG_GET_OK_COIN_DETAIL:
				onDetailResult(content);
				break;
			case PublicType.MSG_GET_FIRE_COIN_DETAIL:
				onDetailResult(content);
				break;
			case PublicType.MSG_GET_BTC_CHINA_TRADE:
				onTradeResult(content);
				break;
			case PublicType.MSG_GET_OK_COIN_TRADE:
				onTradeResult(content);
				break;
			case PublicType.MSG_GET_FIRE_COIN_TRADE:
				onTradeResult(content);
				break;
			default:
				break;
		}
		
		
	}
	
	private void onMarketResult(String content){
		
		PublicType.ConinMarket market = new PublicType.ConinMarket();
		try {
			market.parseJson(content);
			String showString = market.getShowString();
			log.e("onMarketResult -->\n" + showString);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	private void onDetailResult(String content){
		
		PublicType.ConinDetail detail = new PublicType.ConinDetail();
		try {
			detail.parseJson(content);
			String showString = detail.getShowString();
			log.e("onDetailResult -->\n" + showString);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	private void onTradeResult(String content){
		
		PublicType.ConinTradeGroup group = new PublicType.ConinTradeGroup();
		try {
			group.parseJson(content);
			String showString = group.getShowString();
			log.e("onTradeResult -->\n" + showString);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	

}
