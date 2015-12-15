package com.geniusgithub.bcoinmonitor.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.adapter.TradeListViewAdapter;
import com.geniusgithub.bcoinmonitor.datacenter.ConinTradeManager;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinTradeGroup;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.widget.RefreshListView;

public class TradeFragment extends Fragment  implements RefreshListView.IOnRefreshListener{

	private static final CommonLog log = LogFactory.createLog();
	private Context mContext;
	
	private RefreshListView mTradeListView;
	private TradeListViewAdapter mTradeAdapter;
	private ConinTradeManager mTradeManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.e("TradeFragment onCreateView");
		View view = inflater.inflate(R.layout.content_trade_layout, null);
		
		mTradeListView = (RefreshListView) view.findViewById(R.id.trade_list_view);
		
		return view;
		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		initData();
	}
	
	private void initData(){
		mContext = getActivity();
		

	
		mTradeListView.setOnRefreshListener(this);
		
		mTradeManager = ConinTradeManager.getInstance(MonitorApplication.getInstance());
		mTradeAdapter = new TradeListViewAdapter(mContext, mTradeManager.getCurTradeGroup());
		mTradeListView.setAdapter(mTradeAdapter);
	}


	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void OnRefresh() {
		mTradeManager.requestBTCData(true);
	}
	
	
	public void updateListData(){
		ConinTradeGroup group = mTradeManager.getCurTradeGroup();
		mTradeAdapter.refreshData(group);
		mTradeListView.onRefreshComplete();
	}
}
