package com.geniusgithub.bcoinmonitor.factory;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketEx;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketManager;
import com.geniusgithub.bcoinmonitor.util.TimeUtil;

public class DialogFactory {

	public static interface IChoiceItem{
		public void onItemChoice(int pos);
	}
	
	public static Dialog  buildSingleChoiceDialog(Context context, int title, int resID, int startPos, final IChoiceItem listener){
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setSingleChoiceItems(resID, startPos, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int pos) {
				if (listener != null){
					listener.onItemChoice(pos);
				}
			}
		});
		
		return builder.create();
	}


	public static Dialog  buildListDialog(Context context, String title, int resID, final IChoiceItem listener){
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setItems(resID, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int pos) {
				if (listener != null) {
					listener.onItemChoice(pos);
				}
			}
		});

		return builder.create();
	}

	public static Dialog  buildCoinMarketDialog(Context context, String title, ConinMarketEx object){

		final Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		View view = LayoutInflater.from(context).inflate(R.layout.coninmarket_dialog_layout, null);
		builder.setView(view);

		TextView lastBusiness = (TextView) view.findViewById(R.id.tv_lastbusiness);
		TextView buyOne = (TextView) view.findViewById(R.id.tv_buyone);
		TextView sellOne = (TextView) view.findViewById(R.id.tv_sellone);
		TextView high = (TextView) view.findViewById(R.id.tv_high);
		TextView low = (TextView) view.findViewById(R.id.tv_low);
		TextView businesscount = (TextView) view.findViewById(R.id.tv_businesscount);
		TextView updateTime = (TextView) view.findViewById(R.id.tv_updatetime);

		lastBusiness.setText(object.mLast);
		buyOne.setText(object.mBuy);
		sellOne.setText(object.mSell);
		high.setText(object.mHigh);
		low.setText(object.mLow);
		businesscount.setText(object.mVol);
		updateTime.setText(TimeUtil.getTimeLong(ConinMarketManager.getInstance(context).getUpdateTime()));
		builder.setNegativeButton(R.string.txt_close, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int pos) {

			}
		});
		return builder.create();
	}
	
}