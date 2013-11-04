package com.src.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


/**
 * Name: FileUtils
 * Description:	文件的一些公共处理
 * @author 
 *
 */
public class FileUtils {
	
	public static final String IMG_LOACL_CACHE = "sdcard/com.lemon.myApp/img-cache/";
	public static final String CONTENT_LOCAL_CACHE = "sdcard/com.lemon.myApp/content-cache/";
	
	public static boolean checkExists(String path){
		
		
		return false;
	}
	
	public static InputStream getInputStream(String path) throws IOException{
		InputStream input = null;
		URL url = new URL(path);
		HttpURLConnection connect = (HttpURLConnection)url.openConnection();
		input = connect.getInputStream();
		return input;
	}
	
	public static void cleanEmptyFile(){
		File file = new File(IMG_LOACL_CACHE);
		if (file.exists() && file.isDirectory()) {
			String fileNames[] = file.list();
			if (fileNames != null && fileNames.length > 0) {
				for (String fileName : fileNames) {
					InputStream inputStream = null;
					try {
						File fileTemp = new File(IMG_LOACL_CACHE + fileName);
						inputStream = new FileInputStream(fileTemp);
						if (inputStream.available() == 0) {
							inputStream.close();
							fileTemp.delete();
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (inputStream != null) {
							try {
								inputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	
	
	
	//--------------图片文件相关操作-----------------------
	/**
	 * 从本地加载图片
	 * @param path
	 * @return
	 */
	public static Drawable loadImgFromLocal(String path){
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO  Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}
	/**
	 * 保存图片到本地
	 * @param path
	 */
	public static void saveImgToLocal(String path){
		
	}
	
	/**
	 * 保存图片到本地
	 * @param path
	 */
	public static void saveImgToLocal(String path, Drawable drawable) throws IOException{
		
	}

}
