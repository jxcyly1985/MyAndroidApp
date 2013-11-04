package com.src.g3assistant.broadcastreceiver;

import java.lang.reflect.Method;
import java.util.Set;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import com.src.g3assistant.internal.telephony.ITelephony;

public class CallStateReceiver extends BroadcastReceiver {

	public static Set<String> mBlockedNumberSet;
	
	private TelephonyManager mTelManager;
	private ITelephony	mITelephony;	
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		
		String action = arg1.getAction();
		String number = arg1.getStringExtra("EXTRA_INCOMING_NUMBER");
		if(action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
			for(String tmp : mBlockedNumberSet){
				if(number.equals(tmp)){
					//属于被屏蔽的电话则进行拦截挂断电话
					ReflectToEndCall();
					
					 try {  
                         //挂断电话  
                         mITelephony.endCall();  
                     } catch (Exception e) {  
                         e.printStackTrace();  
                     }  
				}
			}
		}
		
	}
	
	public void ReflectToEndCall(){
		 try {  
	            Method getITelephonyMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);  
	            getITelephonyMethod.setAccessible(true);  
	            mITelephony = (ITelephony) getITelephonyMethod.invoke(mTelManager, (Object[]) null);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	}
	
}
