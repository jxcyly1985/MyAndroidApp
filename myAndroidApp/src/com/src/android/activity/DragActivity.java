package com.src.android.activity;

import com.src.R;
import com.src.android.global.BaseActivity;
import com.src.ui.DragView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class DragActivity extends BaseActivity {
	
	private WindowManager	mWindowManager;
	private WindowManager.LayoutParams mLayoutParams;
	
	private ImageView		mDragView;
	private Bitmap		mDragBmp;
	
	private int			mOriginalX;
	private int			mOriginalY;
	
	private int[]		mOriginalLoc = new int[2];
	
	private boolean		mBooleanDrag = false;
	private DragView	mDragitem;
	
	private int		mDrawableWidth = 0;
	private int		mDrawableHeight = 0;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initView();
		
	}
	public void initView()
	{
		mWindowManager = getWindowManager();
		
		setContentView(R.layout.drag_source);
		mDragView = (ImageView)findViewById(R.id.id_drag_image);
		
		//LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);;
		//mDragView = inflater.inflate(R.layout.drag_view, null);
		
		Button TestDragBtn = (Button)findViewById(R.id.test_drag_btn);
		TestDragBtn.setOnClickListener(new View.OnClickListener()
    	{
    		public void onClick(View v)
    		{

    			mDrawableWidth = mDragView.getDrawable().getIntrinsicWidth();
    			mDrawableHeight = mDragView.getDrawable().getIntrinsicHeight();
    			createDragItem(mDragView);
    	    	show(50, 50);
    		}
    	});
    	
    	mDragView.setOnLongClickListener(new LongClickListener());
 
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int tx = (int)event.getX();
		int ty = (int)event.getY();
		System.out.println("onTouchEvent-->X ");
		System.out.println(tx);
		System.out.println("onTouchEvent-->Y ");
		System.out.println(ty);
		if(mBooleanDrag){
			mDragitem.move(tx, ty);
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			if(mBooleanDrag){
				mDragitem.remove();
				mBooleanDrag = false;
			}
		}
	
		return false;
	}
	
	public void show(int touchX, int touchY)
	{
		IBinder windowToken = this.getWindow().getDecorView().getWindowToken();
		
		 WindowManager.LayoutParams lp;
	        int pixelFormat;

	        pixelFormat = PixelFormat.TRANSLUCENT;

	        lp = new WindowManager.LayoutParams(
	                ViewGroup.LayoutParams.WRAP_CONTENT,
	                ViewGroup.LayoutParams.WRAP_CONTENT,
	                touchX, touchY,
	                WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL,
	                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
	                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
	                    /*| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM*/,
	                pixelFormat);
//	        lp.token = mStatusBarView.getWindowToken();
	        lp.gravity = Gravity.LEFT | Gravity.TOP;
	        lp.token = windowToken;
	        lp.setTitle("DragView");
	        mLayoutParams = lp;

	        mWindowManager.addView(mDragitem, lp);
	}
	
    void move(int touchX, int touchY) {
        WindowManager.LayoutParams lp = mLayoutParams;
        lp.x = touchX - 0;
        lp.y = touchY - 0;
        mWindowManager.updateViewLayout(mDragView, lp);
    }
    
    
    /**
     * Draw the view into a bitmap.
     */
    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
    
   private class LongClickListener implements View.OnLongClickListener
   {
	   public boolean onLongClick(View v){
		 
		   createDragItem(v);
		   mBooleanDrag = true;
		   return false;
	   }
   }
   public void createDragItem(View v)
   {
	   mDragBmp = getViewBitmap(v);
	   
	   Button btn = (Button)findViewById(R.id.test_drag_btn);
	   ViewGroup.LayoutParams l2 = btn.getLayoutParams();
	   int w2 = l2.width;
	   int h2 = l2.height;
	   btn.getMeasuredWidth();
	   btn.getWidth();
	   
	   mDragBmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
	   
	   mDrawableWidth = mDragView.getDrawable().getIntrinsicWidth();
	   mDrawableHeight = mDragView.getDrawable().getIntrinsicHeight();
	   
	   mDragView.getMeasuredWidth();
	   mDragView.getWidth();
	   
	   int [] loc = mOriginalLoc;
 	   v.getLocationInWindow(loc);
 	   System.out.println(loc[0]);
 	   System.out.println(loc[1]);
 	   
 	   ViewGroup.LayoutParams lparam = v.getLayoutParams();
 	   
 	   
 	   int w = lparam.width;
 	   int h = lparam.height;
	   v.setVisibility(View.GONE);
	   mDragitem = new DragView(this, mDragBmp, loc[0], loc[1], 0, 0, mDrawableWidth, mDrawableHeight);
	   //startDrag(loc[0], loc[1]);
	   
	   mDragBmp.recycle();
   }
   
   public void startDrag(int x, int y){
	   IBinder windowToken = this.getWindow().getDecorView().getWindowToken();
	   mDragitem.show(windowToken, x, y);
	
   }
}
