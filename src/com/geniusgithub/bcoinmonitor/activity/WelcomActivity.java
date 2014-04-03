package com.geniusgithub.bcoinmonitor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.CommonUtil;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

public class WelcomActivity extends BaseActivity{

	private static final CommonLog log = LogFactory.createLog();
	
	private static final int SEND_MSG_ID = 0x0001;
	private static final int EXIT_MSG_ID = 0x0002;
	
	private MonitorApplication mApplication;
	private Handler mHandler;

	private ConinMarketManager mMarketManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}
	
	@Override
	public void setupViews() {
		setContentView(R.layout.welcome_layout);
	}



	@Override
	public void initData() {
		mApplication = MonitorApplication.getInstance();	
		if (mApplication.getEnterFlag()){
			goMainActivity();
			return ;
		}
		
		
		
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SEND_MSG_ID:
					goMainActivity();
					break;
				case EXIT_MSG_ID:
					finish();
					break;
				default:
					break;
				}
			}
			
		};
		
		boolean ret = CommonUtil.isNetworkConnect(this);
		if (!ret){
			CommonUtil.showToast(R.string.toast_net_error, this);
			mHandler.sendEmptyMessageDelayed(EXIT_MSG_ID, 2000);
			return ;
		}
		
		mHandler.sendEmptyMessageDelayed(SEND_MSG_ID, 2000);
		
		mMarketManager = ConinMarketManager.getInstance(MonitorApplication.getInstance());
		mMarketManager.requestBTCData(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	 


	@Override
	public void onBackPressed() {
		exit();
	}
	
	
	private void exit(){
		mHandler.removeMessages(SEND_MSG_ID);
		finish();
	}


	private void goMainActivity(){
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
