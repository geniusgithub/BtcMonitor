package com.geniusgithub.bcoinmonitor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.adapter.DetailListViewAdapter;
import com.geniusgithub.bcoinmonitor.datacenter.ConinDetailManager;
import com.geniusgithub.bcoinmonitor.datacenter.ConinTradeManager;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinDetail;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.widget.RefreshListView;

public class DetailFragment extends BaseFragment implements RefreshListView.IOnRefreshListener{

	private static final CommonLog log = LogFactory.createLog();
	private static final int REFRESH_DETAIL_DATA = 0x0001;

	private Context mContext;
	
	private RefreshListView mDetailListView;
	private DetailListViewAdapter mDetaildapter;
	private ConinDetailManager mDetailManager;
	private ConinTradeManager mTradeManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.e("DetailFragment onCreateView");
		View view = inflater.inflate(R.layout.content_detail_layout, null);		
		mDetailListView = (RefreshListView) view.findViewById(R.id.detail_list_view); 
		
		return view;
		
	}
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initData();
	}
	
	private void initData(){
		mContext = getActivity();
		

		mDetailListView.setOnRefreshListener(this);
		
		mDetailManager = ConinDetailManager.getInstance(MonitorApplication.getInstance());
		mDetaildapter = new DetailListViewAdapter(mContext,  mDetailManager.getConinDetail(IBCointType.BTC_CHINA));
		mDetailListView.setAdapter(mDetaildapter);
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void OnRefresh() {
		mDetailManager.requestBTCData(true);
	}
	
	
	public void updateListData(){
		ConinDetail object = mDetailManager.getCurConinDetail();
		mDetaildapter.refreshData(object);
		mDetailListView.onRefreshComplete();
	}

}
