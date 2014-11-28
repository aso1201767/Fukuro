package com.android.fukuro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;



public class Coordinate extends Activity implements View.OnClickListener  {

	private DBHelper dbHelper = new DBHelper(this);

	public static SQLiteDatabase db;
    private Bitmap mBitmap;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    int flag = 1;
	File dir =new File("/data/data/com.android.fukuro/Item");
	private RelativeLayout Rela;
	 String MylistID = null;
	 String ItemID = "1";
	 View view;
	 String ItemID1=null;
	 String ItemID2=null;
	 String ItemID3=null;
	 String ItemID4=null;
	 String ItemID5=null;
	 String ItemID6=null;
	 String ItemID7=null;
	 
	 String returnValue=null;
	 
	 private static final int SUB_ACTIVITY = 1001;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coordinate);
		Log.i("coor","onCreate");

        super.onCreate(savedInstanceState);
//    	addContentView(Rela, new LayoutParams(FC, FC));
        Rela = (RelativeLayout)findViewById(R.id.rela);

	    ImageButton imgbutton = (ImageButton)findViewById(R.id.addbtn);
        Button btn1 =(Button)findViewById(R.id.btn1);
       	imgbutton.setOnClickListener(this);
       	btn1.setOnClickListener(this);

		//読み書き可能なデータベースをオープン
		// 読み取り専用の場合はgetReadableDatabase()を用いる
		db=dbHelper.getReadableDatabase();
		Window window = getWindow();
		view = window.getDecorView();
		view.setDrawingCacheEnabled(true);

	}
	
//	    imgbutton.setOnClickListener(new OnClickListener() {

//	private void setContentView(SampleView mView2) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		dbHelper.close();
		Log.i("coor","onDestroy");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}


