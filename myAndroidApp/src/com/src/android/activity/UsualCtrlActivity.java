package com.src.android.activity;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Base64;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.lemon.utils.AgentUtil;
import cn.lemon.utils.regex.HtmlTagFilter;
import cn.lemon.utils.text.HtmlTextUtil;

import com.src.R;
import com.src.android.global.BaseActivity;

//import cn.lemon.livetrue.god.utils.AgentUtil;



public class UsualCtrlActivity extends BaseActivity {

	private ProgressDialog _progressDlg;
	private LinearLayout _linearlayout;
	private TextView _tv1;
	private TextView _tv2;
	
	private ProgressBar _pb1;
	private ProgressBar _pb2;
	
	private int _pi = 0;
	private Handler _handler;
	
	private String htmlString = "<p>dfsdfsffsdfsd</p><img>dfsdf</img><p></p><p>快递费</p>";
	
	@Override
	public void initParameter() {
		// TODO Auto-generated method stub
		setContentView(R.layout.usual_control_layout);
		super.initParameter();
		
		String shaHex = DigestUtils.shaHex("1");
		Log.i("leiyong", "shaHex="+shaHex);
		
		
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		//showDialog(1);
		
		_tv1 = (TextView) findViewById(R.id.id_usual_ctrl_shadow_text_view_1);
		Log.i("leiyong", "getTextSize=" + _tv1.getTextSize());
		_tv1.setMovementMethod(LinkMovementMethod.getInstance());
		_tv1.setText(HtmlTextUtil.getHtmlWithHyperLink("http://www.baidu.com"));
		
		byte[] base64 = Base64.decode("IA==");
		String base64Str = new String(base64,0, base64.length);
		Log.i("leiyong", "UsualCtrlActivity base64=" + base64Str);
		Log.i("leiyong", "UsualCtrlActivity base64 length=" + base64Str.length());
		
		_tv2 = (TextView) findViewById(R.id.id_usual_ctrl_shadow_text_view_2);
		Log.i("leiyong", "getTextSize=" + _tv2.getTextSize());
		
		_linearlayout = (LinearLayout) findViewById(R.id.id_take_control_container_linearlayout);
		LayoutInflater.from(this)
				.inflate(R.layout.public_indeternate_progress_bar_layout, _linearlayout, true);
		View pv = View.inflate(this, R.layout.public_indeternate_progress_bar_layout, null);		
		measureView(pv);
		
//		_linearlayout.addView(pv, new LinearLayout.LayoutParams(
//				pv.getMeasuredWidth(),
//				pv.getMeasuredHeight()));
//		
//		_linearlayout.addView(pv, new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.WRAP_CONTENT,
//				LinearLayout.LayoutParams.WRAP_CONTENT));
				
		LayoutInflater.from(this)
				.inflate(R.layout.public_horizontal_progress_layout, _linearlayout, true);
		
		_pb1 = (ProgressBar) _linearlayout.findViewById(R.id.id_public_indeternate_progress_bar);
		_pb2 = (ProgressBar) _linearlayout.findViewById(R.id.id_public_horizontal_progress_bar);
		_pb2.setMax(100);
		_pb2.setIndeterminate(false);
		_pb2.setProgress(0);
		_handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Log.i("leiyong", "setProgress");
				_pi++;
				_pb2.setProgress(_pi * 10);
				if(_pi * 10 < 100){
					_handler.sendEmptyMessageDelayed(0, 1000);
				}
				
			}
		};
		
		//_handler.sendEmptyMessageDelayed(0, 1000);
		
	}
	
	public void  getParagraphTagString(){
		String pTagContent = HtmlTagFilter.getParagraphTagContent(htmlString);
		Log.i("leiyong", pTagContent);
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		Dialog dlg = null;
		switch(id){
		case 1:
			dismissProgressDialog();
			createProgressDialog();
			return _progressDlg;
		default:
				break;
		}
		return super.onCreateDialog(id);
	}
	
	private void createProgressDialog(){
		_progressDlg = AgentUtil.createProgressDialog(this, "Loading", "正在加载中");
		_progressDlg.show();
		//return _progressDlg;
	}
	
	private void dismissProgressDialog() {
		if (_progressDlg != null && _progressDlg.isShowing()) {
			_progressDlg.dismiss();
		}
	}
	
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	
}
