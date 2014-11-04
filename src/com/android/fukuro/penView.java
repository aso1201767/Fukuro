package com.android.fukuro;

import java.io.File;
import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap.Config;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.view.*;
import android.util.*;
 
public class penView extends View{
	private ArrayList<Path> draw_list = new ArrayList<Path>();
	private ArrayList<Float> draw_paint_list = new ArrayList<Float>();
	private ArrayList<String> draw_paint_mode = new ArrayList<String>();
	private float posx = 0f;
	private float posy = 0f;
	private Path path;
	private boolean flg=true;
	float paintx;
	String pen_mode;
	private Paint paint;
	private Paint p;
	private boolean paint_f=true;
	public Bitmap mbitmap;
	private Canvas mcanvas;//描画キャンパス
	private Bitmap source;//画像
	private Bitmap result;//描画領域
	private boolean f=true;
	
  public penView(Context context, AttributeSet attrs){ 
    super(context,attrs);
    setFocusable(true);
    Log.d("context","context="+context);
    Log.d("context","attrs="+attrs);
  }
   
  protected void onDraw(Canvas canvas){
    super.onDraw(canvas);
    canvas.drawColor(Color.argb(255, 0, 0, 0));
    File dir = new File("/data/data/com.android.fukuro/Item");
    if(dir.exists()){
        File file = new File(dir.getAbsolutePath()+"/item_all4.png");
        if (file.exists()) {
                Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
                mbitmap = BitmapFactory.decodeFile(file.getPath());
                source =BitmapFactory.decodeFile(file.getPath());
                //((ImageView)findViewById(R.id.imageView1)).setImageBitmap(_bm); 
                canvas.drawBitmap(_bm, 0, 0, null);
        }else{
            //存在しない
        }
    }
    Resources r = getResources();
    Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.cutting_on);
    source=BitmapFactory.decodeResource(r, R.drawable.cutting_on);
    mbitmap=BitmapFactory.decodeResource(r, R.drawable.cutting_on);
    canvas.drawBitmap(bmp, 0, 0, null);
    if(draw_list.size()==0){
		 f=true;
	 }
    if(paint_f){
     paint = new Paint();
	 paint.setARGB(255, 0, 0, 0); 
	 paint.setStrokeWidth(paintx);
	 paint.setStrokeCap(Paint.Cap.ROUND);
	 paint.setStrokeJoin(Paint.Join.ROUND);
	// paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.XOR));
	 Log.d("penview",""+pen_mode);
	 //現在、描画した線のPaint
	 p = new Paint();
	 p.setARGB(255, 0, 0, 0);
	 p.setStyle(Paint.Style.STROKE);
	 p.setStrokeCap(Paint.Cap.ROUND);
	 p.setStrokeJoin(Paint.Join.ROUND);
	 
	 paint_f=false;
    }
    if(!f){
    result = Bitmap.createBitmap(source.getWidth(),source.getHeight(),Config.ARGB_8888);
    mcanvas = new Canvas(result);
    //mcanvas.drawColor(Color.argb(255, 0, 0, 0));
    }
    Boolean cutting_flg=false;
	 for (int i = 0; i < draw_list.size(); i++) {//切り取りのペイントを描画する
		 System.out.println("onDraw called.");
		 Path pt = draw_list.get(i);
		 float x = draw_paint_list.get(i);
		 String paint_mode = draw_paint_mode.get(i);
		 if(paint_mode=="cutting"){
			 paint.setStyle(Paint.Style.FILL_AND_STROKE);
			 paint.setStrokeWidth(x);
			 mcanvas.drawPath(pt, paint);//全ての線を描く
			 cutting_flg=true;
		 }
	 }
	 if(!f){
		 if(cutting_flg){
				 paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
				 mcanvas.drawBitmap(source, 0, 0,paint);
				 paint.setXfermode(null);	 
		 }else{
			 mcanvas.drawBitmap(source, 0, 0,paint);
		 }
	 }
	for (int i = 0; i < draw_list.size(); i++) {//消しゴムのペイントを描画する
		 System.out.println("onDraw called.");
		 Path pt = draw_list.get(i);
		 float x = draw_paint_list.get(i);
		 String paint_mode = draw_paint_mode.get(i);
		 if(paint_mode=="eraser"){
			 paint.setStyle(Paint.Style.STROKE);
			 paint.setStrokeWidth(x);
			 mcanvas.drawPath(pt, paint);//全ての線を描く
		 }
	 }
	 
	 if(!f){ 
		 canvas.drawColor(Color.argb(255, 0, 0, 0)); 
		 canvas.drawBitmap(result, 0, 0, null);
	 }
	//current
		 if(flg){
			 if(path != null){
				 //paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR));
				 p.setStrokeWidth(paintx);
				 canvas.drawPath(path, p);//現在の線を描く
			 }
		 }
	 
	 if(f){
	 f=false;
	 }
  }
  
  public boolean onTouchEvent(MotionEvent e){
		 switch(e.getAction()){
		 case MotionEvent.ACTION_DOWN: //最初のポイント
		 path = new Path();
		 posx = e.getX();
		 posy = e.getY();
		 path.moveTo(e.getX(), e.getY());
		 flg=true;
		 break;
		 case MotionEvent.ACTION_MOVE: //途中のポイント
		 posx += (e.getX()-posx)/1.4;
		 posy += (e.getY()-posy)/1.4;
		 path.lineTo(posx, posy);
		 invalidate();
		 break;
		 case MotionEvent.ACTION_UP: //最後のポイント
		 path.lineTo(e.getX(), e.getY());
		 draw_list.add(path);
		 draw_paint_list.add(paintx);
		 draw_paint_mode.add(pen_mode);
		 invalidate();
		 break;
		 default:
		 break;
		 }
		 return true;
  }
  
  //一手戻る
  public void undo(){
	  int size = draw_list.size()-1;
	  int size2 = draw_paint_list.size()-1;
	  int size3 = draw_paint_mode.size()-1;
	  if(size >= 0 && size2 >= 0 && size3 >= 0){
		  draw_list.remove(size);
		  draw_paint_list.remove(size2);
		  draw_paint_mode.remove(size3);
		  flg=false;
		  invalidate();
	  }
	  return;
  }
  
  public ArrayList<Path> capview(){
	  return draw_list;
  }
  public ArrayList<Float> paint_width(){
	  return draw_paint_list;
  }

}