package com.src.android.activity;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ActivityThree extends Activity {
	
	private byte[] _bigByte = new byte[1024*1024*12];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		
		Log.i("leiyong", "ActivityThree oncreate");
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		LinearLayout linearlayout = new LinearLayout(this);
		Button btn = new Button(this);
		btn.setText("启动activityTwo");
		linearlayout.addView(btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityThree.this, ActivityTwo.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
		
		setContentView(linearlayout);
			
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
//		ActivityManager manager = (ActivityManager) getSystemService (Context.ACTIVITY_SERVICE);
//				
//		List<ActivityManager.RunningTaskInfo>	infolist = 	manager.getRunningTasks(1);
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
		
		Log.i("leiyong", "ActivityThree onNewIntent");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		Log.i("leiyong", "ActivityThree onStop");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Log.i("leiyong", "ActivityThree onDestroy");
	}
}
