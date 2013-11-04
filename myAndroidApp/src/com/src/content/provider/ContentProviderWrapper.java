package com.src.content.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class ContentProviderWrapper {
	
	private static ContentProviderWrapper mself;
	private Context mContext;
	
	public static ContentProviderWrapper getInstance(){
		if(mself == null){
			return mself = new ContentProviderWrapper();
		}
		return mself;
	}
	
	public void setContext(Context context){
		mContext = context;
	}
	
	public Cursor getCursorOfMediaImage(){
		
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		ContentResolver cr = mContext.getContentResolver();
		String[] project = new String[]{MediaStore.Images.Media.DATA};
		return MediaStore.Images.Media.query(cr, uri, project);
	}

}
