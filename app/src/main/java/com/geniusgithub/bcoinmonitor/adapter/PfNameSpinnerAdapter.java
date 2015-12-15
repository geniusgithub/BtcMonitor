package com.geniusgithub.bcoinmonitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geniusgithub.bcoinmonitor.R;

public class PfNameSpinnerAdapter extends BaseAdapter{

	private String []data = null;
	
	private Context mContext;
	
	public PfNameSpinnerAdapter(Context context, String [] data)
	{
		mContext = context;
		refreshData(data);
	}
	
	public void refreshData(String[] data)
	{
		if (data != null){
			this.data = data;		
		}else{
			this.data = null;
		}

		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (data == null){
			return 0;
		}
		return data.length;
	}

	@Override
	public Object getItem(int pos) {
		if (data == null){
			return null;
		}

		return data[pos];
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
			view = LayoutInflater.from(mContext).inflate(R.layout.spiner_item_layout2, null);
			viewHolder = new ViewHolder();
			viewHolder.tvPlatiform = (TextView) view.findViewById(R.id.tv_name);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}	

		viewHolder.tvPlatiform.setText(data[pos]);
		
		return view;
	}
	
	
   static class ViewHolder { 
        public TextView tvPlatiform;
    } 

}
