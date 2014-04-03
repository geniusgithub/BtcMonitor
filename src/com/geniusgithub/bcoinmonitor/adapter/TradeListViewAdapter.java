package com.geniusgithub.bcoinmonitor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinTrade;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinTradeGroup;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

public class TradeListViewAdapter extends BaseAdapter{

	private static final CommonLog log = LogFactory.createLog();
	
	private ConinTradeGroup data = new ConinTradeGroup();
	private Context mContext;
	
	public TradeListViewAdapter(Context context, ConinTradeGroup data)
	{
		mContext = context;
		refreshData(data);
	}
	
	public void refreshData(ConinTradeGroup data)
	{
		if (data != null){
			this.data = data;		
		}else{
			log.e("TradeListViewAdapter refreshData fail!!! data = null");
			this.data = new ConinTradeGroup();
		}
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {		
		return data.mConinTradeList.size();	
	}

	@Override
	public Object getItem(int pos) {

		return data;
	}

	@Override
	public long getItemId(int arg0) {

		return 0;
	}
		
	@Override
	public View getView(int pos, View view, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (view == null)
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.trade_listitem_layout, null);
			viewHolder = new ViewHolder();

			viewHolder.tvTradeTime = (TextView) view.findViewById(R.id.tv_trade_time);
			viewHolder.tvTradePrice = (TextView) view.findViewById(R.id.tv_trade_price);
			viewHolder.tvTradeAmount = (TextView) view.findViewById(R.id.tv_trade_amount);
			viewHolder.ivAmount = view.findViewById(R.id.v_amount);
			viewHolder.ivAmountEmpty = view.findViewById(R.id.v_amount_empty);

			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		
		ConinTradeGroup group = (ConinTradeGroup) getItem(pos);		
		ConinTrade object = group.mConinTradeList.get(pos);
		
		viewHolder.tvTradeTime.setText(object.mDataEx);
		viewHolder.tvTradePrice.setText(object.mPrice);
		viewHolder.tvTradeAmount.setText(object.mAmount);
		
		updateRateView(object, viewHolder);

		return view;
	}
	
	private void updateRateView(ConinTrade object, ViewHolder viewHolder){

		
		double rateAmount = object.mRate;
		double rateAmountEmpty = 1- rateAmount;
	
		viewHolder.ivAmount.setLayoutParams(getParamByWeight(1-rateAmount));
		viewHolder.ivAmountEmpty.setLayoutParams(getParamByWeight(1-rateAmountEmpty));
		
		if (object.mIsUp){
			viewHolder.ivAmount.setBackgroundResource(R.drawable.point_green_bg);
			viewHolder.tvTradePrice.setTextColor(Color.GREEN);
		}else{
			viewHolder.ivAmount.setBackgroundResource(R.drawable.point_red_bg);
			viewHolder.tvTradePrice.setTextColor(Color.RED);
		}
		
	}
	
	private LinearLayout.LayoutParams getParamByWeight(double weight){
			int value = (int) (weight * 100);
			if (value < 1){
				value = 1;
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.FILL_PARENT, value);
            return params;
	}
	
	
   static class ViewHolder { 
        public TextView tvTradeTime;
        public TextView tvTradePrice;
        public TextView tvTradeAmount;
        public TextView tvSellAmount;
        public View ivAmount;
        public View ivAmountEmpty;
    } 

}
