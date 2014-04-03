package com.geniusgithub.bcoinmonitor.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.activity.BaseActivity;

public class HelpActivity extends BaseActivity  implements OnClickListener{
	
	private Button mBtnBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}
	
	
	@Override
	public void setupViews() {
		setContentView(R.layout.help_layout);
		
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.btn_back:
				finish();
				break;
		}
	}

}
