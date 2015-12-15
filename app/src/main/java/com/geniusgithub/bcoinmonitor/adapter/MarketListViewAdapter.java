package com.geniusgithub.bcoinmonitor.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.datacenter.ConinMarketEx;
import com.geniusgithub.bcoinmonitor.model.IBCointType;

public class MarketListViewAdapter extends BaseAdapter{

	
	private List<ConinMarketEx> data = new ArrayList<ConinMarketEx>();
	
	private Context mContext;
	
	public MarketListViewAdapter(Context context, List<ConinMarketEx> data)
	{
		mContext = context;
		refreshData(data);
	}
	
	public void refreshData(List<ConinMarketEx> data)
	{
		if (data != null){
			this.data = data;		
		}else{
			this.data.clear();
		}

		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return data.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder viewHolder = null;
		if (view == null)
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.market_listitem_layout, null);
			viewHolder = new ViewHolder();
			viewHolder.tvPlatiform = (TextView) view.findViewById(R.id.tv_platform);
			viewHolder.tvLastbusiness = (TextView) view.findViewById(R.id.tv_lastbusiness);
			viewHolder.tvBuy = (TextView) view.findViewById(R.id.tv_buy);
			viewHolder.tvSell = (TextView) view.findViewById(R.id.tv_sell);
			viewHolder.tvVol = (TextView) view.findViewById(R.id.tv_vol);
			viewHolder.ivIcon = (ImageView) view.findViewById(R.id.icon_image);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		
		ConinMarketEx object = (ConinMarketEx) getItem(pos);
		viewHolder.tvPlatiform.setText(getPlatiformName(object.mPlatiformType));
		viewHolder.tvLastbusiness.setText(object.mLast);
		viewHolder.tvBuy.setText(object.mBuy);
		viewHolder.tvSell.setText(object.mSell);
		viewHolder.tvVol.setText(object.mVol);
		
		switch(object.mStatus){
			case ConinMarketEx.IStatusType.ICT_NOSTATUS:
				break;
			case ConinMarketEx.IStatusType.ICT_NORMAL:			
				break;
			case ConinMarketEx.IStatusType.ICT_UP:
				viewHolder.ivIcon.setVisibility(View.VISIBLE);
				viewHolder.ivIcon.setBackgroundResource(R.drawable.up_icon);
				viewHolder.tvLastbusiness.setTextColor(Color.GREEN);
				break;
			case ConinMarketEx.IStatusType.ICT_DOWN:
				viewHolder.ivIcon.setVisibility(View.VISIBLE);
				viewHolder.ivIcon.setBackgroundResource(R.drawable.down_icon);
				viewHolder.tvLastbusiness.setTextColor(Color.RED);
				break;
		}
		
		
		return view;
	}
	
	
   static class ViewHolder { 
        public TextView tvPlatiform;
        public TextView tvLastbusiness;
        public TextView tvBuy;
        public TextView tvSell;
        public TextView tvVol;
        public ImageView ivIcon;
    } 
   
   public String getPlatiformName(int type){
	   String value = "unknow";
	   switch(type){
		   case IBCointType.BTC_CHINA:
			   value = mContext.getResources().getString(R.string.pf_btcchina);
			   break;
		   case IBCointType.OK_COIN:
			   value = mContext.getResources().getString(R.string.pf_okcoin);
			   break;
		   case IBCointType.FIRE_COIN:
			   value = mContext.getResources().getString(R.string.pf_firecoin);
			   break;
	   }
	   
	   return value;
   }

}
