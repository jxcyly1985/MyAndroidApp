package com.src.android.activity;

import java.io.File;
import java.io.IOException;


import com.src.R;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class AndroidApp extends Activity {
	
	private Button	mDialerBtn;
	private Button	mStartAnimBtn;
	
	private View	mAnimationView;
	private Animation	mDismissAnim;
	private Animation 	mShowAnim;
	private LinearLayout mAnimationLL;
	
	private int[]	mDialerBtnLoc = new int[2];
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try{
        	this.initRes();
        }
        catch(IOException e)
        {
        	
        }
        this.initView();
    }
    public void initView()
    {
    	
    	NotificationManager mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	Notification mNif = new Notification(android.R.drawable.ic_dialog_alert,"������ϣ�����ʧ��",System.currentTimeMillis());
		Intent intent = new Intent(this, AndroidApp.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent appIntent = PendingIntent.getActivity(this, 0, intent, 0);
		mNif.contentIntent = appIntent;
		mNif.setLatestEventInfo(this, "����",  "û�� remote view ����?��", appIntent);
		mNM.notify(1, mNif);
    	
    	mAnimationView = LayoutInflater.from(this).inflate(R.layout.desktop_contact_action, null);
    	mAnimationView.setOnTouchListener(new View.OnTouchListener()
    	{

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP){
					System.out.println("mAnimationView MotionEvent.ACTION_UP");
					getWindowManager().removeView(mAnimationView);
				}
				if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
					System.out.println("mAnimationView MotionEvent.ACTION_OUTSIDE");
					getWindowManager().removeView(mAnimationView);
				}
				return false;
			}

    	});
    	//mAnimationView = findViewById(R.id.id_animation_ll);
    	//mAnimationLL = (LinearLayout)findViewById(R.id.id_ll_for_animation);
    	mDismissAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
    	mShowAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
    	//mAnimationLL.addView(mAnimationView);
    	
    	
    	mDialerBtn = (Button)findViewById(R.id.dialer_btn);
    	mDialerBtn.setOnClickListener(new View.OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			/*
    			Intent intent = new Intent();
    			intent.setAction(Intent.ACTION_DIAL);
    			intent.setData(Uri.parse("tel:15920523505"));
    			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			startActivity(intent);
    			*/
    			//Intent intent = new Intent(AndroidApp.this, SimpleFlipperActivity.class);
    			int [] loc = mDialerBtnLoc;
    	    	mDialerBtn.getLocationOnScreen(loc);
    	    	System.out.println("DialerBtn Location-->");
    	    	System.out.println(loc[0]);
    	    	System.out.println("<---");
    	    	System.out.println(loc[1]);
    	    	System.out.println("<--");
    			
    			Intent intent = new Intent(AndroidApp.this, FlipperActivity.class);
    	    	//Intent intent = new Intent(AndroidApp.this, DragActivity.class);
    			startActivity(intent);
    			
    			
    		}
    	});
    	
    	mStartAnimBtn = (Button)findViewById(R.id.id_start_animation);
    	mStartAnimBtn.setOnClickListener(new View.OnClickListener()
    	{
    		public void onClick(View v)
     		{
    			WindowManager.LayoutParams lp;
    	        int pixelFormat;

    	        pixelFormat = PixelFormat.TRANSLUCENT;

    	        lp = new WindowManager.LayoutParams(
    	                ViewGroup.LayoutParams.WRAP_CONTENT,
    	                ViewGroup.LayoutParams.WRAP_CONTENT,
    	                0, 0,
    	                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
    	                0
    	                    /*| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM*/,
    	                pixelFormat);
    	        
    	        lp.gravity = Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL;
    	        lp.token = getWindow().getDecorView().getWindowToken();
    	        lp.windowAnimations = R.style.desktop_popuwindow;
    	        
    	        //lp.setTitle("DragView");
    	        
    	        //mAnimationView.startAnimation(mShowAnim);
    	        
    			System.out.println("onClick Animation-->mAnimationView");
    			getWindowManager().addView(mAnimationView, lp);	
    			
    			//mAnimationView.startAnimation(mShowAnim);
    			//mAnimationView.requestLayout();
    			
    			//getWindowManager().removeView(mAnimationView);
    			
//   			mAnimationLL.startAnimation(mDismissAnim);
    		}
    	});
    	int [] loc = mDialerBtnLoc;
    	mDialerBtn.getLocationOnScreen(loc);
    	System.out.println("DialerBtn Location-->");
    	System.out.println(loc[0]);
    	System.out.println("<---");
    	System.out.println(loc[1]);
    	System.out.println("<--");
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
    	
    	System.out.println("onTouchEvent X--->");
    	System.out.println(event.getX());
    	System.out.println("<-->");
    	System.out.println(event.getRawX());
    	System.out.println("onTouchEvent Y--->");
    	System.out.println(event.getY());
    	System.out.println("<-->");
    	System.out.println(event.getRawY());
    	System.out.println("<--");
    	return super.onTouchEvent(event);

    }
    
    public void initRes() throws IOException
    {
    	AssetManager am = getAssets();
    	String[] localS = am.getLocales();
    	String fp = getPackageResourcePath();
    	File fpl = getCacheDir();
    	fp = getPackageResourcePath();
    	fpl = getFilesDir(); 
    	//fpl = getExternalCacheDir();
    	fpl = getDir("myDir", MODE_PRIVATE);
    	fpl = getFileStreamPath("myFile");
    	String[] fl = am.list(fp);
    	for(String str : localS)
    	{
    		System.out.println("Locales-->"+str);
    		Log.i("Locales-->", str);
    	}
    	for(String str1: fl)
    	{
    		System.out.println("ListAssetPath-->"+str1);
    		Log.i("ListAssetPath-->", str1);
    	}
    }
}