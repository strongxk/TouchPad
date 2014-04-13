package com.example.touchpad;
 
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;

import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
//���͵���Ϣ
//0.�ƶ�������Ϣ��0 , velocityX , velocityY 
//1.��������������Ϣ:	1
//2.̧������������Ϣ: 2
//3.��������Ҽ�����Ϣ: 3
//4.̧������Ҽ�����Ϣ: 4
//5.��С�����ڵ���Ϣ:10
//6.��󻯴��ڣ�11
//7.��ǰһ��ǩҳ:12
//8.����һ��ǩҳ:13
//9.�رյ�ǰ��ǩҳ:14
//10.��ʾ���棺15
public class TouchActivity extends Activity implements OnTouchListener{
	
	private int sensitivity = 2; //���������
	Handler mHandler = MainActivity.mHandler;
	
	Socket mSocket;
	PrintWriter pw;
	
	Button btnLeft = null;
	Button btnRight = null;
	Button btnMove = null;
	
	GestureDetector myGestureDetector;
	public static int windowWidth;
	public static int windowHeight;
	
	private float o_x = 0.0f;
	private float o_y = 0.0f;
	private float c_x = 0.0f;
	private float c_y = 0.0f;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);
		
		init();
		myGestureDetector = new GestureDetector(this, new MyGestureListener(getApplicationContext(),sensitivity));
		
		
		findViews();
		setListeners();
	}
	

	
	public boolean onTouch(View v,MotionEvent e){
		if(v.getId() == R.id.imgMove){
			//Log.i("MYTAG", "imgMove");
			return myGestureDetector.onTouchEvent(e);
//			if(e.getAction() == MotionEvent.ACTION_DOWN){
//				o_x = e.getX();
//				o_y = e.getY();
//			}else if(e.getAction() == MotionEvent.ACTION_MOVE){
//				c_x = e.getX();
//				c_y = e.getY();
//				
//				float x = c_x - o_x;
//				float y = c_y - o_y;
//				Log.i("MOVE"," "+x+" , "+y );
//				
//				pw.println("0 "+x+" "+y);
//				o_x = c_x;
//				o_y = c_y;
//			}
		}else{
			if(v.getId() == R.id.btnLeft){//���������
				if(e.getAction() == MotionEvent.ACTION_DOWN){//����
					
					mHandler.sendEmptyMessage(1);
					//pw.println("1");
					//pw.flush();
					Log.i("myTag", "�������");
				}else if(e.getAction() == MotionEvent.ACTION_UP){//̧��
					//pw.println("2");
					//pw.flush();
					mHandler.sendEmptyMessage(2);
					Log.i("myTag", "̧�����");
				}
			}else if(v.getId() == R.id.btnRight){//�������Ҽ�
				if(e.getAction() == MotionEvent.ACTION_DOWN){
					pw.println("3");
					//pw.flush();
					Log.i("myTag", "�����Ҽ�");
				}else if(e.getAction() == MotionEvent.ACTION_UP){
					pw.println("4");
					//pw.flush();
					Log.i("myTag", "̧���Ҽ�");
				}
			}
		}
		return true;
	}
	
	private void init(){
		mSocket = MainActivity.mSocket;
		pw = MainActivity.pw;
		
		DisplayMetrics  dm = new DisplayMetrics();    
	    getWindowManager().getDefaultDisplay().getMetrics(dm);    
	    TouchActivity.windowWidth = dm.widthPixels;  
	    TouchActivity.windowHeight = dm.heightPixels;
	    Log.i("��Ļ���", TouchActivity.windowWidth+" ");
	   
	}
	
	private void findViews(){
		btnLeft = (Button)findViewById(R.id.btnLeft);
		btnRight = (Button)findViewById(R.id.btnRight);
		btnMove = (Button)findViewById(R.id.imgMove);
	}
	
	private void setListeners(){
		btnLeft.setOnTouchListener(this);
		btnRight.setOnTouchListener(this);
		btnMove.setOnTouchListener(this);
		
//		btnMove.setOnTouchListener(new OnTouchListener(){
//			@Override
//			public boolean onTouch(View v, MotionEvent ev) {
//				Log.i("TouchTag", "Gesture");
//				myGestureDetector.onTouchEvent(ev);
//				return true;
//			}
//		});
	}
}
