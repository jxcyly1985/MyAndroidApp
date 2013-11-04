package com.src.android.activity;

import com.src.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MulScreenTest extends Activity {

	private ImageView _imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.multi_screen_layout);
		initView();
		
	}
	
	protected void initView(){
		
		_imageView = (ImageView) findViewById(R.id.id_multi_image_show);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inDensity = 240;
//		options.inTargetDensity = 240;
//		options.inScaled = false;
		
		
//		Bitmap bm = BitmapFactory.decodeFile("mnt/sdcard/w320-1.png", options);
		
		Bitmap bm = BitmapFactory.decodeFile("mnt/sdcard/w320-1.png");
		
		BitmapDrawable drawable = new BitmapDrawable(bm);
		//设置目标设备的密度（如果设备密度设置比机器本身的小
		// 图片会被缩小）
		drawable.setTargetDensity(240);
		
		System.out.println(bm.getWidth());
		System.out.println(bm.getHeight());
		
		_imageView.setImageDrawable(drawable);
		
		//_imageView.setImageBitmap(bm);
		
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//去掉imageView引用
		_imageView.getDrawable().setCallback(null);
		
	}
	
}
