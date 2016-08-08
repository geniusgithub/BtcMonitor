package com.geniusgithub.bcoinmonitor.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.CommonUtil;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.util.PermissionsUtil;

public class WelcomActivity extends Activity{

	private static final CommonLog log = LogFactory.createLog();
	
	private static final int SEND_MSG_ID = 0x0001;
	private static final int EXIT_MSG_ID = 0x0002;
	
	private MonitorApplication mApplication;
	private Handler mHandler;

	private ConinMarketManager mMarketManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setupViews();
		initData();
	}


	public void setupViews() {
		setContentView(R.layout.welcome_layout);
	}

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

		if (PermissionsUtil.hasNecessaryRequiredPermissions(this)){
			Intent intent = new Intent();
			intent.setClass(this, BtcMainActivity.class);
			startActivity(intent);
			finish();

		}else{
			requestNecessaryRequiredPermissions();
		}
	}


	private final int REQUEST_PHONE_PERMISSION =  0X0001;
	private void requestNecessaryRequiredPermissions(){
		requestSpecialPermissions(PermissionsUtil.PHONE, REQUEST_PHONE_PERMISSION);
	}

	@TargetApi(Build.VERSION_CODES.M)
	private void requestSpecialPermissions(String permission, int requestCode){
		String []permissions = new String[]{permission};
		requestPermissions(permissions, requestCode);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

		switch(requestCode){
			case REQUEST_PHONE_PERMISSION:
				doPhonePermission(grantResults);
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
				break;
		}

	}

	private void doPhonePermission(int[] grantResults){
		if (grantResults[0] == PackageManager.PERMISSION_DENIED){
			log.e("doPhonePermission is denied!!!" );
			Dialog dialog = PermissionsUtil.createPermissionSettingDialog(this, "读电话权限");
			dialog.show();
		}else if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
			log.i("doPhonePermission, is granted!!!" );
			goMainActivity();
		}

	}

}
