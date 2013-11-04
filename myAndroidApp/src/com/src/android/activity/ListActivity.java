package com.src.android.activity;

import java.util.ArrayList;
import java.util.List;

import com.src.R;
import com.src.android.adapter.ContentListItemAdapter;
import com.src.android.asynctask.RefreshAsyncTask;
import com.src.android.global.BaseActivity;
import com.src.android.model.AbstractInfo;
import com.src.android.model.ItemInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 
 * Title:	ListActivity
 * 
 * Description:
 * 1. 用于展现列表交互界面
 * 2. AdapterView抽象类-提供AdapterDataSetObserver
 * 3. AbsListView抽象-提供obtainView获取子项目item视图
 * 4. ListView实现类-提供onMeasure onTouchEvent处理具体的UI交互
 * 5. BaseAdapter-invoke-ListView视图是数据变化的监听者
 * @author leiyong
 *
 */

public class ListActivity extends BaseActivity {

	private ListView _itemListView;
	private TextView _copTextView;
	private ContentListItemAdapter _listAdapter;
	private List<AbstractInfo> _dataList;
	private static MyHandler _handler = null;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("leiyong", "ListActivity Destroy");
		System.gc();
		//myHandler.sendEmptyMessageDelayed(0, 25000);
		//new RefreshThread(_copTextView).start();
		//new RefreshAsyncTask(_copTextView).execute();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(_listAdapter != null){
			_listAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initParameter() {
		// TODO Auto-generated method stub
		super.initParameter();
		this.setContentView(R.layout.content_list_item_layout);
		_dataList = new ArrayList<AbstractInfo>();
		
		ItemInfo info = new ItemInfo();
		info._title = "Hello World " ;
		info._content = "I Love This Girl";
		_dataList.add(info);
		
		Log.i("leiyong", "new _dataList=" + _dataList.hashCode());
		
		if(_handler == null){
			_handler = new MyHandler();
		}
		_listAdapter = new ContentListItemAdapter(this, _dataList, _handler);
		//_listAdapter = new ContentListItemAdapter(this, _dataList);
		Log.i("leiyong", "new _listAdapter=" + _listAdapter.hashCode());
		Message msg = Message.obtain();
		msg.what = 0;
		//ContentListItemAdapter._staticHandler.sendMessageDelayed(msg, 5000);
		_handler.sendMessageDelayed(msg, 5000);
	}
	@Override
	public void initView(){
		super.initView();
		_copTextView = (TextView) findViewById(R.id.id_complete_count_text_view);
		_itemListView = (ListView) findViewById(R.id.id_content_item_list_view);
		_itemListView.setAdapter(_listAdapter);
		
		//不影响子控件的onTouch事件 也不影响自己的onTouch事件
		//影响的是onClick事件向下派发
		_itemListView.setClickable(false);
		
		//  if (mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED &&
        //  mOnTouchListener.onTouch(this, event)
		//	只有ENABLED才能调用mOnTouchListener.onTouch
		_itemListView.setEnabled(true);	//dispatch->ontouchEvent中没有处理toucn事件
		
		/**
		 * 子控件没有监听onTouchEvent 会调用到onItemClick
		 * 因为ListView实现了onInterceptTouchEvent实现了
		 * 的向子控件消息的派发,
		 */
		_itemListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("leiyong", "ListView onItemClick");
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DIAL);
				intent.setData( Uri.parse("tel:15920523505"));
				
				startActivity(intent);
				
			}
			
		});
		
		
//		_itemListView.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				Log.i("leiyong", "ListView onTouch");
//				return false;
//			}
//		});
		
		//throw new RuntimeException("Don't call setOnClickListener for an AdapterView. "
        //+ "You probably want setOnItemClickListener instead");
//		_itemListView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.i("leiyong", "ListView onClick");
//			}
//		});
	}
	
	
	private class MyHandler extends Handler{
		public void handleMessage(android.os.Message msg) {
			Log.i("leiyong", "handler _dataList=" + _dataList.hashCode());
			ItemInfo info = new ItemInfo();
			info._title = "Hello World " ;
			info._content = "I Love This Girl";
			_dataList.add(info);
			Log.i("leiyong", "handler after _dataList=" + _dataList.hashCode());
			Log.i("leiyong", "handler _dataList=" + _dataList);
			Log.i("leiyong", "handler _listAdapter=" + _listAdapter.hashCode());
			_listAdapter.notifyDataSetChanged();
		};
	};
	
	
	public class RefreshThread extends Thread{
		
		private TextView _mRefreshTextView;
		
		public RefreshThread(TextView tv){
			_mRefreshTextView = tv;
		}
		
		@Override
		public void run() {
			super.run();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_mRefreshTextView.setText("complete!");
			
		}
	}
	
}
