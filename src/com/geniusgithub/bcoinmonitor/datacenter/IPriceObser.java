package com.geniusgithub.bcoinmonitor.datacenter;

public interface IPriceObser {

	public void onPriceUpdate(int type, String price);
}
