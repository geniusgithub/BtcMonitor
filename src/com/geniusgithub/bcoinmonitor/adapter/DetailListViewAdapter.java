package com.geniusgithub.bcoinmonitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.R;
import com.geniusgithub.bcoinmonitor.model.BaseType;
import com.geniusgithub.bcoinmonitor.model.PublicType.ConinDetail;
import com.geniusgithub.bcoinmonitor.util.CommonLog;
import com.geniusgithub.bcoinmonitor.util.LogFactory;

public class DetailListViewAdapter extends BaseAdapter{

	private static final CommonLog log = LogFactory.createLog();
	
	private ConinDetail data = new ConinDetail();
	private Context mContext;
	
	public DetailListViewAdapter(Context context, ConinDetail data)
	{
		mContext = context;
		refreshData(data);
	}
	
	public void refreshData(ConinDetail data)
	{
		if (data != null){
			this.data = data;		
		}else{
			this.data = new ConinDetail();		
		}
		notifyDataSetChanged();

	}
	
	@Override
	public int getCount() {
		
		int size1 = data.mAsksList.size();
		int size2 = data.mBidsList.size();
		
		return Math.min(size1, size2);
		
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
			view = LayoutInflater.from(mContext).inflate(R.layout.detail_listitem_layout, null);
			viewHolder = new ViewHolder();
			viewHolder.ivBuy =  view.findViewById(R.id.v_buy_amount);
			viewHolder.ivBuyEmpty =  view.findViewById(R.id.v_buy_amount_empty);
			viewHolder.tvBuyAmount = (TextView) view.findViewById(R.id.tv_buy_amount);
			viewHolder.tvBuyPrice = (TextView) view.findViewById(R.id.tv_buy_price);
			viewHolder.tvSellPrice = (TextView) view.findViewById(R.id.tv_sell_price);
			viewHolder.tvSellAmount = (TextView) view.findViewById(R.id.tv_sell_amount);
			viewHolder.ivSell = view.findViewById(R.id.v_sell_amount);
			viewHolder.ivSellEmpty =  view.findViewById(R.id.v_sell_amount_empty);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		
		ConinDetail object = (ConinDetail) getItem(pos);
		
		BaseType.ConinInst buyInst = getBuyInst(pos);
		BaseType.ConinInst sellInst = getSellInst(pos);
		
		try {
			String value1 = String.valueOf(buyInst.mCount);
			viewHolder.tvBuyAmount.setText(value1);

			String value2 = String.valueOf(buyInst.mPrice);		
			viewHolder.tvBuyPrice.setText(value2);
			
			String value3 = String.valueOf(sellInst.mPrice);		
			viewHolder.tvSellPrice.setText(value3);
			
			String value4 = String.valueOf(sellInst.mCount);		
			viewHolder.tvSellAmount.setText(value4);

			updateRateView(pos, viewHolder);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	

			return view;
	}
	
	private void updateRateView(int pos, ViewHolder viewHolder){
		BaseType.ConinInst buyInst = getBuyInst(pos);
		BaseType.ConinInst sellInst = getSellInst(pos);

		double rateBuy = buyInst.mRate;
		double rateBuyEmpty = 1- rateBuy;
	

		double rateSell =  sellInst.mRate;
		double rateSellEmpty = 1- rateSell;

	
		viewHolder.ivBuy.setLayoutParams(getParamByWeight(1-rateBuy));
		viewHolder.ivBuyEmpty.setLayoutParams(getParamByWeight(1-rateBuyEmpty));

		
		viewHolder.ivSell.setLayoutParams(getParamByWeight(1-rateSell));
		viewHolder.ivSellEmpty.setLayoutParams(getParamByWeight(1-rateSellEmpty));
		
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
	
	private BaseType.ConinInst getBuyInst(int pos){
		return data.mBidsList.get(pos);
	}
	
	private BaseType.ConinInst getSellInst(int pos){
		return data.mAsksList.get(pos);
	}
	
   static class ViewHolder { 
	    public View ivBuy;
	    public View ivBuyEmpty;
        public TextView tvBuyAmount;
        public TextView tvBuyPrice;
        public TextView tvSellPrice;
        public TextView tvSellAmount;
        public View ivSell;
        public View ivSellEmpty;
    } 

}
