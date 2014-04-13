package com.example.touchpad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity{

	private final int DELAY_TIME = 2000;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable(){
			public void run()
			{
				Intent intent = new Intent(SplashActivity.this,MainActivity.class);
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
			}
		},DELAY_TIME);
	}
}
