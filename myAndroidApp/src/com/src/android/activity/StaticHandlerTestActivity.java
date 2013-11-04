package com.src.android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.src.android.global.BaseActivity;

public class StaticHandlerTestActivity extends BaseActivity {

	private List<String> _datalist;
	
	private class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.i("leiyong", "_datalist=" + _datalist.hashCode());
			Log.i("leiyong","StaticHandlerTestActivity=" + StaticHandlerTestActivity.this.hashCode());
			_datalist.add("handleMessage");
			
		}
	}
	
	private static MyHandler _handler = null;

	@Override
	public void initParameter() {
		super.initParameter();
		
		_datalist = new ArrayList<String>();
		
		if(_handler == null){
			_handler = new MyHandler();
		}
		_handler.sendEmptyMessage(0);
	}
	
	@Override
	public void initView() {
		super.initView();
	}
	
}
