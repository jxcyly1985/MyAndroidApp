package com.src.g3assistant.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.src.R;
import com.src.g3assistant.broadcastreceiver.CallStateReceiver;
import com.src.util.XYUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

public class CallBlockerActivity extends Activity implements View.OnClickListener{
	
	private Button mAddNumberBtn;
	private ListView mListView;
	private SharedPreferences mSP;
	
	private PopupWindow mPopup;
	private View	mContentView;
	private Set<String> mBlockedNumberSet;
	private EditText mEditText;
	
	private List<Map<String,String>> mMapData = new ArrayList<Map<String,String>>();
	private ListAdapter mAdatper = null;
	
	private TelephonyManager telManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.g3assistant_callblocker_layout);
		initData();
		initView();
		initPopupWindow();
		initListView();
		
		initIntentFilter();
	}

	public void initView(){
		mAddNumberBtn = (Button) findViewById(R.id.id_add_block_callnumber_btn);
		mListView = (ListView) findViewById(R.id.id_block_callnumber_listview);
		mAddNumberBtn.setOnClickListener(this);
	}
	
	public void initData(){
//		mSP = getSharedPreferences("blocked_number", MODE_PRIVATE);
//		mBlockedNumberSet = mSP.getStringSet("block_number", null);	
		
		telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public void initPopupWindow(){
		
		mContentView = LayoutInflater.from(this).inflate(R.layout.g3assistant_input_layout, null);
		mContentView.measure(0, 0);	//����֮��õ���ͼ�ļ����С
		mContentView.findViewById(R.id.id_input_edit_cancel_btn).setOnClickListener(this);
		mContentView.findViewById(R.id.id_input_edit_ok_btn).setOnClickListener(this);
		mEditText = (EditText) mContentView.findViewById(R.id.id_input_block_number_edittext);
		mPopup = XYUtil.getPopupWindow(this, mContentView, getWindow().getDecorView());
		mContentView.setOnClickListener(this);
	}

	public void initListView(){
		
		if(mBlockedNumberSet != null){
			for(String number:mBlockedNumberSet){
				Map<String,String> map = new HashMap<String,String>();
				map.put("title", number);
				mMapData.add(map);
			}
			
			mAdatper = new SimpleAdapter(this, mMapData,
	                android.R.layout.simple_list_item_1, new String[] { "title" },
	                new int[] { android.R.id.text1 });
			mListView.setAdapter( mAdatper );
		}	
		
		if(mBlockedNumberSet == null)
			mBlockedNumberSet = new HashSet<String>();
	}
	
	//��������
	public void initIntentFilter(){
		CallStateReceiver receiver = new CallStateReceiver();
		CallStateReceiver.mBlockedNumberSet = mBlockedNumberSet;
		IntentFilter filter = new IntentFilter();
		filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
		registerReceiver(receiver, filter);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id = arg0.getId();
		switch(id){
		case R.id.id_add_block_callnumber_btn:
		{
			if(!mPopup.isShowing()){
				Log.i("G3Assistant", "show input popupwindow");
				mPopup.showAtLocation(getWindow().getDecorView(),Gravity.CENTER, 0, 0);
			}
		}
			break;
		case R.id.id_block_callnumber_listview:
			break;
		case R.id.id_input_edit_cancel_btn:
			mPopup.dismiss();
			break;
		case R.id.id_input_edit_ok_btn:
			addNumberToSharePreference();
			break;
		}	
	}
	
	public void addNumberToSharePreference(){
		String addNumber = mEditText.getText().toString();
		mBlockedNumberSet.add(addNumber);
		SharedPreferences.Editor editor = mSP.edit();
		//editor.putStringSet("block_number", mBlockedNumberSet);
		editor.commit();
		
		CallStateReceiver.mBlockedNumberSet = mBlockedNumberSet;
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("title", addNumber);
		mMapData.add(map);
			
		mAdatper = new SimpleAdapter(this, mMapData,
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 });
		mListView.setAdapter( mAdatper );
		
		mEditText.setText("");
		mPopup.dismiss();
	}
}
