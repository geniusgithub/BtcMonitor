package com.geniusgithub.bcoinmonitor.activity;

import com.geniusgithub.bcoinmonitor.MonitorApplication;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity{

	public abstract void setupViews();
	public abstract void initData();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MonitorApplication.onCatchError(this);
		setupViews();
		initData();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		MonitorApplication.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		MonitorApplication.onResume(this);
	}
	
}
