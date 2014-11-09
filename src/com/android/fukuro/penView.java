package com.android.fukuro;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuffXfermode;
import android.view.*;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.util.*;

public class penView extends ImageView {
	private ArrayList<Path> draw_list = new ArrayList<Path>();
	private ArrayList<Float> draw_paint_list = new ArrayList<Float>();
	private ArrayList<String> draw_paint_mode = new ArrayList<String>();
	private ArrayList<Matrix> draw_matrix= new ArrayList<Matrix>();
	private float posx = 0f;
	private float posy = 0f;
	private Path path;
	private Path nowpath;
	private boolean flg = true;
	float paintx;
	String pen_mode;
	private Paint paint;
	private Paint p;
	private boolean paint_f = true;
	public Bitmap mbitmap;
	private Canvas mcanvas;// 描画キャンパス
	private Bitmap source;// 画像
	private Bitmap result;// 描画領域
	private boolean f = true;
	//private Canvas scanvas;
	private Bitmap sresult;
	private Matrix amt;
	private boolean imagef=true;

	// タッチの状態管理
	private static final int TOUCH_NONE = 0;
	private static final int TOUCH_SINGLE = 1;
	private static final int TOUCH_MULTI = 2;
	private int touchMode = TOUCH_NONE;
	// 画像処理
	private Matrix baseMatrix = new Matrix(); // タッチダウン時の画像保存用
	private Matrix imgMatrix = new Matrix(); // 　画像変換用
	private PointF po0 = new PointF(); // 移動の開始点
	private PointF po1 = new PointF();

	private ScaleGestureDetector gesDetect = null;

