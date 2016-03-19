package com.geniusgithub.bcoinmonitor.fragment;

import android.app.Activity;

import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

public abstract  class BtcMainBaseFragment extends  BaseFragment {
    protected static final CommonLog log = LogFactory.createLog();
    protected IToolbarEvent mToolbarEvent;
    public static interface IToolbarEvent{
        public void setToolbarTitle(String title);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mToolbarEvent = (IToolbarEvent)activity;

    }

    protected boolean isEnter = false;
    public  void onTabSelected(){
        isEnter = true;
    }
    public  void onTabUnselected(){
        isEnter = false;
    }
    public  void onTabReselected(){
        isEnter = true;
    }

    public void setToolbarTitle(String title){
        if (mToolbarEvent != null){
            mToolbarEvent.setToolbarTitle(title);
        }else{
            log.e("mToolbarEvent = null!!!");
        }
    }
}
