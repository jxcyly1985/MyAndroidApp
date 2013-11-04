package com.src.service;

import com.src.android.ActivitySelector;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import android.os.Message;
import android.os.Messenger;
import android.util.Log;


public class XRemoteService extends Service {

	private IBinder	mBinder;
	private Messenger mMegr;
	private Handler mHandler = new Handler();
	
	private boolean _break = false;
	
	public final static int MSG_REGISTER_CLIENT = 0x1;
	public final static int MSG_UNREGISTER_CLIENT = 0x2;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i("leiyong","XRemoteService onBind");
		return mMegr.getBinder();			//remoteͨ��messenger����
	}
	

	@Override 
	public void onCreate()
	{
		Log.i("leiyong", "XRemoteService onCreate");
		
		mBinder = new XBinder();
		mMegr = new Messenger(messengerHandler);	//remote��Ҫͨ��messenger������handler
//		new Thread(){
//			public void run() {
//				for(int i=0; i < 1000; ++i){
//					if(_break){
//						break;
//					}
//					Log.i("XRemoteService", ActivitySelector.globalString);
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				
//			};
//		}.start();
		
//		mHandler.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				popDialog();
//			}
//			
//		}, 1000);
		
	}
	
	public void popDialog(){
		AlertDialog.Builder builder_about = new AlertDialog.Builder(getApplicationContext());
		builder_about.setTitle("测试")
		       .setMessage("来自Service")
		       .setPositiveButton("确定",null)
		       .create().show();
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		System.out.println("XRemoteService onStartCommand");
		
		//popDialog();
		
		Log.i("leiyong","XRemoteService onStartCommand");
		return 0;
		
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		_break = true;
		
		Log.i("leiyong","XRemoteService onDestroy");
	}
	private Handler messengerHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MSG_REGISTER_CLIENT:{
				System.out.println("MSG_REGISTER_CLIENT");
				break;}
			case MSG_UNREGISTER_CLIENT:{
				System.out.println("MSG_UNREGISTER_CLIENT");
				break;}
			default:{
				System.out.println("MSG NOT ACCEPT");
				super.handleMessage(msg);}
			}
		}
	};
	
	//��remote��ʽֱ��ͨ��binder���ؼ���
	private class XBinder extends Binder
	{
		
	}
}
