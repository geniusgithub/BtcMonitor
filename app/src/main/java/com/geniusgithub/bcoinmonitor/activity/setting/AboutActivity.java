package com.geniusgithub.bcoinmonitor.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.activity.BaseActivity;
import com.geniusgithub.bcoinmonitor.util.CommonUtil;
public class AboutActivity extends BaseActivity implements OnClickListener{

	private Button mBtnBack;
	
	private TextView mTVVersion; 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}
	
	
	@Override
	public void setupViews() {
		setContentView(R.layout.about_layout);
		
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);
	
		mTVVersion = (TextView) findViewById(R.id.tv_version);
	}

	@Override
	public void initData() {
		String value = getResources().getString(R.string.tvt_ver_pre) + CommonUtil.getSoftVersion(this);
		mTVVersion.setText(value);
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
