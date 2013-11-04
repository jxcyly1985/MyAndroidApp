package com.src.ui;

import com.src.R;
import java.util.ArrayList;

import com.src.util.XYUtil;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.view.GestureDetector;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ScreenFlipper extends FrameLayout implements GestureDetector.OnGestureListener{

	private GestureDetector mGestureDetector;
	private ArrayList<View>	mViewList;
	private int				mCurrentWhich = 0;
	private Context			mContext;
	
	private final int		FLIPPER_DIRECT_NEXT = 0;
	private final int		FLIPPER_DIRECT_PREVIOUS = 1;
	private final int		SCROLL_DIRECT_NEXT = 0;
	private final int		SCROLL_DIRECT_PREVIOUS = 1;
	
	private Animation		mLeftInAnimation;
	private Animation		mLeftOutAnimation;
	
	private Animation		mRightInAnimation;
	private Animation		mRightOutAnimation;
	
	private boolean			mBooleanPress = false;
	private boolean			mBooleanDragging = false;
	
	
	public ScreenFlipper(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		mContext = context;
		mViewList = new ArrayList<View>();
		mGestureDetector = new GestureDetector(this);
		
		mLeftInAnimation = AnimationUtils.loadAnimation(context, R.anim.flipper_left_in_animation);
		mLeftOutAnimation = AnimationUtils.loadAnimation(context, R.anim.flipper_left_out_animation);
		
		mRightInAnimation = AnimationUtils.loadAnimation(context, R.anim.flipper_right_in_animation);
		mRightOutAnimation = AnimationUtils.loadAnimation(context, R.anim.flipper_right_out_animation);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		Log.e("ScreenFlipper", "onTouchEvent");
		super.onTouchEvent(event);
		boolean consumed =  mGestureDetector.onTouchEvent(event);
		
		if(event.getAction() == MotionEvent.ACTION_UP){
			if(mBooleanPress || mBooleanDragging){
				processTouchActionUp();
			}
		}
		return consumed;
	
	}
	
	public void processTouchActionUp()
	{
		for(View v: mViewList){
			v.scrollTo(0, 0);
		}
			
	}
	
	public void addPage(View v)
	{
		super.addView(v);
		mViewList.add(v);
		if(getChildCount() == 1)
			v.setVisibility(View.VISIBLE);
		else
			v.setVisibility(View.GONE);
	}

	public void showOnly(int which, int direct)
	{
		for(View v: mViewList){
			v.setVisibility(View.GONE);
			if(v.getAnimation() == mLeftInAnimation){
				v.clearAnimation();
			}
		}
		if(direct == FLIPPER_DIRECT_NEXT){
			View CurView = getChildAt(which);
			CurView.setVisibility(View.VISIBLE);
			View PreView = getChildAt(which-1);
			PreView.startAnimation(mRightOutAnimation);
			CurView.startAnimation(mRightInAnimation);
			
		}
		else if(direct == FLIPPER_DIRECT_PREVIOUS){
			View CurView = getChildAt(which);
			CurView.setVisibility(View.VISIBLE);
			View NextView = getChildAt(which+1);
			NextView.startAnimation(mLeftOutAnimation);
			CurView.startAnimation(mLeftInAnimation);
		}
	}
	
	public void ScrollView(int which, int direct, int clapse)
	{
		if(direct == SCROLL_DIRECT_NEXT){
			System.out.println(" ScrollView-->SCROLL_DIRECT_NEXT");
			View CurView = getChildAt(which);
			View NextView = getChildAt(which+1);
			NextView.setVisibility(View.VISIBLE);
			int sw = CurView.getWidth();
			CurView.scrollTo(clapse, 0);
			NextView.scrollTo(-sw+clapse, 0);
		}
		else if(direct == SCROLL_DIRECT_PREVIOUS){
			System.out.println(" ScrollView-->SCROLL_DIRECT_PREVIOUS");
			View CurView = getChildAt(which);
			View PreView = getChildAt(which-1);
			PreView.setVisibility(View.VISIBLE);
			int sw = CurView.getWidth();
			CurView.scrollTo(clapse, 0);
			PreView.scrollTo(sw+clapse, 0);
		}
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.e("ScreenFlipper", "OnGestureListener-onDown");
		
		return true;		//这里必须return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		Log.e("ScreenFlipper", "OnGestureListener-onFling");
		if(e1.getX() - e2.getX() > 120){
			mCurrentWhich += 1;
			if(mCurrentWhich >= mViewList.size())
			{
				mCurrentWhich -= 1;
				XYUtil.showToast(mContext, "已经到最后一页了");
				return false;
			}
			showOnly(mCurrentWhich, FLIPPER_DIRECT_NEXT);
			
		}
		if(e1.getX() - e2.getX() < -120){
			mCurrentWhich -= 1;
			if(mCurrentWhich < 0)
			{
				mCurrentWhich += 1;
				XYUtil.showToast(mContext, "已经到第一页了");
				return false;
			}
			showOnly(mCurrentWhich, FLIPPER_DIRECT_PREVIOUS);
			
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.e("ScreenFlipper", "OnGestureListener-onLongPress");
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		if(e2.getAction() == MotionEvent.ACTION_MOVE){
			int	clapse = 0;
			float ex = e1.getX() - e2.getX();
			Float fo = Float.valueOf(ex);
			clapse = fo.intValue();
			Log.e("ScreenFlipper", "OnGestureListener-onScroll");
			if(e1.getX() - e2.getX() > 0){
				
				System.out.println(" >0 onScroll-->"+Integer.toString(clapse));
				if(mCurrentWhich+1 == mViewList.size())
				{
					mCurrentWhich -= 1;
					XYUtil.showToast(mContext, "已经到最后一页了");
					return false;
				}
				ScrollView(mCurrentWhich, SCROLL_DIRECT_NEXT, clapse);
			}
			else{
				System.out.println(" <0 onScroll-->"+Integer.toString(clapse));
				if(mCurrentWhich == 0)
				{
					mCurrentWhich += 1;
					XYUtil.showToast(mContext, "已经到第一页了");
					return false;
				}
				ScrollView(mCurrentWhich, SCROLL_DIRECT_PREVIOUS, clapse);
			}
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.e("ScreenFlipper", "OnGestureListener-onShowPress");
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.e("ScreenFlipper", "OnGestureListener-onSingleTapUp");
		return false;
	}

}
