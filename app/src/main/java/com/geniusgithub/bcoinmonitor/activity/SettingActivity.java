package com.geniusgithub.bcoinmonitor.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.datacenter.ConinDetailManager;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.datastore.LocalConfigSharePreference;
import com.geniusgithub.bcoinmonitor.factory.DialogFactory;
import com.geniusgithub.bcoinmonitor.model.IBCointType;
import com.geniusgithub.bcoinmonitor.util.UIHelper;

public class SettingActivity extends  BaseActivity implements View.OnClickListener{

    private Context mContext;
    private View mRootView;
    private View ll_marketView;
    private View ll_deepView;
    private View ll_noteView;
    private View ll_warnView;

    private TextView mTVMarketInterval;
    private TextView mTVDeepInterval;
    private TextView mTVPfName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setupViews() {
        mContext  = this;
        setContentView(R.layout.setting_fragment);
        initToolBar();

        ll_marketView = findViewById(R.id.ll_market);
        ll_deepView = findViewById(R.id.ll_deep);
        ll_noteView = findViewById(R.id.ll_notice_price);
        ll_warnView = findViewById(R.id.ll_warning);



        ll_marketView.setOnClickListener(this);
        ll_deepView.setOnClickListener(this);
        ll_noteView.setOnClickListener(this);
        ll_warnView.setOnClickListener(this);



        mTVMarketInterval = (TextView) findViewById(R.id.tv_market_interval);
        mTVDeepInterval = (TextView) findViewById(R.id.tv_deep_interval);
        mTVPfName = (TextView) findViewById(R.id.tv_pfname);
    }

    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Setting");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
    }


    @Override
    public void initData() {
        String vaString[] = getResources().getStringArray(R.array.string_refresh_items);

        int interval1 = LocalConfigSharePreference.getMarketInterval(mContext);
        int pos1 = 0;

        switch(interval1){
            case 20:
                pos1 = 0;
                break;
            case 30:
                pos1 = 1;
                break;
            case 60:
                pos1 = 2;
                break;
        }
        mTVMarketInterval.setText(vaString[pos1]);

        int interval2 = LocalConfigSharePreference.getDetailInterval(mContext);
        int pos2 = 0;
        switch(interval2){
            case 20:
                pos2 = 0;
                break;
            case 30:
                pos2 = 1;
                break;
            case 60:
                pos2 = 2;
                break;
        }


        mTVDeepInterval.setText(vaString[pos2]);


        int type = LocalConfigSharePreference.getPriceNoticeType(mContext);
        int pos = 0;
        switch(type){
            case -1:
                pos = 0;
                break;
            case IBCointType.BTC_CHINA:
                pos = 1;
                break;
            case IBCointType.OK_COIN:
                pos = 2;
                break;
            case IBCointType.FIRE_COIN:
                pos = 3;
                break;
        }

        String names[] = getResources().getStringArray(R.array.platiform_name_ex);
        mTVPfName.setText(names[pos]);
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ll_market:
                onMarket();
                break;
            case R.id.ll_deep:
                onDeep();
                break;
            case R.id.ll_notice_price:
                onNoticePrice();
                break;
            case R.id.ll_warning:
                UIHelper.openWarnInterface(mContext);
                break;
        }

    }

    private Dialog mRefreshDialog;
    private void onMarket(){
        int interval = LocalConfigSharePreference.getMarketInterval(mContext);
        int pos = 0;
        switch(interval){
            case 20:
                pos = 0;
                break;
            case 30:
                pos = 1;
                break;
            case 60:
                pos = 2;
                break;
        }

        mRefreshDialog = DialogFactory.buildSingleChoiceDialog(this, R.string.txt_marketinterval,
                R.array.string_refresh_items, pos, new DialogFactory.IChoiceItem() {

                    @Override
                    public void onItemChoice(int pos) {
                        int[] arrys = getResources().getIntArray(R.array.int_refresh_items);
                        LocalConfigSharePreference.commintMarketInterval(mContext, arrys[pos]);
                        ConinMarketManager.getInstance(MonitorApplication.getInstance()).setTimeInterval(arrys[pos]);
                        ConinMarketManager.getInstance(MonitorApplication.getInstance()).startRequestTimer();
                        String vaString[] = getResources().getStringArray(R.array.string_refresh_items);
                        mTVMarketInterval.setText(vaString[pos]);
                        mRefreshDialog.dismiss();
                    }
                });

        mRefreshDialog.show();
    }

    private void onDeep(){
        int interval = LocalConfigSharePreference.getDetailInterval(mContext);
        int pos = 0;
        switch(interval){
            case 20:
                pos = 0;
                break;
            case 30:
                pos = 1;
                break;
            case 60:
                pos = 2;
                break;
        }

        mRefreshDialog = DialogFactory.buildSingleChoiceDialog(this, R.string.txt_deepinterval,
                R.array.string_refresh_items, pos, new DialogFactory.IChoiceItem() {

                    @Override
                    public void onItemChoice(int pos) {
                        int[] arrys = getResources().getIntArray(R.array.int_refresh_items);
                        LocalConfigSharePreference.commintDetailInterval(mContext, arrys[pos]);
                        ConinDetailManager.getInstance(MonitorApplication.getInstance()).setTimeInterval(arrys[pos]);
                        ConinDetailManager.getInstance(MonitorApplication.getInstance()).startRequestTimer();
                        String vaString[] = getResources().getStringArray(R.array.string_refresh_items);
                        mTVDeepInterval.setText(vaString[pos]);
                        mRefreshDialog.dismiss();
                    }
                });

        mRefreshDialog.show();
    }



    private Dialog mNotifyDialog;
    private void onNoticePrice(){
        int interval = LocalConfigSharePreference.getPriceNoticeType(mContext);
        int pos = 0;
        switch(interval){
            case -1:
                pos = 0;
                break;
            case IBCointType.BTC_CHINA:
                pos = 1;
                break;
            case IBCointType.OK_COIN:
                pos = 2;
                break;
            case IBCointType.FIRE_COIN:
                pos = 3;
                break;
        }

        mNotifyDialog = DialogFactory.buildSingleChoiceDialog(this, R.string.txt_noteprice,
                R.array.platiform_name_ex, pos, new DialogFactory.IChoiceItem() {

                    @Override
                    public void onItemChoice(int pos) {
                        int type = -1;
                        switch(pos){
                            case 0:
                                type = -1;
                                break;
                            case 1:
                                type = IBCointType.BTC_CHINA;
                                break;
                            case 2:
                                type = IBCointType.OK_COIN;
                                break;
                            case 3:
                                type = IBCointType.FIRE_COIN;
                                break;
                        }

                        LocalConfigSharePreference.commitPriceNoticeType(mContext, type);
                        MonitorApplication.getInstance().setNotifyPlatiformInfo(type);
                        String vaString[] = getResources().getStringArray(R.array.platiform_name_ex);
                        mTVPfName.setText(vaString[pos]);
                        mNotifyDialog.dismiss();
                    }
                });

        mNotifyDialog.show();
    }



/*    private void onHelp(){
        Intent intent = new Intent();
        intent.setClass(mContext, HelpActivity.class);
        startActivity(intent);
    }

    private void onCheckUpdate(){
        CommonUtil.showToast(R.string.toast_lastversion, mContext);
    }

    private void onAbout(){
        Intent intent = new Intent();
        intent.setClass(mContext, com.geniusgithub.bcoinmonitor.activity.setting.AboutActivity.class);
        startActivity(intent);
    }*/


}
