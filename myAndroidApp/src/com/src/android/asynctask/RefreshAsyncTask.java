package com.src.android.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class RefreshAsyncTask extends AsyncTask<Void, Void, Void> {

	private TextView _mText;
	
	public RefreshAsyncTask(){

	}
	
	@Override
	protected Void doInBackground(Void... params) {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("leiyong", "RefreshAsyncTask doInBackground");
		//_mText.setText("hello world");
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		//_mText.setText("hello world");
	}

}
