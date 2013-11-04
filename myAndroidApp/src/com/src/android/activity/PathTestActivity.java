package com.src.android.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

public class PathTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		File f = null;
//		File f = getDir("hello", Context.MODE_PRIVATE);
//		System.out.println("dir="+f.getAbsolutePath());
//		f = getFilesDir();
//		System.out.println("filedir="+f.getAbsoluteFile());
//		//f = getExternalCacheDir();
//		if(f != null){
//			System.out.println("ext cache dir="+ f.getAbsolutePath());
//		}
//			
//		//f = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//		if(f != null){
//			System.out.println("ext file dir="+f.getAbsolutePath());
//		}
//		
//		//f = getExternalFilesDir(null);
//		if(f != null){
//			System.out.println("ext file dir="+f.getAbsolutePath());
//		}
		f = getFileStreamPath("portrait_crc.dat");
		
		//不能随便调用mkdirs
		//f.mkdirs();
		f.mkdir(); //创建出来的是文件夹
		
		try {
			FileOutputStream outputStrem = new FileOutputStream(f);
			String test = "hello world";
			byte[] array = test.getBytes();
			try {
				outputStrem.write(array);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(f != null){
			System.out.println("file stream path="+f.getAbsolutePath());
		}
		System.out.println("file stream path="+f.getAbsolutePath());
		f = getDatabasePath("mydatabase");
		if(f != null){
			System.out.println("database path="+f.getAbsolutePath());
		}
		
		/**
		 * 	dir=/data/data/com.src/app_hello
			filedir=/data/data/com.src/files
			ext cache dir=/mnt/sdcard/Android/data/com.src/cache
 			ext file dir=/mnt/sdcard/Android/data/com.src/files/Pictures
 			file stream path=/data/data/com.src/files/portrait_crc.dat
 			database path=/data/data/com.src/databases/mydatabase

		 */
		
	}
}
