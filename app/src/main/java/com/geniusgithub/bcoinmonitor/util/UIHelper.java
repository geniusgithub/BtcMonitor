package com.geniusgithub.bcoinmonitor.util;

import android.content.Context;
import android.content.Intent;

import com.geniusgithub.bcoinmonitor.activity.AboutActivity;
import com.geniusgithub.bcoinmonitor.activity.AdviseActivity;
import com.geniusgithub.bcoinmonitor.activity.ScanActivity;
import com.geniusgithub.bcoinmonitor.activity.SettingActivity;
import com.geniusgithub.bcoinmonitor.activity.ShareActivity;
import com.geniusgithub.bcoinmonitor.activity.setting.WarningActivity;

public class UIHelper {
    public static void openShareInterface(Context context){
        Intent intent = new Intent();
        intent.setClass(context, ShareActivity.class);
        context.startActivity(intent);
    }

    public static void openScanInterface(Context context){
        Intent intent = new Intent();
        intent.setClass(context, ScanActivity.class);
        context.startActivity(intent);
    }

    public static void openSettingInterface(Context context){
        Intent intent = new Intent();
        intent.setClass(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public static void openAdviseInterface(Context context){
        Intent intent = new Intent();
        intent.setClass(context, AdviseActivity.class);
        context.startActivity(intent);
    }

    public static void openAboutInterface(Context context){
        Intent intent = new Intent();
        intent.setClass(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public static void openWarnInterface(Context context){
        Intent intent = new Intent();
        intent.setClass(context, WarningActivity.class);
        context.startActivity(intent);
    }
}
