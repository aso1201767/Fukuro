package com.android.fukuro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class CustomImageView extends ImageView {
	private ScaleGestureDetector gesDetect = null;
	// タッチの状態管理
		private static final int TOUCH_NONE   = 0;
		private static final int TOUCH_SINGLE = 1;
		private static final int TOUCH_MULTI  = 2;
		private int touchMode = TOUCH_NONE;
		// 画像処理
		private Matrix baseMatrix = new Matrix(); // タッチダウン時の画像保存用
		private Matrix imgMatrix = new Matrix(); //　画像変換用
		private PointF po0 = new PointF();   // 移動の開始点
  
	public CustomImageView(Context context) {
		super(context);
		
		// TODO Auto-generated constructor stub
		setImageResource(R.drawable.cutting_on);
		setScaleType(ImageView.ScaleType.MATRIX);
		gesDetect = new ScaleGestureDetector(context, onScaleGestureListener);
	}
	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setImageResource(R.drawable.cutting_on);
		Log.d("Image", "セット");
		setScaleType(ImageView.ScaleType.MATRIX);
		Log.d("scaletype", "セット");
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