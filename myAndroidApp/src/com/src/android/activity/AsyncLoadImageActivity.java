package com.src.android.activity;

import android.widget.ListView;

import com.src.android.global.BaseActivity;
import com.src.ui.ElasticListView;
import com.src.ui.ElasticView;

public class AsyncLoadImageActivity extends BaseActivity {
	
	
	private ListView _listview;
	private ElasticView _elasticview;
	private ElasticListView _elasticlistview;
	
	@Override
	public void initParameter() {
		// TODO Auto-generated method stub
		super.initParameter();
		
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		
		_elasticview = new ElasticView(this);
		_elasticlistview = new ElasticListView(this);
	}
	
	

}
