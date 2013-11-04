package com.src.android.global;

import java.util.Observer;

import android.view.View;

public interface IActivityFramework extends View.OnClickListener, Observer {
	
	//增加消息的监听
	public void addObserver();
	//删除消息的监听
	public void deleteObserver();
	
}
