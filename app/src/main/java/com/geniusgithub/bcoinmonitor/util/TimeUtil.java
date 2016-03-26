package com.geniusgithub.bcoinmonitor.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {


	public static String getTimeShort(long time) {
	  SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	  Date currentTime = new Date(time);
	  String dateString = formatter.format(currentTime);
	  return dateString;
	}

	public static String getTimeLong(long time) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
		Date currentTime = new Date(time);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getTimeShort(String time) {
		  long times = Long.valueOf(time);
		  SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		  Date currentTime = new Date(times);
		  String dateString = formatter.format(currentTime);
		  return dateString;
	}
}
