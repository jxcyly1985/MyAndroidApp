package com.src.android.activity.entity;

public class MoveRectangleState {
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	
	public int getFirstX(){
		return this.x1;
	}
	public int getFirstY(){
		return this.y1;
	}
	public void setFirstX(int x){
		this.x1 = x;
	}
	public void setFirstY(int y){
		this.y1 = y;
	}
	
	public int getSecondX(){
		return this.x2;
	}
	public int getSecondY(){
		return this.y2;
	}
	public void setSecondX(int x){
		this.x2 = x;
	}
	public void setSecondY(int y){
		this.y2 = y;
	}
}
