package com.android.fukuro;

import java.util.Timer;
import java.util.TimerTask;

import com.android.fukuro.MainActivity.MainTimerTask;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Fragment2 extends Fragment {
	private Timer mainTimer;					//タイマー用
	private MainTimerTask mainTimerTask;		//タイマタスククラス
	private TextView countText;					//テキストビュー
	private int count = 0;						//カウント
	private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		Log.d("Fragment","Create1");
		
	}

	@Override
	  public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
		//テキストビュー
				this.countText = (TextView)view.findViewById(R.id.tv1);
				
				Button btnMove = (Button) view.findViewById(R.id.btn_picture_edit);
		        btnMove.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View v) {
		            	Log.d("timer","停止");
		            	mainTimer.cancel();
		            }
		        });
	    return view;
	  }
	
	
	/**
	 * タイマータスク派生クラス
	 * run()に定周期で処理したい内容を記述
	 * 
	 */
	public class MainTimerTask extends TimerTask {
		@Override
		public void run() {
			//ここに定周期で実行したい処理を記述します			
	         mHandler.post( new Runnable() {
	             public void run() {
	            	 Log.d("main","run");
	 
	                 //実行間隔分を加算処理
	            	 count += 1;
	            	 //画面にカウントを表示
	            	 countText.setText(String.valueOf(count));
	             }
	         });
		}
	}


	@Override
	public void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		Log.d("Fragment","Destroy");
	}

	@Override
	public void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
		
		//タイマーインスタンス生成
				this.mainTimer = new Timer();
				//タスククラスインスタンス生成
				this.mainTimerTask = new MainTimerTask();				
				//タイマースケジュール設定＆開始
				this.mainTimer.schedule(mainTimerTask, 1000,500);
		Log.d("Fragment","start");
	}

	@Override
	public void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Log.d("Fragment","Resume");
	}

	@Override
	public void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
		mainTimer.cancel();
		Log.d("Fragment","Pause");
	}

	@Override
	public void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
	}
	
	

}
