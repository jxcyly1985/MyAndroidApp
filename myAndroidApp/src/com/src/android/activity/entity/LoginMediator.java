package com.src.android.activity.entity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/** 
 * abstract interface if override controlChange function
 * @author leiyong
 *
 */
public class LoginMediator {
	
	private Context mContext;
	
	private EditText	mUsername;
	private EditText	mUserpwd;
	private CheckBox	mRememberpwd;
	private CheckBox	mAutologin;
	private Button		mLoginbtn;
	
	public LoginMediator(Context context){
		
		mContext = context;
	}

	public void createControl(){
		
	}
	
	public void controlChange(View control){
		if(control == mUsername){
			
		}else if(control == mUserpwd){
			
		}else if(control == mRememberpwd){
			
		}else if(control == mAutologin){
			
		}else if(control == mLoginbtn){
			
		}
	}
}
