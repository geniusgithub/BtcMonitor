package com.geniusgithub.bcoinmonitor.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.activity.BaseActivity;

public class CopRightActivity extends BaseActivity  implements OnClickListener{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}
	
	
	@Override
	public void setupViews() {
		setContentView(R.layout.copyright_layout);
		initToolBar();
	
	}

	private void initToolBar(){
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("CopyRight");
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		setSupportActionBar(toolbar);
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void onClick(View view) {

	}

}
