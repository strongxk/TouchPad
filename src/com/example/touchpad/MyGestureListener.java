package com.example.touchpad;

import java.io.PrintWriter;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MyGestureListener extends SimpleOnGestureListener{
	private Context mContext = null;
	private int sensitivity;
	private int windowWidth12;//1/2��Ļ���
	private int windowHeight;//��Ļ�߶�
	private PrintWriter pw;
	
	private int leftMaxX;
	private int rightMinX;
	private int bottomMinY;
	
	private boolean isTriggering = false;
	
	private final String myTag = "MyTag";
	
	public MyGestureListener(Context context,int sensitivity){
		
		this.mContext = context;
		this.sensitivity = sensitivity;
		this.pw = MainActivity.pw;
		windowWidth12 = (TouchActivity.windowWidth / 2);
		windowHeight = (TouchActivity.windowHeight);
		
		leftMaxX = windowWidth12 / 4;
		rightMinX = TouchActivity.windowWidth - leftMaxX;
		bottomMinY = windowHeight - windowHeight / 8;
	}
	
	/*public boolean onDoubleTap(MotionEvent event){
		//if(event.getPointerCount() == 2){
			Toast.makeText(mContext, "˫��", 2000).show();
			Log.i("xk", event.getPointerCount()+" ");
		//}
		//if(event.getPointerCount() == 2)
		//Log.i("XK", "˫��");
		return true;
	}*/
	
	
	
	
	public boolean onScroll(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY){
		//1.��ָ�ƶ�����ָ���ƶ�����Ϣ
		//2.˫ָ�ƶ�����ҳ�������Ϣ
		
		velocityX = -(velocityX * sensitivity);
		velocityY = -(velocityY * sensitivity);
		//Log.i("XK", velocityX+","+velocityY);
		//Log.i("MyTAG", e2.getPointerCount()+" ");
		if(e2.getPointerCount() == 1){//1...
			pw.println("0 "+velocityX+" "+velocityY);
			
			//�������¼�
			if((e2.getX() < leftMaxX) && (e2.getY() > bottomMinY)){//�������½��¼�
				if(!isTriggering){
					Log.i("Trigger","�������½��¼�");
					isTriggering = true;
				}
					
			}else if((e2.getX() > rightMinX) && (e2.getY() > bottomMinY)){//�������½��¼�
				if(!isTriggering){
					Log.i("Trigger","�������½��¼�");
					isTriggering = true;
				}
			}else{
				isTriggering = false;
			}
			
		}else if(e2.getPointerCount() == 2){//2...
			//pw.println("1 "+velocityX+" "+velocityY);//��ߵ�velocityXĿǰû����,ֻ��Ϊ�˷�ֹ������������Խ��
			String str = "1 "+velocityX+" "+velocityY;
			//Log.i("2Move", str);
			pw.println(str);
		}
		
		return true;
	}
	
	public boolean onDoubleTap(MotionEvent e){
		Log.i("MYTAG", "˫����Ļ����"+e.getX()+" , "+e.getY()+"   ��Ļ��һ������ = "+windowWidth12);
		
		if(e.getX() <= windowWidth12){
			Log.i(myTag, "˫�������Ļ");
		}else{
			Log.i(myTag,"˫���Ұ���Ļ");
		}
		Log.i("MyTAG", e.getPointerCount()+" ");
		return true;
	}
	
	@Override
	public void onLongPress(MotionEvent e){
		if(e.getX() <= windowWidth12){
			Log.i(myTag, "���������Ļ");
		}else{
			Log.i(myTag,"�����Ұ���Ļ");
		}
	}
	
//	@Override
//	public void onShowPress(MotionEvent e){
//		if(e.getX() <= windowWidth12){
//			Log.i(myTag, "����show�����Ļ");
//		}else{
//			Log.i(myTag,"����show�Ұ���Ļ");
//		}
//	}
}
