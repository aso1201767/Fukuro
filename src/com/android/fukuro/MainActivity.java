package com.android.fukuro;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private Timer mainTimer;					//タイマー用
	private MainTimerTask mainTimerTask;		//タイマタスククラス
	private TextView countText;					//テキストビュー
	private int count = 0;						//カウント
	private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//タイマーインスタンス生成
		this.mainTimer = new Timer();
		//タスククラスインスタンス生成
		this.mainTimerTask = new MainTimerTask();				
		//タイマースケジュール設定＆開始
		this.mainTimer.schedule(mainTimerTask, 1000,500);
		//テキストビュー
		this.countText = (TextView)findViewById(R.id.tv1);
		
		Button btnMove = (Button) findViewById(R.id.btn_picture_edit);

        btnMove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	Log.d("timer","停止");
            	mainTimer.cancel();
            }
        });
			 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
}