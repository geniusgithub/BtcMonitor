package com.geniusgithub.bcoinmonitor.activity;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity{

	public abstract void setupViews();
	public abstract void initData();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setupViews();
		initData();
	}
	
	
}
