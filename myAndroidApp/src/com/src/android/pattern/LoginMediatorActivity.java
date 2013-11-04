package com.src.android.pattern;

import com.src.android.activity.entity.LoginMediator;

import android.app.Activity;
import android.os.Bundle;

public class LoginMediatorActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	public void initView(){
		LoginMediator mediator = new LoginMediator(this);
		
		mediator.createControl();
		
	}
}
