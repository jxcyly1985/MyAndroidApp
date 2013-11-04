package com.src.android.activity;

import java.util.List;

import com.src.R;
import com.src.content.provider.ContentProviderWrapper;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

public class ContentProviderActivity extends Activity {
	
	private Button mBtn;
	private ListView	mlistView;
	private Adapter	mAdapter;
	private List<String> mPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_provider_layout);
		
		mBtn = (Button) findViewById(R.id.id_show_all_content_btn);
		mlistView = (ListView) findViewById(R.id.id_show_all_content_list);
		mBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void generateListAdapter(){
		
		Cursor cursor = ContentProviderWrapper.getInstance().getCursorOfMediaImage();
		cursor.moveToFirst();
		int i = 0;
		while( i < cursor.getCount()){
			
		}
		
	}
}
