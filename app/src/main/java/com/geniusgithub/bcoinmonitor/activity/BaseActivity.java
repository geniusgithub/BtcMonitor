package com.geniusgithub.bcoinmonitor.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geniusgithub.bcoinmonitor.MonitorApplication;

public abstract class BaseActivity extends AppCompatActivity{



	public abstract void setupViews();
	public abstract void initData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setupViews();;
		initData();;
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
