package com.geniusgithub.bcoinmonitor.activity;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.datacenter.ConinDetailManager;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

public class MainActivity extends TabActivity{
	
	private static final CommonLog log = LogFactory.createLog();
	
	private TabHost		m_tabHost;		
	private RadioGroup  m_radioGroup;
	
	private ConinMarketManager mMarketManager;
	private ConinDetailManager mDetailManager;
	
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
	

	public void setupViews() {
		
		setContentView(R.layout.main_layout);
		
		m_tabHost = getTabHost();
		
		int count = Constant.mTabClassArray.length;		
		for(int i = 0; i < count; i++)
		{	
			TabSpec tabSpec = m_tabHost.newTabSpec(Constant.mTextviewArray[i]).
													setIndicator(Constant.mTextviewArray[i]).
													setContent(getTabItemIntent(i));
			m_tabHost.addTab(tabSpec);
		}
		
		m_radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
		m_radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId){
				case R.id.main_tab_market:
					m_tabHost.setCurrentTabByTag(Constant.mTextviewArray[0]);
					break;
				case R.id.main_tab_deep:
					m_tabHost.setCurrentTabByTag(Constant.mTextviewArray[1]);
					break;
				case R.id.main_tab_kline:
					m_tabHost.setCurrentTabByTag(Constant.mTextviewArray[2]);
					break;
				case R.id.main_tab_settings:
					m_tabHost.setCurrentTabByTag(Constant.mTextviewArray[3]);
					break;
				}
			}
		});
		
		 ((RadioButton) m_radioGroup.getChildAt(0)).toggle();
		
	}


	public void initData() {

		MonitorApplication.getInstance().setStatus(true);
		
		mMarketManager = ConinMarketManager.getInstance(MonitorApplication.getInstance());
		mMarketManager.startRequestTimer();
		
		mDetailManager = ConinDetailManager.getInstance(MonitorApplication.getInstance());
		mDetailManager.startRequestTimer();
	}

	private Intent getTabItemIntent(int index)
	{
		Intent intent = new Intent(this, Constant.mTabClassArray[index]);
		
		return intent;
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case Menu.FIRST + 1:
				exitProcess();
				break;
			case Menu.FIRST + 2:
				runInBackground();
				break;
		}
		return false;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch(item.getItemId()){
			case Menu.FIRST + 1:
				exitProcess();
				break;
			case Menu.FIRST + 2:
				runInBackground();
				break;
		}
		return false;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, getResources().getString(R.string.menu_exitprocess));
		menu.add(Menu.NONE, Menu.FIRST + 2, 2, getResources().getString(R.string.menu_runinbackground));
		 
		return true;
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
	        if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	        	log.e("dispatchKeyEvent back");
	        	openOptionsMenu();
	            return false;
	        }

	        return super.dispatchKeyEvent(event);
	}

	

	private void exitProcess(){
		finish();
		MonitorApplication.getInstance().exitProcess();
	}
	
	private void runInBackground(){
		finish();
	}
	
}
