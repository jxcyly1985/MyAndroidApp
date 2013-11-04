package com.richinfo.mmdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chinaMobile.MobileAgent;

public class ThirdActivity extends Activity{
    
    Button btn1;
    Button btn2;
    Button btn3;
    
    Button btn4;
    Button btn5;
    Button btn6;
	private Button btExit;   
    private final String TAG="ThirdActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    this.setTitle(TAG);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setText("SecondActivity");
        btn2.setText("FirstActivity");
        btn3.setText("exit");
        
        btn1.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        
        btn2.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(ThirdActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });
        
        btn3.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn4.setText("Third EVENT1");
        btn5.setText("Third EVENT2  LABEL1");
        btn6.setText("Third EVENT2  LABEL2");
        btn4.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View arg0) {
                MobileAgent.onEvent(ThirdActivity.this, "Third EVENT1");
            }
        });
        btn5.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View arg0) {
                MobileAgent.onEvent(ThirdActivity.this, "Third EVENT2", " LABEL1");
            }
        });
        btn6.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View arg0) {
                MobileAgent.onEvent(ThirdActivity.this, "Third EVENT2", " LABEL2", 3);
            }
        });  
        
        btExit = (Button) findViewById(R.id.btexit);
		btExit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//MobileAgent.exit();
				ThirdActivity.this.finish();
				// System.exit(-1);

			}
		});
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        MobileAgent.onResume(this);

    }
    
    @Override
    public void onPause() {
        super.onPause();
        MobileAgent.onPause(this);

    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
