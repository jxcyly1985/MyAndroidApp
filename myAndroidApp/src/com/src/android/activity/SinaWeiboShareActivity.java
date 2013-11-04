package com.src.android.activity;

import java.util.Observable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;


import com.sina.weibo.net.WeiboException;
import com.src.android.global.BaseActivity;
import com.src.android.global.MessageManager;
import com.src.android.global.MsgTypeConstant;
import com.src.util.ShareUtils;

public class SinaWeiboShareActivity extends BaseActivity {
	
	@Override
	public void initParameter() {
		// TODO Auto-generated method stub
		super.initParameter();
		startSinaWeiBoShare();
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
	}
	
	@Override
	public void addObserver() {
		// TODO Auto-generated method stub
		
		super.addObserver();
		MessageManager.getInstance().addOberver(MsgTypeConstant.MSG_SINA_WEIBO_BIND_SUCCESS, this);
		
	}
	
	@Override
	public void deleteObserver() {
		// TODO Auto-generated method stub
		super.deleteObserver();
		MessageManager.getInstance().deleteOberver(MsgTypeConstant.MSG_SINA_WEIBO_BIND_SUCCESS, this);
	}
	

	
	public void startSinaWeiBoShare(){
		if(!ShareUtils.isBind(SinaWeiboShareActivity.this)){
			ShareUtils.authorizeSina(SinaWeiboShareActivity.this);
		}else{
			try {
				ShareUtils.share2Sina(SinaWeiboShareActivity.this, "hello world !", null, null, null);
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		super.update(observable, data);
		Message msg = (Message)data;
		int what = msg.what;
		if(what == MsgTypeConstant.MSG_SINA_WEIBO_BIND_SUCCESS){
			SharedPreferences preferences = getSharedPreferences("token", Context.MODE_PRIVATE);
			String content = "hello world!";
			String token = preferences.getString("token_sina", "");
			String expires_in = preferences.getString("expires_in_sina", "");
			try {
				ShareUtils.share2Sina(SinaWeiboShareActivity.this, content, null, token, expires_in);
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
