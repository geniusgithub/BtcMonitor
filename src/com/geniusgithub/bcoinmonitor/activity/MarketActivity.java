package com.geniusgithub.bcoinmonitor.activity;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.adapter.MarketListViewAdapter;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketEx;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.CommonUtil;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.util.TimeUtil;
import com.geniusgithub.bcoinmonitor.widget.RefreshListView;

public class MarketActivity extends BaseActivity implements RefreshListView.IOnRefreshListener{

	private static final CommonLog log = LogFactory.createLog();
	private static final int REFRESH_DATA = 0x0001;
	
	private RefreshListView mListView;
	private MarketListViewAdapter adapter;
	
	private TextView mTvTime;

	private ConinMarketManager mMarketManager;
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		boolean ret = CommonUtil.isNetworkConnect(this);
		if (!ret){
			CommonUtil.showToast(R.string.toast_net_error, this);
			return ;
		}
	}



	@Override
	public void setupViews() {
		setContentView(R.layout.market_layout);
		
		mListView = (RefreshListView) findViewById(R.id.market_list_view); 	
		
		mTvTime = (TextView) findViewById(R.id.refresh_text);
	}

	@Override
	public void initData() {
		
		mMarketManager = ConinMarketManager.getInstance(MonitorApplication.getInstance());
		adapter = new MarketListViewAdapter(this,  mMarketManager.getMaList());

		mListView.setAdapter(adapter);
		mListView.setOnRefreshListener(this);
		
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
					case REFRESH_DATA:
						updateData();
						break;
				}
			}
			
		};
		
		
		mMarketManager.registerHandler(mHandler, REFRESH_DATA);
		
		long time = mMarketManager.getUpdateTime();
		if (time != 0){
			String value = TimeUtil.getTimeShort(time);
			mTvTime.setText(value);
		}

	}

	

	@Override
	protected void onDestroy() {

		mMarketManager.unregisterHandler();
		
		super.onDestroy();
	}



	@Override
	public void OnRefresh() {
		mMarketManager.requestBTCData(true);
	}


	private void updateData(){
		List<ConinMarketEx> list = mMarketManager.getMaList();
		adapter.refreshData(list);
		mListView.onRefreshComplete();
		long time = mMarketManager.getUpdateTime();
		String value = TimeUtil.getTimeShort(time);
		mTvTime.setText(value);
		
	}
}
