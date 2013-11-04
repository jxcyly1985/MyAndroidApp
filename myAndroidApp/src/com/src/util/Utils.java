package com.src.util;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

public class Utils {
	
	static boolean mExternalStorageAvailable = false;
	static boolean mExternalStorageWriteable = false;

	//一次请求栏目下文章列表的数目默认值是10条
	public static final String _requestNumber = "10";	
	
	public static String getImageCachePath(){
			
		/**
		 * 本地保存路径规则
		 *	=/mnt/sdcard/com.richinfo.subscribe/cache/image/uuid(.jpg)
		 */
		String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        mExternalStorageAvailable = mExternalStorageWriteable = true;
	    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        mExternalStorageAvailable = true;
	        mExternalStorageWriteable = false;
	    } else {
	        mExternalStorageAvailable = mExternalStorageWriteable = false;
	    }

		File f = Environment.getExternalStorageDirectory();
		String tmp = f.getAbsolutePath();
		String path = tmp + "/com.richinfo.subscribe/cache/image/";
		File cachef = new File(path);
		if(!cachef.exists()){
			cachef.mkdirs();
		}
		return cachef.getAbsolutePath();
	}
	
	public static String getHtmlCachePath(){
		
		/**
		 * 本地保存路径规则
		 *	=/mnt/sdcard/com.richinfo.subscribe/cache/html/uuid(.html)
		 */
		String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        mExternalStorageAvailable = mExternalStorageWriteable = true;
	    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        mExternalStorageAvailable = true;
	        mExternalStorageWriteable = false;
	    } else {
	        mExternalStorageAvailable = mExternalStorageWriteable = false;
	    }

		File f = Environment.getExternalStorageDirectory();
		String tmp = f.getAbsolutePath();
		String path = tmp + "/com.richinfo.subscribe/cache/html/";
		File cachef = new File(path);
		if(!cachef.exists()){
			cachef.mkdirs();
		}
		return cachef.getAbsolutePath();
	}
	
	public static String getExternalName(String filename, String extdef){
		if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');

            if ((i >-1) && (i < (filename.length() - 1))) {
                return filename.substring(i + 1);
            }
        }
        return extdef; 
	}
	
	
	
	
}
