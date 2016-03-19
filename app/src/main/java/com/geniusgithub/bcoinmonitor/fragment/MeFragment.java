package com.geniusgithub.bcoinmonitor.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;
import com.geniusgithub.bcoinmonitor.util.UIHelper;


public class MeFragment extends BtcMainBaseFragment implements View.OnClickListener {

    private static final CommonLog log = LogFactory.createLog();
    private Context mContext;
    private View mRootView;


    private View ll_shareView;
    private View ll_scanView;
    private View ll_setView;
    private View ll_adviseView;
    private View ll_aboutView;

    private Button mBtnExit;


    public MeFragment() {
        // Required empty public constructor
    }

    private void updateToolbarTitle(){
        if (isEnter){
            setToolbarTitle("SETTING");
        }
    }

    @Override
    public   void onTabSelected(){
        super.onTabSelected();
        updateToolbarTitle();
    }

    @Override
    public  void onTabUnselected(){
        super.onTabUnselected();
    }

    @Override
    public  void onTabReselected(){
        super.onTabReselected();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.me_fragment, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViews(mRootView);
        initData();
    }

    public void setupViews(View view) {
        ll_shareView = view.findViewById(R.id.ll_shapre);
        ll_scanView = view.findViewById(R.id.ll_scan);
        ll_setView = view.findViewById(R.id.ll_setting);
        ll_adviseView = view.findViewById(R.id.ll_advise);
        ll_aboutView = view.findViewById(R.id.ll_about);


        ll_shareView.setOnClickListener(this);
        ll_scanView.setOnClickListener(this);
        ll_setView.setOnClickListener(this);
        ll_adviseView.setOnClickListener(this);
        ll_aboutView.setOnClickListener(this);

        mBtnExit = (Button)view.findViewById(R.id.btn_exit);

        mBtnExit.setOnClickListener(this);

    }


    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();

        updateToolbarTitle();

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
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ll_shapre:
                UIHelper.openShareInterface(mContext);
                break;
            case R.id.ll_scan:
                UIHelper.openScanInterface(mContext);
                break;
            case R.id.ll_setting:
                UIHelper.openSettingInterface(mContext);
                break;
            case R.id.ll_advise:
                UIHelper.openAdviseInterface(mContext);
                break;
            case R.id.ll_about:
                UIHelper.openAboutInterface(mContext);
                break;
            case R.id.btn_exit:
                exitProcess();
                break;

        }

    }




    private void exitProcess(){
        getActivity().finish();
        MonitorApplication.getInstance().exitProcess();
    }
}
