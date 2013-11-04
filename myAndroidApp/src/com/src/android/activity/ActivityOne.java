package com.src.android.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActivityOne extends Activity {

	
	private byte[] _bigByte = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Log.i("leiyong", "ActivityOne oncreate");
		super.onCreate(savedInstanceState);

		LinearLayout linearlayout = new LinearLayout(this);
		Button btn = new Button(this);
		btn.setText("启动activityTwo");

		Button btn21 = new Button(this);
		btn21.setText("测试获取图片像素时间");

		linearlayout.addView(btn);
		linearlayout.addView(btn21);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityOne.this, ActivityTwo.class);
				// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				// Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		});

		btn21.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				long start = System.currentTimeMillis();
//				BitmapFactory.Options opts = new BitmapFactory.Options();
//				opts.inJustDecodeBounds = true;

				String sdcard = Environment.getExternalStorageDirectory()
						.getPath();

				String filepath = sdcard
						+ "/lock_thumbnail_2.jpg";
				try {
//					Bitmap bmp = BitmapFactory.decodeFile(filepath, opts);
					Bitmap bmp = BitmapFactory.decodeFile(filepath);
					long end1 = System.currentTimeMillis();
					Log.i("leiyong","time past=" + (end1 - start));
					
					long start2 = System.currentTimeMillis();
//					FileOutputStream fileOutputStream = new FileOutputStream(new File(sdcard + "/lock_thumbnail_2.webp"));
//					bmp.compress(Bitmap.CompressFormat.WEBP, 100, fileOutputStream);
//					Log.i("leiyong","bmp.getWidth()="+opts.outWidth);
//					Log.i("leiyong","bmp.getWidth()="+opts.outHeight);
//					bmp.getWidth();
//					bmp.getHeight();
					long end2 = System.currentTimeMillis();
					Log.i("leiyong","time past=" + (end2 - start2));
					
				} catch (Exception e) {
					Log.i("leiyong", e.getMessage());
				}

				

			}
		});
		
		//byte[] bigByte = new byte[1024*1024*12];
//		File file = new File("mnt/sdcard/IMG_0001.JPG");
//		Bitmap bitmap = null;
//		InputStream inputStream;
//		try {
//			inputStream = new FileInputStream(file);
//			bitmap = BitmapFactory.decodeFile("mnt/sdcard/IMG_0001.JPG");
//			
//			//bitmap = BitmapFactory.decodeStream(inputStream);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		setContentView(linearlayout);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//_bigByte = new byte[1024*1024*12];

//		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//
//		List<ActivityManager.RunningTaskInfo> infolist = manager
//				.getRunningTasks(1);
//		ActivityManager.RunningTaskInfo info = infolist.get(0);
//
//		Log.i("leiyong", "" + info.id);
//		Log.i("leiyong", "" + info.numRunning);
//		Log.i("leiyong", "" + info.numActivities);
//		Log.i("leiyong", info.topActivity.toString());

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);

		Log.i("leiyong", "ActivityOne onNewIntent");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i("leiyong", "ActivityOne onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		_bigByte = null;
//		System.gc();
//		System.runFinalization();
		
		Log.i("leiyong", "ActivityOne onDestroy");
	}

}
