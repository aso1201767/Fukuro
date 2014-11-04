package com.android.fukuro;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class picture_edit extends Activity {
	private penView penview;
	private Bitmap _bm;
	public static SQLiteDatabase db;
	SeekBar seekBar;
	public Canvas mcanvas;
	public RadioButton eraser;
	public RadioButton cutting;
	private void setViewId(){
		penview = (penView)findViewById(R.id.view1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_edit);
		PreferenceManager
				.getDefaultSharedPreferences(this);
		setViewId();
		
		setTitle("画像編集");
		penview.pen_mode="eraser";
		    
		File dir = new File("/data/data/com.android.fukuro/Item");
        if(dir.exists()){
            
            File file = new File(dir.getAbsolutePath()+"/Testo01.png");
            if (file.exists()) {
                    Bitmap bm2 = BitmapFactory.decodeFile(file.getPath());
                    Bitmap bm3 = Bitmap.createScaledBitmap(bm2,230,250,false);
                    ((ImageView)findViewById(R.id.imageView2)).setImageBitmap(bm3); 
            }else{
                //存在しない
            }
        }
		
		Button btn = (Button)findViewById(R.id.draw_back_btn);
		 btn.setOnClickListener(new View.OnClickListener() {
			 @Override
	        	public void onClick(View v) {
				 
				 View imageView = (View) findViewById(R.id.view1);
				  _bm = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
				  mcanvas = new Canvas(_bm);
				  mcanvas.drawColor(Color.argb(0, 0, 0, 0));
				  
				  File dir = new File("/data/data/com.android.fukuro/Item");
				    if(dir.exists()){
				        File file = new File(dir.getAbsolutePath()+"/item_all4.png");
				        if (file.exists()) {
				                Bitmap bm6 = BitmapFactory.decodeFile(file.getPath());
				                //((ImageView)findViewById(R.id.imageView1)).setImageBitmap(_bm); 
				                mcanvas.drawBitmap(bm6, 0, 0, null);
				                
				        }else{
				            //存在しない
				        }
				        
				    }
				  //mcanvas.drawColor(Color.BLACK);
				 	//一つ戻る
					penview.undo();
			 	}
		 });
		   
		   seekBar = (SeekBar)findViewById(R.id.SeekBar);
	         
	        // シークバーの初期値をTextViewに表示
	        penview.paintx=seekBar.getProgress()+10;
	        seekBar.setOnSeekBarChangeListener(
	                new OnSeekBarChangeListener() {
	                    public void onProgressChanged(SeekBar seekBar,
	                            int progress, boolean fromUser) {
	                        // ツマミをドラッグしたときに呼ばれる
	                        penview.paintx=seekBar.getProgress()+10;
	                    }
	 
	                    public void onStartTrackingTouch(SeekBar seekBar) {
	                        // ツマミに触れたときに呼ばれる
	                    }
	 
	                    public void onStopTrackingTouch(SeekBar seekBar) {
	                        // ツマミを離したときに呼ばれる
	                    }
	                }
	        );
	        
	     // ラジオグループのオブジェクトを取得
	        RadioGroup rg = (RadioGroup)findViewById(R.id.RadioGroup);
	        eraser = (RadioButton)findViewById(R.id.radioButton1);
	        cutting = (RadioButton)findViewById(R.id.radioButton2);

	        // ラジオグループのチェック状態変更イベントを登録
	        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	            // チェック状態変更時に呼び出されるメソッド
	            public void onCheckedChanged(RadioGroup group, int checkedId) {
	                // チェック状態時の処理を記述
	                // チェックされたラジオボタンオブジェクトを取得
	                RadioButton radioButton = (RadioButton)findViewById(checkedId);
	                if(radioButton.getId() == eraser.getId()) {
	                	penview.pen_mode="eraser";
	                	Log.d("ラジオボタン","消しゴムを押した");
	                }else if(radioButton.getId()==cutting.getId()){
	                	penview.pen_mode="cutting";
	                	Log.d("ラジオボタン","ハサミを押した");
	                }
	            }
	        });
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save) {
			View imageView = (View) findViewById(R.id.view1);
			  _bm = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
			  mcanvas = new Canvas(_bm);
			  mcanvas.drawColor(Color.argb(0, 0, 0, 0));
			  
			  File dir = new File("/data/data/com.android.fukuro/Item");
			    if(dir.exists()){
			        File file = new File(dir.getAbsolutePath()+"/item_all4.png");
			        if (file.exists()) {
			                Bitmap bm6 = BitmapFactory.decodeFile(file.getPath());
			                //((ImageView)findViewById(R.id.imageView1)).setImageBitmap(_bm); 
			                mcanvas.drawBitmap(bm6, 0, 0, null);
			                
			        }else{
			            //存在しない
			        }
			        
			    }
			ArrayList<Path> draw_list=penview.capview();
			ArrayList<Float> paint_width = penview.paint_width();
			Paint paint = new Paint();
			//paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR));
		    paint.setColor(Color.RED);
			 //paint.setAntiAlias(true);
			 paint.setStyle(Paint.Style.STROKE);
			 paint.setStrokeWidth(10);
			 paint.setStrokeCap(Paint.Cap.ROUND);
			 paint.setStrokeJoin(Paint.Join.ROUND);
			 
			 for (int i = 0; i < draw_list.size(); i++) {
				 System.out.println("onDraw called.");
				 Path pt = draw_list.get(i);
				 Float paint_w = paint_width.get(i);
				 paint.setStrokeWidth(paint_w);
				 mcanvas.drawPath(pt, paint);//全ての線を描く
				 Log.d("paint","01");
			 }
			 int width = _bm.getWidth();
			 int height = _bm.getHeight();
			 int pixels[] = new int[width * height];

			 _bm.getPixels(pixels, 0, width, 0, 0, width, height);

			 for (int y = 0; y < height; y++) {
			     for (int x = 0; x < width; x++) {
			         int pixel = pixels[x + y * width];
			         if(pixel==Color.RED){
			        	 pixels[x + y * width] = 0;
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
			 
			 _bm.setPixels(pixels, 0, width, 0, 0, width, height);
			saveBitmapToSd(_bm);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void saveBitmapToSd(Bitmap mBitmap) {
		
		 try {
		 // sdcardフォルダを指定
		 File root = new File("/data/data/com.android.fukuro/Item");

		 // 保存処理開始
		 FileOutputStream fos = null;
		 fos = new FileOutputStream(new File(root, "test02.png"));

		 // jpegで保存
		 mBitmap.compress(CompressFormat.PNG, 100, fos);

		 // 保存処理終了
		 fos.close();
		 } catch (Exception e) {
		 Log.e("Error", "" + e.toString());
		 }
		}
}
