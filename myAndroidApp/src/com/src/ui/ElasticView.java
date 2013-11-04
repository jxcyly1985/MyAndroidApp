package com.src.ui;

import java.util.Calendar;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.src.R;
/**
 * 本类旨在实现通用的弹性刷新效果。只要实现了IScrollOverable的view都可以据有弹性效果。上下方向
 * 
 */
public class ElasticView extends LinearLayout {

	// 一些状态标识
	final int RELEASE_H = 0; // 头部刷新
	final int REFRESHING_H = 2; // 头部正在刷新
	final int PULL = 1; // 下拉
	final int PUSH = 3; // 上推
	final int RELEASE_F = 4; // 底部刷新
	final int REFRESHING_F = 5; // 底部正在刷新
	final int NORMAL = 6; // 正常状态
	int factor = 3; // 因子
	float maxElastic = 0.2f; // 最大弹性值
	int critical = 2; // 临界值
	int maxElasticH, maxElasticF; // 最大头部、底部弹性值
	boolean isTopRecored; // 头部标记
	boolean isBtmRecored = false; // 底部标记
	int startY; // 是否开始y轴
	public IRefresh irefresh; // 刷新接口
	RotateAnimation animation; // 开始弹性动画
	RotateAnimation reverseAnimation; // 弹性回转动画
	int state = NORMAL; // 当前状态
	boolean isBack; // 是否弹回

	View rv; //
	// 相对布局，头部、底部View，容器，主容器
	RelativeLayout headerView, footerView, continer, main_continer;
	// 头部、底部进度条
	ProgressBar hPro, fPro;
	// 头部、底部imageView
	ImageView hArow, fArow;
	// 头部、底部提示
	TextView tvTipH, tvTipF, tvLstH, tvLstF;
	Context ctx;

	// 滚动接口
	private IScrollOverable overable;

