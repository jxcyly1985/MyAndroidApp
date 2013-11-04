package com.src.android.activity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.src.android.asynctask.RefreshAsyncTask;
import com.src.android.global.BaseActivity;

public class ThreadPoolTestActivity extends BaseActivity {

	private LinearLayout _contentView;
	private TextView _textView;

	@Override
	protected void initParameter() {
		super.initParameter();

	}

	@Override
	protected void initView() {
		super.initView();

		_contentView = new LinearLayout(this);
		_contentView.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));
		_contentView.setOrientation(LinearLayout.VERTICAL);
		Button testBtn = new Button(this);
		testBtn.setText("测试线程池");
		testBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				// 最多138个asynctask
//				for (int i = 1; i < 140; ++i) {
//					RefreshAsyncTask task = new RefreshAsyncTask();
//					task.execute();
//				}
				testFutureTask();
			}
		});
		_textView = new TextView(this);
		_textView.setText("1");
		_contentView.addView(testBtn);
		_contentView.addView(_textView);

		setContentView(_contentView);
	}

	public void testFutureTask() {
		Runnable runnable = new FuturetaskRunnalbe();
		String result = "hello world";
		String getStr = null;
		FutureTask<String> task = new FutureTask<String>(
				runnable, result);
		task.run();
		
		try {
			getStr = task.get();
			if(getStr != null){
				System.out.println(getStr);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class FuturetaskRunnalbe implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("FuturetaskRunnalbe");
		}
	}
	
	private void testExecutorService(){
		ExecutorService service = Executors.newFixedThreadPool(5);
		
	}

}
