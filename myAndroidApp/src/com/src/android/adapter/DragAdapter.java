package com.src.android.adapter;

/**
 * Title: 拖拽列表Adapter
 * Description: XXXX (简单对此类或接口的名字进行描述)
 * Copyright: Copyright (c) 2008
 * Company:深圳彩讯科技有限公司
 *
 * @author XXX
 * @version 1.0
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.src.android.model.AbstractInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



public abstract class DragAdapter extends BaseAdapter {
	protected Context mContext;
	protected List<List<AbstractInfo>> mlist;
	public HashMap<Integer, Integer> subscribedColumnsIndex = new HashMap<Integer, Integer>();

	@SuppressWarnings("unchecked")
	public DragAdapter(Context mContext,
			List<List<AbstractInfo>> subscribedColumns) {
		this.mContext = mContext;
		this.mlist = subscribedColumns;
		for (Object infos : this.mlist) {

		}
	}

	public int getCount() {
		//需要预留一个添加栏目的item
		if (this.mlist != null) {
			return this.mlist.size() + 1;
		}
		return 1;
	}

	public Object getItem(int position) {
		return this.mlist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public void addMsg(List<AbstractInfo> msg) {
		this.mlist.add(msg);
	}

	public final void reFlag() {
		for (Entry<Integer, Integer> entry : subscribedColumnsIndex.entrySet()) {
			subscribedColumnsIndex.put(entry.getKey(), 0);
		}
		notifyDataSetChanged();
	}

	public final void swap(int srcPosition, int dragPosition) {
		Collections.swap(this.mlist, srcPosition, dragPosition);
		notifyDataSetChanged();
	}

	public final void setFlag(int position, int flag) {
		
		notifyDataSetChanged();
	}

	public abstract View getView(int paramInt, View paramView,
			ViewGroup paramViewGroup);
}
