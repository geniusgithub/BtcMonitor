package com.geniusgithub.bcoinmonitor.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.adapter.MarketListViewAdapter;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketEx;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.util.CommonUtil;
import com.geniusgithub.bcoinmonitor.util.TimeUtil;
import com.geniusgithub.bcoinmonitor.widget.RefreshListView;

import java.util.List;


public class MarketFragment extends BaseFragment implements RefreshListView.IOnRefreshListener{

    private static final int REFRESH_DATA = 0x0001;

    private Context mContext;
    private View mRootView;
    private RefreshListView mListView;
    private MarketListViewAdapter adapter;

   // private TextView mTvTime;

    private ConinMarketManager mMarketManager;
    private Handler mHandler;

    public MarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.market_fragment, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = (RefreshListView)mRootView. findViewById(R.id.market_list_view);
    //    mTvTime = (TextView) mRootView.findViewById(R.id.refresh_text);

        initData();
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean ret = CommonUtil.isNetworkConnect(mContext);
        if (!ret){
            CommonUtil.showToast(R.string.toast_net_error, mContext);
            return ;
        }
    }

    @Override
    public void onDestroy() {

        mMarketManager.unregisterHandler();
        super.onDestroy();
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void OnRefresh() {
        mMarketManager.requestBTCData(true);
    }

    public void initData() {

        mMarketManager = ConinMarketManager.getInstance(MonitorApplication.getInstance());
        adapter = new MarketListViewAdapter(mContext,  mMarketManager.getMaList());

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
        //    mTvTime.setText(value);
        }

    }

    private void updateData(){
        List<ConinMarketEx> list = mMarketManager.getMaList();
        adapter.refreshData(list);
        mListView.onRefreshComplete();
        long time = mMarketManager.getUpdateTime();
        String value = TimeUtil.getTimeShort(time);
  //      mTvTime.setText(value);

    }


}
