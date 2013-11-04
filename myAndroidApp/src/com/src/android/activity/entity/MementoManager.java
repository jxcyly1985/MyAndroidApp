package com.src.android.activity.entity;

import java.util.Stack;

import com.src.android.activity.entity.MoveRectangleView.MoveRectangleMemento;

public class MementoManager {
	
	private Stack<MoveRectangleMemento> mStack = new Stack<MoveRectangleMemento>();
	
	private static MementoManager mSelf;
	
	public static MementoManager getInstance(){
		if(mSelf != null){
			return mSelf;
		}else{
			return mSelf = new MementoManager();
		}
	}
	
	private MementoManager(){
		
	}
	
	
	public void addMememto(MoveRectangleMemento mememto){
		
		mStack.push(mememto);
		
	}
	public MoveRectangleMemento getMemento(){
		if(!mStack.empty()){
			return mStack.pop();	
		}
		return null;
	}

}
