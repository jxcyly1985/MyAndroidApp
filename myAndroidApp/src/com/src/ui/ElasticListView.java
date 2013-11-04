package com.src.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Title: XXXX (类或者接口名称)
 * Description: XXXX (简单对此类或接口的名字进行描述)
 * Copyright: Copyright (c) 2008
 * Company:深圳彩讯科技有限公司
 *
 * @author XXX
 * @version 1.0
 */
public class ElasticListView extends ListView implements
		ElasticView.IScrollOverable {

	public ElasticListView(Context context) {
		super(context);
	}

	public ElasticListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ElasticListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isScrollOnTop() {
		return 0 == getFirstVisiblePosition() ? true : false;
	}

	@Override
	public boolean isScrollOnBtm() {
		return  false;
	}

}
