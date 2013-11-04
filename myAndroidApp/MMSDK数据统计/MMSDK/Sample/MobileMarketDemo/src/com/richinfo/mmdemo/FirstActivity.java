package com.richinfo.mmdemo;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chinaMobile.MMOnlineConfigureListener;
import com.chinaMobile.MobileAgent;

/**
 * Title: XXXX (类或者接口名称)
 * Description: XXXX (简单对此类或接口的名字进行描述)
 * Copyright: Copyright (c) 2008
 * Company:深圳彩讯科技有限公司
 *
 * @author XXX
 * @version 1.0
 */
public class FirstActivity extends Activity {
	Button btn1;
	Button btn2;
	Button btn3;

	Button btn4;
	Button btn5;
	Button btn6;

	Button btExit;


	private final String TAG = "FirstActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.setTitle(TAG);
		String sdkversion = MobileAgent.SDK_VERSION;
		if (ValueObject.isOnce) {
			Log.i(TAG, "firstActivity");
			ValueObject.isOnce = false;
			MobileAgent
					.setUpdateOnlineConfigListen(new MMOnlineConfigureListener() {

						@Override
						public void onDataReceive(JSONObject jsoParams) {
							Log.i(TAG, jsoParams.toString());
							if (!jsoParams.toString().equals("")) {
								String key = "";
								String value = "";
								String message = "";
								for (Iterator it = jsoParams.keys(); it
										.hasNext();) {
									key = (String) it.next();
									try {
										value = jsoParams.getString(key);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									message = message + key + ":" + value
											+ "\r\n";
								}
								Log.i(TAG, message);

								new AlertDialog.Builder(FirstActivity.this)
										.setTitle("接收到配置参")
										.setMessage(message)
										.setNegativeButton(
												"确认",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int whichButton) {
														return;
													}
												})

										.show();

							}

						}
					});
			MobileAgent.updateOnlineConfig(this);
		}

		// MobileAgent.update(this);
		// MobileAgent.updateAutoPopup = false;
		// MobileAgent.setUpdateListener(new MMUpdateListener() {
		// @Override
		// public void onUpdateReturned(int arg, String versionDescrip,
		// String uri) {
		// //showUpdateDialog();
		// MobileAgent.showUpdateDialog(FirstActivity.this, uri);
		//
		// }
		// });
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn1.setText("SecondActivity");
		btn2.setText("ThirdActivity");
		btn3.setText("报错");

		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(FirstActivity.this, SecondActivity.class);
				startActivity(intent);
			}
		});

		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(FirstActivity.this, ThirdActivity.class);
				startActivity(intent);
			}
		});

		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ArrayList<String> arrayList = null;
				int i = arrayList.size();
			}
		});

		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		btn4.setText("反馈");
		btn5.setText("事件2");
		btn6.setText("EVENT3");
		btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				MobileAgent.openFeedbackDialog(FirstActivity.this);
			}
		});
		btn5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MobileAgent.onEvent(FirstActivity.this, "First 事件2", "快跑");
			}
		});
		btn6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// catchErr(FirstActivity.this);
				MobileAgent.onEvent(FirstActivity.this, "First EVENT3",
						"no jump", 1);
			}
		});
		btExit = (Button) findViewById(R.id.btexit);
		btExit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// MobileAgent.exit();
				FirstActivity.this.finish();
				// System.exit(-1);

			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();

		MobileAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();

		MobileAgent.onPause(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
