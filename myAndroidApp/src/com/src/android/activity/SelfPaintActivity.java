package com.src.android.activity;

import com.src.R;
import com.src.ui.AnimationSwitchTab;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class SelfPaintActivity extends Activity{ //implements OnGestureListener {

	private GestureDetector _detector;
	private ViewFlipper _flipper;

	private boolean _next;
	
	private AnimationSwitchTab _switchTab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
//		LinearLayout container = new LinearLayout(this);
//		container.setLayoutParams(new FrameLayout.LayoutParams(
//				 FrameLayout.LayoutParams.WRAP_CONTENT,
//				 FrameLayout.LayoutParams.WRAP_CONTENT));
//		
//		 LemonTabCtrl ctrl = new LemonTabCtrl(this);
//		 ctrl.setLayoutParams(new LinearLayout.LayoutParams(
//				 LinearLayout.LayoutParams.WRAP_CONTENT,
//				 LinearLayout.LayoutParams.WRAP_CONTENT));
//		 ctrl.setOrientation(LinearLayout.HORIZONTAL);
//		 ctrl.setBackgroundResource(R.drawable.bg_tab);
//		 
//		 LinearLayout ctrl2 = new LinearLayout(this);
//		 LinearLayout ctrl3 = new LinearLayout(this);
//		 ctrl2.setLayoutParams(new LinearLayout.LayoutParams(
//				 LinearLayout.LayoutParams.FILL_PARENT,
//				 LinearLayout.LayoutParams.FILL_PARENT));
//		 ctrl3.setLayoutParams(new LinearLayout.LayoutParams(
//				 LinearLayout.LayoutParams.FILL_PARENT,
//				 LinearLayout.LayoutParams.FILL_PARENT));
//		 
//		 Button btn1 = new Button(this);
//		 Button btn2 = new Button(this);
//		 
//		 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//				 LinearLayout.LayoutParams.FILL_PARENT,
//				 LinearLayout.LayoutParams.FILL_PARENT);
//		 lp.weight = 1;
//		 
//		 LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
//				 LinearLayout.LayoutParams.WRAP_CONTENT,
//				 LinearLayout.LayoutParams.WRAP_CONTENT);
//	
//		 
//		 ctrl2.setLayoutParams(lp);
//		 ctrl3.setLayoutParams(lp);
//		 
//		 btn1.setLayoutParams(lp2);
//		 btn2.setLayoutParams(lp2);
//		 
//		 ctrl2.addView(btn1);
//		 ctrl3.addView(btn2);
//		 
//		 ctrl.addView(ctrl2);
//		 ctrl.addView(ctrl3);
		 
		 

		 
//		 btn1.setBackgroundResource(R.drawable.ic_tab_select);
//		 btn2.setBackgroundResource(R.drawable.ic_tab_select);
//		 btn1.setText("资讯xxxxxx");
//		 btn2.setText("杂志");
		 
//		 ctrl.addView(btn1);
//		 ctrl.addView(btn2);
		 
//		 container.addView(ctrl);
		 
//		 setContentView(container);
		 
//		setContentView(R.layout.view_flipper_layout);
//		_detector = new GestureDetector(this);
//		_flipper = (ViewFlipper) findViewById(R.id.id_flipper);
		
		
		setContentView(R.layout.use_switch_tab_layout);
		
		_switchTab = (AnimationSwitchTab) findViewById(R.id.id_switch_tab);
		_switchTab.create();
		_switchTab.setLeftTabName("资讯");
		_switchTab.setRightTabName("杂志");
		AnimationSwitchTab.TabSwitchListener listener = new AnimationSwitchTab.TabSwitchListener(){

			@Override
			public void toLeft() {
				
			}

			@Override
			public void toRight() {
				
			}
			
		};
		
		_switchTab.setTabSwitchListener(listener);
		
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
//		if(_detector.onTouchEvent(event)){
//			return true;
//		}
		
		return super.onTouchEvent(event);
	}
	
	private class LemonTabCtrl extends LinearLayout implements OnGestureListener{
		
		public LemonTabCtrl(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

//		@Override
//		protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//			int childCount = getChildCount();
//			int childLeft = 0;
//			for (int i = 0; i < childCount; ++i) {
//				View child = getChildAt(i);
//				int childWidth = child.getMeasuredWidth();
//				int childHeight = child.getMeasuredHeight();
//
//				child.layout(0, 0, childLeft + childWidth, childHeight);
//				childLeft += childWidth;
//			}
//
//		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			return super.onTouchEvent(event);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
						
			return false;
		}

	}

//	@Override
//	public boolean onDown(MotionEvent e) {
//		return false;
//	}
//
//	@Override
//	public void onShowPress(MotionEvent e) {
//	}
//
//	@Override
//	public boolean onSingleTapUp(MotionEvent e) {
//		return false;
//	}
//
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//			float distanceY) {
//		return false;
//	}
//
//	@Override
//	public void onLongPress(MotionEvent e) {
//	}
//
//	@Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//			float velocityY) {
//		
//		// 可以设置动画效果
//		if(!_next){
//			_flipper.showNext();
//			_next = true;
//		}
//		else{
//			_flipper.showPrevious();
//			_next = false;
//		}
//			
//		return false;
//	}

}
