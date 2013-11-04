package com.src.android.activity;

import com.src.R;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

public class MyTabActivity extends Activity{

	private TabHost _tabhost;
	private TabWidget _widget;
	private FrameLayout _content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scroll_tab_host_layout);

		initView();
	}

	protected void initView(){
		// 设置窗口的宽度 
		_tabhost = (TabHost) findViewById(android.R.id.tabhost);
		_widget = (TabWidget) findViewById(android.R.id.tabs);
		_content = (FrameLayout) findViewById(android.R.id.tabcontent);
		
		//必须调用setup
		_tabhost.setup();
			
//		_tabhost = getTabHost();
//		_widget = getTabWidget();
//		_content = _tabhost.getTabContentView();
		
		_content =  _tabhost.getTabContentView();
		
		LayoutInflater.from(this).inflate(R.layout.tab_content_layout, _content, true);   
		
		
		//setIndicator 设置选项卡 可以设置文字 也可以设置自定义的View
		
		TabSpec tabSpec1 = _tabhost.newTabSpec("资讯")
				.setIndicator("1")
				.setContent(R.id.tab_demo_tv1);
		TabSpec tabSpec2 = _tabhost.newTabSpec("杂志")
				.setIndicator("2")
				.setContent(R.id.tab_demo_tv2);
		
		TabSpec tabSpec3 = _tabhost.newTabSpec("小说")
				.setIndicator("3")
				.setContent(R.id.tab_demo_tv3);
		
		_tabhost.addTab(tabSpec1);
		_tabhost.addTab(tabSpec2);
		_tabhost.addTab(tabSpec3);
		
		
		// 设置窗口的宽度 
		DisplayMetrics dm = new DisplayMetrics(); 
		getWindowManager().getDefaultDisplay().getMetrics(dm); 
		int screenWidth = dm.widthPixels; 
		
	    for (int i = 0; i < 3; i++) { 
	        // 设置每个选项卡的宽度 
	    	_widget.getChildTabViewAt(i).setMinimumWidth(screenWidth / 3); 
	    } 
		
	}

}