	public penView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setImageResource(R.drawable.cutting_on);
		Log.d("Image", "セット");
		setScaleType(ImageView.ScaleType.MATRIX);
		Log.d("scaletype", "セット");
		gesDetect = new ScaleGestureDetector(context, onScaleGestureListener);
	}

	

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (pen_mode == "move") {
			//setImageBitmap(result);
			Log.d("onDraw", "move");
		} else {
			Log.d("onDraw", "cutting");
			canvas.drawColor(Color.argb(255, 0, 0, 0));
			File dir = new File("/data/data/com.android.fukuro/Item");
			if (dir.exists()) {
				File file = new File(dir.getAbsolutePath() + "/item_all4.png");
				if (file.exists()) {
					Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
					mbitmap = BitmapFactory.decodeFile(file.getPath());
					source = BitmapFactory.decodeFile(file.getPath());
					// ((ImageView)findViewById(R.id.imageView1)).setImageBitmap(_bm);
					canvas.drawBitmap(_bm, 0, 0, null);
				} else {
					// 存在しない
				}
			}
			Resources r = getResources();
			//Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.cutting_on);
			//source = BitmapFactory.decodeResource(r, R.drawable.cutting_on);
			//mbitmap = BitmapFactory.decodeResource(r, R.drawable.cutting_on);
			//canvas.drawBitmap(bmp, 0, 0, null);
			if (draw_list.size() == 0) {
				f = true;
			}
			if (paint_f) {
				paint = new Paint();
				paint.setARGB(255, 0, 0, 0);
				paint.setStrokeWidth(paintx);
				paint.setStrokeCap(Paint.Cap.ROUND);
				paint.setStrokeJoin(Paint.Join.ROUND);
				// paint.setXfermode(new
				// PorterDuffXfermode(android.graphics.PorterDuff.Mode.XOR));
				Log.d("penview", "" + pen_mode);
				// 現在、描画した線のPaint
				p = new Paint();
				p.setARGB(255, 0, 0, 0);
				p.setStyle(Paint.Style.STROKE);
				p.setStrokeCap(Paint.Cap.ROUND);
				p.setStrokeJoin(Paint.Join.ROUND);

				paint_f = false;
			}
			if (!f) {
				int h=canvas.getHeight();
				int w=canvas.getWidth();
				result = Bitmap.createBitmap(w,
						h, Config.ARGB_8888);
				mcanvas = new Canvas(result);
				// mcanvas.drawColor(Color.argb(255, 0, 0, 0));
			}
			Boolean cutting_flg = false;
			for (int i = 0; i < draw_list.size(); i++) {// 切り取りのペイントを描画する
				System.out.println("onDraw called.");
				Path pt = draw_list.get(i);
				float x = draw_paint_list.get(i);
				String paint_mode = draw_paint_mode.get(i);
				if (paint_mode == "cutting") {
					paint.setStyle(Paint.Style.FILL_AND_STROKE);
					paint.setStrokeWidth(x);
					mcanvas.drawPath(pt, paint);// 全ての線を描く
					cutting_flg = true;
				}
			}
			if (!f) {
				if (cutting_flg) {
					paint.setXfermode(new PorterDuffXfermode(
							android.graphics.PorterDuff.Mode.SRC_IN));
					mcanvas.drawBitmap(source, 0,0, paint);
					paint.setXfermode(null);
				} else {
					mcanvas.drawBitmap(source, 0,0, paint);
				}
			}
			for (int i = 0; i < draw_list.size(); i++) {// 消しゴムのペイントを描画する
				
				System.out.println("onDraw called.");
				Path pt = draw_list.get(i);

				float x = draw_paint_list.get(i);
				String paint_mode = draw_paint_mode.get(i);
				if (paint_mode == "eraser") {
					paint.setStyle(Paint.Style.STROKE);
					paint.setStrokeWidth(x);
					mcanvas.drawPath(pt, paint);// 全ての線を描く
				}
			}

			if (!f) {
				canvas.drawColor(Color.argb(255, 0, 0, 0));
				canvas.drawBitmap(result, imgMatrix, null);
				sresult=result;
			}
			// current
			if (flg) {
				if (nowpath != null) {
					// paint.setXfermode(new
					// PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR));
					p.setStrokeWidth(paintx);
					canvas.drawPath(nowpath, p);// 現在の線を描く
				}
			}

			if (f) {
				f = false;
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		// switch(e.getAction()){
		// case MotionEvent.ACTION_DOWN: //最初のポイント
		// path = new Path();
		// posx = e.getX();
		// posy = e.getY();
		// path.moveTo(e.getX(), e.getY());
		// flg=true;
		// break;
		// case MotionEvent.ACTION_MOVE: //途中のポイント
		// posx += (e.getX()-posx)/1.4;
		// posy += (e.getY()-posy)/1.4;
		// path.lineTo(posx, posy);
		// invalidate();
		// break;
		// case MotionEvent.ACTION_UP: //最後のポイント
		// path.lineTo(e.getX(), e.getY());
		// draw_list.add(path);
		// draw_paint_list.add(paintx);
		// draw_paint_mode.add(pen_mode);
		// invalidate();
		// break;
		// default:
		// break;
		// }
		// return true;
		// TODO Auto-generated method stub
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		int count = event.getPointerCount();

		// 移動
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (pen_mode == "move") {
				if(result==null){
					setImageBitmap(source);
				}else{
					setImageBitmap(result);
				}
				if (touchMode == TOUCH_NONE && count == 1) {
					Log.v("touch", "DOWN");
					Log.v("onScale", "1=" + baseMatrix);
					Log.v("onScale", "2=" + imgMatrix);
					po0.set(event.getX(), event.getY());
					baseMatrix.set(imgMatrix);
					touchMode = TOUCH_SINGLE;
				}
			} else {
				path = new Path();
				nowpath= new Path();
				posx = event.getX();
				posy = event.getY();
				path.moveTo(event.getX()-po1.x, event.getY()-po1.y);
				nowpath.moveTo(event.getX(), event.getY());
				flg = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (pen_mode == "move") {
				if (touchMode == TOUCH_SINGLE) {
					Log.v("touch", "MOVE");
					Log.v("onScale", "3=" + baseMatrix);
					Log.v("onScale", "4=" + imgMatrix);
					// 移動処理
					imgMatrix.set(baseMatrix);
					imgMatrix.postTranslate(event.getX() - po0.x, event.getY()
							- po0.y);
				}
			} else {
				posx += (event.getX() - posx) / 1.4;
				posy += (event.getY() - posy) / 1.4;
				path.lineTo(posx-po1.x, posy-po1.y);
				nowpath.lineTo(posx, posy);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			if (pen_mode == "move") {
				if (touchMode == TOUCH_SINGLE) {
					Log.v("touch", "UP");
					Log.v("onScale", "5=" + baseMatrix);
					Log.v("onScale", "6=" + imgMatrix);
					touchMode = TOUCH_NONE;
					po1.x+=event.getX()-po0.x;
					po1.y+=event.getY()-po0.y;
					Log.d("onScale","7="+po1.x+"8="+po1.y);
				}
			} else {
				path.lineTo(event.getX()-po1.x, event.getY()-po1.y);
				nowpath.lineTo(event.getX(), event.getY());
				draw_list.add(path);
				draw_paint_list.add(paintx);
				draw_paint_mode.add(pen_mode);
				draw_matrix.add(imgMatrix);
				invalidate();
			}
			break;
		}
		if (count >= 2) {
			gesDetect.onTouchEvent(event);
		}
		setImageMatrix(imgMatrix);
		Log.e("img", "imgMatrix" + imgMatrix);
		return true;
	}

	// 一手戻る
	public void undo() {
		int size = draw_list.size() - 1;
		int size2 = draw_paint_list.size() - 1;
		int size3 = draw_paint_mode.size() - 1;
		int size4 = draw_matrix.size()-1;
		if (size >= 0 && size2 >= 0 && size3 >= 0 && size>=0) {
			draw_list.remove(size);
			draw_paint_list.remove(size2);
			draw_paint_mode.remove(size3);
			draw_matrix.remove(size4);
			flg = false;
			invalidate();
		}
		return;
	}

	public ArrayList<Path> capview() {
		return draw_list;
	}

	public ArrayList<Float> paint_width() {
		return draw_paint_list;
	}

	private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener() {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			Log.d("onScale", "1");
			imgMatrix.set(baseMatrix);
			imgMatrix.postScale(detector.getScaleFactor(),
					detector.getScaleFactor(), detector.getFocusX(),
					detector.getFocusY());
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
	public void saveBitmapToSd() {
		int width = result.getWidth();
		 int height = result.getHeight();
		 int pixels[] = new int[width * height];
		 int inputw=0;
		 int inputh=0;
		 Bitmap robot2;
		 Canvas scv;

		 result.getPixels(pixels, 0, width, 0, 0, width, height);

		 for (int y = 0; y < height; y++) {
		     for (int x = 0; x < width; x++) {
		         int pixel = pixels[x + y * width];
		         if(pixel==Color.BLACK){
		        	 pixels[x + y * width] = 0;
		         }
		         if(pixel!=0 && inputw<x){
		        	 inputw=x;
		        	 Log.d("pixels","whdth="+inputw);
		         }
		         if(pixel!=0 && inputh<y){
		        	 inputh=y;
		        	 Log.d("pixels","height="+inputh);
		         }
		         //if(pixel==0){
		        	// pixels[x+y*width]=Color.GREEN;
		         //}
		         

		        // pixels[x + y * width] = 0;//Color.argb(
		                 //Color.alpha(pixel),
		                 //0xFF - Color.red(pixel),
		                 //0xFF - Color.green(pixel),
		                 //0xFF - Color.blue(pixel));
		         //if(x==15||x==16||x==17||x==18||x==19||x==20||x==21||x==22||x==23||x==24||x==25||x==26||x==27||x==28||x==29||x==30||x==31){
		        	// pixels[x + y * width]=0;
		        // }
		     }
		 }
		 
		 result.setPixels(pixels, 0, width, 0, 0, width, height);
		 robot2= Bitmap.createBitmap(inputw+10, inputh+10, Config.ARGB_8888);
		 scv = new Canvas(robot2);
		 scv.drawBitmap(result, 0, 0, paint);
		
		 try {
		 // sdcardフォルダを指定
		 File root = new File("/data/data/com.android.fukuro/Item");

		 // 保存処理開始
		 FileOutputStream fos = null;
		 fos = new FileOutputStream(new File(root, "test02.png"));

		 // jpegで保存
		 robot2.compress(CompressFormat.PNG, 100, fos);

		 // 保存処理終了
		 fos.close();
		 } catch (Exception e) {
		 Log.e("Error", "" + e.toString());
		 }
	}

}