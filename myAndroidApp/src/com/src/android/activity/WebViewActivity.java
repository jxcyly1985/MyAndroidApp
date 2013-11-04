package com.src.android.activity;

import java.util.Observable;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import cn.lemon.utils.AgentUtil;
import cn.lemon.utils.SourceUtil;
import cn.lemon.utils.regex.HtmlTagFilter;

import com.src.android.global.BaseActivity;

public class WebViewActivity extends BaseActivity {
	private LinearLayout _container;
	private TextView _tv;
	private Button _btn;
	private WebView _webview;
	private String _htmlData;
	private int _fontIndex = 0;
	private int _moveCount = 0;
	private int _webviewMoveCount = 0;
	private ScrollView _scrollview;

	/** ------- Public接口以及Public类 ---------------- **/

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		super.update(observable, data);
	}

	/** ---------------------------------------------- **/

	/** ------- Protected方法以及Protected类 ---------- **/

	private Handler _hanlder = new Handler() {

		public void handleMessage(android.os.Message msg) {

			// _webview.scrollTo(0, 600);

			Log.i("leiyong",
					"onPageFinished _webview.getScale=" + _webview.getScale());
			Log.i("leiyong", "onPageFinished _webview.getMeasuredHeight="
					+ _webview.getMeasuredHeight());
			Log.i("leiyong",
					"onPageFinished _webview.getHeight=" + _webview.getHeight());
			Log.i("leiyong", "onPageFinished _webview.getContentHeight="
					+ _webview.getContentHeight());

			_webview.setLayoutParams(new FrameLayout.LayoutParams(_webview
					.getWidth(), _webview.getContentHeight()));
		};
	};

	@Override
	protected void onStart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// webView调用JS
		// Log.i("leiyong", "WebViewActivity Func[onResume] DO[loadUrl]");
		// myHandler.sendEmptyMessage(0);
		// myHandler.sendEmptyMessageDelayed(0, 500);

	}

	@Override
	protected void onPause() {
		super.onPause();

		int scrollY = _webview.getScrollY();
		Log.i("leiyong", "WebViewActivity onPause scrollY=" + scrollY);

	}

	@Override
	protected void initParameter() {
		// TODO Auto-generated method stub
		super.initParameter();
		// try {
		// _htmlData =
		// SourceUtil.loadContentFromLocalByLocalPath("/sdcard/webview_activity_test.html");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		_container = new LinearLayout(this);
		_container.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));
		_container.setOrientation(LinearLayout.VERTICAL);

		// LinearLayout TextViewContainer = new LinearLayout(this);
		// TextViewContainer.setLayoutParams(new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.FILL_PARENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT));
		// TextViewContainer.setOrientation(LinearLayout.HORIZONTAL);
		//
		// _tv = new TextView(this);
		// _tv.setTextSize(17);
		// Log.i("leiyong", "WebViewActivity getTextSize" + _tv.getTextSize());
		// //25.5
		// _tv.setText("你好");
		//
		// TextView tv2 = new TextView(this);
		// tv2.setTextSize(26);
		// Log.i("leiyong", "WebViewActivity getTextSize" + tv2.getTextSize());
		// tv2.setText("你好");
		//
		// TextView tv3 = new TextView(this);
		// tv3.setTextSize(34);
		// Log.i("leiyong", "WebViewActivity getTextSize" + tv3.getTextSize());
		// tv3.setText("你好");
		//
		//
		// TextViewContainer.addView(_tv);
		// TextViewContainer.addView(tv2);
		// TextViewContainer.addView(tv3);
		//
		// _btn = new Button(this);
		// _btn.setText("缩放字体大小");
		// _btn.setOnClickListener(new OnClickListener() {
		//
		// private boolean _change = false;
		// @Override
		// public void onClick(View v) {
		// Log.i("leiyong", "_webview.getScale=" + _webview.getScale() );
		// Log.i("leiyong", "_webview.getMeasuredHeight=" +
		// _webview.getMeasuredHeight() );
		// Log.i("leiyong", "_webview.getHeight="+_webview.getHeight());
		// Log.i("leiyong",
		// "_webview.getContentHeight="+_webview.getContentHeight());
		// if(_fontIndex == 0){
		// //_webview.getSettings().setTextSize(TextSize.SMALLER);
		// _webview.loadUrl("javascript:changefont(24)");
		// _fontIndex ++;
		// }else if(_fontIndex == 1){
		// //_webview.getSettings().setTextSize(TextSize.NORMAL);
		// _webview.loadUrl("javascript:changefont(26)");
		// _fontIndex ++;
		// }else if(_fontIndex == 2){
		// //_webview.getSettings().setTextSize(TextSize.LARGER);
		// _webview.loadUrl("javascript:changefont(30)");
		// _fontIndex ++;
		// }else if(_fontIndex == 3){
		// //_webview.getSettings().setTextSize(TextSize.LARGEST);
		// _webview.loadUrl("javascript:changefont(32)");
		// _fontIndex = 0;
		// }
		//
		// }
		// });
		//
		// _container.addView(TextViewContainer);
		// _container.addView(_btn);

		_webview = new WebView(this);
		// _webview.setLayoutParams(new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.FILL_PARENT,
		// LinearLayout.LayoutParams.FILL_PARENT));

		_webview.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));

		_webview.getSettings().setJavaScriptEnabled(true);
		_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		// 开启支持缩放 如果不设置WideViewPort 则缩放无效
		_webview.getSettings().setSupportZoom(false);
		// 使用内建的缩放控制 无视WideViewPort是否设置为true
		_webview.getSettings().setBuiltInZoomControls(false);
		// 使用了宽窗口视图，则窗口会被放大，导致左右移动
		// 开启了的话支持点击缩放 但是会影响默认的字体大小
		_webview.getSettings().setUseWideViewPort(false);
		// 尽可能的缩放到原图大小
		_webview.getSettings().setLoadWithOverviewMode(true);
		// 设置字体大小
		_webview.getSettings().setTextSize(TextSize.NORMAL);
		// 设置使用缓存模式
		_webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		// 设置单行显示模式 与游览器宽度为标准缩放 需要设置WideViewPort为false
		// 以设置setInitialScale setDefaultZoom 替换该方法
		_webview.getSettings()
				.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		// 内置滚动栏 效果不好 一般不显示滚动栏
		// _webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

		// 设置WEBVIEW页面的默认缩放密度 不设置导致页面超出屏幕范围
		// 设置了默认缩放大小之后文字大小PX不会有SP的效果
		// 该设置告诉WEBVIEW当前DPI是多少 防止WEBVIEW因为DPI高做了等比
		// 的放大引起超过屏幕问题
		int screenDensity = getResources().getDisplayMetrics().densityDpi;
		WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
		switch (screenDensity) {
		case DisplayMetrics.DENSITY_LOW:
			zoomDensity = WebSettings.ZoomDensity.CLOSE;
			break;
		case DisplayMetrics.DENSITY_MEDIUM:
			zoomDensity = WebSettings.ZoomDensity.MEDIUM;
			break;
		case DisplayMetrics.DENSITY_HIGH:
			zoomDensity = WebSettings.ZoomDensity.FAR;
		default:
			zoomDensity = WebSettings.ZoomDensity.FAR;
			break;
		}
		// 对于DPI大于240的情况 设置WebSettings.ZoomDensity.FAR后
		// 放大倍数是320/240 = 1.33333
		_webview.getSettings().setDefaultZoom(zoomDensity);

		// 去掉水平垂直的滚动栏
		_webview.setVerticalScrollBarEnabled(false);
		_webview.setHorizontalScrollBarEnabled(false);
		// 设置缩放比例 设置了100%就是device-width
		// 设置了这个属性font-size不会随着屏幕密度缩放
		// 不设置该属性font-size的px可以和sp效果一样
		// 设置了INITIALSCALE 但是通过getScale获取又不是该值 奇怪
		// _webview.setInitialScale(100);

		_webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 调用外部系统selector浏览器
				// startActivity(new Intent(Intent.ACTION_VIEW,
				// Uri.parse(url)));
				// 调用自身浏览器
				view.loadUrl(url);
				return false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);

				// 页面加载完毕之后调用字体修改

				Log.i("leiyong",
						"onPageFinished _webview.getScale="
								+ _webview.getScale());
				Log.i("leiyong", "onPageFinished _webview.getMeasuredHeight="
						+ _webview.getMeasuredHeight());
				Log.i("leiyong", "onPageFinished _webview.getHeight="
						+ _webview.getHeight());
				Log.i("leiyong", "onPageFinished _webview.getContentHeight="
						+ _webview.getContentHeight());

				// _webview.setLayoutParams(new FrameLayout.LayoutParams(
				// _webview.getWidth(), _webview.getContentHeight()));

				_hanlder.sendEmptyMessageDelayed(0, 1000);
			}

		});
		// JS需要在WebChromeClient才有
		_webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}
		});

		_webview.addJavascriptInterface(new JsObj(WebViewActivity.this),
				"openIntent");
		// _htmlData = getTestHtmlData();
		// _webview.loadDataWithBaseURL("", _htmlData, "text/html", "UTF-8",
		// null);
		// _webview.loadUrl("file:///android_asset/test-1.html");
		_webview.loadUrl("http://wap.baidu.com/ssid=0/from=0/bd_page_type=1/uid=FC8020B7313D18E41FC65D80976D1771/s?word=%E5%9C%A3%E7%8E%8B&uc_param_str=upssntdnvelami&st_1=111041&st_2=102041&pu=sz@176_220&idx=20000&tn_1=webmain&tn_2=fwapadv&ct_2=%E6%90%9CWap");
		// webview如果没有设置setDefaultZoom
		// 默认的缩放比例是和屏幕密度相关的 在240DPI下的
		// 网页默认是要放大1.5倍来进行适应
		Log.i("leiyong", "_webview.getScale=" + _webview.getScale());

		// _tv.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.i("leiyong", "WebViewActivity TextView Click");
		// //加载页面以后再调用设置缩放比无效
		// _webview.setInitialScale(300);
		// }
		// });

		_webview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.i("leiyong", "webview MotionEvent.ACTION_DOWN");
					break;
				case MotionEvent.ACTION_MOVE:
					return true;
					// Log.i("leiyong", "webview MotionEvent.ACTION_MOVE");
					// _webviewMoveCount++;
					// break;
				default:
					break;
				}
				// if (event.getAction() == MotionEvent.ACTION_UP &&
				// _webviewMoveCount >
				// 0) {
				// Log.i("leiyong", "webview MotionEvent.ACTION_UP");
				// _webviewMoveCount = 0;
				// WebView webview = (WebView) v;
				// Log.i("leiyong", "webview.getScrollY="+webview.getScrollY());
				// Log.i("leiyong", "webview.getHeight="+webview.getHeight());
				// Log.i("leiyong", "webview.getContentHeight=" +
				// webview.getContentHeight());
				// if (webview.getContentHeight() <= webview.getScrollY() +
				// webview.getHeight()) {
				// //加载数据代码
				// Log.i("leiyong", "_webviewMoveCount webview to bottom");
				// }
				// }
				return false;
			}
		});

		_scrollview = new ScrollView(this);

		_scrollview.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
