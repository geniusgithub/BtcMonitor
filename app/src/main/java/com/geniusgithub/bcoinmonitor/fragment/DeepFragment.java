package com.geniusgithub.bcoinmonitor.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.adapter.SubPagerAdapter;
import com.geniusgithub.bcoinmonitor.datacenter.ConinDetailManager;
import com.geniusgithub.bcoinmonitor.datacenter.ConinTradeManager;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.CommonUtil;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.util.TimeUtil;
import com.geniusgithub.bcoinmonitor.widget.spinner.AbstractSpinerAdapter;
import com.geniusgithub.bcoinmonitor.widget.spinner.CustemObject;
import com.geniusgithub.bcoinmonitor.widget.spinner.CustemSpinerAdapter;
import com.geniusgithub.bcoinmonitor.widget.spinner.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;


public class DeepFragment extends BaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final CommonLog log = LogFactory.createLog();
    private static final int REFRESH_DETAIL_DATA = 0x0001;
    private static final int REFRESH_TRADE_DATA = 0x0002;
    private static final int DELAY_SHOW = 0x0003;

    private Context mContext;
    private View mRootView;
    private RadioGroup m_radioGroup;
    private SubViewPager mPager;
    private TextView mTvTime;
    private TextView mTvLastBusiness;

    private View mTitleLayoutView;
    private TextView mTVPlatiform;
    private List<CustemObject> platiformNameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mPlatiformAdapter;
    private SpinerPopWindow mSpinerPopWindow;


    private DetailFragment mDetailFragment;
    private TradeFragment mTradeFragment;
    private SubPagerAdapter mAdapter;

    private Handler mHandler;
    private ConinDetailManager mDetailManager;
    private ConinTradeManager mTradeManager;

    public DeepFragment() {
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
        mRootView = inflater.inflate(R.layout.deep_fragment, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViews(mRootView);
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

        super.onDestroy();
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPageSelected(int pos) {
        switch(pos){
            case 0:
                m_radioGroup.check(R.id.rb_detail);
                break;
            case 1:
                m_radioGroup.check(R.id.rb_trade);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.title_layout:
                showSpinWindow();
                break;
        }
    }
    public void setupViews(View view) {
/*        mTitleLayoutView = view.findViewById(R.id.title_layout);
        mTitleLayoutView.setOnClickListener(this);*/
 //       mTVPlatiform = (TextView) view.findViewById(R.id.tv_pfname);

        mPager = (SubViewPager) view.findViewById(R.id.view_pager);
        m_radioGroup = (RadioGroup) view.findViewById(R.id.main_radiogroup);
        mTvTime = (TextView) view.findViewById(R.id.refresh_text);
        mTvLastBusiness = (TextView) view.findViewById(R.id.tv_last);
    }


    public void initData() {

        String[] names = getResources().getStringArray(R.array.platiform_name);
        for(int i = 0; i < names.length; i++){
            CustemObject object = new CustemObject();
            object.data = names[i];
            platiformNameList.add(object);
        }


        mPlatiformAdapter = new CustemSpinerAdapter(mContext);
        mPlatiformAdapter.refreshData(platiformNameList, 0);

        mSpinerPopWindow = new SpinerPopWindow(mContext);
        mSpinerPopWindow.setAdatper(mPlatiformAdapter);
        mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {

            @Override
            public void onItemClick(int pos) {
                switch (pos) {
                    case 0:
                        mTVPlatiform.setText(mPlatiformAdapter.getItem(0).toString());
                        refreshAndRequestData(IBCointType.BTC_CHINA);
                        break;
                    case 1:
                        mTVPlatiform.setText(mPlatiformAdapter.getItem(1).toString());
                        refreshAndRequestData(IBCointType.OK_COIN);
                        break;
                    case 2:
                        mTVPlatiform.setText(mPlatiformAdapter.getItem(2).toString());
                        refreshAndRequestData(IBCointType.FIRE_COIN);
                        break;
                }
            }

        });

        mDetailFragment = new DetailFragment();
        mTradeFragment = new TradeFragment();

        mAdapter = new SubPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(mDetailFragment);
        mAdapter.addFragment(mTradeFragment);

        mPager.setAdapter(mAdapter);
        mPager.setScanScroll(true);
        mPager.setOnPageChangeListener(this);

        m_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkedId) {
                switch(checkedId){
                    case R.id.rb_detail:
                        mPager.setCurrentItem(0);
                        break;
                    case R.id.rb_trade:
                        mPager.setCurrentItem(1);
                        break;
                }
            }
        });

        mHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case REFRESH_DETAIL_DATA:
                        updateDetailData();
                        break;
                    case REFRESH_TRADE_DATA:
                        updateTradeData();
                        break;
                    case DELAY_SHOW:
                        delayShow();
                        break;
                }
            }

        };

        mDetailManager = ConinDetailManager.getInstance(MonitorApplication.getInstance());
        mTradeManager = ConinTradeManager.getInstance(MonitorApplication.getInstance());

        mDetailManager.registerHandler(mHandler, REFRESH_DETAIL_DATA);
        mTradeManager.registerHandler(mHandler, REFRESH_TRADE_DATA);

     //   mTVPlatiform.setText(mPlatiformAdapter.getItem(0).toString());
        mHandler.sendEmptyMessageDelayed(DELAY_SHOW, 500);
    }

    private void delayShow(){
        refreshAndRequestData(IBCointType.BTC_CHINA);
    }

    private void refreshAndRequestData(int type){
        mHandler.removeMessages(REFRESH_DETAIL_DATA);
        mHandler.sendEmptyMessageDelayed(REFRESH_DETAIL_DATA, 200);
        mHandler.removeMessages(REFRESH_TRADE_DATA);
        mHandler.sendEmptyMessageDelayed(REFRESH_TRADE_DATA, 200);

        mDetailManager.setDetailType(type);
        mTradeManager.setTradeType(type);

        mDetailManager.requestBTCData(true);
        mTradeManager.requestBTCData(true);

    }
    private void updateDetailData(){
        log.e("updateDetailData");
        long time = mDetailManager.getUpdateTime();
        String value = TimeUtil.getTimeShort(time);
        mTvTime.setText(value);

        mDetailFragment.updateListData();

    }

    private void updateTradeData(){
        log.e("updateTradeData");
        long time = mTradeManager.getUpdateTime();
        String value = TimeUtil.getTimeShort(time);
        mTvTime.setText(value);

        String lastBusiness = mTradeManager.getLastBusiness();
        mTvLastBusiness.setText(lastBusiness);

        mTradeFragment.updateListData();
    }

    private void showSpinWindow(){

        mSpinerPopWindow.setWidth(mTitleLayoutView.getWidth());
        mSpinerPopWindow.showAsDropDown(mTitleLayoutView);
    }



}
