package com.geniusgithub.bcoinmonitor.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

public class CommonUtil {

	private static final CommonLog log = LogFactory.createLog();
	
	public static String getMacAdress(Context context){
	    try {  
		       return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
		    } catch (IOException e) {  
		       e.printStackTrace();  
		       log.e("getMacAdress e = " + e.getMessage());
		       return getMac();
		    }  
	} 
	
	public static  String getMac() {
        String macSerial = "";
        String str = "";
        try {
                Process pp = Runtime.getRuntime().exec(
                                "cat /sys/class/net/wlan0/address");
                InputStreamReader ir = new InputStreamReader(pp.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                for (; null != str;) {
                        str = input.readLine();
                        if (str != null) {
                                macSerial = str.trim();// ȥ�ո�
                                break;
                        }
                }
        } catch (IOException ex) {
        	 log.e("getMac e = " + ex.getMessage());
        }
        return macSerial;
	}

	public static String loadFileAsString(String filePath) throws java.io.IOException{  
		StringBuffer fileData = new StringBuffer(1000);  	
		BufferedReader reader = new BufferedReader(new FileReader(filePath));  	
		char[] buf = new char[1024];  
		int numRead=0;  
		while((numRead=reader.read(buf)) != -1){  
			String readData = String.valueOf(buf, 0, numRead);  
			fileData.append(readData);  	
		 }  	
		 
		reader.close();  	
	     return fileData.toString();  
	 }  

	public static boolean isNetworkConnect(Context context) { 
        // ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ��? 
		try { 
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 
			if (connectivity != null) { 
	            // ��ȡ�������ӹ���Ķ��� 
	            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
	            if (info != null&& info.isConnected()) { 
	                // �жϵ�ǰ�����Ƿ��Ѿ�����
	                if (info.getState() == NetworkInfo.State.CONNECTED) { 
	                    return true; 
	                } 
	            } 
			}
         }catch (Exception e) {
	    	e.printStackTrace();
	    }
        return false; 
    } 

	
	public static void showToast(int stringID, Context context){
		Toast.makeText(context, context.getString(stringID), Toast.LENGTH_SHORT).show();
	}
	
	public static void showToast(String  text, Context context){
		Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
	}
	
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		} 
		return true;
	}
	
	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath: /data/data/
		}
	}
	
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}
	
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}
	
	public static String toHexString(int num)
	{
		String string = "0x" + Integer.toHexString(num);
		return string;
	}
	
	public static String getSoftCode(Context context){
		
		 PackageManager manager = context.getPackageManager();
		 PackageInfo info;
		 String version = "010001";
		 try {
				info = manager.getPackageInfo(context.getPackageName(), 0);
				int value  = info.versionCode;
				version = String.valueOf(value);
		 } catch (NameNotFoundException e) {			
				e.printStackTrace();
				log.e("getSoftCode error...");
		}
		
		 return version;
		
	}
	
	public static String getSoftVersion(Context context){
	
		 PackageManager manager = context.getPackageManager();
		 PackageInfo info;
		 String version = "00.00.01";
		 try {
				info = manager.getPackageInfo(context.getPackageName(), 0);
				version  = info.versionName;
		 } catch (NameNotFoundException e) {			
				e.printStackTrace();
		}
		
		 return version;
		
	}

	public static String getOSVersion()
	{
		return android.os.Build.VERSION.RELEASE;
	}
	
	public static String getDeviceManufacturer()
	{
		return android.os.Build.MANUFACTURER;
	}
	
	public static String getDeviceModel()
	{
		return android.os.Build.MODEL;
	}
	
	public static String getIMSI(Context context){
		 TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
		 String IMSI = telephonyManager.getSubscriberId();  
		 if (IMSI == null){
			 IMSI = "";
		 }
		 return IMSI;
	}
	
	public static String getProvidersName(Context context) {  

		 String ProvidersName = "";  
		 
		 TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
		 
		 // ����Ψһ���û�ID;�������ſ��ı�������  
		 String IMSI = telephonyManager.getSubscriberId();  
		 if (IMSI == null){
			 ProvidersName = "";
			 return "";
		 }
		 
		 // IMSI��ǰ��3λ460�ǹ�ң������ź���2λ00 02���й��ƶ���01���й���ͨ��03���й���š�  
		 if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {  
			 	ProvidersName = "�й��ƶ�";  
		 } else if (IMSI.startsWith("46001")) {  	
		     	ProvidersName = "�й���ͨ";  
		 } else if (IMSI.startsWith("46003")) {  
			 	ProvidersName = "�й����";  	
		 }  
	
		 return ProvidersName;  
      }  
	
	public static String getScreeenSize(Context context){
		int width = getScreenWidth(context);
		int height = getScreenHeight(context);
		String value = String.valueOf(width) + "*" + String.valueOf(height);
		return value;
	}
}
