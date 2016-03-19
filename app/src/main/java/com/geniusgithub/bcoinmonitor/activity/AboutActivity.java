package com.geniusgithub.bcoinmonitor.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.util.UIHelper;

public class AboutActivity extends  BaseActivity implements View.OnClickListener{

    private Context mContext;
    private View mRootView;
    private View ll_checkUpdateView;
    private View ll_copyrightView;
    private View ll_HelpView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setupViews() {
        setContentView(R.layout.about_layout);
        initToolBar();

        ll_checkUpdateView = findViewById(R.id.ll_checkupdate);
        ll_copyrightView = findViewById(R.id.ll_copyRight);
        ll_HelpView = findViewById(R.id.ll_help);

        ll_checkUpdateView.setOnClickListener(this);
        ll_copyrightView.setOnClickListener(this);
        ll_HelpView.setOnClickListener(this);
    }

    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("About");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
    }


    @Override
    public void initData() {

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
            case R.id.ll_checkupdate:
                checkUpdate();
                break;
            case R.id.ll_copyRight:
                UIHelper.openCopyRightInterface(this);
                break;
            case R.id.ll_help:
                UIHelper.openHelpInterface(this);
                break;
        }

    }

    private void checkUpdate(){
        Toast.makeText(this, "当前已是最新版本", Toast.LENGTH_LONG).show();
    }

}
