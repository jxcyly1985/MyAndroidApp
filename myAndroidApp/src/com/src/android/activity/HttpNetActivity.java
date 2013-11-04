package com.src.android.activity;


import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.lemon.utils.net.NetManager;

import com.src.R;
import com.src.android.global.BaseActivity;
 
/**
 * 接口强调的是实现 控制的是最终用户需要实现的抽象
 * 继承强调的是过程 控制的是最终用户执行的预定义过程
 * @author lemon
 *
 */

public class HttpNetActivity extends BaseActivity {
	
	private Button _findBtn;
	private Button _cmwapBtn;
	
	private String _proxyHost;
	private String _proxyPort;

		@Override
		public void initParameter() {
			// TODO Auto-generated method stub
			super.initParameter();
			setContentView(R.layout.http_net_test_layout);
		}
		@Override
		public void initView() {
			// TODO Auto-generated method stub
			super.initView();
			_findBtn = (Button) findViewById(R.id.id_find_is_use_cmwap_btn);
			_findBtn.setOnClickListener(this);
			_cmwapBtn = (Button) findViewById(R.id.id_use_cmwap_to_surf_btn);
			_cmwapBtn.setOnClickListener(this);
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			super.onClick(v);
			
			switch(v.getId()){
			case R.id.id_find_is_use_cmwap_btn:
				if(NetManager.isUsingCMWap(this)){
					Toast.makeText(this, "当前使用cmwap网络", Toast.LENGTH_SHORT).show();
					_cmwapBtn.setEnabled(true);
				}else{
					Toast.makeText(this, "当前使用cmnet网络", Toast.LENGTH_SHORT).show();
					_cmwapBtn.setEnabled(false);
				}
				break;
			case R.id.id_use_cmwap_to_surf_btn:
				tocmwap();
				break;
			default:break;
			}
		}
		
		public void tocmwap(){

			getProxyFromJavaVMValues();			
		}
		
		public void getProxyFromJavaVMValues(){
			
			//获取不到结果 PROXY为NULL 
			String proxy = System.getProperty("net.gprs.http-proxy");	
			if(proxy != null){
				Log.i("leiyong", proxy);
			}
			
			//可以使用外部JAR获取到手机的代理PROXY
			String host = android.os.SystemProperties.get("net.gprs.http-proxy");
			if(host != null){
				Log.i("leiyong", host);
			}
	      
		}
		
		public java.net.Proxy getProxyFromAndroidNetProxy(){
			String proxyHost = android.net.Proxy.getDefaultHost();
			if(proxyHost != null){
				java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP,
						new InetSocketAddress(  
							      android.net.Proxy.getDefaultHost(), android.net.Proxy.getDefaultPort()));
				Log.i("leiyong", proxy.toString());
				return proxy;
			}
			return null;
		}
		
		public void getProxyFromAndroidProviderSetting(){
			//这种方式获取不到HTTP代理地址
			String httpProxy = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.HTTP_PROXY);
			if(httpProxy != null){
				Log.i("leiyong", httpProxy);
			}
			//获取android_id 433d4eb056c7f0df
			String android_Id = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			if(android_Id != null){
				Log.i("leiyong", android_Id);
			}
		}
		
		public void setProxySelectorConfig(){
			
			ProxySelector selector = ProxySelector.getDefault();
			Log.i("leiyong", selector.toString());
			List<Proxy> proxylist1 = selector.select(URI.create("http://www.baidu.com"));
			Log.i("leiyong", proxylist1.toString());
			List<Proxy> proxylist2 = selector.select(URI.create("http://10.0.0.172"));
			Log.i("leiyong", proxylist2.toString());
			
		}
		
 }
