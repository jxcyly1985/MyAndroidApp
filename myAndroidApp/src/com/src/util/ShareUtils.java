package com.src.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sina.weibo.net.AccessToken;
import com.sina.weibo.net.DialogError;
import com.sina.weibo.net.SinaShareActivity;
import com.sina.weibo.net.Weibo;
import com.sina.weibo.net.WeiboDialogListener;
import com.sina.weibo.net.WeiboException;
import com.src.android.global.MessageManager;
import com.src.android.global.MsgTypeConstant;

/**
 * Title: 分享工具类
 * Description: 分享工具类
 * Copyright: Copyright (c) 2008
 * Company:深圳彩讯科技有限公司
 *
 * @author XXX
 * @version 1.0
 */
public class ShareUtils {

	// 新浪微博分享
	private static final String CONSUMER_KEY_SINA = "504227114";// 替换为开发者的appkey，例如"1646212960";
	private static final String CONSUMER_SECRET_SINA = "12d02f6af4518e790843d84cc11434a3";// 替换为开发者的appkey，例如"94098772160b6f8ffc1315374d8861f9";
	
	public interface AuthorizeComplete {
		void onComplete(String token, String expressIn);
	}

	/**
	 * Name:    
	 * Description:授权、绑定新浪微博 
	 * Author:    
	 * @param context
	 * @param authorizeComplete    
	 * @return   
	 *
	 */
	public static void authorizeSina(final Context context) {
		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(CONSUMER_KEY_SINA, CONSUMER_SECRET_SINA);
		// Oauth2.0
		// 隐式授权认证方式
		// 此处回调页内容应该替换为与appkey对应的应用回调页
		// 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
		// 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
		// 应用回调页不可为空
		weibo.setRedirectUrl("http://open.weibo.com/apps/504227114/info/advanced");

		weibo.authorize((Activity) context, new WeiboDialogListener() {

			@Override
			public void onWeiboException(WeiboException e) {
				// handler
			}

			@Override
			public void onError(DialogError e) {
				// handler
			}

			@Override
			public void onComplete(Bundle values) {

				String token = values.getString("access_token");
				String expires_in = values.getString("expires_in");
				SharedPreferences preferences = context.getSharedPreferences(
						"token", Context.MODE_PRIVATE);
				preferences.edit().putString("token_sina", token).commit();
				preferences.edit().putString("expires_in_sina", expires_in)
						.commit();

				System.out.println("recoveryTest:"
						+ preferences.getString("token_sina", ""));
				System.out.println("recoveryTest:"
						+ preferences.getString("expires_in_sina", ""));
				
				//发送消息更新Sharepreference的值，和更新按钮的文字
				Message msg = new Message();
				msg.what = MsgTypeConstant.MSG_SINA_WEIBO_BIND_SUCCESS;
				MessageManager.getInstance().sendNotifyMessage(msg);
			}

			@Override
			public void onCancel() {
				// handler
			}
		});
	}

	/**
	 * Name:    
	 * Description: 授权成功后分享微博
	 * Author:    
	 * @param context
	 * @param content
	 * @param picPath
	 * @return
	 * @throws WeiboException    
	 * @return   
	 *
	 */
	public static boolean share2Sina(Context context, String content,
			String picPath, String token, String expires_in)
			throws WeiboException {
		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(CONSUMER_KEY_SINA, CONSUMER_SECRET_SINA);
	

		if (token == null || "".equals(token)) {
			token = "";
			expires_in = "";

			SharedPreferences preferences = context.getSharedPreferences(
					"token", Context.MODE_PRIVATE);
			token = preferences.getString("token_sina", token);
			expires_in = preferences.getString("expires_in_sina", expires_in);
		}

		if (token == null || "".equals(token)) {
			return false;
		}

		System.out.println("token:" + token);
		System.out.println("expires_in:" + expires_in);

		AccessToken accessToken = new AccessToken(token, CONSUMER_SECRET_SINA);
		accessToken.setExpiresIn(expires_in);
		weibo.share2weibo((Activity) context, accessToken.getToken(),
				accessToken.getSecret(), content, picPath);
		return true;
	}

	public static boolean isBind(Context context){
		
		String token = "";
		String expires_in = "";

		SharedPreferences preferences = context.getSharedPreferences(
				"token", Context.MODE_PRIVATE);
		token = preferences.getString("token_sina", token);
		expires_in = preferences.getString("expires_in_sina", expires_in);
		
		if(token.equals("")){
			return false;
		}else{
			return true;
		}
		
	}
	
	/**
	 * Name:    
	 * Description: 解除新浪微博绑定 
	 * Author:    
	 * @param context    
	 * @return   
	 *
	 */
	public static void unBind(Context context, SharedPreferences settingPreference) {
		SharedPreferences preferences = context.getSharedPreferences("token",
				Context.MODE_PRIVATE);
		preferences.edit().clear().commit();
		
		settingPreference.edit().remove("sinaWeibo").commit();
	}
}
