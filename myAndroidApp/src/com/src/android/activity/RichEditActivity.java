package com.src.android.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import cn.lemon.utils.SourceUtil;
import cn.lemon.utils.image.ImageNetUtil;
import cn.lemon.utils.net.NetUtils;

import com.src.android.global.BaseActivity;

public class RichEditActivity extends BaseActivity {
	private LinearLayout _linearlayout;
	private Button _btn;
	private EditText _et;
	private String _htmlData;
	@Override
	public void initParameter() {
		super.initParameter();
		
		try {
			_htmlData = SourceUtil.loadContentFromLocalByLocalPath("/sdcard/webview_activity_test.html");
			Log.i("leiyong", _htmlData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initView() {
		super.initView();
		_linearlayout = new LinearLayout(this);
		_linearlayout.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));
		_linearlayout.setOrientation(LinearLayout.VERTICAL);
		
		_btn = new Button(this);
		_btn.setText("打印控件文本");
		_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("leiyong", _et.getText().toString());
				Log.i("leiyong", Html.toHtml(_et.getText()));
			}
		});

		_et = new EditText(this);
		_et.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));
		_et.setPadding(30, 30, 30, 30);
		_et.setSelection(0);
		_et.setGravity(Gravity.TOP | Gravity.LEFT);
//		Spanned text = Html.fromHtml(
//				"你好" +
//				"<img src=\"http://imgsrc.baidu.com/forum/pic/item/b0529822720e0cf38512b46c0a46f21fbf09aa44.jpg\">" +
//				"</img>" +
//				"我爱你中国"
//				,imgGetter, null);
		
		//不能处理HTML中的CSS和JS 因此_htmlData不能包含CSS和JS
		//只能包含基本的HTML标签 用户修改标签之后 
		//通过Html.toHtml(_et.getText())获取HTML标签之后
		//添加<HTML><HEAD>CSS JS</HEAD></HTML>
		
		//_htmlData = "&nbsp;&amp;&copy;&reg 你好 中国";
		_htmlData = "<html><body><a href=\"http://www.baidu.com\">http://www.baidu.com</a></body></html>";
		
		Spanned text = Html.fromHtml(_htmlData, imgGetter, null);
		Log.i("leiyong", "fromHtml text=" + text);
		_et.setText(text);
		_et.setCompoundDrawablePadding(100);
		
		
		Log.i("leiyong", "_etPadding="+ _et.getPaddingLeft());
		Log.i("leiyong", "_etPadding="+ _et.getPaddingBottom());
		
		_linearlayout.addView(_btn);
		_linearlayout.addView(_et);
		setContentView(_linearlayout);
		
	}
	
	
    ImageGetter imgGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
              Drawable drawable = null;
              drawable = ImageNetUtil.createDrawableFromUrl(source);  // Or fetch it from the URL
              // Important
              drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
	                            .getIntrinsicHeight());
              return drawable;
			 
        }
    };



}
