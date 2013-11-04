package com.src.android.activity;

import com.src.ui.ScreenFlipper;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class FlipperActivity extends Activity {

	private ScreenFlipper 	mflipper;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mflipper = new ScreenFlipper(this);
		TextView	tv1 = new TextView(this);
		TextView	tv2 = new TextView(this);
		TextView	tv3 = new TextView(this);
		
		mflipper.setLayoutParams(new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.FILL_PARENT, 
								FrameLayout.LayoutParams.FILL_PARENT));
		tv1.setLayoutParams(new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.FILL_PARENT, 
								FrameLayout.LayoutParams.FILL_PARENT));
		tv2.setLayoutParams(new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.FILL_PARENT, 
								FrameLayout.LayoutParams.FILL_PARENT));
		tv3.setLayoutParams(new FrameLayout.LayoutParams(
								FrameLayout.LayoutParams.FILL_PARENT, 
								FrameLayout.LayoutParams.FILL_PARENT));
		
		LinearLayout l1 = new LinearLayout(this);
		LinearLayout l2 = new LinearLayout(this);
		LinearLayout l3 = new LinearLayout(this);
		l1.addView(tv1,new LinearLayout.LayoutParams(   
                    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		l2.addView(tv2,new LinearLayout.LayoutParams(   
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		l3.addView(tv3,new LinearLayout.LayoutParams(   
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		tv1.setBackgroundColor(Color.RED);
		tv2.setBackgroundColor(Color.GREEN);
		tv3.setBackgroundColor(Color.BLUE);
		
		mflipper.addPage(l1);
		mflipper.addPage(l2);
		mflipper.addPage(l3);
		
		setContentView(mflipper);
	}
	
	@Override 
	public boolean onTouchEvent(MotionEvent event)
	{
		return false;
	}
	
	
}
