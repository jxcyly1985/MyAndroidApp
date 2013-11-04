package com.src.android.activity;

import java.io.File;

import com.src.service.XRemoteService;

import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleFlipperActivity extends Activity {
	
	private Messenger mMessenger;
	
	private LinearLayout	mlayout;
	private TextView _tv;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mlayout = new LinearLayout(this);
		mlayout.setOrientation(LinearLayout.VERTICAL );
		mlayout.setLayoutParams(new ViewGroup.LayoutParams
				(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		
		_tv = new TextView(this);
		_tv.setLayoutParams(new ViewGroup.LayoutParams
				(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		_tv.setBackgroundColor(Color.GRAY);
		_tv.setTextSize(20);
		_tv.setText("Hello World!");
		mlayout.addView(_tv);
			
		setContentView(mlayout);
		

	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	};
	
	
	@Override 
	public boolean onTouchEvent(MotionEvent event)
	{
//		if(event.getAction() == MotionEvent.ACTION_MOVE){
//			int x = (int)event.getX();
//			int y = (int)event.getY();
//			System.out.println("SimpleFlipperActivity onTouchEvent--->"+Integer.toString(x));
//			mlayout.scrollTo(x, 0);
//		}
		
		if(event.getAction() == MotionEvent.ACTION_UP){
			
			TranslateAnimation Anim = new TranslateAnimation(100, 300, 100,500);
			Anim.setDuration(1000);
			_tv.setAnimation(Anim);
			
			_tv.startAnimation(_tv.getAnimation());
		}
		return false;
	}
}
