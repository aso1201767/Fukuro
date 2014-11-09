package com.android.fukuro;

import java.util.Timer;
import java.util.TimerTask;

import com.android.fukuro.ViewFlipperSample.MyView;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private Timer mainTimer;					//タイマー用
	private MainTimerTask mainTimerTask;		//タイマタスククラス
	private TextView countText;					//テキストビュー
	private int count = 0;						//カウント
	private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ
	
	// タッチの状態管理
		private static final int TOUCH_NONE   = 0;
		private static final int TOUCH_SINGLE = 1;
		private static final int TOUCH_MULTI  = 2;
		private int touchMode = TOUCH_NONE;
		// 画像処理
		private Matrix baseMatrix = new Matrix(); // タッチダウン時の画像保存用
		private Matrix imgMatrix = new Matrix(); //　画像変換用
		private PointF po0 = new PointF();   // 移動の開始点

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
	
	class MyView extends ImageView {
		private ScaleGestureDetector gesDetect = null;
	  
		public MyView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			setImageResource(R.drawable.cutting_on);
			setScaleType(ImageView.ScaleType.MATRIX);
			gesDetect = new ScaleGestureDetector(context, onScaleGestureListener);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int count = event.getPointerCount();
	  
			// 移動
			switch(action) {
			case MotionEvent.ACTION_DOWN:
				if(touchMode == TOUCH_NONE && count == 1) {
					Log.v("touch", "DOWN");
					po0.set(event.getX(), event.getY());
					baseMatrix.set(imgMatrix);
					touchMode = TOUCH_SINGLE;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if(touchMode == TOUCH_SINGLE) {
					Log.v("touch", "MOVE");
					// 移動処理
					imgMatrix.set(baseMatrix);
					imgMatrix.postTranslate(event.getX() - po0.x, event.getY() - po0.y);
				}
				break;
			case MotionEvent.ACTION_UP:
				if(touchMode == TOUCH_SINGLE) {
					Log.v("touch", "UP");
					touchMode = TOUCH_NONE;
				}
				break;
			}
			if(count >= 2) {
				gesDetect.onTouchEvent(event);
			}
			setImageMatrix(imgMatrix);
			return true;
		}
	  
		private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener() {

			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				imgMatrix.set(baseMatrix);
				imgMatrix.postScale(detector.getScaleFactor(), detector.getScaleFactor(),
			      detector.getFocusX(), detector.getFocusY());
				return super.onScale(detector);
			}
		
			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				Log.v("touch", "onScaleBegin");
				baseMatrix.set(imgMatrix);
				touchMode = TOUCH_MULTI;
				return super.onScaleBegin(detector);
			}
		
			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {
				// TODO Auto-generated method stub
				Log.v("touch", "onScaleEnd");
				touchMode = TOUCH_NONE;
				super.onScaleEnd(detector);
			}
			
		};
	}
}