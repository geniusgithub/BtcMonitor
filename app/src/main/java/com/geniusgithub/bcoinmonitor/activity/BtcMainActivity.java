package com.geniusgithub.bcoinmonitor.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.fragment.DeepFragment;
import com.geniusgithub.bcoinmonitor.fragment.FragmentAdapter;
import com.geniusgithub.bcoinmonitor.fragment.MarketFragment;
import com.geniusgithub.bcoinmonitor.fragment.SettingFragment;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class BtcMainActivity extends AppCompatActivity {
    private static final CommonLog log = LogFactory.createLog();

    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btc_main_layout);

        initView();
    }


    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar);


        setupViewPager();

    }



    private void initToolBar(Toolbar toolbar){

        setSupportActionBar(toolbar);
        toolbar.setTitle("BTC");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
    }


    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<String>();
        titles.add(getResources().getString(R.string.main_market));
        titles.add(getResources().getString(R.string.main_deep));
        titles.add(getResources().getString(R.string.main_setting));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

        List<Fragment> fragments = new ArrayList<Fragment>();
        Fragment fragment = new MarketFragment();
        fragments.add(fragment);
        fragment = new DeepFragment();
        fragments.add(fragment);
        fragment = new SettingFragment();
        fragments.add(fragment);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

        mViewPager.setOffscreenPageLimit(fragments.size());

    }




}
