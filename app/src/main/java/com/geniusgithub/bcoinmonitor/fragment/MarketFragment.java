package com.geniusgithub.bcoinmonitor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusgithub.bcoinmonitor.R;


public class MarketFragment extends BaseFragment {



    public MarketFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.market_fragment, container, false);
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }



}
