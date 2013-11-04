package com.src.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncImageLoader {

	private HashMap<String, SoftReference<Drawable>> _imageCache;
	String _imageUrl;
	private Context _context;

	ExecutorService executors;

	public AsyncImageLoader(Context context) {
		this._context = context;
		_imageCache = new HashMap<String, SoftReference<Drawable>>();
		executors = Executors.newCachedThreadPool();

		// 创建图片加载器，清除本地空img文件
		FileUtils.cleanEmptyFile();
	}
	
	/**
	 * 当前Activity销毁关联的context置为null
	 */
	public void deleteRelatedContext(){
		_context = null;
	}

	//传递的context可能销毁了
	public Drawable loadDrawable(final List<String> imageUrls, final ImageCallback imageCallback) {
		if (imageUrls == null || imageUrls.size() == 0) {
			return null;
		}
		_imageUrl = imageUrls.get(0);
		if (_imageCache.containsKey(_imageUrl)) {
			SoftReference<Drawable> softReference = _imageCache.get(_imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				Log.i("AsyncImageLoader", "AsyncLoader get drawable from cache");
				return drawable;
			}
		}
		new Thread() {
			@Override
			public void run() {
				final Drawable drawable = loadImageFromUrl(_context, _imageUrl);
				_imageCache.put(_imageUrl, new SoftReference<Drawable>(drawable));

				if (_context != null && drawable != null && imageCallback != null ) {
					Activity activity = (Activity) _context;
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							imageCallback.imageLoaded(drawable, _imageUrl);
							Log.i("AsyncImageLoader","AsyncImageLoader get drawable from url");

						}
					});
				}
			}
		}.start();
		return null;
	}

	public static Drawable loadImageFromUrl(Context context, String url) {
		Drawable drawable = null;
		// 首先从本地加载URL对应的图片
		try {
			if (FileUtils.checkExists(url)) {
				drawable = FileUtils.loadImgFromLocal(url);
				if (drawable != null) {
					Log.i("AsyncImageLoader", "从本地加载文件" + url + "成功");
					return drawable;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("AsyncImageLoader", "not exists in local: [" + url
					+ "] error.");
		}
		InputStream i = null;
		try {
			i = FileUtils.getInputStream(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		drawable = Drawable.createFromStream(i, "");

		if (drawable != null) {
			Log.i("AsyncImageLoader", "下载文件" + url + " 成功");
		}

		try {
			FileUtils.saveImgToLocal(url, drawable);
			Log.i("AsyncImageLoader", "保存文件" + url + "到本地成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return drawable;
	}

	public class LoadImageThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
		}
	}
	
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

}
