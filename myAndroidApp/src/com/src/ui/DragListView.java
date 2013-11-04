package com.src.ui;

/**
 * Title: 拖拽排序列表
 * Description: 拖拽排序列表
 * Copyright: Copyright (c) 2008
 * Company:深圳彩讯科技有限公司
 *
 * @author 
 * @version 1.0
 */
import com.src.android.adapter.DragAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;



public class DragListView extends ListView {
	private ImageView dragImageView;
	private int dragPosition;
	private int dragPoint;
	private int dragOffset;
	private WindowManager windowManager;
	private WindowManager.LayoutParams windowParams;
	private int upScrollBounce;
	private int downScrollBounce;
	private int dragImageId;
	private int itemHeight;
	private int dragColor = -65536;
	private int moveHeight = 0;

	private int changePosition = -1;

	private long scrollDelayMillis = 10L;

	private RefreshHandler scrollDelayUp = new RefreshHandler(
			Looper.getMainLooper(), true);

	private RefreshHandler scrollDelayDown = new RefreshHandler(
			Looper.getMainLooper(), false);
	private int middleX;
	private int middleY;
	private int scrollHeight = 4;

	private int maxSH = 20;

	private int minSH = 4;

	private boolean isScroll = false;
	private DragListener dragListener;

	private int dragedItemIndex = -1; // 被拖拽的item下标

	public void setMoveHeight(int height) {
		this.moveHeight = height;
	}

	public void setDragColor(int color) {
		this.dragColor = color;
	}

	public void setDragImageId(int id) {
		this.dragImageId = id;
	}

	public DragListView(Context context, DragListener dragListener) {
		super(context);
		this.dragListener = dragListener;
	}

	public interface DragListener {
		/**
		 * Name:    
		 * Description: 拖拽监听 
		 * Author:    
		 * @param currentColumnIndex 当前被拖拽的Item
		 * @param ultimateIndex 被最终拖拽到的位置
		 * @return   
		 *
		 */
		void onDrag(int currentColumnIndex, int ultimateIndex);
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == 0) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			this.dragPosition = pointToPosition(x, y);
			if (dragPosition >= getAdapter().getCount()) { // 最后一个添加栏目的item不能被拖动
				return false;
			}
			if (this.dragPosition == -1) {
				return super.onInterceptTouchEvent(ev);
			}

