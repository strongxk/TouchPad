package com.example.touchpad;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{

	Button btnConnect = null;
	EditText etIp = null;
	EditText etPort = null;
	
	public static Socket mSocket;
	public static PrintWriter pw;
	
	
	public static Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			if(mSocket!=null&&mSocket.isConnected())
			if(pw != null){
				pw.println(msg.what);
				pw.flush();
				Log.i("XK", "handlermessage"+msg.what);
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViews();
		setListeners();
	}

	private void findViews(){
		etIp = (EditText)findViewById(R.id.etIp);
		etPort = (EditText)findViewById(R.id.etPort);
		btnConnect = (Button)findViewById(R.id.btnConnect);
	}

	private void setListeners(){
		btnConnect.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view){
		new Thread(){
			public void run(){
				try{
					String ip = etIp.getText().toString();
					int port = Integer.parseInt(etPort.getText().toString());
					
					mSocket = new Socket(ip,port);
					//dos = new DataOutputStream(mSocket.getOutputStream());
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())),true);
					
					if(mSocket.isConnected()){	
						Log.i("xk","connect");
					
						//String msg = "info received";
						//pw.println(msg);
						//pw.flush();
						//pw.println("1");
						//pw.println("2");
						
						//dos.write(msg.getBytes());
						//dos.flush();
						
						Intent intent = new Intent(MainActivity.this,TouchActivity.class);
						MainActivity.this.startActivity(intent);
					}
				}catch(NumberFormatException ee){
					ee.printStackTrace();
				}catch(UnknownHostException ee){
					ee.printStackTrace();
				}catch(Exception ee){
					ee.printStackTrace();
				}
			}
		}.start();
	}
}
