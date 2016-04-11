package com.geniusgithub.bcoinmonitor.activity;


import com.geniusgithub.bcoinmonitor.MonitorApplication;
import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.fragment.DeepFragment;
import com.geniusgithub.bcoinmonitor.fragment.MarketFragment;
import com.geniusgithub.bcoinmonitor.fragment.MeFragment;

public enum MainTab {

	TMZS(0, MonitorApplication.getInstance().getResources().getString(R.string.main_market), MarketFragment.class),

	TMDR(1, MonitorApplication.getInstance().getResources().getString(R.string.main_deep), DeepFragment.class),

	LENOVO(2, MonitorApplication.getInstance().getResources().getString(R.string.main_more), MeFragment.class);


	private int idx;
	private String resName;
	private Class<?> clz;

	private MainTab(int idx, String resName, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}


	public Class<?> getFragmentClass() {
		return clz;
	}

	public void setFragmentClass(Class<?> clz) {
		this.clz = clz;
	}
}
