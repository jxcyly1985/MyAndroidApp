package com.src.ui;

import com.src.ui.AnimationSwitchTab.TabSwitchListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Button;

public class FlipButton extends Button implements OnGestureListener{
	
	private GestureDetector _detector;
	private TabSwitchListener _listener;
	
	public FlipButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public FlipButton(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public FlipButton(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
	}

	public void create(TabSwitchListener listener){
		_detector = new  GestureDetector(this);
		_listener = listener;
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
		
		int distanceX = Math.abs((int) e2.getX() - (int) e1.getX());
		int distanceY = Math.abs((int) e2.getY() - (int) e1.getY());
		if (distanceX < 100 || distanceX < distanceY) {
			// 限制滑动角度，当 distanceX < distanceY时
			// ，就证明角度太大，可以归并为是垂直滑动不是水平滑动
			return false;
		}
		
		if (velocityX > 500) { 
			
			_listener.toRight();
			return true;
			
		} else if (velocityX < -500) { 
			_listener.toLeft();
			return true;
		}

		
					
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(_detector.onTouchEvent(event)){
			return true;
		}
		
		return super.onTouchEvent(event);
	}
	
}