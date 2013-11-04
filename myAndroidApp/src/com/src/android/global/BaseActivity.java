package com.src.android.global;

import java.util.Observable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity implements IActivityFramework {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initParameter();
		initView();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		this.deleteObserver();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub

	}

	
	protected void initParameter() {
		// TODO Auto-generated method stub
		addObserver();
	}

	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addObserver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObserver() {
		// TODO Auto-generated method stub

	}

}
