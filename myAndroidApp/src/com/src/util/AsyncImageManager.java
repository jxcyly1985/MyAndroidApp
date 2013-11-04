package com.src.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class AsyncImageManager {

	private Context _context;
	
	public AsyncImageManager(Context context){
		_context = context;
	}
	
	public void invokeCallback(ImageView view, ImageCallback cb){
		
		boolean isAlive = false;
		if(_context != null){
			isAlive = true;
		}
		Drawable imageDrawable = null;
		String imageUrl = null;
		if(isAlive){
			cb.imageLoaded(imageDrawable, imageUrl);
		}
	}
	
	public void deleteRelatedContext(Context context){
		_context = null;
	}
	
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}
}


