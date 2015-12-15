package com.geniusgithub.bcoinmonitor.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.network.ServerUrlBuilder;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.CommonUtil;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.widget.spinner.AbstractSpinerAdapter;
import com.geniusgithub.bcoinmonitor.widget.spinner.CustemObject;
import com.geniusgithub.bcoinmonitor.widget.spinner.CustemSpinerAdapter;
import com.geniusgithub.bcoinmonitor.widget.spinner.SpinerPopWindow;

public class KLineActivity extends BaseActivity implements OnClickListener{

	private static final CommonLog log = LogFactory.createLog();
	
	private View mRootView;
	private View mTitleLayoutView;
	private TextView mTVPlatiform;
	private List<CustemObject> platiformNameList = new ArrayList<CustemObject>();
	private AbstractSpinerAdapter mPlatiformAdapter;
	private SpinerPopWindow mSpinerPopWindow;
	

	private LinearLayout mLayout;
	private WebView mWebView;
	private ProgressBar mProgress;
	private ScanWebViewClient mWeiboWebViewClient;
	
	
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


	public void setupViews() {
		setContentView(R.layout.kline_layout);
		mRootView = findViewById(R.id.rootView);
		mTitleLayoutView = findViewById(R.id.title_layout);
		mTitleLayoutView.setOnClickListener(this);
		mTVPlatiform = (TextView) findViewById(R.id.tv_pfname);
		mLayout = (LinearLayout) findViewById(R.id.lL_webview);
		
		mWebView = (WebView) findViewById(R.id.webview);
		mProgress = (ProgressBar) findViewById(R.id.progress_bar);
	    
	    WebSettings webSettings = mWebView.getSettings();
	    webSettings.setJavaScriptEnabled(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setGeolocationEnabled(false);
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);
		
	

	}


	public void initData() {
		
		String[] names = getResources().getStringArray(R.array.platiform_name);
		for(int i = 0; i < names.length; i++){
			CustemObject object = new CustemObject();
			object.data = names[i];
			platiformNameList.add(object);
		}
		
		
		mPlatiformAdapter = new CustemSpinerAdapter(this);
		mPlatiformAdapter.refreshData(platiformNameList, 0);

		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.setAdatper(mPlatiformAdapter);
		mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener(){

			@Override
			public void onItemClick(int pos) {
				switch(pos){
					case 0:
						mTVPlatiform.setText(mPlatiformAdapter.getItem(0).toString());
						reloadWebView(IBCointType.BTC_CHINA);
						break;
					case 1:
						mTVPlatiform.setText(mPlatiformAdapter.getItem(1).toString());
						reloadWebView(IBCointType.OK_COIN);
						break;
					case 2:
						mTVPlatiform.setText(mPlatiformAdapter.getItem(2).toString());
						reloadWebView(IBCointType.FIRE_COIN);
						break;
				}
			}
			
		});
		
		mTVPlatiform.setText(mPlatiformAdapter.getItem(0).toString());
		
		mWeiboWebViewClient = new ScanWebViewClient();
		mWebView.setWebViewClient(mWeiboWebViewClient);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(true);
		mWebView.setHorizontalFadingEdgeEnabled(true);
		mWebView.setHorizontalScrollbarOverlay(true);
		mWebView.setInitialScale(100);
		mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		
		mWebView.loadUrl(ServerUrlBuilder.getChart(IBCointType.BTC_CHINA));
	}

	
	private void reloadWebView(int type){
		mWebView.clearCache(true);
		mWebView.removeAllViews();
		mWebView.loadUrl(ServerUrlBuilder.getChart(type));
	}


	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.title_layout:
				showSpinWindow();
				break;
		}
	}
	
	private void showSpinWindow(){

		mSpinerPopWindow.setWidth(mTitleLayoutView.getWidth());
		mSpinerPopWindow.showAsDropDown(mTitleLayoutView);
	}
	
	
	private void showProgress()
	{
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mProgress.setVisibility(View.VISIBLE);
			}
		});
		
	}
	
	private void hideProgress()
	{
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mProgress.setVisibility(View.INVISIBLE);
			}
		});


	}

	
	 private class ScanWebViewClient extends WebViewClient {

	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            log.e("shouldOverrideUrlLoading url = " + url);
	           // showProgress();
	            view.loadUrl(url);
	            return super.shouldOverrideUrlLoading(view, url);
	        }

	        @Override
	        public void onReceivedError(WebView view, int errorCode, String description,
	                String failingUrl) {
	            
	        	log.e("onReceivedError failingUrl = " + failingUrl);
	            super.onReceivedError(view, errorCode, description, failingUrl);
	        }

	        @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	        	
	        	log.e("onPageStarted url = " + url);
	            showProgress();
	            super.onPageStarted(view, url, favicon);

	        }

	        @Override
	        public void onPageFinished(WebView view, String url) {
	         	log.e("onPageFinished url = " + url);
	        	hideProgress();
	            super.onPageFinished(view, url);
	        }
	        
	    
	 }

}