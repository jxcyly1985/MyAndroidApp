package com.src.android.pattern;

import com.src.R;
import com.src.android.activity.entity.MementoManager;
import com.src.android.activity.entity.MoveRectangleView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MoveRectangleActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.move_rectangle_layout);
		
		final MoveRectangleView mrv = new MoveRectangleView(this);
		mrv.init(10, 10, 10, 200);
		
		LinearLayout ll = (LinearLayout)findViewById(R.id.id_move_rectange_container_ll);
		ll.addView(mrv);
		
		Button changeBTN = (Button) findViewById(R.id.id_change_pos_btn);
		Button restoreBTN = (Button) findViewById(R.id.id_restore_pos_btn);
		changeBTN.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/** 如果是规范的memento模式 需要把请求委托给MementoManager
				 *  由mementoManager来创建备忘录 */
				com.src.android.activity.entity.MoveRectangleView.MoveRectangleMemento mememto = mrv.createMemento();
				MementoManager.getInstance().addMememto(mememto);
				mrv.change();
				mrv.invalidate();
				
				
			}
		});
		restoreBTN.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				com.src.android.activity.entity.MoveRectangleView.MoveRectangleMemento memento = MementoManager.getInstance().getMemento();
				if(memento != null){
					mrv.setMemento(memento);
					mrv.invalidate();
				}
				
				
			}
		});
	}
		
}
