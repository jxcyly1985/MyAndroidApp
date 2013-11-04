package com.src.android.adapter;

import java.util.ArrayList;
import java.util.List;

import com.src.android.model.AbstractInfo;
import com.src.android.model.ItemInfo;

import com.src.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentListItemAdapter extends BaseAdapter {
	
	
	private Context _context;
	private List<AbstractInfo>	_list;
	private List<ImageView>	_imagelist = new ArrayList<ImageView>();
	
	private Handler _handler;
	
	public static Handler _staticHandler = null;
	
	private class ViewHolder{
		TextView _ctrl1;
		TextView _ctrl2;
		ImageView _ctrl3;
		/**
		 * ListView中包含Button CheckBox会占据焦点 导致ListView的OnItemClick失效
		 * 需要设置属性 android:focusable="false" android:clickable="false"
		 */
		Button _ctrl4;
		
		String _tag;
	}

	public ContentListItemAdapter(Context context, List<AbstractInfo> list, Handler handler){
		_context = context;
		_list = list;
		Log.i("leiyong", "ContentListItemAdapter() _list=" + _list.hashCode());
		_handler = handler;
		
	}
	
	public ContentListItemAdapter(Context context, List<AbstractInfo> list){
		_context = context;
		_list = list;
		Log.i("leiyong", "ContentListItemAdapter() _list=" + _list.hashCode());
		if(_staticHandler == null){
			_staticHandler = new MyHandler();
		}
	}
	
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//Log.i("leiyong", "Adapter getCount" + _list.size());
		return _list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Log.i("leiyong", "adapter getview position="+arg0);
		View contentView = arg1;
//		String tag = null;
//		if(contentView != null){
//			tag = (String) contentView.getTag();
//			Log.i("leiyong", "adapter tag=" + tag);
//		}
		
		ItemInfo info = (ItemInfo) _list.get(arg0);
		ViewHolder holder = null;
		//如果arg1和返回的view不是同一个 AbsListView会缓存返回的View
		//这样会导致缓存越来越大 从而引起内存溢出
		if(contentView == null){
			holder = new ViewHolder();
			contentView = LayoutInflater.from(_context)
					.inflate(R.layout.item_in_adapter_view_layout, null, false);
			holder._ctrl1 = (TextView) contentView.findViewById(R.id.id_item_ctrl_1);
			holder._ctrl2 = (TextView) contentView.findViewById(R.id.id_item_ctrl_2);
			holder._ctrl3 = (ImageView) contentView.findViewById(R.id.id_item_ctrl_3);
			//holder._ctrl4 = (Button) contentView.findViewById(R.id.id_item_ctrl_4); 
			holder._tag = Integer.toString(arg0);
			contentView.setTag(holder);
		}else{
			holder = (ViewHolder) contentView.getTag();
		}
		
		//Log.i("leiyong", "holder tag="+ holder._tag);
		
//		contentView.setOnTouchListener(new OnTouchListener(){
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				if(event.getAction() == MotionEvent.ACTION_UP){
//					Log.i("leiyong", "Adpater contentView onTouch action up");
//				}
//				if(event.getAction() == MotionEvent.ACTION_DOWN){
//					Log.i("leiyong", "Adpater contentView onTouch action down");
//				}
//				
//				return false;
//			}
//		});
//		
//		contentView.setOnClickListener(new View.OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.i("leiyong", "Adpater contentView onClick");
//			}
//		});
//		
//		holder._ctrl1.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event)  {
//				// TODO Auto-generated method stub
//				if(event.getAction() == MotionEvent.ACTION_UP){
//					Log.i("leiyong", "Adpater holder._ctrl1 onTouch action up");
//				}
//				if(event.getAction() == MotionEvent.ACTION_DOWN){
//					Log.i("leiyong", "Adpater holder._ctrl1 onTouch action down");
//				}
//				return false;
//			}
//		});
//		holder._ctrl1.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.i("leiyong", "Adpater holder._ctrl1 onClick");
//			}
//		});
//		holder._ctrl2.setOnTouchListener(new OnTouchListener() {
//					
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//						// TODO Auto-generated method stub
//						Log.i("leiyong", "Adpater holder._ctrl2 onTouch");
//						return false;
//					}
//				});
//		holder._ctrl2.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Log.i("leiyong", "Adpater holder._ctrl2 onClick");
//					}
//				});
//		holder._ctrl3.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				Log.i("leiyong", "Adpater holder._ctrl3 onTouch");
//				return false;
//			}
//		});
//		holder._ctrl3.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Log.i("leiyong", "Adpater holder._ctrl3 onClick");
//					}
//		});
		
		
//		holder._ctrl4.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				Log.i("leiyong", "Adpater holder._ctrl4 onTouch");
//				return false;
//			}
//		});
//		holder._ctrl4.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Log.i("leiyong", "Adpater holder._ctrl4 onClick");
//					}
//		});
		
//		new Thread(){
//			public void run() {
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				_handler.sendEmptyMessage(0);
//				File datap = _context.getDatabasePath("hl");
//				Log.i("leiyong", datap.getAbsolutePath());
//			};
//		}.start();
		
		//tag = (String) contentView.getTag();
		//Log.i("leiyong", "adapter tag=" + tag);
			
		holder._ctrl1.setText(info._title);
		holder._ctrl2.setText(info._content);
		holder._ctrl3.setImageBitmap(getBitmap());
		holder._tag = Integer.toString(arg0);
		//contentView.setTag(info._title);
		return contentView;
	}

	public Bitmap getBitmap(){
		Bitmap bmp = BitmapFactory.decodeFile("mnt/sdcard/DCIM/2000.jpg");
		return bmp;
	}
	
	
	private class MyHandler extends Handler{
		public void handleMessage(android.os.Message msg) {
			Log.i("leiyong", "ContentListItemAdapter=" + ContentListItemAdapter.this.hashCode());
			Log.i("leiyong", "ContentListItemAdapter _list=" + _list.hashCode());
			ItemInfo info = new ItemInfo();
			info._title = "Hello World " ;
			info._content = "I Love This Girl";
			_list.add(info);
			Log.i("leiyong", "ContentListItemAdapter _list after add=" + _list.hashCode());
			Log.i("leiyong", "ContentListItemAdapter _list=" + _list);
			
			ContentListItemAdapter.this.notifyDataSetChanged();
		};
	};
	
}