//		_scrollview.setFillViewport(true);
		//
		// _scrollview.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// // TODO Auto-generated method stub
		//
		// switch (event.getAction()) {
		// case MotionEvent.ACTION_DOWN :
		// Log.i("leiyong", "scrollview ACTION_DOWN");
		// break;
		// case MotionEvent.ACTION_MOVE :
		// Log.i("leiyong", "scrollview ACTION_MOVE");
		// _moveCount++;
		// break;
		// default :
		// break;
		// }
		// if (event.getAction() == MotionEvent.ACTION_UP && _moveCount > 0) {
		// _moveCount = 0;
		// WebView view = (WebView)((ScrollView) v).getChildAt(0);
		// Log.i("leiyong",
		// "_scrollview webview.getMeasuredHeight="+view.getMeasuredHeight());
		// Log.i("leiyong", "_scrollview webview.getHeight="+view.getHeight());
		// Log.i("leiyong",
		// "_scrollview webview.getContentHeight="+view.getContentHeight());
		//
		// Log.i("leiyong",
		// "_scrollview.getMeasuredHeight="+_scrollview.getMeasuredHeight());
		// Log.i("leiyong", "_scrollview.getHeight="+_scrollview.getHeight());
		//
		// if (view.getMeasuredHeight() <= v.getScrollY() + v.getHeight()) {
		// //加载数据代码
		// Log.i("leiyong", "_moveCount webview to bottom");
		// }
		// }
		// return false;
		// }
		// });
		//
		// //去掉水平垂直的滚动栏
		_scrollview.setVerticalScrollBarEnabled(true);
		_scrollview.setHorizontalScrollBarEnabled(false);
		// webView调用JS
		// _webview.loadUrl("javascript:changefont(17)");

		_scrollview.addView(_webview);
		// _container.addView(_webview);
		_container.addView(_scrollview);
		setContentView(_container);
	}

	/** ---------------------------------------------- **/

	/** ------- Private方法以及Private类 -------------- **/

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Log.i("leiyong", "WebViewActivity handleMessage FUNC[loadUrl]");
			_webview.loadUrl("javascript:changefont(17)");
		};

	};

	private String getTestHtmlData() {
		String data = "<html><head><style type=\"text/css\">p{font-size:17px}</style>"
				+ "<script type=\"text/javascript\"> "
				+ "function myfunction2(element){openIntent.open(element.id, element.src);}"
				+ "function changefont(fontSize){	"
				+ "var elements = document.getElementsByTagName('p'); "
				+ "for(var i=0; i < elements.length; ++i){"
				+ "elements[i].style.fontSize=fontSize+'px'; }"
				+ "var sourceEle = document.getElementById('sourcelinkbtn');"
				+ "sourceEle.style.fontSize=fontSize+4+'px';}</script>"
				+ "</head> "
				+ "<body>"
				+ "<p>你好</p>"
				+ "<div class=\"sourcelink\" style=\"padding:20px;text-align:center\">"
				+ "<button id='sourcelinkbtn' class=\"sourcelinkbtn\" type=\"button\"  ; style==\"width:200px; height:50px;\" onclick=\"window.open('"
				+ "http://www.baidu.com"
				+ "')\">"
				+ "查看原文</button>"
				+ "</div>"
				+ "<script type=\"text/javascript\"> "
				+ "changefont(40)"
				+ "</script>" + "</body>" + "</html>";
		return data;
	}

	private class JsObj {
		private Context con;

		public JsObj(Context con) {
			this.con = con;
		}

		public void open(String id, String url) {
			Log.i("leiyong", "id=" + id);
			Log.i("leiyong", "url=" + url);

			if (url.startsWith("http://")) {

			}
			if (url.startsWith("file://")) {

			}

		}
	}

	/** ---------------------------------------------- **/

}