			ViewGroup itemView = null;
			try {
				itemView = (ViewGroup) getChildAt(this.dragPosition
						- getFirstVisiblePosition());
			} catch (Exception e) {
				return false; // 点击到最后一个item
			}
			this.itemHeight = itemView.getHeight();
			this.dragPoint = (y - itemView.getTop());
			this.dragOffset = (int) (ev.getRawY() - y);
			View dragger = itemView.findViewById(this.dragImageId);
			if ((dragger != null) && (x > dragger.getLeft())
					&& (x < dragger.getWidth() + dragger.getLeft())) {
				if ((this.moveHeight <= 0)
						|| (this.moveHeight >= getHeight() / 2)) {
					this.upScrollBounce = (getHeight() / 3);
					this.downScrollBounce = (getHeight() * 2 / 3);
				} else {
					this.upScrollBounce = this.moveHeight;
					this.downScrollBounce = (getHeight() - this.moveHeight);
				}

				itemView.setDrawingCacheEnabled(true);
				Drawable background = itemView.getBackground();
				itemView.setBackgroundColor(this.dragColor);
				Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
				itemView.setBackgroundDrawable(background);
				startDrag(bm, y);
			}
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}

	public boolean onTouchEvent(MotionEvent ev) {
		if ((this.dragImageView != null) && (this.dragPosition != -1)) {
			int action = ev.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				dragedItemIndex = -1;
				break;
			case MotionEvent.ACTION_UP:
				stopDrag();
				onDrop();
				break;
			case MotionEvent.ACTION_MOVE:
				int moveY = (int) ev.getY();
				int moveX = (int) ev.getX();
				this.middleX = moveX;
				if (moveY <= 0)
					this.middleY = 0;
				else if (moveY >= getHeight())
					this.middleY = getHeight();
				else {
					this.middleY = moveY;
				}
				dragPositionChanged();
				onDrag(moveY);
				break;
			}

			return true;
		}
		return super.onTouchEvent(ev);
	}

	private void dragPositionChanged() {
		DragAdapter adapter = (DragAdapter) getAdapter();
		if (this.changePosition != this.dragPosition) {
			if (this.changePosition == -1) {
				this.changePosition = this.dragPosition;
				adapter.setFlag(this.changePosition, 1);
				return;
			}

			adapter.swap(this.changePosition, this.dragPosition);
			if (dragedItemIndex == -1) {
				dragedItemIndex = changePosition;
			}
			this.changePosition = this.dragPosition;
		}
	}

	private void startDrag(Bitmap bm, int y) {
		stopDrag();

		this.windowParams = new WindowManager.LayoutParams();
		this.windowParams.gravity = 48;
		this.windowParams.x = 0;
		this.windowParams.y = (y - this.dragPoint + this.dragOffset);
		this.windowParams.width = -2;
		this.windowParams.height = -2;
		this.windowParams.flags = 408;

		this.windowParams.format = -3;
		this.windowParams.windowAnimations = 0;

		ImageView imageView = new ImageView(getContext());
		imageView.setImageBitmap(bm);
		this.windowManager = ((WindowManager) getContext().getSystemService(
				"window"));
		this.windowManager.addView(imageView, this.windowParams);
		this.dragImageView = imageView;
	}

	private void stopDrag() {
		if (this.dragImageView != null) {
			this.windowManager.removeView(this.dragImageView);
			this.dragImageView = null;
		}
		this.changePosition = -1;
		this.isScroll = false;
	}

	private void onDrag(int y) {
		if (this.dragImageView != null) {
			this.windowParams.alpha = 0.8F;

			if (this.middleY - this.dragPoint <= 0)
				this.windowParams.y = this.dragOffset;
			else if (this.middleY - this.dragPoint >= getHeight()
					- this.itemHeight)
				this.windowParams.y = (getHeight() - this.itemHeight + this.dragOffset);
			else {
				this.windowParams.y = (this.middleY - this.dragPoint + this.dragOffset);
			}

			this.windowManager.updateViewLayout(this.dragImageView,
					this.windowParams);
		}

		int tempPosition = pointToPosition(this.middleX, this.middleY);
		if (tempPosition != -1) {
			this.dragPosition = tempPosition;
		}

		if ((y >= this.upScrollBounce) && (y <= this.downScrollBounce)) {
			this.isScroll = false;
			return;
		}

		if (y < this.upScrollBounce) {
			float a = this.upScrollBounce - this.middleY;
			float b = this.upScrollBounce;
			float c = a / b;
			this.scrollHeight = (int) (c * (this.maxSH - this.minSH) + this.minSH);
			this.isScroll = true;
			this.scrollDelayUp.sleep(0L);
		} else if (y > this.downScrollBounce) {
			float a = this.middleY - this.downScrollBounce;
			float b = this.upScrollBounce;
			float c = a / b;
			this.scrollHeight = (int) (c * (this.maxSH - this.minSH) + this.minSH);
			this.isScroll = true;
			this.scrollDelayDown.sleep(0L);
		}
	}

	private void onDrop() {
		dragListener.onDrag(dragedItemIndex, dragPosition);
		DragAdapter adapter = (DragAdapter) getAdapter();
		adapter.reFlag();
	}

	public void setMaxSH(int sh) {
		this.maxSH = sh;
	}

	public void setMinSH(int sh) {
		this.minSH = sh;
	}

	private void actDown() {
		int tempPosition = pointToPosition(this.middleX, this.middleY);
		if (tempPosition != -1) {
			this.dragPosition = tempPosition;
		}
		dragPositionChanged();
		setSelectionFromTop(this.dragPosition,
				getChildAt(this.dragPosition - getFirstVisiblePosition())
						.getTop() - this.scrollHeight);
	}

	private void actUp() {
		int tempPosition = pointToPosition(this.middleX, this.middleY);
		if (tempPosition != -1) {
			this.dragPosition = tempPosition;
		}
		dragPositionChanged();
		setSelectionFromTop(this.dragPosition,
				getChildAt(this.dragPosition - getFirstVisiblePosition())
						.getTop() + this.scrollHeight);
	}

	class RefreshHandler extends Handler {
		boolean isUp;

		public RefreshHandler(Looper looper, boolean isUp) {
			super();
			this.isUp = isUp;
		}

		public RefreshHandler(Looper l) {
			super();
		}

		public void handleMessage(Message msg) {
			if (DragListView.this.isScroll) {
				if (this.isUp)
					DragListView.this.actUp();
				else {
					DragListView.this.actDown();
				}
				sleep(DragListView.this.scrollDelayMillis);
			}
		}

		public void sleep(long delayMillis) {
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	}
}
