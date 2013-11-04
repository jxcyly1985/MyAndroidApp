package com.src.android.activity;

import com.src.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MyActionBarActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String title = getTitle().toString();
		Log.i("leiyong","title=" +title);
		
		setTitle("new title");
		
//		setTitleColor(0xff000000);
		
//		int titleId = Resources.getSystem().getIdentifier(  
//                "action_bar_title", "id", "android");  
//		TextView yourTextView = (TextView) findViewById(titleId);  
//		yourTextView.setTextColor(0xffefefef);
//		int spSize = getResources().getDimensionPixelSize(R.dimen.content_detail_title_font_size);
//		Log.i("leiyong","size=" +spSize);
//		yourTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
		
		ActionBar actionbar = getActionBar();
		actionbar.setIcon(null);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayUseLogoEnabled(false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar_menu, menu);
	    return true;
	}
	
	 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 Log.i("leiyong", "item id=" + item.getItemId());
		 
		 if(item.getItemId() == android.R.id.home){
			 
		 }
		return super.onOptionsItemSelected(item);
		
		
	} 
	
	
}
