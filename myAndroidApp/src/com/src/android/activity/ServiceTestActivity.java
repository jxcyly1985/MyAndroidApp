package com.src.android.activity;

import com.src.service.XRemoteService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ServiceTestActivity extends Activity {
	
	private LinearLayout	mlayout;
	private Messenger	mMessenger;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mlayout = new LinearLayout(this);
		mlayout.setOrientation(LinearLayout.VERTICAL );
		mlayout.setLayoutParams(new ViewGroup.LayoutParams
				(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		
		TextView tv = new TextView(this);
		tv.setLayoutParams(new ViewGroup.LayoutParams
				(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		tv.setBackgroundColor(Color.GRAY);
		tv.setTextSize(20);
		tv.setText("Hello World!");
		mlayout.addView(tv);
		
		Button btn1 = new Button(this);
		btn1.setText("开启服务");
		btn1.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				startRemoteService();
			}
		});
		
		Button btn2 = new Button(this);
		btn2.setText("关闭闭服务");
		btn2.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				stopRemoteService();
			}
		});
		
		Button btn3 = new Button(this);
		btn3.setText("绑定服务器");
		btn3.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				bindRemoteService();
			}
		});
		
		Button btn4 = new Button(this);
		btn4.setText("解绑服务");
		btn4.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				unBindRemoteService();
			}
		});
		mlayout.addView(btn1);
		mlayout.addView(btn2);
		mlayout.addView(btn3);
		mlayout.addView(btn4);
		
		
		setContentView(mlayout);
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		unBindRemoteService();
	}
	
	private ServiceConnection mConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
			mMessenger = new Messenger(service);
			
			Message msg = Message.obtain(null,                   
					 XRemoteService.MSG_REGISTER_CLIENT);            
			msg.replyTo = mMessenger;            
			try {
				mMessenger.send(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.i("leiyong","onServiceConnected");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			Log.i("leiyong","onServiceDisconnected");
			
			Message msg = Message.obtain(null,                   
					 XRemoteService.MSG_UNREGISTER_CLIENT);            
			msg.replyTo = mMessenger;            
			try {
				mMessenger.send(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
	
	public void startRemoteService(){
		Log.i("leiyong","startRemoteService");
		startService(new Intent("com.src.android.service.xremoteservice"));	
		
	}
	public void stopRemoteService(){
		Log.i("leiyong","stopRemoteService");
		stopService(new Intent("com.src.android.service.xremoteservice") );
			
	}
	
	public void bindRemoteService(){
		Log.i("leiyong","bindRemoteService");
		bindService(new Intent("com.src.android.service.xremoteservice"), mConnection, 
				BIND_AUTO_CREATE);
	}
	public void unBindRemoteService(){
		Log.i("leiyong","unBindRemoteService");
		if(mConnection != null){
			unbindService(mConnection);
		}
		
	}
}
