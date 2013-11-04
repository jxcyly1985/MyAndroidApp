package com.src.android.activity.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.View;


/**
 * MoveRectangleView相当于originator
 * @author leiyong
 *
 */
public class MoveRectangleView extends View{
	
	private int mX1;
	private int mY1;
	private int mX2;
	private int mY2;
	
	private final int RectangleWidth = 150;
	private final int RectangleHeight = 100;
	private final int offsetX1 = 10;
	private final int offsetY1 = 10;
	private final int offsetX2 = 20;
	private final int offsetY2 = 20;
	
	private MoveRectangleMemento memento;

	public MoveRectangleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void init(int x1, int y1, int x2, int y2){
		mX1 = x1;
		mY1 = y1;
		mX2 = x2;
		mY2 = y2;
	}
	
	public void change(){
		mX1 = mX1+offsetX1;
		mY1 = mY1+offsetY1;
		mX2 = mX2+offsetX2;
		mY2 = mY2+offsetY2;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		paint.setColor(0xff0000ff);
		paint.setStyle(Paint.Style.FILL);
		
		LinearGradient gradient1 = new LinearGradient(mX1, mY1, mX1+RectangleWidth,
				mY1+RectangleHeight, Color.RED, Color.BLUE, Shader.TileMode.MIRROR );
		paint.setShader(gradient1);
		Rect r1 = new Rect();
		r1.left = mX1;
		r1.right = mX1 + RectangleWidth;
		r1.top = mY1;
		r1.bottom = mY1 + RectangleHeight;
		canvas.drawRect(r1, paint);
		
		paint.setColor(0xff00ff00);
		
		LinearGradient gradient2 = new LinearGradient(mX2, mY2, mX2+RectangleWidth,
				mY2+RectangleHeight, Color.BLUE, Color.RED, Shader.TileMode.MIRROR );
		paint.setShader(gradient2);
		
		Rect r2 = new Rect();
		r2.left = mX2;
		r2.right = mX2 + RectangleWidth;
		r2.top = mY2;
		r2.bottom = mY2 + RectangleHeight;
		canvas.drawRect(r2, paint);
		
		paint.setColor(0xffff0000);
		paint.setStrokeWidth(2);
		
		canvas.drawLine(mX1+RectangleWidth, mY1 + RectangleHeight, mX2, mY2, paint);
		
	}
	
	public MoveRectangleMemento createMemento(){
		MoveRectangleMemento m = new MoveRectangleMemento();
		MoveRectangleState state = new MoveRectangleState();
		state.setFirstX(mX1);
		state.setFirstY(mY1);
		state.setSecondX(mX2);
		state.setSecondY(mY2);
		m.setRectangleState(state);
		return m;
	}
	
	public void setMemento(MoveRectangleMemento memento){
		this.memento = memento;
		MoveRectangleState state = memento.getRectangleState();
		mX1 = state.getFirstX();
		mY1 = state.getFirstY();
		mX2 = state.getSecondX();
		mY2 = state.getSecondY();
	}
	
	public class MoveRectangleMemento {
		
		private MoveRectangleState mState;
		
		//状态应该不能被除originator之外的对象访问 
		//比如备忘录管理器
		private void setRectangleState(MoveRectangleState state){
			mState = state;
		}
		private MoveRectangleState getRectangleState(){
			return this.mState;
		}

	}

}