//	addContentView(relativeLayout, new LayoutParams(FC, FC));
	RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(WC, WC);
	RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(WC, WC);
	RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(WC, WC);
	RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(WC, WC);
	RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(WC, WC);
	RelativeLayout.LayoutParams param5 = new RelativeLayout.LayoutParams(WC, WC);
	RelativeLayout.LayoutParams param6 = new RelativeLayout.LayoutParams(WC, WC);
	RelativeLayout.LayoutParams param7 = new RelativeLayout.LayoutParams(WC, WC);
	int a = 0;
	int b = 0;
	int c = 0;
	int d = 0;
	int a1 = 0;
	int b1 = 0;
	int c1 = 0;
	int d1 = 0;
	int a2 = 0;
	int b2 = 0;
	int c2 = 0;
	int d2 = 0;
	int a3 = 0;
	int b3 = 0;
	int c3 = 0;
	int d3 = 0;
	int a4 = 0;
	int b4 = 0;
	int c4 = 0;
	int d4 = 0;
	int a5 = 0;
	int b5 = 0;
	int c5 = 0;
	int d5 = 0;
	int a6 = 0;
	int b6 = 0;
	int c6 = 0;
	int d6 = 0;

	@Override
	public void onClick(View v) {
		ImageView img1 = (ImageView)findViewById(R.id.imageView1);
		 ImageView img2 = (ImageView)findViewById(R.id.imageView2);
		 ImageView img3 = (ImageView)findViewById(R.id.imageView3);
		 ImageView img4 = (ImageView)findViewById(R.id.imageView4);
		 ImageView img5 = (ImageView)findViewById(R.id.imageView5);
		 ImageView img6 = (ImageView)findViewById(R.id.imageView6);
		 ImageView img7 = (ImageView)findViewById(R.id.imageView7);
    switch(v.getId()){

         case R.id.addbtn:

    	 if (flag == 1){
    		 Intent intent = new Intent(getApplicationContext(),ItemList.class);
    		 startActivityForResult(intent, SUB_ACTIVITY);
    		
//			if(dir.exists()){
//				File file = new File(dir.getAbsolutePath()+"/item_jacket.jpg");
//				if(file.exists()){
//				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
//				((ImageView)findViewById(R.id.imageView1)).setImageBitmap(_bm);
//				}
//			}
//			// ドラッグ対象Viewとイベント処理クラスを紐付ける
//	        ImageView dragView = (ImageView) findViewById(R.id.imageView1);
//			DragViewListener listener = new DragViewListener(dragView);
//			dragView.setOnTouchListener(listener);

    	 }else if(flag == 2){
    		 Intent intent = new Intent(getApplicationContext(),ItemList.class);
    		 startActivityForResult(intent, SUB_ACTIVITY);

// 	 		 a =  img1.getLeft()-32;
// 	 		 b =img1.getTop()-32;
// 	 		 c = img1.getRight();
// 	 		 d = img1.getBottom();
// 	 	    Rela.removeView(img1);
// 	 // マージンを指定（左、上、右、下）
//			 param.setMargins(a, b, c, d);
//			 Rela.addView(img1, param);
//
// 			if(dir.exists()){
// 				File file = new File(dir.getAbsolutePath()+"/item_jacket&1.png");
// 				if(file.exists()){
// 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
// 				((ImageView)findViewById(R.id.imageView2)).setImageBitmap(_bm);
// 				}
// 			}
// 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
// 	        ImageView dragView = (ImageView) findViewById(R.id.imageView2);
// 			DragViewListener1 listener = new DragViewListener1(dragView);
// 			dragView.setOnTouchListener(listener);
    	 }else if(flag == 3){
    		 Intent intent = new Intent(getApplicationContext(),ItemList.class);
    		 startActivityForResult(intent, SUB_ACTIVITY);
//  	 		 a =  img1.getLeft()-32;
//  	 		 b =img1.getTop()-32;
//  	 		 c = img1.getRight();
//  	 		 d = img1.getBottom();
//  	 	    Rela.removeView(img1);
//  	 // マージンを指定（左、上、右、下）
// 			 param.setMargins(a, b, c, d);
// 			 Rela.addView(img1, param);
//  	 		 a1 =  img2.getLeft()-32;
//  	 		 b1 =img2.getTop()-32;
//  	 		 c1 = img2.getRight();
//  	 		 d1 = img2.getBottom();
//  	 	    Rela.removeView(img2);
//  	 // マージンを指定（左、上、右、下）
// 			 param1.setMargins(a1, b1, c1, d1);
// 			 Rela.addView(img2, param1);
//
// 			if(dir.exists()){
// 				File file = new File(dir.getAbsolutePath()+"/item_nitt&1.png");
// 				if(file.exists()){
// 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
// 				((ImageView)findViewById(R.id.imageView3)).setImageBitmap(_bm);
// 				}
// 			}
// 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
// 	        ImageView dragView = (ImageView) findViewById(R.id.imageView3);
// 			DragViewListener listener = new DragViewListener(dragView);
// 			dragView.setOnTouchListener(listener);
    	 }else if(flag == 4){
    		 
    		 Intent intent = new Intent(getApplicationContext(),ItemList.class);
    		 startActivityForResult(intent, SUB_ACTIVITY);

//   	 		 a =  img1.getLeft()-32;
//   	 		 b =img1.getTop()-32;
//   	 		 c = img1.getRight();
//   	 		 d = img1.getBottom();
//   	 	    Rela.removeView(img1);
//   	 // マージンを指定（左、上、右、下）
//  			 param.setMargins(a, b, c, d);
//  			 Rela.addView(img1, param);
//   	 		 a1 =  img2.getLeft()-32;
//   	 		 b1 =img2.getTop()-32;
//   	 		 c1 = img2.getRight();
//   	 		 d1 = img2.getBottom();
//   	 	    Rela.removeView(img2);
//   	 // マージンを指定（左、上、右、下）
//  			 param1.setMargins(a1, b1, c1, d1);
//  			 Rela.addView(img2, param1);
//    	 	 a2 = img3.getLeft()-32;
//       	 	 b2 = img3.getTop()-32;
//       	 	 c2 = img3.getRight();
//       	 	 d2 = img3.getBottom();
//       	 	 Rela.removeView(img3);
//       	 // マージンを指定（左、上、右、下）
//      		 param2.setMargins(a2, b2, c2, d2);
//      		 Rela.addView(img3, param2);
//
// 			if(dir.exists()){
// 				File file = new File(dir.getAbsolutePath()+"/item_pants1.png");
// 				if(file.exists()){
// 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
// 				((ImageView)findViewById(R.id.imageView4)).setImageBitmap(_bm);
// 				}
// 			}
// 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
// 	        ImageView dragView = (ImageView) findViewById(R.id.imageView4);
// 			DragViewListener listener = new DragViewListener(dragView);
// 			dragView.setOnTouchListener(listener);
    	 }else if(flag == 5){
    		 Intent intent = new Intent(getApplicationContext(),ItemList.class);
    		 startActivityForResult(intent, SUB_ACTIVITY);

//    	 	 a =  img1.getLeft()-32;
//       	 	 b =img1.getTop()-32;
//       	 	 c = img1.getRight();
//       	 	 d = img1.getBottom();
//       	 	    Rela.removeView(img1);
//       	 // マージンを指定（左、上、右、下）
//      			 param.setMargins(a, b, c, d);
//      			 Rela.addView(img1, param);
//       	 	 a1 =  img2.getLeft()-32;
//       	 	 b1 =img2.getTop()-32;
//       	 	 c1 = img2.getRight();
//       	 	 d1 = img2.getBottom();
//       	 	    Rela.removeView(img2);
//       	 // マージンを指定（左、上、右、下）
//      			 param1.setMargins(a1, b1, c1, d1);
//      			 Rela.addView(img2, param1);
//        	 a2 = img3.getLeft()-32;
//           	 b2 = img3.getTop()-32;
//           	 c2 = img3.getRight();
//           	 d2 = img3.getBottom();
//           	 	 Rela.removeView(img3);
//           	 // マージンを指定（左、上、右、下）
//          		 param2.setMargins(a2, b2, c2, d2);
//          		 Rela.addView(img3, param2);
//         	  a3 = img4.getLeft()-32;
//           	  b3 = img4.getTop()-32;
//           	  c3 = img4.getRight();
//           	  d3 = img4.getBottom();
//           	 	 Rela.removeView(img4);
//           	 // マージンを指定（左、上、右、下）
//          		 param3.setMargins(a3, b3, c3, d3);
//          		 Rela.addView(img4, param3);
//
// 			if(dir.exists()){
// 				File file = new File(dir.getAbsolutePath()+"/item_shats1.png");
// 				if(file.exists()){
// 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
// 				((ImageView)findViewById(R.id.imageView5)).setImageBitmap(_bm);
// 				}
// 			}
// 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
// 	        ImageView dragView = (ImageView) findViewById(R.id.imageView5);
// 			DragViewListener listener = new DragViewListener(dragView);
// 			dragView.setOnTouchListener(listener);
    	 }else if(flag == 6){
    		 Intent intent = new Intent(getApplicationContext(),ItemList.class);
    		 startActivityForResult(intent, SUB_ACTIVITY);

// 	 		 a =  img1.getLeft()-32;
//   	 		 b =img1.getTop()-32;
//   	 		 c = img1.getRight();
//   	 		 d = img1.getBottom();
//   	 	    Rela.removeView(img1);
//   	 // マージンを指定（左、上、右、下）
//  			 param.setMargins(a, b, c, d);
//  			 Rela.addView(img1, param);
//   	 		 a1 =  img2.getLeft()-32;
//   	 		 b1 =img2.getTop()-32;
//   	 		 c1 = img2.getRight();
//   	 		 d1 = img2.getBottom();
//   	 	    Rela.removeView(img2);
//   	 // マージンを指定（左、上、右、下）
//  			 param1.setMargins(a1, b1, c1, d1);
//  			 Rela.addView(img2, param1);
//    	 	 a2 = img3.getLeft()-32;
//       	 	 b2 = img3.getTop()-32;
//       	 	 c2 = img3.getRight();
//       	 	 d2 = img3.getBottom();
//       	 	 Rela.removeView(img3);
//       	 // マージンを指定（左、上、右、下）
//      		 param2.setMargins(a2, b2, c2, d2);
//      		 Rela.addView(img3, param2);
//     	 	 a3 = img4.getLeft()-32;
//       	 	 b3 = img4.getTop()-32;
//       	 	 c3 = img4.getRight();
//       	 	 d3 = img4.getBottom();
//       	 	 Rela.removeView(img4);
//       	 // マージンを指定（左、上、右、下）
//      		 param3.setMargins(a3, b3, c3, d3);
//      		 Rela.addView(img4, param3);
//      	 	 a4 = img5.getLeft()-32;
//       	 	 b4 = img5.getTop()-32;
//       	 	 c4 = img5.getRight();
//       	 	 d4 = img5.getBottom();
//       	 	 Rela.removeView(img5);
//       	 // マージンを指定（左、上、右、下）
//      		 param4.setMargins(a4, b4, c4, d4);
//      		 Rela.addView(img5, param4);
//
// 			if(dir.exists()){
// 				File file = new File(dir.getAbsolutePath()+"/item_shortpants1.png");
// 				if(file.exists()){
// 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
// 				((ImageView)findViewById(R.id.imageView6)).setImageBitmap(_bm);
// 				}
// 			}
// 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
// 	        ImageView dragView = (ImageView) findViewById(R.id.imageView6);
// 			DragViewListener listener = new DragViewListener(dragView);
// 			dragView.setOnTouchListener(listener);
    	 }else if(flag == 7){
    		 Intent intent = new Intent(getApplicationContext(),ItemList.class);
    		 startActivityForResult(intent, SUB_ACTIVITY);

//  	 		 a =  img1.getLeft()-32;
//   	 		 b =img1.getTop()-32;
//   	 		 c = img1.getRight();
//   	 		 d = img1.getBottom();
//   	 	    Rela.removeView(img1);
//   	 // マージンを指定（左、上、右、下）
//  			 param.setMargins(a, b, c, d);
//  			 Rela.addView(img1, param);
//   	 		 a1 =  img2.getLeft()-32;
//   	 		 b1 =img2.getTop()-32;
//   	 		 c1 = img2.getRight();
//   	 		 d1 = img2.getBottom();
//   	 	    Rela.removeView(img2);
//   	 // マージンを指定（左、上、右、下）
//  			 param1.setMargins(a1, b1, c1, d1);
//  			 Rela.addView(img2, param1);
//    	 	 a2 = img3.getLeft()-32;
//       	 	 b2 = img3.getTop()-32;
//       	 	 c2 = img3.getRight();
//       	 	 d2 = img3.getBottom();
//       	 	 Rela.removeView(img3);
//       	 // マージンを指定（左、上、右、下）
//      		 param2.setMargins(a2, b2, c2, d2);
//      		 Rela.addView(img3, param2);
//     	 	 a3 = img4.getLeft()-32;
//       	 	 b3 = img4.getTop()-32;
//       	 	 c3 = img4.getRight();
//       	 	 d3 = img4.getBottom();
//       	 	 Rela.removeView(img4);
//       	 // マージンを指定（左、上、右、下）
//      		 param3.setMargins(a3, b3, c3, d3);
//      		 Rela.addView(img4, param3);
//      	 	 a4 = img5.getLeft()-32;
//       	 	 b4 = img5.getTop()-32;
//       	 	 c4 = img5.getRight();
//       	 	 d4 = img5.getBottom();
//       	 	Rela.removeView(img5);
//       	 // マージンを指定（左、上、右、下）
//      		 param4.setMargins(a4, b4, c4, d4);
//      		 Rela.addView(img5, param4);
//       	 	 a5 = img6.getLeft()-32;
//       	 	 b5 = img6.getTop()-32;
//       	 	 c5 = img6.getRight();
//       	 	 d5 = img6.getBottom();
//       	 	 Rela.removeView(img6);
//       	 // マージンを指定（左、上、右、下）
//      		 param5.setMargins(a5, b5, c5, d5);
//      		 Rela.addView(img6, param5);
//
// 			if(dir.exists()){
// 				File file = new File(dir.getAbsolutePath()+"/item_Tshats1.png");
// 				if(file.exists()){
// 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
// 				((ImageView)findViewById(R.id.imageView7)).setImageBitmap(_bm);
// 				}
// 			}
// 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
// 	        ImageView dragView = (ImageView) findViewById(R.id.imageView7);
// 			DragViewListener listener = new DragViewListener(dragView);
// 			dragView.setOnTouchListener(listener);
    	 }
    	 flag = flag + 1 ;

    	 break;

         case R.id.btn1:
        	 if(flag > 1){
        		 Log.d("coordinate","test1");
        		 Time time = new Time("Asia/Tokyo");
        		 time.setToNow();
        		 String date = time.year + "/" + (time.month+1) + "/" + time.monthDay + " " + time.hour + ":" + time.minute + ":" + time.second;
        		 MylistID = dbHelper.InsertMylist(db,date);
        		
        		 Bitmap mBitmap = Bitmap.createBitmap(view.getDrawingCache(),30,175,570,880,null,true);
        		 
        		 try {
        			 // sdcardフォルダを指定
//        			 File root = new File("/data/data/com.android.fukuro/Item");
        			 File root = new File(Environment.getExternalStorageDirectory() + "/Item");

        			 // 保存処理開始
        			 FileOutputStream fos = null;
        			 fos = new FileOutputStream(new File(root, MylistID+".png"));

        			 // jpegで保存
        			 mBitmap.compress(CompressFormat.PNG, 100, fos);

        			 // 保存処理終了
        			 fos.close();
        			 } catch (Exception e) {
        			 Log.e("Error", "" + e.toString());
        			 }
        	 }
        	 if(flag == 2){
      	 		 a =  img1.getLeft()-32;
      	 		 b =img1.getTop()-32;
      	 		 c = img1.getRight();
      	 		 d = img1.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID1,a,b,c,d);
        	 }else if(flag == 3){
      	 		 a =  img1.getLeft()-32;
      	 		 b =img1.getTop()-32;
      	 		 c = img1.getRight();
      	 		 d = img1.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID1,a,b,c,d);
      	 		 a1 =  img2.getLeft()-32;
      	 		 b1 =img2.getTop()-32;
      	 		 c1 = img2.getRight();
      	 		 d1 = img2.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID2,a1,b1,c1,d1);
        	 }else if(flag == 4){
      	 		 a =  img1.getLeft()-32;
      	 		 b =img1.getTop()-32;
      	 		 c = img1.getRight();
      	 		 d = img1.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID1,a,b,c,d);
      	 		 a1 =  img2.getLeft()-32;
      	 		 b1 =img2.getTop()-32;
      	 		 c1 = img2.getRight();
      	 		 d1 = img2.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID2,a1,b1,c1,d1);
      	 		 a2 =  img3.getLeft()-32;
      	 		 b2 =img3.getTop()-32;
      	 		 c2 = img3.getRight();
      	 		 d2 = img3.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID3,a2,b2,c2,d2);

        	 }else if(flag == 5){
      	 		 a =  img1.getLeft()-32;
      	 		 b =img1.getTop()-32;
      	 		 c = img1.getRight();
      	 		 d = img1.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID1,a,b,c,d);
      	 		 a1 =  img2.getLeft()-32;
      	 		 b1 =img2.getTop()-32;
      	 		 c1 = img2.getRight();
      	 		 d1 = img2.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID2,a1,b1,c1,d1);
      	 		 a2 =  img3.getLeft()-32;
      	 		 b2 =img3.getTop()-32;
      	 		 c2 = img3.getRight();
      	 		 d2 = img3.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID3,a2,b2,c2,d2);
      	 		 a3 =  img4.getLeft()-32;
      	 		 b3 =img4.getTop()-32;
      	 		 c3 = img4.getRight();
      	 		 d3 = img4.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID4,a3,b3,c3,d3);
        	 }else if(flag == 6){
      	 		 a =  img1.getLeft()-32;
      	 		 b =img1.getTop()-32;
      	 		 c = img1.getRight();
      	 		 d = img1.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID1,a,b,c,d);
      	 		 a1 =  img2.getLeft()-32;
      	 		 b1 =img2.getTop()-32;
      	 		 c1 = img2.getRight();
      	 		 d1 = img2.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID2,a1,b1,c1,d1);
      	 		 a2 =  img3.getLeft()-32;
      	 		 b2 =img3.getTop()-32;
      	 		 c2 = img3.getRight();
      	 		 d2 = img3.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID3,a2,b2,c2,d2);
      	 		 a3 =  img4.getLeft()-32;
      	 		 b3 =img4.getTop()-32;
      	 		 c3 = img4.getRight();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID4,a3,b3,c3,d3);
      	 		 a4 =  img5.getLeft()-32;
      	 		 b4 =img5.getTop()-32;
      	 		 c4 = img5.getRight();
      	 		 d4 = img5.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID5,a4,b4,c4,d4);
        	 }else if(flag == 7) {
      	 		 a =  img1.getLeft()-32;
      	 		 b =img1.getTop()-32;
      	 		 c = img1.getRight();
      	 		 d = img1.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID1,a,b,c,d);
      	 		 a1 =  img2.getLeft()-32;
      	 		 b1 =img2.getTop()-32;
      	 		 c1 = img2.getRight();
      	 		 d1 = img2.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID2,a1,b1,c1,d1);
      	 		 a2 =  img3.getLeft()-32;
      	 		 b2 =img3.getTop()-32;
      	 		 c2 = img3.getRight();
      	 		 d2 = img3.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID3,a2,b2,c2,d2);
      	 		 a3 =  img4.getLeft()-32;
      	 		 b3 =img4.getTop()-32;
      	 		 c3 = img4.getRight();
      	 		 d3 = img4.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID4,a3,b3,c3,d3);
      	 		 a4 =  img5.getLeft()-32;
      	 		 b4 =img5.getTop()-32;
      	 		 c4 = img5.getRight();
      	 		 d4 = img5.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID5,a4,b4,c4,d4);
      	 		 a5 =  img6.getLeft()-32;
      	 		 b5 =img6.getTop()-32;
      	 		 c5 = img6.getRight();
      	 		 d5 = img6.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID6,a5,b5,c5,d5);
        	 }else{
      	 		 a =  img1.getLeft()-32;
      	 		 b =img1.getTop()-32;
      	 		 c = img1.getRight();
      	 		 d = img1.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID1,a,b,c,d);
      	 		 a1 =  img2.getLeft()-32;
      	 		 b1 =img2.getTop()-32;
      	 		 c1 = img2.getRight();
      	 		 d1 = img2.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID2,a1,b1,c1,d1);
      	 		 a2 =  img3.getLeft()-32;
      	 		 b2 =img3.getTop()-32;
      	 		 c2 = img3.getRight();
      	 		 d2 = img3.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID3,a2,b2,c2,d2);
      	 		 a3 =  img4.getLeft()-32;
      	 		 b3 =img4.getTop()-32;
      	 		 c3 = img4.getRight();
      	 		 d3 = img4.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID4,a3,b3,c3,d3);
      	 		 a4 =  img5.getLeft()-32;
      	 		 b4 =img5.getTop()-32;
      	 		 c4 = img5.getRight();
      	 		 d4 = img5.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID5,a4,b4,c4,d4);
      	 		 a5 =  img6.getLeft()-32;
      	 		 b5 =img6.getTop()-32;
      	 		 c5 = img6.getRight();
      	 		 d5 = img6.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID6,a5,b5,c5,d5);
      	 		 a6 =  img7.getLeft()-32;
      	 		 b6 =img7.getTop()-32;
      	 		 c6 = img7.getRight();
      	 		 d6 = img7.getBottom();
        		 dbHelper.InsertMylistmaking(db,MylistID,ItemID7,a6,b6,c6,d6);
        	 }
        	 finish();

        	 break;
        	 }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	// requestCodeがサブ画面か確認する
		ImageView img1 = (ImageView)findViewById(R.id.imageView1);
		 ImageView img2 = (ImageView)findViewById(R.id.imageView2);
		 ImageView img3 = (ImageView)findViewById(R.id.imageView3);
		 ImageView img4 = (ImageView)findViewById(R.id.imageView4);
		 ImageView img5 = (ImageView)findViewById(R.id.imageView5);
		 ImageView img6 = (ImageView)findViewById(R.id.imageView6);
		 ImageView img7 = (ImageView)findViewById(R.id.imageView7);
		if(requestCode == SUB_ACTIVITY){
		// resultCodeがOKか確認する
			if(resultCode == RESULT_OK){
				// 結果を取得して, 表示する.
				if (flag == 2){
					returnValue=(String) data.getCharSequenceExtra("filepath");
					ItemID1=(String) data.getCharSequenceExtra("MylistID");
		    		
					if(dir.exists()){
						File file = new File(returnValue);
						if(file.exists()){
						Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
						((ImageView)findViewById(R.id.imageView1)).setImageBitmap(_bm);
						}
					}
					// ドラッグ対象Viewとイベント処理クラスを紐付ける
			        ImageView dragView = (ImageView) findViewById(R.id.imageView1);
					DragViewListener listener = new DragViewListener(dragView);
					dragView.setOnTouchListener(listener);
					Log.i("test","a+"+ItemID1);

		    	 }else if(flag == 3){
		    		 returnValue=(String) data.getCharSequenceExtra("filepath");
					 ItemID2=(String) data.getCharSequenceExtra("MylistID");

		 	 		 a =  img1.getLeft()-32;
		 	 		 b =img1.getTop()-32;
		 	 		 c = img1.getRight()+a;
		 	 		 d = img1.getBottom()+b;
		 	 	    Rela.removeView(img1);
		 	 // マージンを指定（左、上、右、下）
					 param.setMargins(a, b, c, d);
					 Rela.addView(img1, param);

		 			if(dir.exists()){
		 				File file = new File(returnValue);
		 				if(file.exists()){
		 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
		 				((ImageView)findViewById(R.id.imageView2)).setImageBitmap(_bm);
		 				}
		 			}
		 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
		 	        ImageView dragView = (ImageView) findViewById(R.id.imageView2);
		 			DragViewListener1 listener = new DragViewListener1(dragView);
		 			dragView.setOnTouchListener(listener);
		    	 }else if(flag == 4){
		    		 returnValue=(String) data.getCharSequenceExtra("filepath");
					 ItemID3=(String) data.getCharSequenceExtra("MylistID");

		  	 		 a =  img1.getLeft()-32;
		  	 		 b =img1.getTop()-32;
		  	 		 c = img1.getRight()+a;
		  	 		 d = img1.getBottom()+b;
		  	 	    Rela.removeView(img1);
		  	 // マージンを指定（左、上、右、下）
		 			 param.setMargins(a, b, c, d);
		 			 Rela.addView(img1, param);
		  	 		 a1 =  img2.getLeft()-32;
		  	 		 b1 =img2.getTop()-32;
		  	 		 c1 = img2.getRight()+a1;
		  	 		 d1 = img2.getBottom()+b1;
		  	 	    Rela.removeView(img2);
		  	 // マージンを指定（左、上、右、下）
		 			 param1.setMargins(a1, b1, c1, d1);
		 			 Rela.addView(img2, param1);

		 			if(dir.exists()){
		 				File file = new File(returnValue);
		 				if(file.exists()){
		 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
		 				((ImageView)findViewById(R.id.imageView3)).setImageBitmap(_bm);
		 				}
		 			}
		 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
		 	        ImageView dragView = (ImageView) findViewById(R.id.imageView3);
		 			DragViewListener listener = new DragViewListener(dragView);
		 			dragView.setOnTouchListener(listener);
		    	 }else if(flag == 5){
		    		 returnValue=(String) data.getCharSequenceExtra("filepath");
					 ItemID4=(String) data.getCharSequenceExtra("MylistID");

		   	 		 a =  img1.getLeft()-32;
		   	 		 b =img1.getTop()-32;
		   	 		 c = img1.getRight()+a;
		   	 		 d = img1.getBottom()+b;
		   	 	    Rela.removeView(img1);
		   	 // マージンを指定（左、上、右、下）
		  			 param.setMargins(a, b, c, d);
		  			 Rela.addView(img1, param);
		   	 		 a1 =  img2.getLeft()-32;
		   	 		 b1 =img2.getTop()-32;
		   	 		 c1 = img2.getRight()+a1;
		   	 		 d1 = img2.getBottom()+b1;
		   	 	    Rela.removeView(img2);
		   	 // マージンを指定（左、上、右、下）
		  			 param1.setMargins(a1, b1, c1, d1);
		  			 Rela.addView(img2, param1);
		    	 	 a2 = img3.getLeft()-32;
		       	 	 b2 = img3.getTop()-32;
		       	 	 c2 = img3.getRight()+a2;
		       	 	 d2 = img3.getBottom()+b2;
		       	 	 Rela.removeView(img3);
		       	 // マージンを指定（左、上、右、下）
		      		 param2.setMargins(a2, b2, c2, d2);
		      		 Rela.addView(img3, param2);
		 			if(dir.exists()){
		 				File file = new File(returnValue);
		 				if(file.exists()){
		 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
		 				((ImageView)findViewById(R.id.imageView4)).setImageBitmap(_bm);
		 				}
		 			}
		 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
		 	        ImageView dragView = (ImageView) findViewById(R.id.imageView4);
		 			DragViewListener listener = new DragViewListener(dragView);
		 			dragView.setOnTouchListener(listener);
		    	 }else if(flag == 6){
		    		 
		    		 returnValue=(String) data.getCharSequenceExtra("filepath");
					 ItemID5=(String) data.getCharSequenceExtra("MylistID");

		    	 	 a =  img1.getLeft()-32;
		       	 	 b =img1.getTop()-32;
		       	 	 c = img1.getRight()+a;
		       	 	 d = img1.getBottom()+b;
		       	 	    Rela.removeView(img1);
		       	 // マージンを指定（左、上、右、下）
		      			 param.setMargins(a, b, c, d);
		      			 Rela.addView(img1, param);
		       	 	 a1 =  img2.getLeft()-32;
		       	 	 b1 =img2.getTop()-32;
		       	 	 c1 = img2.getRight()+a1;
		       	 	 d1 = img2.getBottom()+b1;
		       	 	    Rela.removeView(img2);
		       	 // マージンを指定（左、上、右、下）
		      			 param1.setMargins(a1, b1, c1, d1);
		      			 Rela.addView(img2, param1);
		        	 a2 = img3.getLeft()-32;
		           	 b2 = img3.getTop()-32;
		           	 c2 = img3.getRight()+a2;
		           	 d2 = img3.getBottom()+b2;
		           	 	 Rela.removeView(img3);
		           	 // マージンを指定（左、上、右、下）
		          		 param2.setMargins(a2, b2, c2, d2);
		          		 Rela.addView(img3, param2);
		         	  a3 = img4.getLeft()-32;
		           	  b3 = img4.getTop()-32;
		           	  c3 = img4.getRight()+a3;
		           	  d3 = img4.getBottom()+b3;
		           	 	 Rela.removeView(img4);
		           	 // マージンを指定（左、上、右、下）
		          		 param3.setMargins(a3, b3, c3, d3);
		          		 Rela.addView(img4, param3);

		 			if(dir.exists()){
		 				File file = new File(returnValue);
		 				if(file.exists()){
		 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
		 				((ImageView)findViewById(R.id.imageView5)).setImageBitmap(_bm);
		 				}
		 			}
		 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
		 	        ImageView dragView = (ImageView) findViewById(R.id.imageView5);
		 			DragViewListener listener = new DragViewListener(dragView);
		 			dragView.setOnTouchListener(listener);
		    	 }else if(flag == 7){
		    		 returnValue=(String) data.getCharSequenceExtra("filepath");
					 ItemID6=(String) data.getCharSequenceExtra("MylistID");

		 	 		 a =  img1.getLeft()-32;
		   	 		 b =img1.getTop()-32;
		   	 		 c = img1.getRight()+a;
		   	 		 d = img1.getBottom()+b;
		   	 	    Rela.removeView(img1);
		   	 // マージンを指定（左、上、右、下）
		  			 param.setMargins(a, b, c, d);
		  			 Rela.addView(img1, param);
		  			 
		   	 		 a1 =  img2.getLeft()-32;
		   	 		 b1 =img2.getTop()-32;
		   	 		 c1 = img2.getRight()+a1;
		   	 		 d1 = img2.getBottom()+b1;
		   	 	    Rela.removeView(img2);
		   	 // マージンを指定（左、上、右、下）
		  			 param1.setMargins(a1, b1, c1, d1);
		  			 Rela.addView(img2, param1);
		  			 
		    	 	 a2 = img3.getLeft()-32;
		       	 	 b2 = img3.getTop()-32;
		       	 	 c2 = img3.getRight()+a2;
		       	 	 d2 = img3.getBottom()+b2;
		       	 	 Rela.removeView(img3);
		       	 // マージンを指定（左、上、右、下）
		      		 param2.setMargins(a2, b2, c2, d2);
		      		 Rela.addView(img3, param2);
		      		 
		     	 	 a3 = img4.getLeft()-32;
		       	 	 b3 = img4.getTop()-32;
		       	 	 c3 = img4.getRight()+a3;
		       	 	 d3 = img4.getBottom()+b3;
		       	 	 Rela.removeView(img4);
		       	 // マージンを指定（左、上、右、下）
		      		 param3.setMargins(a3, b3, c3, d3);
		      		 Rela.addView(img4, param3);
		      		 
		      	 	 a4 = img5.getLeft()-32;
		       	 	 b4 = img5.getTop()-32;
		       	 	 c4 = img5.getRight()+a4;
		       	 	 d4 = img5.getBottom()+b4;
		       	 	 Rela.removeView(img5);
		       	 // マージンを指定（左、上、右、下）
		      		 param4.setMargins(a4, b4, c4, d4);
		      		 Rela.addView(img5, param4);

		 			if(dir.exists()){
		 				File file = new File(returnValue);
		 				if(file.exists()){
		 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
		 				((ImageView)findViewById(R.id.imageView6)).setImageBitmap(_bm);
		 				}
		 			}
		 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
		 	        ImageView dragView = (ImageView) findViewById(R.id.imageView6);
		 			DragViewListener listener = new DragViewListener(dragView);
		 			dragView.setOnTouchListener(listener);
		    	 }else if(flag == 8){
		    		 returnValue=(String) data.getCharSequenceExtra("filepath");
					 ItemID2=(String) data.getCharSequenceExtra("MylistID");

		  	 		 a =  img1.getLeft()-32;
		   	 		 b =img1.getTop()-32;
		   	 		 c = img1.getRight()+a;
		   	 		 d = img1.getBottom()+b;
		   	 	    Rela.removeView(img1);
		   	 // マージンを指定（左、上、右、下）
		  			 param.setMargins(a, b, c, d);
		  			 Rela.addView(img1, param);
		   	 		 a1 =  img2.getLeft()-32;
		   	 		 b1 =img2.getTop()-32;
		   	 		 c1 = img2.getRight()+a1;
		   	 		 d1 = img2.getBottom()+b1;
		   	 	    Rela.removeView(img2);
		   	 // マージンを指定（左、上、右、下）
		  			 param1.setMargins(a1, b1, c1, d1);
		  			 Rela.addView(img2, param1);
		    	 	 a2 = img3.getLeft()-32;
		       	 	 b2 = img3.getTop()-32;
		       	 	 c2 = img3.getRight()+a2;
		       	 	 d2 = img3.getBottom()+b2;
		       	 	 Rela.removeView(img3);
		       	 // マージンを指定（左、上、右、下）
		      		 param2.setMargins(a2, b2, c2, d2);
		      		 Rela.addView(img3, param2);
		     	 	 a3 = img4.getLeft()-32;
		       	 	 b3 = img4.getTop()-32;
		       	 	 c3 = img4.getRight()+a3;
		       	 	 d3 = img4.getBottom()+b3;
		       	 	 Rela.removeView(img4);
		       	 // マージンを指定（左、上、右、下）
		      		 param3.setMargins(a3, b3, c3, d3);
		      		 Rela.addView(img4, param3);
		      	 	 a4 = img5.getLeft()-32;
		       	 	 b4 = img5.getTop()-32;
		       	 	 c4 = img5.getRight()+a4;
		       	 	 d4 = img5.getBottom()+b4;
		       	 	Rela.removeView(img5);
		       	 // マージンを指定（左、上、右、下）
		      		 param4.setMargins(a4, b4, c4, d4);
		      		 Rela.addView(img5, param4);
		       	 	 a5 = img6.getLeft()-32;
		       	 	 b5 = img6.getTop()-32;
		       	 	 c5 = img6.getRight()+a5;
		       	 	 d5 = img6.getBottom()+b5;
		       	 	 Rela.removeView(img6);
		       	 // マージンを指定（左、上、右、下）
		      		 param5.setMargins(a5, b5, c5, d5);
		      		 Rela.addView(img6, param5);

		 			if(dir.exists()){
		 				File file = new File(returnValue);
		 				if(file.exists()){
		 				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
		 				((ImageView)findViewById(R.id.imageView7)).setImageBitmap(_bm);
		 				}
		 			}
		 		     // ドラッグ対象Viewとイベント処理クラスを紐付ける
		 	        ImageView dragView = (ImageView) findViewById(R.id.imageView7);
		 			DragViewListener listener = new DragViewListener(dragView);
		 			dragView.setOnTouchListener(listener);
		    	 }
			}
		}
	}
}