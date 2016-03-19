package com.geniusgithub.bcoinmonitor.activity.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.activity.BaseActivity;
import com.geniusgithub.bcoinmonitor.adapter.PfNameSpinnerAdapter;
import com.geniusgithub.bcoinmonitor.datastore.LocalConfigSharePreference;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.util.CommonUtil;

public class WarningActivity extends BaseActivity  implements OnClickListener{

	
	private EditText mLowEditText;
	private EditText mHightTeEditText;
	private Spinner mSpinnerWarnPlatiform;
	private PfNameSpinnerAdapter mWarnPfAdapter;
	
	private Spinner mSpinnerPf1;
	private Spinner mSpinnerPf2;
	private EditText mPriceInterval;
	
	private Button mBtnSave;
	
	
	private int mNoticeType = IBCointType.BTC_CHINA;
	private int mComparePf1 = IBCointType.BTC_CHINA;
	private int mComparePf2 = IBCointType.BTC_CHINA;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}
	
	@Override
	public void setupViews() {
		setContentView(R.layout.warning_layout);
		initToolBar();


		
		mLowEditText = (EditText) findViewById(R.id.et_lowprice);
		mHightTeEditText = (EditText) findViewById(R.id.et_highprice);
		mSpinnerWarnPlatiform = (Spinner) findViewById(R.id.sp_warnplatiform);
		mSpinnerPf1 = (Spinner) findViewById(R.id.sp_pf1);
		mSpinnerPf2 = (Spinner) findViewById(R.id.sp_pf2);
		mPriceInterval = (EditText) findViewById(R.id.et_priceinterval);
		mBtnSave = (Button) findViewById(R.id.btn_save);
		mBtnSave.setOnClickListener(this);
		
		String []pfName = getResources().getStringArray(R.array.platiform_name);
		mWarnPfAdapter = new PfNameSpinnerAdapter(this, pfName);
		mSpinnerWarnPlatiform.setAdapter(mWarnPfAdapter);  		
		mSpinnerPf1.setAdapter(mWarnPfAdapter);
		mSpinnerPf2.setAdapter(mWarnPfAdapter);
		
		
	}

	private void initToolBar(){
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Warning");
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		setSupportActionBar(toolbar);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		int lowPrice = LocalConfigSharePreference.getLowPrice(this);
		int hightPrice = LocalConfigSharePreference.getHightPrice(this);
		
		mNoticeType = LocalConfigSharePreference.getPriceWarningType(this);
		mComparePf1 = LocalConfigSharePreference.getComparePF1(this);
		mComparePf2 = LocalConfigSharePreference.getComparePF2(this);
		
		int priceInterval = LocalConfigSharePreference.getPriceInterval(this);
		

		mLowEditText.setText(String.valueOf(lowPrice));
		mHightTeEditText.setText(String.valueOf(hightPrice));
		mPriceInterval.setText(String.valueOf(priceInterval));

		
		mSpinnerWarnPlatiform.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				switch(pos){
					case 0:
						mNoticeType = IBCointType.BTC_CHINA;
						break;
					case 1:
						mNoticeType = IBCointType.OK_COIN;
						break;
					case 2:
						mNoticeType = IBCointType.FIRE_COIN;
						break;
				
			
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mSpinnerWarnPlatiform.setSelection(getSelection(mNoticeType));
		

		mSpinnerPf1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				switch(pos){
				case 0:
					mComparePf1 = IBCointType.BTC_CHINA;
					break;
				case 1:
					mComparePf1 = IBCointType.OK_COIN;
					break;
				case 2:
					mComparePf1 = IBCointType.FIRE_COIN;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mSpinnerPf1.setSelection(getSelection(mComparePf1));
		
		mSpinnerPf2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				switch(pos){
					case 0:
						mComparePf2 = IBCointType.BTC_CHINA;
						break;
					case 1:
						mComparePf2 = IBCointType.OK_COIN;
						break;
					case 2:
						mComparePf2 = IBCointType.FIRE_COIN;
						break;
					}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mSpinnerPf2.setSelection(getSelection(mComparePf2));
		
		
	
	}
	
	
	private int getSelection(int type){
		int pos = 0;
		switch(type){
			case IBCointType.BTC_CHINA:
				 pos = 0;
				break;
			case IBCointType.OK_COIN:
				 pos = 1;
				break;
			case IBCointType.FIRE_COIN:
				 pos = 2;
				break;
		}
		return pos;
	}
	
	private String getPfName(int type){
		String[] names = getResources().getStringArray(R.array.platiform_name);
		return names[type];
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()){
				break;
			case R.id.btn_save:
				save();
				break;
		}
	}
	
	private void save(){
		String lowString = mLowEditText.getText().toString();
		String hightString = mHightTeEditText.getText().toString();
		
		if (lowString.length() == 0){
			lowString = "0";
		}
		if (hightString.length() == 0){
			hightString = "0";
		}
		int lowPrice = Integer.valueOf(lowString);
		int hightPrice = Integer.valueOf(hightString);
		
		if (lowPrice > hightPrice){
			CommonUtil.showToast(R.string.toast_price_error, this);			
			return ;
		}
		
		String intervalString = mPriceInterval.getText().toString();
		if (intervalString.length() == 0){
			intervalString = "0";
		}
		int priceInterval = Integer.valueOf(intervalString);
		
		LocalConfigSharePreference.commitLowPrice(this, lowPrice);
		LocalConfigSharePreference.commitHightPrice(this, hightPrice);
		
		
		LocalConfigSharePreference.commitPriceWarningType(this, mNoticeType);
		LocalConfigSharePreference.commitComparePF1(this, mComparePf1);
		LocalConfigSharePreference.commitComparePF2(this, mComparePf2);
		LocalConfigSharePreference.commitPriceInterval(this, priceInterval);
		
		MonitorApplication.getInstance().setWarnPlatiformInfo(mNoticeType, lowPrice, hightPrice);
		MonitorApplication.getInstance().setCompareInfo(mComparePf1, mComparePf2, priceInterval);
		
		CommonUtil.showToast(R.string.toast_save_success, this);
		
		finish();
	}
}
