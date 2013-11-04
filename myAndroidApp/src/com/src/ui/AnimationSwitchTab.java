package com.src.ui;

import com.src.R;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 	onDraw的时候应该能获取到吧。不过我用的方法是：
	获取到整个布局的View（可以在整个xml的顶级Layout上设置一个id然后在onCreate中findViewById出来），然后：
	contentView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
    public boolean onPreDraw() {
        //这里坐标已经确定了
         return true;
    	}
	});
 * @author lemon	
 * 
 * 显示选择TAB时候增加动画效果 可以滑动TAB进行TAB选择
 * 
 * onWindowFocusChanged
 *
 */

public class AnimationSwitchTab extends FrameLayout {

	private Button _btn1;
	private Button _btn2;
	private FlipButton _btn3;
	
	private LinearLayout _btn3Wrapper;

	private View _tabView;
	private TabSwitchListener _listener;
	private TabSwitchListener _childlistener;

	private Animation _leftAnim;
	private Animation _rightAnim;

	private int _childWidth;
	private int _bgWidth;

	private int _movechildPosX;
	private int _movechildPosY;
	private int _movechildWidth;
	private int _movechildHeight;

	private String _leftName;
	private String _rightName;
	
	private boolean _toRight;
	private boolean _toLeft;

	// 必须提供全部的构造函数 要不就不要写构造函数 使用默认的构造函数
	public AnimationSwitchTab(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public AnimationSwitchTab(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public AnimationSwitchTab(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		if(_toRight){
			
			int l = _bgWidth - _movechildWidth;
			int t = _movechildPosY;
			int r = _bgWidth;
			int b = _movechildPosY + _movechildHeight;
			_btn3Wrapper.layout(l, t, r, b);
		}
		if(_toLeft){
			
			int l = 0;
			int t = _movechildPosY;
			int r = _movechildWidth;
			int b = _movechildPosY + _movechildHeight;
			_btn3Wrapper.layout(l, t, r, b);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
	}
	
	public void create() {

		_tabView = LayoutInflater.from(getContext()).inflate(
				R.layout.animation_switch_tab_layout, this, true);
		
		_btn1 = (Button) _tabView.findViewById(R.id.id_animation_switch_tab1);
		_btn2 = (Button) _tabView.findViewById(R.id.id_animation_switch_tab2);
		_btn3 = (FlipButton) _tabView.findViewById(R.id.id_animation_switch_tab3);
		_btn3Wrapper = (LinearLayout) _tabView.findViewById(R.id.id_flipbutton_wrapper);

		addListener();
	}

	private void addListener() {

		_btn1.setEnabled(false);
		
		_childlistener = new TabSwitchListener() {
			
			@Override
			public void toRight() {
				
				FlipToRight();
			}
			
			@Override
			public void toLeft() {
				
				FlipToLeft();
			}
		};
		_btn3.create(_childlistener);

		_btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				FlipToLeft();

			}
		});

		_btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				FlipToRight();

			}
		});

	}

	public void setTabSwitchListener(TabSwitchListener listener) {

		_listener = listener;
	}

	public void setLeftTabName(String name) {

		_leftName = name;
		_btn1.setText(name);
		_btn3.setText(name);

	}

	public void setRightTabName(String name) {

		_rightName = name;

		_btn2.setText(name);
	}

	private void FlipToLeft(){
		
		_bgWidth = _tabView.getMeasuredWidth();
		
		_movechildPosX = _btn3Wrapper.getLeft();
		_movechildPosY = _btn3Wrapper.getTop();

		_movechildWidth = _btn3Wrapper.getMeasuredWidth();
		_movechildHeight = _btn3Wrapper.getMeasuredHeight();

		_childWidth = _btn1.getMeasuredWidth();

		_leftAnim = new TranslateAnimation(0, -_childWidth, 0, 0);
		_leftAnim.setFillAfter(true); 

		_leftAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {

				onLeftAnimEnd();
			}
		});

		_leftAnim.setDuration(300);

		// 启动动画
		_btn3Wrapper.startAnimation(_leftAnim);
	}
	private void FlipToRight(){
		
		_bgWidth = _tabView.getMeasuredWidth();
		
		_movechildPosX = _btn3Wrapper.getLeft();
		_movechildPosY = _btn3Wrapper.getTop();

		_movechildWidth = _btn3Wrapper.getMeasuredWidth();
		_movechildHeight = _btn3Wrapper.getMeasuredHeight();

		_childWidth = _btn1.getMeasuredWidth();

		_rightAnim = new TranslateAnimation(_movechildPosX,
				_movechildPosX + _childWidth, 0, 0);
		
		_rightAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {

				onRightAnimEnd();

			}
		});

		_rightAnim.setDuration(300);
		// 启动动画
		_btn3Wrapper.startAnimation(_rightAnim);
	}
	
	private void onRightAnimEnd() {

		_btn3Wrapper.clearAnimation();
		
		_toRight = true;
		_toLeft = false;

		_btn3.setText(_rightName);
		
		_btn1.setEnabled(true);
		_btn2.setEnabled(false);
		// 执行回调
		_listener.toRight();

	}
	
	private void onLeftAnimEnd() {

		_btn3Wrapper.clearAnimation();

		_toLeft = true;
		_toRight = false;
		 _btn3.setText(_leftName);

		_btn1.setEnabled(false);
		_btn2.setEnabled(true);

		// 执行回调
		_listener.toLeft();
	}

	public interface TabSwitchListener {

		public void toLeft();

		public void toRight();

	}
	
}
