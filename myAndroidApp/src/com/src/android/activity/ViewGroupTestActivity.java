package com.src.android.activity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.src.android.global.BaseActivity;

public class ViewGroupTestActivity extends BaseActivity {
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		
		LinearLayout contentView = new LinearLayout(this);
		contentView.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));
		
		contentView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				Log.i("leiyong", "parentView onTouch action="+action);
				return false;
			}
		});
		
		
		TextView tv = new TextView(this);
		tv.setText("Helle world");
		tv.setWidth(200);
		contentView.addView(tv);
		
		tv.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action = event.getAction();
				Log.i("leiyong", "childView onTouch action="+action);
				return false;
			}
		});
		
//		tv.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		setContentView(contentView);
	}

}
