package com.android.fukuro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import android.widget.Toast;
import android.os.Environment;
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
	private Bitmap capturedImage;
	String previousview=null;//前画面
	String camerapath=null;
	FileInputStream in = null;
	String picname=null;
	String info=null;	

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
		setScaleType(ImageView.ScaleType.MATRIX);
		gesDetect = new ScaleGestureDetector(context, onScaleGestureListener);
	}

	protected void onDraw(Canvas canvas) {
		Log.i("test","onDraw");
//		if(previousview.equals("camera")){
			try {
				in = new FileInputStream(camerapath);
			} catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
//		}else{
//		}
		super.onDraw(canvas);
		if (pen_mode == "move") {
			//setImageBitmap(result);
		} else {
			
			canvas.drawColor(Color.argb(255, 0, 0, 0));
			Resources r = getResources();
//			Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.cutting_on);
//			source = BitmapFactory.decodeResource(r, R.drawable.cutting);
//			mbitmap = BitmapFactory.decodeResource(r, R.drawable.cutting_on);
			BitmapFactory.Options options= new BitmapFactory.Options();
			if(info.equals("pic")){
				options.inSampleSize = 5;	
			}else{
				options.inSampleSize = 1;
			}
			
			source= BitmapFactory.decodeStream(in,null,options);
			canvas.drawBitmap(source, imgMatrix, null);

			
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
				Log.d("","result10="+imgMatrix);
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

	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getAction() & MotionEvent.ACTION_MASK;
		int count = e.getPointerCount();
		if(result==null){
			setImageBitmap(source);
		}else{
			setImageBitmap(result);
		}

		switch (action) {
		case MotionEvent.ACTION_DOWN://タッチイベント開始
			if (pen_mode == "move") {//移動
				if (touchMode == TOUCH_NONE && count == 1) {
					po0.set(e.getX(), e.getY());
					baseMatrix.set(imgMatrix);
					touchMode = TOUCH_SINGLE;
				}
			} else {//消しゴム・切り取り
				path = new Path();
				nowpath= new Path();
				posx = e.getX();
				posy = e.getY();
				path.moveTo(e.getX()-po1.x, e.getY()-po1.y);
				nowpath.moveTo(e.getX(), e.getY());
				flg = true;
			}
			break;
		case MotionEvent.ACTION_MOVE://タッチイベント処理
			if (pen_mode == "move") {//移動
				if (touchMode == TOUCH_SINGLE) {
					// 移動処理
					imgMatrix.set(baseMatrix);
					imgMatrix.postTranslate(e.getX() - po0.x, e.getY()
							- po0.y);
				}
			} else {//消しゴム・切り取り
				posx += (e.getX() - posx) / 1.4;
				posy += (e.getY() - posy) / 1.4;
				path.lineTo(posx-po1.x, posy-po1.y);
				nowpath.lineTo(posx, posy);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP://タッチイベント終了
			if (pen_mode == "move") {//移動
				if (touchMode == TOUCH_SINGLE) {
					touchMode = TOUCH_NONE;
					po1.x+=e.getX()-po0.x;
					po1.y+=e.getY()-po0.y;
				}
			} else {//消しゴム・切り取り
				path.lineTo(e.getX()-po1.x, e.getY()-po1.y);
				nowpath.lineTo(e.getX(), e.getY());
				draw_list.add(path);
				draw_paint_list.add(paintx);
				draw_paint_mode.add(pen_mode);
				draw_matrix.add(imgMatrix);
				invalidate();
			}
			break;
		}
		if (count >= 2) {
			gesDetect.onTouchEvent(e);
		}
		setImageMatrix(imgMatrix);
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

	//拡大・縮小
	private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener() {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			imgMatrix.set(baseMatrix);
			imgMatrix.postScale(detector.getScaleFactor(),
					detector.getScaleFactor(), detector.getFocusX(),
					detector.getFocusY());
			return super.onScale(detector);
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			baseMatrix.set(imgMatrix);
			touchMode = TOUCH_MULTI;
			return super.onScaleBegin(detector);
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			touchMode = TOUCH_NONE;
			super.onScaleEnd(detector);
		}
	};
	
	//保存処理
	public void saveBitmapToSd() {
		if(draw_list.size()!=0){
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
		         }
		         if(pixel!=0 && inputh<y){
		        	 inputh=y;
		         }
		     }
		 }
		 
		 result.setPixels(pixels, 0, width, 0, 0, width, height);
		 robot2= Bitmap.createBitmap(inputw+10, inputh+10, Config.ARGB_8888);
		 scv = new Canvas(robot2);
		 scv.drawBitmap(result, 0, 0, paint);
		 
		 
		
		 try {
			//sd画像削除
//				File file = new File(Environment.getExternalStorageDirectory() + "/Item/"+picname);
//				file.delete();
//				Log.d("testo01","file="+file);
		 // sdcardフォルダを指定
			// File root = new File("/data/data/com.android.fukuro/Item/");
		 File root = new File(Environment.getExternalStorageDirectory() + "/Item/");

		 // 保存処理開始
		 FileOutputStream fos = null;
		 fos = new FileOutputStream(new File(root,picname));
		 

		 // jpegで保存
		 robot2.compress(CompressFormat.PNG, 100, fos);

		 // 保存処理終了
		 fos.close();
		 } catch (Exception e) {
		 Log.e("Error", "" + e.toString());
		 }
	}
	}

}