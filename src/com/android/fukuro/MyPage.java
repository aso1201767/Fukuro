package com.android.fukuro;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MyPage extends Fragment{
	 private TabLayout parent;
	 private static final String TAG = "myTag";
	 private int selectedIndex = 0;
	 private MyHandler handler;
     public static SQLiteDatabase db;
	 private ArrayList<String> coordename = new ArrayList<String>();
	 /** Called when the activity is first created. */
	 private boolean firstflg = false;
	 private String topimage; 
	 private File dir = new File("/data/data/com.android.fukuro/Item");
	 private Timer mainTimer;					//タイマー用
	 private MyTask mainTimerTask;		//タイマタスククラス
	 private MyHandler mHandler = new MyHandler();   //UI Threadへのpost用ハンドラ
	 private Bitmap _bm1=null;
	 private Bitmap _bm2=null;
	 private Bitmap _bm3=null;
	 private Bitmap _bm4=null;
	 
	 class MyTask extends TimerTask {
		 public void run() {
			mHandler.post(new Runnable(){
				public void run(){	 
					//繰り返し処理
					Log.d("main","run");
					if(firstflg){
						selectedIndex = selectedIndex + 3;
					}else{
						firstflg = true;
					}
					if(coordename.size()!=0){
					selectedIndex = selectedIndex % coordename.size();
					}
					Log.d("タブ", "selectedIndex = " + selectedIndex);
					Message msg1 = new Message();
					msg1.what = selectedIndex;
					handler.sendMessage(msg1);	
				}
			});
		 }
	 }
	 
	 @Override
	public void onStop(){
	    super.onStop();
	    
	}
	

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.mypage, container, false);
	        coordename =parent.mypage_db();
	        topimage = parent.mypage_topimage();
	        //if(_bm2!=null){
	       // if(sbm2!=null){
//	        	parent.setimage(R.id.imageView3,_bm2);
//	        }
	        //}
	        return view;
	    }
	    
	    @Override
		public void onStart() {
			// TODO 自動生成されたメソッド・スタブ
			super.onStart();
			Log.d("Mapage", "onStart");
			if(coordename!=null){
			 handler = new MyHandler();
		     Log.d("mTimer","開始");
	         //タイマーの初期化処理
		     this.mainTimer = new Timer();
			//タスククラスインスタンス生成
			this.mainTimerTask = new MyTask();				
			//タイマースケジュール設定＆開始
			this.mainTimer.schedule(mainTimerTask, 0,6000);
	         Log.d("Fragment","start");
			}
	         
	    }


		@Override
		public void onResume() {
			// TODO 自動生成されたメソッド・スタブ
			super.onResume();
			Log.d("Mapage", "onResume");
			if(_bm1!=null){
				Log.d("Mypage","topimage="+_bm1);
				parent.setimage(R.id.topimage,_bm1);
			}else{
				if(dir.exists()){
     	            File file = new File(Environment.getExternalStorageDirectory() + "/Item/" + topimage);
     	            Log.d("mypage","top="+topimage);
     	            if (file.exists()) {
     	                    _bm1 = BitmapFactory.decodeFile(file.getPath());
     	                    _bm1 = Bitmap.createScaledBitmap(_bm1, 400, 500, false);
     	                    parent.setimage(R.id.topimage,_bm1);
     	            }else{
     	                //存在しない
     	            }
     	        } 
			}
			if(_bm2!=null){
				parent.setimage(R.id.imageView3,_bm2);
			}
			if(_bm3!=null){
				parent.setimage(R.id.imageView4,_bm3);
			}
			if(_bm4!=null){
				parent.setimage(R.id.imageView2,_bm4);
			}
			
		}


		@Override
		public void onPause() {
			// TODO 自動生成されたメソッド・スタブ
			super.onPause();
			mainTimer.cancel();
			Log.d("Mapage", "onPause");
		}

		
		class MyHandler extends Handler{
        	@Override
        	public void handleMessage(Message msg1){
        		Log.d(TAG, "handleMessage:" + msg1.what);
//        		if(_bm1==null){
//	        		 if(dir.exists()){
//	     	            File file = new File(dir.getAbsolutePath()+ "/" + topimage);
//	     	            if (file.exists()) {
//	     	                    _bm1 = BitmapFactory.decodeFile(file.getPath());
//	     	                    _bm1 = Bitmap.createScaledBitmap(_bm1, 400, 500, false);
//	     	                    parent.setimage(R.id.topimage,_bm1);
//	     	            }else{
//	     	                //存在しない
//	     	            }
//	     	        } 
//        		}
  
        		if(coordename.size()!=0){
	    	        int index1 = msg1.what;
	        		index1 = index1 % coordename.size();
	    	        if(dir.exists()){
	    	            File file = new File(Environment.getExternalStorageDirectory() + "/Item/"+ coordename.get(index1));
	    	            if (file.exists()) {
	    	                    _bm2 = BitmapFactory.decodeFile(file.getPath());
	    	                    _bm2 = Bitmap.createScaledBitmap(_bm2, 230, 250, false);
	    	                    parent.setimage(R.id.imageView3,_bm2);
	    	            }else{
	    	                //存在しない
	    	            }
	    	        }  
        		}
        		if(coordename.size()!=0){
	    	        int index2 = msg1.what;
	    	        index2 = index2 + 1;
	        		index2 = index2 % coordename.size();
	    	        if(dir.exists()){
	    	            File file = new File(Environment.getExternalStorageDirectory() + "/Item/" + coordename.get(index2));
	    	            if (file.exists()) {
	    	                    _bm3 = BitmapFactory.decodeFile(file.getPath());
	    	                    _bm3 = Bitmap.createScaledBitmap(_bm3, 230, 250, false);
	    	                    parent.setimage(R.id.imageView4,_bm3);
	    	            }else{
	    	                //存在しない
	    	            }
	    	        } 
        		}
        		if(coordename.size()!=0){
	    	        int index3 = msg1.what;
	        		index3 = index3 + 2;
	        		index3 = index3 % coordename.size();
	    	        if(dir.exists()){
	    	        	File file = new File(Environment.getExternalStorageDirectory() + "/Item/" + coordename.get(index3));
	    	            if (file.exists()) {
	    	                    _bm4 = BitmapFactory.decodeFile(file.getPath());
	    	                    _bm4 = Bitmap.createScaledBitmap(_bm4, 230, 250, false);
	    	                    parent.setimage(R.id.imageView2,_bm4);
	    	            }else{
	    	                //存在しない
	    	            }
	    	        }
        		}
        	}
        }
	    

	    @Override
	    public void onAttach(Activity activity) {
	        parent = (TabLayout) activity;
	        super.onAttach(activity);
	    }

}
