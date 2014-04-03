package com.geniusgithub.bcoinmonitor.factory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

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
	
	
	
	
}