	// 容器高度、宽度
	public int continerH, continerW, headerH, headerW, footerH, footerW, rvH,
			rvW;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				state = NORMAL;
				changeFooterViewByState();
				break;
			}
		}

	};

	/**
	 * 构造函数
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ElasticView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 构造函数
	 * @param context
	 * @param attrs
	 */
	public ElasticView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 构造函数
	 * @param context
	 */
	public ElasticView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 设置滚动接口，并将实现IScrollOverable接口的View添加到容器中
	 * Name:    
	 * Description: 
	 * Author:    
	 * @param o ：实现IScrollOverable接口的View
	 * @return   
	 *
	 */
	public void setScrollOverable(IScrollOverable o) {
		overable = o;
		addView((View) o);
	}

	/**
	 * 初始化弹性容器
	 * Name:    
	 * Description: 
	 * Author:    
	 * @param ctx    
	 * @return   
	 *
	 */
	public void init(Context ctx) {
		this.ctx = ctx;

		rv = View.inflate(ctx, R.layout.elastic_view, null); // 加载弹性容器
		main_continer = (RelativeLayout) rv.findViewById(R.id.main_continer); // 获取主容器

		headerView = (RelativeLayout) rv.findViewById(R.id.header); // 头部相对布局
		hPro = (ProgressBar) rv.findViewById(R.id.pro_h); // 头部进度条
		hArow = (ImageView) rv.findViewById(R.id.iv_harow); // 头部ImageView
		tvTipH = (TextView) rv.findViewById(R.id.tv_htip); // 头部提示
		tvLstH = (TextView) rv.findViewById(R.id.tv_lst); // 头部最后刷新时间

		measureViewHeight(headerView); // 丈量头部视图尺寸
		headerH = headerView.getMeasuredHeight(); // 获取头部高度
		headerW = headerView.getMeasuredWidth(); // 获取头部宽度

		footerView = (RelativeLayout) rv.findViewById(R.id.footer); // 底部相对布局
		fPro = (ProgressBar) rv.findViewById(R.id.pro_f); // 底部进度条
		fArow = (ImageView) rv.findViewById(R.id.iv_farow); // 底部ImageView
		tvTipF = (TextView) rv.findViewById(R.id.tv_ftip); // 底部提示
		tvLstF = (TextView) rv.findViewById(R.id.tv_lstf); // 底部最后刷新时间
		footerH = headerH; // 底部高度和宽度与头部一致
		footerW = headerW;

		continer = (RelativeLayout) rv.findViewById(R.id.continer); // 获取容器

		// 添加弹性容器到当前视图中，作为当前视图的子视图
		addView(rv, new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		// 设置容器padding
		main_continer.setPadding(0, -1 * headerH, 0, 0);
		// 刷新弹性视图
		rv.invalidate();
		rv.requestLayout();

		// 设置动画效果
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(500);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setDuration(500);
		reverseAnimation.setFillAfter(true);

		// 当前状态置为：正常
		state = NORMAL;
	}

	/**
	 * 获取视图尺寸
	 * Name:    
	 * Description: 
	 * Author:    
	 * @param child    
	 * @return   
	 *
	 */
	private void measureViewHeight(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();

		if (null == p) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT);
		}

		int childHeightSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.height);
		int lpWidth = p.width;
		int childWidthSpec;
		if (lpWidth > 0) {
			childWidthSpec = MeasureSpec.makeMeasureSpec(lpWidth,
					MeasureSpec.EXACTLY);
		} else {
			childWidthSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		rvH = b;
		rvW = r;
		footerH = headerH;
		footerW = headerW;
		continerH = rvH;
		continerW = rvW;
		maxElasticH = (int) (headerH + continerH * maxElastic);
		maxElasticF = -maxElasticH;
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(rvW,
				rvH);
		rl.addRule(RelativeLayout.BELOW, R.id.header);
		continer.setLayoutParams(rl);
		continer.requestLayout();
	}

	/**
	 * 
	 * Title: 滚动接口类
	 * Description: 判断滚动到头部还是滚动到底部
	 * Copyright: Copyright (c) 2008
	 * Company:深圳彩讯科技有限公司
	 * @author XXX
	 * @version 1.0
	 */
	public interface IScrollOverable {
		public boolean isScrollOnTop();

		public boolean isScrollOnBtm();
	}

	/**
	 * 添加视图
	 */
	public void addView(View v) {
		continer.addView(v, new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT));
	}

	/**
	 * 更新头部的视图
	 * Name:    
	 * Description: 
	 * Author:        
	 * @return   
	 *
	 */
	public void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_H:
			hArow.setVisibility(View.VISIBLE);
			hPro.setVisibility(View.GONE);
			tvTipH.setVisibility(View.VISIBLE);
			tvLstH.setVisibility(View.VISIBLE);
			hArow.clearAnimation();
			hArow.startAnimation(animation);
			tvTipH.setText("松开刷新");
			break;
		case PULL:
			hPro.setVisibility(View.GONE);
			tvTipH.setVisibility(View.VISIBLE);
			tvLstH.setVisibility(View.VISIBLE);
			hArow.clearAnimation();
			hArow.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				hArow.clearAnimation();
				hArow.startAnimation(reverseAnimation);
			}
			tvTipH.setText("下拉刷新");
			break;
		case REFRESHING_H:
			hPro.setVisibility(View.VISIBLE);
			hArow.clearAnimation();
			hArow.setVisibility(View.GONE);
			tvTipH.setText("正在刷新...");
			tvLstH.setVisibility(View.INVISIBLE);
			tvLstH.setText("上次更新："
					+ Calendar.getInstance().getTime().toLocaleString());
			break;
		case NORMAL:
			setRvPadding(-1 * headerH);
			hPro.setVisibility(View.GONE);
			hArow.clearAnimation();
			hArow.setImageResource(R.drawable.arrow_down);
			tvTipH.setText("下拉刷新");
			tvLstH.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 更新底部视图
	 * Name:    
	 * Description: 
	 * Author:        
	 * @return   
	 *
	 */
	public void changeFooterViewByState() {
		switch (state) {
		case RELEASE_F:
			fArow.setVisibility(View.VISIBLE);
			fPro.setVisibility(View.GONE);
			tvTipF.setVisibility(View.VISIBLE);
			tvLstF.setVisibility(View.VISIBLE);
			fArow.clearAnimation();
			fArow.startAnimation(animation);
			tvTipF.setText("松开刷新");
			break;
		case PUSH:
			fPro.setVisibility(View.GONE);
			tvTipF.setVisibility(View.VISIBLE);
			tvLstF.setVisibility(View.VISIBLE);
			fArow.clearAnimation();
			fArow.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				fArow.clearAnimation();
				fArow.startAnimation(reverseAnimation);
			}
			tvTipF.setText("上推刷新");
			break;
		case REFRESHING_F:
			fPro.setVisibility(View.VISIBLE);
			fArow.clearAnimation();
			fArow.setVisibility(View.GONE);
			tvTipF.setText("正在刷新...");
			tvLstF.setVisibility(View.INVISIBLE);
			tvLstF.setText("上次更新："
					+ Calendar.getInstance().getTime().toLocaleString());
			break;
		case NORMAL:
			setRvPadding(-1 * headerH);
			fPro.setVisibility(View.GONE);
			fArow.clearAnimation();
			fArow.setImageResource(R.drawable.arrow_up);
			tvTipF.setText("上推刷新");
			tvLstF.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * 更新主容器的上下padding，实现上推和下拉时底部和头部的空白
	 * Name:    
	 * Description: 
	 * Author:    
	 * @param padding    
	 * @return   
	 *
	 */
	public void setRvPadding(int padding) {
		if (padding < maxElasticF) {
			return;
		} else if (padding > maxElasticH) {
			return;
		}

		main_continer.setPadding(0, padding, 0, 0);
		rv.requestLayout();
	}

	/**
	 * 记录处理动作
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startRecord(event);
			break;
		case MotionEvent.ACTION_UP:
			handleUpF();
			handleUpH();
			break;
		case MotionEvent.ACTION_MOVE:
			handleMove(event);
			break;
		}
		if (state == REFRESHING_H || state == REFRESHING_F) {
			return true;
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			startRecord(event);
//			break;
//		case MotionEvent.ACTION_UP:
//			handleUpF();
//			handleUpH();
//			break;
//		case MotionEvent.ACTION_MOVE:
//			handleMove(event);
//			break;
//		}
//		if (state == NORMAL || state == REFRESHING_H || state == REFRESHING_F) {
//			return true;
//		}
		return super.onTouchEvent(event);
	}

	/**
	 * 处理动作
	 * Name:    
	 * Description: 
	 * Author:    
	 * @param event    
	 * @return   
	 *
	 */
	public void handleMove(MotionEvent event) {
		int tempY = (int) event.getY();
		tempY = tempY < 0 ? 0 : tempY;
		startRecord(event);
		if (state != REFRESHING_F && isBtmRecored) {
			handleMoveF(tempY);
		} else if (state != REFRESHING_H && isTopRecored) {
			handleMoveH(tempY);
		}
	}

	/**
	 * 处理头部动作
	 * Name:    
	 * Description: 
	 * Author:    
	 * @param tempY    
	 * @return   
	 *
	 */
	public void handleMoveH(int tempY) {
		if (state == RELEASE_H) {
			if (((tempY - startY) / factor < headerH * critical)
					&& (tempY - startY) > 0) {
				state = PULL;
				changeHeaderViewByState();
			} else if (startY - tempY > 0) {
				state = NORMAL;
				changeHeaderViewByState();
			}
		}
		if (state == PULL) {
			if ((tempY - startY) / factor >= headerH * critical) {
				state = RELEASE_H;
				isBack = true;
				changeHeaderViewByState();
			} else if (tempY - startY <= 0) {
				state = NORMAL;
				changeHeaderViewByState();
			}
		}

		if (state == NORMAL) {
			if (tempY - startY > 0) {
				state = PULL;
				changeHeaderViewByState();
			}
		}

		// 这里就是处理弹性效果的地方
		if (state == PULL || state == RELEASE_H) {
			setRvPadding((tempY - startY) / factor - headerH);
		}
	}

	/**
	 * 处理底部动作
	 * Name:    
	 * Description: 
	 * Author:    
	 * @param tempY    
	 * @return   
	 *
	 */
	public void handleMoveF(int tempY) {
		if (state == RELEASE_F) {
			if (((tempY - startY) / factor > 0 - headerH * critical)
					&& (tempY - startY) < 0) {
				state = PUSH;
				changeFooterViewByState();
			} else if (tempY - startY >= 0) {
				state = NORMAL;
				changeFooterViewByState();
			}
		}
		if (state == PUSH) {
			if ((tempY - startY) / factor <= (0 - headerH * critical)) {
				state = RELEASE_F;
				isBack = true;
				changeFooterViewByState();
			} else if (tempY - startY >= 0) {
				state = NORMAL;
				changeFooterViewByState();
			}
		}

		if (state == NORMAL) {
			if (tempY - startY < 0) {
				state = PUSH;
				changeFooterViewByState();
			}
		}

		// 这里就是处理弹性效果的地方。
		if (state == PUSH || state == RELEASE_F) {
			setRvPadding((tempY - startY) / factor - headerH * 2);
		}
	}

	/**
	 * 记录动作
	 * Name:    
	 * Description: 
	 * Author:    
	 * @param event    
	 * @return   
	 *
	 */
	public void startRecord(MotionEvent event) {
		if (overable.isScrollOnTop() && !isTopRecored) {
			isTopRecored = true;
			startY = (int) event.getY();
		}
		if (overable.isScrollOnBtm() && !isBtmRecored) {
			isBtmRecored = true;
			startY = (int) event.getY();
		}
	}

	/**
	 * 处理底部向上
	 * Name:    
	 * Description: 
	 * Author:        
	 * @return   
	 *
	 */
	public void handleUpF() {
		if (state != REFRESHING_F) {
			if (state == PUSH) {
				state = NORMAL;
				changeFooterViewByState();
			}

			if (state == RELEASE_F) {
				state = REFRESHING_F;
				changeFooterViewByState();
				setRvPadding(-1 * headerH - 1);
				irefresh.refreshBtm();
			}
		}
		isBtmRecored = false;
		isBack = false;
	}

	/**
	 * 处理头部向上
	 * Name:    
	 * Description: 
	 * Author:        
	 * @return   
	 *
	 */
	public void handleUpH() {
		if (state != REFRESHING_H) {
			if (state == PULL) {
				state = NORMAL;
				changeHeaderViewByState();
			}

			if (state == RELEASE_H) {
				state = REFRESHING_H;
				changeHeaderViewByState();
				setRvPadding(0);
				irefresh.refreshTop();
			}
		}
		isTopRecored = false;
		isBack = false;
	}

	/**
	 * 刷新完毕
	 * Name:    
	 * Description: 
	 * Author:        
	 * @return   
	 *
	 */
	public void onRefreshComplete() {
		handler.sendEmptyMessage(0);
	}

	/**
	 * 
	 * Title: 刷新接口
	 * Description: 判断是头部还是底部刷新
	 * Copyright: Copyright (c) 2008
	 * Company:深圳彩讯科技有限公司
	 *
	 * @author XXX
	 * @version 1.0
	 */
	public interface IRefresh {
		public boolean refreshTop();

		public boolean refreshBtm();
	}

	/***
	 * 设置拉动效果幅度建议值（1-5）
	 * 
	 * @param factor
	 */
	public void setFactor(int factor) {
		this.factor = factor;
	}

	/***
	 * 设置最大拉动的位置，请在（0.0f-1.0f）之间取值
	 * 
	 * @param maxElastic
	 */
	public void setMaxElastic(float maxElastic) {
		this.maxElastic = maxElastic;
	}

	/***
	 * 设置拉动和松开状态切换的临界值，1-5之间
	 * 
	 * @param critical
	 */
	public void setCritical(int critical) {
		this.critical = critical;
	}

}
