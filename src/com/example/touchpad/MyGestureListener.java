package com.example.touchpad;

import java.io.PrintWriter;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MyGestureListener extends SimpleOnGestureListener{
	private Context mContext = null;
	private int sensitivity;
	private int windowWidth12;//1/2屏幕宽度
	private int windowHeight;//屏幕高度
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
			Toast.makeText(mContext, "双击", 2000).show();
			Log.i("xk", event.getPointerCount()+" ");
		//}
		//if(event.getPointerCount() == 2)
		//Log.i("XK", "双击");
		return true;
	}*/
	
	
	
	
	public boolean onScroll(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY){
		//1.单指移动发送指针移动的消息
		//2.双指移动发送页面滚动消息
		
		velocityX = -(velocityX * sensitivity);
		velocityY = -(velocityY * sensitivity);
		//Log.i("XK", velocityX+","+velocityY);
		//Log.i("MyTAG", e2.getPointerCount()+" ");
		if(e2.getPointerCount() == 1){//1...
			pw.println("0 "+velocityX+" "+velocityY);
			
			//触发角事件
			if((e2.getX() < leftMaxX) && (e2.getY() > bottomMinY)){//触发左下角事件
				if(!isTriggering){
					Log.i("Trigger","触发左下角事件");
					isTriggering = true;
				}
					
			}else if((e2.getX() > rightMinX) && (e2.getY() > bottomMinY)){//触发右下角事件
				if(!isTriggering){
					Log.i("Trigger","触发右下角事件");
					isTriggering = true;
				}
			}else{
				isTriggering = false;
			}
			
		}else if(e2.getPointerCount() == 2){//2...
			//pw.println("1 "+velocityX+" "+velocityY);//这边的velocityX目前没有用,只是为了防止服务器端数组越界
			String str = "1 "+velocityX+" "+velocityY;
			//Log.i("2Move", str);
			pw.println(str);
		}
		
		return true;
	}
	
	public boolean onDoubleTap(MotionEvent e){
		Log.i("MYTAG", "双击屏幕坐标"+e.getX()+" , "+e.getY()+"   屏幕的一半坐标 = "+windowWidth12);
		
		if(e.getX() <= windowWidth12){
			Log.i(myTag, "双击左半屏幕");
		}else{
			Log.i(myTag,"双击右半屏幕");
		}
		Log.i("MyTAG", e.getPointerCount()+" ");
		return true;
	}
	
	@Override
	public void onLongPress(MotionEvent e){
		if(e.getX() <= windowWidth12){
			Log.i(myTag, "长按左半屏幕");
		}else{
			Log.i(myTag,"长按右半屏幕");
		}
	}
	
//	@Override
//	public void onShowPress(MotionEvent e){
//		if(e.getX() <= windowWidth12){
//			Log.i(myTag, "长按show左半屏幕");
//		}else{
//			Log.i(myTag,"长按show右半屏幕");
//		}
//	}
}
