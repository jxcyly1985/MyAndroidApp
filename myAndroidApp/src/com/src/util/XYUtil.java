package com.src.util;

import com.src.R;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

public class XYUtil {
	
	public static void showToast(Context context, String content)
	{
		Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public static PopupWindow getPopupWindow(Context context, View contentView, View anchor){
		//PopupWindow popwin = new PopupWindow(contentView.getMeasuredWidth(), LayoutParams.WRAP_CONTENT);
		PopupWindow popwin = new PopupWindow(contentView.getMeasuredWidth()+50, contentView.getMeasuredHeight()+50);
		//PopupWindow popwin = new PopupWindow();
		popwin.setFocusable(true);	//�����ò����ܵ���¼����ڻ��޷���ʧ
		//�����ñ����Ļ�����������������ʧ
		popwin.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.desktop_pop));
		//popwin.setBackgroundDrawable(new ColorDrawable(0xff124504));
		popwin.setContentView(contentView);
		popwin.setAnimationStyle(R.style.desktop_popuwindow);
		popwin.update();
		//popwin.update(anchor, contentView.getMeasuredWidth(), contentView.getMeasuredHeight());
		return popwin;
	}
}
