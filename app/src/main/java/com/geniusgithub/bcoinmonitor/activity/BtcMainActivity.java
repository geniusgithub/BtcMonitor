package com.geniusgithub.bcoinmonitor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.datacenter.ConinDetailManager;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.fragment.DeepFragment;
import com.geniusgithub.bcoinmonitor.fragment.MarketFragment;
import com.geniusgithub.bcoinmonitor.fragment.SettingFragment;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

import java.util.ArrayList;

public class BtcMainActivity extends AppCompatActivity {
    private static final CommonLog log = LogFactory.createLog();

    private Context mContext;
    private Resources mResource;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    private TabLayout.Tab mTabMarket;
    private TabLayout.Tab mTabDeep;
    private TabLayout.Tab mTabSetting;

    private ConinMarketManager mMarketManager;
    private ConinDetailManager mDetailManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btc_main_layout);
        mContext = this;
        mResource = mContext.getResources();
        initView();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, Menu.FIRST + 1, 1, getResources().getString(R.string.menu_exitprocess));
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, getResources().getString(R.string.menu_runinbackground));

        return true;
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

    private void exitProcess(){
        finish();
        MonitorApplication.getInstance().exitProcess();
    }

    private void runInBackground(){
        finish();
    }

    private void initView(){
        initToolBar();
        setupViewPager();

    }

    public void initData() {

        MonitorApplication.getInstance().setStatus(true);

        mMarketManager = ConinMarketManager.getInstance(MonitorApplication.getInstance());
        mMarketManager.startRequestTimer();

        mDetailManager = ConinDetailManager.getInstance(MonitorApplication.getInstance());
        mDetailManager.startRequestTimer();
    }


    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BTC");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
    }


    private void setupViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), this, mTabLayout);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

        mTabMarket  = mTabLayout.newTab();
        adapter.addTab(mTabMarket, MarketFragment.class, null);
        mTabDeep = mTabLayout.newTab();
        adapter.addTab(mTabDeep, DeepFragment.class, null);
        mTabSetting = mTabLayout.newTab();
        adapter.addTab(mTabSetting, SettingFragment.class, null);
        mTabMarket.setCustomView(newTabView(mResource.getString(R.string.main_market), mResource.getDrawable(R.drawable.tab_icon_new)));
        mTabDeep.setCustomView(newTabView(mResource.getString(R.string.main_deep), mResource.getDrawable(R.drawable.tab_icon_explore)));
        mTabSetting.setCustomView(newTabView(mResource.getString(R.string.main_setting), mResource.getDrawable(R.drawable.tab_icon_me)));

        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        mTabLayout.setOnTabSelectedListener(mOnTabListener);



    }

    private View newTabView(String title, Drawable drawable){
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_indicator, null);
        TextView titleView = (TextView) view.findViewById(R.id.tab_title);
        titleView.setText(title);
        titleView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        return view;
    }




    private class MainFragmentAdapter extends FragmentPagerAdapter{

        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>(3);
        private final TabLayout mTabLayout;
        private final Context mContext;
        private FragmentManager mFragmentManager;

        final class TabInfo {
            private Class<? extends Fragment> clss;
            private Bundle args;
            private Fragment fragment = null;

            TabInfo(Class<? extends Fragment> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }
        public void addTab(TabLayout.Tab tab, Class<? extends Fragment> clss, Bundle args) {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            mTabs.add(info);
            mTabLayout.addTab(tab);
            notifyDataSetChanged();
        }

        public MainFragmentAdapter(FragmentManager fm, Activity activity, TabLayout tablayout) {
            super(fm);
            // TODO Auto-generated constructor stub
            mContext = activity;
            mTabLayout = tablayout;
            mFragmentManager = fm;
            // mTabLayout.setOnTabSelectedListener(this);
        }

        @Override
        public Fragment getItem(int position) {
            // TODO Auto-generated method stub
            TabInfo info = mTabs.get(position);
            if (info.fragment == null) {
                info.fragment = Fragment.instantiate(mContext,
                        info.clss.getName(), info.args);
            }
            return info.fragment;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mTabs.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            return super.instantiateItem(container, position);
        }

        public Fragment getCurrentFragment(int position) {
            TabInfo info = mTabs.get(position);

            if (info.fragment == null) {
                String name = makeFragmentName(mViewPager.getId(),
                        getItemId(position));
                info.fragment = mFragmentManager.findFragmentByTag(name);
                if (info.fragment == null) {
                    info.fragment = Fragment.instantiate(mContext,
                            info.clss.getName());
                }
            }
            return info.fragment;
        }




        private String makeFragmentName(int viewId, long id) {
            return "android:switcher:" + viewId + ":" + id;
        }

    }


    private boolean isOnPageSrolled = false;
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            isOnPageSrolled = true;

        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // TODO Auto-generated method stub
            isOnPageSrolled = false;

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub

        }

    };


    private TabLayout.OnTabSelectedListener mOnTabListener = new TabLayout.OnTabSelectedListener(){

        public void onTabSelected(TabLayout.Tab tab){

            mViewPager.setCurrentItem(tab.getPosition());
        }

        public void onTabUnselected(TabLayout.Tab tab){

        }


        public void onTabReselected(TabLayout.Tab tab){


        }

    };
}
