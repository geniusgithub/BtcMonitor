package com.geniusgithub.bcoinmonitor.activity.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.activity.MainTab;
import com.geniusgithub.bcoinmonitor.fragment.MyFragmentTabHost;

public class TestMainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener{


    private MyFragmentTabHost mFragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_layout);


        initView();
    }

    private void initView(){

        initTabs();
    }

    private void initTabs() {

        mFragmentTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(this, getFragmentManager(), R.id.realtabcontent);
        MainTab[] tabs = MainTab.values();
        for(int i = 0; i < tabs.length; i++){
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mFragmentTabHost.newTabSpec(mainTab.getResName());
            tab.setIndicator(mainTab.getResName());
            Bundle bundle = new Bundle();
            mFragmentTabHost.addTab(tab, mainTab.getFragmentClass(), bundle);
        }

        mFragmentTabHost.setOnTabChangedListener(this);
        mFragmentTabHost.setCurrentTab(0);

    }


    @Override
    public void onTabChanged(String tabId) {

    }
}
