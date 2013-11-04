package com.sina.weibo.net;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.src.R;
import com.sina.weibo.net.AsyncWeiboRunner.RequestListener;
import com.src.android.global.BaseActivity;

public class SinaShareActivity extends BaseActivity implements RequestListener{

	private TextView _countCtrl;
	private EditText _contentCtrl;
	private Button	_shareCtrl;
	
	ProgressDialog progressDialog = null;
	
	private String mPicPath = "";
	private String mContent = "";
	private String mAccessToken = "";
	private String mTokenSecret = "";
	
	public static final String EXTRA_WEIBO_CONTENT = "com.weibo.android.content";
	public static final String EXTRA_PIC_URI = "com.weibo.android.pic.uri";
	public static final String EXTRA_ACCESS_TOKEN = "com.weibo.android.accesstoken";
	public static final String EXTRA_TOKEN_SECRET = "com.weibo.android.token.secret";
	
	public static final int WEIBO_MAX_LENGTH = 140;
	
	@Override
	public void initParameter() {
		// TODO Auto-generated method stub
		setContentView(R.layout.share_sina_weibo_layout);
		super.initParameter();
		
		Intent in = this.getIntent();
		mPicPath = in.getStringExtra(EXTRA_PIC_URI);
		mContent = in.getStringExtra(EXTRA_WEIBO_CONTENT);
		mAccessToken = in.getStringExtra(EXTRA_ACCESS_TOKEN);
		mTokenSecret = in.getStringExtra(EXTRA_TOKEN_SECRET);
		
		AccessToken accessToken = new AccessToken(mAccessToken, mTokenSecret);
		Weibo weibo = Weibo.getInstance();
		weibo.setAccessToken(accessToken);
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		_shareCtrl = (Button) findViewById(R.id.id_sina_weibo_share_button);
		_shareCtrl.setOnClickListener(this);
		_contentCtrl = (EditText) findViewById(R.id.id_sina_weibo_share_content_edit_text);
		_countCtrl = (TextView) findViewById(R.id.id_sina_weibo_share_count_text_view);
		_contentCtrl.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				int len = _contentCtrl.getText().toString().length();
				_countCtrl.setText("还可以输入 " + (WEIBO_MAX_LENGTH - len)+ " 字");
			}
			
		});
		
		_contentCtrl.setText(mContent);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("分享到新浪微博");
			progressDialog.setMessage("正在提交分享数据...");
			progressDialog.show();
			return progressDialog;
		}
		return super.onCreateDialog(id);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.id_sina_weibo_share_button:
			showDialog(0);
			Weibo weibo = Weibo.getInstance();
			try {
				if (!TextUtils.isEmpty((String) (weibo.getAccessToken()
						.getToken()))) {
					this.mContent = _contentCtrl.getText().toString();
					// Just update a text weibo!
					update(weibo, Weibo.getAppKey(), mContent, "", "");
				} else {
					Toast.makeText(this, this.getString(R.string.please_login),
							Toast.LENGTH_LONG);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	
	
	private String update(Weibo weibo, String source, String status,
			String lon, String lat) throws MalformedURLException, IOException,
			WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/update.json";
		AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(weibo);
		weiboRunner.request(this, url, bundle, Utility.HTTPMETHOD_POST, this);
		return rlt;
	}

	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub
		
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(SinaShareActivity.this, R.string.send_sucess,
						Toast.LENGTH_LONG).show();
			}
		});

		this.finish();
		
	}

	@Override
	public void onIOException(IOException e) {
		// TODO Auto-generated method stub
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		cleanToken();
		this.finish();
	}

	@Override
	public void onError(final WeiboException e) {
		// TODO Auto-generated method stub
		
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		cleanToken();
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(
						SinaShareActivity.this,
						String.format(
								SinaShareActivity.this
										.getString(R.string.send_failed)
										+ ":%s", e.getMessage()),
						Toast.LENGTH_LONG).show();
			}
		});
		this.finish();

		
	}
	

	private void cleanToken() {
		SharedPreferences preferences = getSharedPreferences("token",
				MODE_PRIVATE);
		preferences.edit().clear().commit();
	}
	
}
