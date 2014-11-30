package com.android.fukuro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class Coordinate extends Activity implements View.OnClickListener  {

	private DBHelper dbHelper = new DBHelper(this);

	public static SQLiteDatabase db;
    private Bitmap mBitmap;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private ArrayList<Integer> priority=new ArrayList<Integer>();
    private ArrayList<String> usesimg=new ArrayList<String>();
    private ArrayList<String> itemPath=new ArrayList<String>();
    private ArrayList<String> item_priority=new ArrayList<String>();
    private ArrayList<Integer> item_position_L=new ArrayList<Integer>();
    private ArrayList<Integer> item_position_T=new ArrayList<Integer>();
    private ArrayList<Integer> magni=new ArrayList<Integer>();
    private ArrayList<String> item_idlist=new ArrayList<String>();
    private String previousview=null;
    private String[] imglist = { "img1", "img2", "img3", "img4", "img5", "img6", "img7"};
    int flag = 1;
	File dir =new File("/data/data/com.android.fukuro/Item");
	//private RelativeLayout Rela;
	private FrameLayout frameLayout;
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
	 int selectimage;
	 ImageView img1,img2,img3,img4,img5,img6,img7;
	 DragViewListener listener;
	 private int selectedView=0;
	 Toast toast;
	 Long ss = (long) 1;
	 String destPath = null;
	 
	 private static final int SUB_ACTIVITY = 1001;
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coordinate);
		Log.i("coor","onCreate");

        super.onCreate(savedInstanceState);
	    ImageButton imgbutton = (ImageButton)findViewById(R.id.addbtn);
        Button btn1 =(Button)findViewById(R.id.savebtn);
        Button upbtn=(Button)findViewById(R.id.upbtn);
        Button downbtn=(Button)findViewById(R.id.downbtn);
        Button delebtn=(Button)findViewById(R.id.delebtn);
       	imgbutton.setOnClickListener(this);
       	btn1.setOnClickListener(this);
       	upbtn.setOnClickListener(this);
       	downbtn.setOnClickListener(this);
       	delebtn.setOnClickListener(this);

		//読み書き可能なデータベースをオープン
		// 読み取り専用の場合はgetReadableDatabase()を用いる
		db=dbHelper.getReadableDatabase();
		Window window = getWindow();
		view = window.getDecorView();
		view.setDrawingCacheEnabled(true);
        frameLayout = (FrameLayout)findViewById(R.id.frame);
        
        img1 = (ImageView)findViewById(R.id.imageView1);
        img2 = (ImageView)findViewById(R.id.imageView2);
		img3 = (ImageView)findViewById(R.id.imageView3);
		img4 = (ImageView)findViewById(R.id.imageView4);
		img5 = (ImageView)findViewById(R.id.imageView5);
		img6 = (ImageView)findViewById(R.id.imageView6);
		img7 = (ImageView)findViewById(R.id.imageView7);
        
        // ドラッグ対象Viewとイベント処理クラスを紐付ける
		listener = new DragViewListener(img1);
		img1.setOnTouchListener(listener);
		
		// ドラッグ対象Viewとイベント処理クラスを紐付ける
	    listener = new DragViewListener(img2);
	    img2.setOnTouchListener(listener);
	    
	    // ドラッグ対象Viewとイベント処理クラスを紐付ける
	    listener = new DragViewListener(img3);
	    img3.setOnTouchListener(listener);
	    
	    // ドラッグ対象Viewとイベント処理クラスを紐付ける
	    listener = new DragViewListener(img4);
	    img4.setOnTouchListener(listener);
	    
	    // ドラッグ対象Viewとイベント処理クラスを紐付ける
	    listener = new DragViewListener(img5);
	    img5.setOnTouchListener(listener);
	    
	    // ドラッグ対象Viewとイベント処理クラスを紐付ける
	    listener = new DragViewListener(img6);
	    img6.setOnTouchListener(listener);
	    
	    // ドラッグ対象Viewとイベント処理クラスを紐付ける
	    listener = new DragViewListener(img7);
	    img7.setOnTouchListener(listener);
	    itemPath=new ArrayList<String>();
	    item_priority=new ArrayList<String>();
	    item_position_L=new ArrayList<Integer>();
	    item_position_T=new ArrayList<Integer>();
	    magni = new ArrayList<Integer>();
	    item_idlist=new ArrayList<String>();

	    Intent intent= getIntent();
	    previousview=intent.getStringExtra("previousview");
	    if(previousview.equals("MylistDetails")){
	    	MylistID=intent.getStringExtra("ID");
		    //再編集
		    Cursor cr = db.rawQuery("select i.item,mm.item_priority,mm.item_position_L,mm.item_position_T,mm.magni,i.item_id from Mylistmaking mm,Item i where mm.mylist_id=\""+MylistID+"\" and mm.item_id=i.item_id order by item_priority", null);
			cr.moveToFirst();
		    
			for(int cnt = 1; cnt <= cr.getCount(); cnt++){
				destPath = "/data/data/"+this.getPackageName()+"/Item/" + cr.getString(0);
	//			destPath = Environment.getExternalStorageDirectory() +"/Item/" + cr.getString(1);
				// List<String> imgList にはファイルのパスを入れる
				itemPath.add(destPath);
				item_priority.add(cr.getString(1));
				item_position_L.add(cr.getInt(2));
				item_position_T.add(cr.getInt(3));
				magni.add(cr.getInt(4));
				item_idlist.add(cr.getString(5));
				cr.moveToNext();
			}
			for(int i=0;i<itemPath.size();i++){
				File file = new File(itemPath.get(i));
				usesimg.add(imglist[i]);
				String img=imglist[i];
				if(file.exists()){
				Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
					if(img=="img1"){
						img1.setImageBitmap(_bm);
						priority.add(R.id.imageView1);
						ItemID1=item_idlist.get(i);
						frameLayout.removeView(img1);
					    param.setMargins(item_position_L.get(i), item_position_T.get(i), 0, 0);
					}else if(img=="img2"){
						img2.setImageBitmap(_bm);
						priority.add(R.id.imageView2);
						ItemID2=item_idlist.get(i);
						frameLayout.removeView(img2);
					    param1.setMargins(item_position_L.get(i), item_position_T.get(i), 0, 0);
					}else if(img=="img3"){
						img3.setImageBitmap(_bm);
						priority.add(R.id.imageView3);
						ItemID3=item_idlist.get(i);
						frameLayout.removeView(img3);
					    param2.setMargins(item_position_L.get(i), item_position_T.get(i), 0, 0);
					}else if(img=="img4"){
						img4.setImageBitmap(_bm);
						priority.add(R.id.imageView4);
						ItemID4=item_idlist.get(i);
						frameLayout.removeView(img4);
					    param3.setMargins(item_position_L.get(i), item_position_T.get(i), 0, 0);
					}else if(img=="img5"){
						img5.setImageBitmap(_bm);
						priority.add(R.id.imageView5);
						ItemID5=item_idlist.get(i);
						frameLayout.removeView(img5);
					    param4.setMargins(item_position_L.get(i), item_position_T.get(i), 0, 0);
					}else if(img=="img6"){
						img6.setImageBitmap(_bm);
						priority.add(R.id.imageView6);
						ItemID6=item_idlist.get(i);
						frameLayout.removeView(img6);
					    param5.setMargins(item_position_L.get(i), item_position_T.get(i), 0, 0);
					}else if(img=="img7"){
						img7.setImageBitmap(_bm);
						priority.add(R.id.imageView7);
						ItemID7=item_idlist.get(i);
						frameLayout.removeView(img7);
					    param6.setMargins(item_position_L.get(i), item_position_T.get(i), 0, 0);
					}
				}
			}
		    
		    for(int i=0;i<priority.size();i++){
		    	if(priority.get(i)==R.id.imageView1){
		    		frameLayout.addView(img1, param);
		    	}else if(priority.get(i)==R.id.imageView2){
		    		frameLayout.addView(img2, param1);
		    	}else if(priority.get(i)==R.id.imageView3){
		    		frameLayout.addView(img3, param2);
		    	}else if(priority.get(i)==R.id.imageView4){
		    		frameLayout.addView(img4, param3);
		    	}else if(priority.get(i)==R.id.imageView5){
		    		frameLayout.addView(img5, param4);
		    	}else if(priority.get(i)==R.id.imageView6){
		    		frameLayout.addView(img6, param5);
		    	}else if(priority.get(i)==R.id.imageView7){
		    		frameLayout.addView(img7, param6);
		    	}
		    }
	    }
	    
	    Cursor c = db.rawQuery("select category_name from Category where not category_id=\"7\"", null);
		c.moveToFirst();

		
		 CharSequence[] list = new CharSequence[c.getCount()];
         for (int i = 0; i < list.length; i++) {
             list[i] = c.getString(0);
             c.moveToNext();
         }
         c.close();

         Spinner spinner = (Spinner)this.findViewById(R.id.spinner1);
         spinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, list));
         
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
     	    // アイテムが選択された時の動作
     	        public void onItemSelected(AdapterView parent,View view, int position,long id) {
     	        // Spinner を取得
     	        Spinner spinner = (Spinner) parent;
     	        // 選択されたアイテムのテキストを取得
     	       ss = spinner.getSelectedItemId();
     	       ss = ss + 1;
     	       setImageView("");
     	    }

     	    // 何も選択されなかった時の動作
     	    public void onNothingSelected(AdapterView parent) {
     	    	setImageView("");
     	    }
     	    });
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

	FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(WC, WC);
	FrameLayout.LayoutParams param1 = new FrameLayout.LayoutParams(WC, WC);
	FrameLayout.LayoutParams param2 = new FrameLayout.LayoutParams(WC, WC);
	FrameLayout.LayoutParams param3 = new FrameLayout.LayoutParams(WC, WC);
	FrameLayout.LayoutParams param4 = new FrameLayout.LayoutParams(WC, WC);
	FrameLayout.LayoutParams param5 = new FrameLayout.LayoutParams(WC, WC);
	FrameLayout.LayoutParams param6 = new FrameLayout.LayoutParams(WC, WC);
	FrameLayout.LayoutParams param7 = new FrameLayout.LayoutParams(WC, WC);
	int a = 0;
	int b = 0;

	@Override
	public void onClick(View v) {
    switch(v.getId()){

         case R.id.addbtn:

    	 if (flag < 8){
    		 Intent intent = new Intent(getApplicationContext(),ItemList.class);
    		 intent.putExtra("category_id", ss.toString());
    		 intent.putExtra("Itemid1", ItemID1);
    		 intent.putExtra("Itemid2", ItemID2);
    		 intent.putExtra("Itemid3", ItemID3);
    		 intent.putExtra("Itemid4", ItemID4);
    		 intent.putExtra("Itemid5", ItemID5);
    		 intent.putExtra("Itemid6", ItemID6);
    		 intent.putExtra("Itemid7", ItemID7);
    		 startActivityForResult(intent, SUB_ACTIVITY);
    	 }else{
    		 toast = Toast.makeText(Coordinate.this, "これ以上追加できません。",Toast.LENGTH_LONG);
			 toast.setGravity(Gravity.AXIS_CLIP, 0,0);
			 toast.show();
    	 }
    	 break;

         case R.id.savebtn:
        	 if(previousview.equals("MylistDetails")){
        		 Bitmap mBitmap = Bitmap.createBitmap(view.getDrawingCache(),30,175,570,880,null,true);
        		 
        		 try {
        			 // sdcardフォルダを指定
        			 File root = new File("/data/data/com.android.fukuro/Item");
//        			 File root = new File(Environment.getExternalStorageDirectory() + "/Item");

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
        		 dbHelper.DeleMylistmaking(db,MylistID);
        	 }else if(previousview.equals("Mylist")){
	        	 if(flag > 1){
	        		 Log.d("coordinate","test1");
	        		 Time time = new Time("Asia/Tokyo");
	        		 time.setToNow();
	        		 String date = time.year + "/" + (time.month+1) + "/" + time.monthDay + " " + time.hour + ":" + time.minute + ":" + time.second;
	        		 MylistID = dbHelper.InsertMylist(db,date);
	        		
	        		 Bitmap mBitmap = Bitmap.createBitmap(view.getDrawingCache(),30,175,570,880,null,true);
	        		 
	        		 try {
	        			 // sdcardフォルダを指定
	        			 File root = new File("/data/data/com.android.fukuro/Item");
	//        			 File root = new File(Environment.getExternalStorageDirectory() + "/Item");
	
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
        	 }
        	 for(int i=0;i<usesimg.size();i++){
        		 if(usesimg.get(i)=="img1"){
        			 a =  img1.getLeft();
          	 		 b =img1.getTop();
        			 dbHelper.InsertMylistmaking(db,MylistID,ItemID1,a,b,0,0);
        		 }else if(usesimg.get(i)=="img2"){
        			 a =  img2.getLeft();
          	 		 b =img2.getTop();
        			 dbHelper.InsertMylistmaking(db,MylistID,ItemID2,a,b,0,0);
        		 }else if(usesimg.get(i)=="img3"){
        			 a =  img3.getLeft();
          	 		 b =img3.getTop();
        			 dbHelper.InsertMylistmaking(db,MylistID,ItemID3,a,b,0,0);
        		 }else if(usesimg.get(i)=="img4"){
        			 a =  img4.getLeft();
          	 		 b =img4.getTop();
        			 dbHelper.InsertMylistmaking(db,MylistID,ItemID4,a,b,0,0);
        		 }else if(usesimg.get(i)=="img5"){
        			 a =  img5.getLeft();
          	 		 b =img5.getTop();
        			 dbHelper.InsertMylistmaking(db,MylistID,ItemID5,a,b,0,0);
        		 }else if(usesimg.get(i)=="img6"){
        			 a =  img6.getLeft();
          	 		 b =img6.getTop();
        			 dbHelper.InsertMylistmaking(db,MylistID,ItemID6,a,b,0,0);
        		 }else if(usesimg.get(i)=="img7"){
        			 a =  img7.getLeft();
          	 		 b =img7.getTop();
        			 dbHelper.InsertMylistmaking(db,MylistID,ItemID7,a,b,0,0);
        		 }
        	 }       
        	 finish();

        	 break;
         case R.id.upbtn:
        	 if(priority.size()==1 || priority.size()==0 || selectedView==0){
        	 }else{
        		 boolean flg=true;
	        	 for(int i=0;i<priority.size();i++){
	        		 if(selectedView==priority.get(i)&&i<priority.size()-1 && flg){
		        		 Integer ii=priority.get(i+1);
		        		 priority.set(i+1,priority.get(i));
		        		 priority.set(i, ii);
		        		 String jj=usesimg.get(i+1);
		        		 usesimg.set(i+1,usesimg.get(i));
		        		 usesimg.set(i, jj);
		        		 flg=false;
		        		 setImageView("");
	        		 }
	        	 }
        	 }
        	 break;
         case R.id.downbtn:
        	 if(priority.size()==1 || priority.size()==0 ||selectedView==0){
        	 }else{
	        	 boolean flg=true;
	        	 for(int i=0;i<priority.size();i++){
	        		 if(selectedView==priority.get(i)&&i>0 && flg){
		        		 Integer ii=priority.get(i-1);
		        		 priority.set(i-1,priority.get(i));
		        		 priority.set(i, ii);
		        		 String jj=usesimg.get(i-1);
		        		 usesimg.set(i-1,usesimg.get(i));
		        		 usesimg.set(i, jj);
		       			 flg=false;
		       			 setImageView("");
	        		 }
	        	 }
        	 }
        	 break;
         case R.id.delebtn:
        	 if(priority.size()==0 ||selectedView==0){
        	 }else{
	        	 for(int i=0;i<priority.size();i++){
	        		 if(selectedView==priority.get(i)){
		        		 priority.remove(i);
		        		 delesetImageView();
		        		 usesimg.remove(i);
		        		 flag = flag - 1 ;
	        		 }
	        	 }
        	 }
        	 break;
    	}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	// requestCodeがサブ画面か確認する
		if(requestCode == SUB_ACTIVITY){
		// resultCodeがOKか確認する
			if(resultCode == RESULT_OK){
				// 結果を取得して, 表示する
				//現在使用していないimgを取得する
				boolean flg=true;
				String nowimg=null;
				for(int i=0;i<imglist.length && flg;i++){
					boolean flg2=true;
					for(int j=0;j<usesimg.size();j++){
						if(imglist[i]==usesimg.get(j)){
							flg2=false;
						}
					}
					if(flg2){
						usesimg.add(imglist[i]);
						nowimg=imglist[i];
						returnValue=(String) data.getCharSequenceExtra("filepath");
						if(imglist[i]=="img1"){
							ItemID1=(String) data.getCharSequenceExtra("MylistID");
						}else if(imglist[i]=="img2"){
							ItemID2=(String) data.getCharSequenceExtra("MylistID");
						}else if(imglist[i]=="img3"){
							ItemID3=(String) data.getCharSequenceExtra("MylistID");
						}else if(imglist[i]=="img4"){
							ItemID4=(String) data.getCharSequenceExtra("MylistID");
						}else if(imglist[i]=="img5"){
							ItemID5=(String) data.getCharSequenceExtra("MylistID");
						}else if(imglist[i]=="img6"){
							ItemID6=(String) data.getCharSequenceExtra("MylistID");
						}else if(imglist[i]=="img7"){
							ItemID7=(String) data.getCharSequenceExtra("MylistID");
						}
						flg=false;
					}
				}
				
				if(dir.exists()){
					File file = new File(returnValue);
					Log.d("", "returnValue="+returnValue);
					if(file.exists()){
					Bitmap _bm = BitmapFactory.decodeFile(file.getPath());
						if(nowimg=="img1"){
							img1.setImageBitmap(_bm);
							priority.add(R.id.imageView1);
						}else if(nowimg=="img2"){
							img2.setImageBitmap(_bm);
							priority.add(R.id.imageView2);
						}else if(nowimg=="img3"){
							img3.setImageBitmap(_bm);
							priority.add(R.id.imageView3);
						}else if(nowimg=="img4"){
							img4.setImageBitmap(_bm);
							priority.add(R.id.imageView4);
						}else if(nowimg=="img5"){
							img5.setImageBitmap(_bm);
							priority.add(R.id.imageView5);
						}else if(nowimg=="img6"){
							img6.setImageBitmap(_bm);
							priority.add(R.id.imageView6);
						}else if(nowimg=="img7"){
							img7.setImageBitmap(_bm);
							priority.add(R.id.imageView7);
						}
					}
				}
				setImageView(nowimg);
				flag = flag + 1 ;
			}
		}
	}
	
	public void setImageView(String nowimg) {
		for(int j=0;j<usesimg.size();j++){
			if(usesimg.get(j)=="img1"){
				a =  img1.getLeft();
		 		b =img1.getTop();
		 		frameLayout.removeView(img1);
			    param.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img2"){
				a =  img2.getLeft();
		 		b =img2.getTop();
		 		frameLayout.removeView(img2);
			    param1.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img3"){
				a =  img3.getLeft();
		 		b =img3.getTop();
		 		frameLayout.removeView(img3);
			    param2.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img4"){
				a =  img4.getLeft();
		 		b =img4.getTop();
		 		frameLayout.removeView(img4);
			    param3.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img5"){
				a =  img5.getLeft();
		 		b =img5.getTop();
		 		frameLayout.removeView(img5);
			    param4.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img6"){
				a =  img6.getLeft();
		 		b =img6.getTop();
		 		frameLayout.removeView(img6);
			    param5.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img7"){
				a =  img7.getLeft();
		 		b =img7.getTop();
		 		frameLayout.removeView(img7);
			    param6.setMargins(a, b, 0, 0);
			}
		}
		Log.i("31","31="+usesimg);
		Log.i("31","nowimg="+nowimg);
		if(nowimg=="img1"){
		    param.setMargins(0, 0, 0, 0);
		}else if(nowimg=="img2"){
		    param1.setMargins(0, 0, 0, 0);
		}else if(nowimg=="img3"){
		    param2.setMargins(0, 0, 0, 0);
		}else if(nowimg=="img4"){
		    param3.setMargins(0, 0, 0, 0);
		}else if(nowimg=="img5"){
		    param4.setMargins(0, 0, 0, 0);
		}else if(nowimg=="img6"){
		    param5.setMargins(0, 0, 0, 0);
		}else if(nowimg=="img7"){
		    param6.setMargins(0, 0, 0, 0);
		}
	    
	    for(int i=0;i<priority.size();i++){
	    	if(priority.get(i)==R.id.imageView1){
	    		frameLayout.addView(img1, param);
	    	}else if(priority.get(i)==R.id.imageView2){
	    		frameLayout.addView(img2, param1);
	    	}else if(priority.get(i)==R.id.imageView3){
	    		frameLayout.addView(img3, param2);
	    	}else if(priority.get(i)==R.id.imageView4){
	    		frameLayout.addView(img4, param3);
	    	}else if(priority.get(i)==R.id.imageView5){
	    		frameLayout.addView(img5, param4);
	    	}else if(priority.get(i)==R.id.imageView6){
	    		frameLayout.addView(img6, param5);
	    	}else if(priority.get(i)==R.id.imageView7){
	    		frameLayout.addView(img7, param6);
	    	}
	    }
	}
	
	public void delesetImageView() {
		for(int j=0;j<usesimg.size();j++){
			if(usesimg.get(j)=="img1"){
				a =  img1.getLeft();
		 		b =img1.getTop();
		 		frameLayout.removeView(img1);
			    param.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img2"){
				a =  img2.getLeft();
		 		b =img2.getTop();
		 		frameLayout.removeView(img2);
			    param1.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img3"){
				a =  img3.getLeft();
		 		b =img3.getTop();
		 		frameLayout.removeView(img3);
			    param2.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img4"){
				a =  img4.getLeft();
		 		b =img4.getTop();
		 		frameLayout.removeView(img4);
			    param1.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img5"){
				a =  img5.getLeft();
		 		b =img5.getTop();
		 		frameLayout.removeView(img5);
			    param4.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img6"){
				a =  img6.getLeft();
		 		b =img6.getTop();
		 		frameLayout.removeView(img6);
			    param5.setMargins(a, b, 0, 0);
			}else if(usesimg.get(j)=="img7"){
				a =  img7.getLeft();
		 		b =img7.getTop();
		 		frameLayout.removeView(img7);
			    param6.setMargins(a, b, 0, 0);
			}
		}
		Log.i("31","31="+usesimg);

	    for(int i=0;i<priority.size();i++){
	    	if(priority.get(i)==R.id.imageView1){
	    		frameLayout.addView(img1, param);
	    	}else if(priority.get(i)==R.id.imageView2){
	    		frameLayout.addView(img2, param1);
	    	}else if(priority.get(i)==R.id.imageView3){
	    		frameLayout.addView(img3, param2);
	    	}else if(priority.get(i)==R.id.imageView4){
	    		frameLayout.addView(img4, param3);
	    	}else if(priority.get(i)==R.id.imageView5){
	    		frameLayout.addView(img5, param4);
	    	}else if(priority.get(i)==R.id.imageView6){
	    		frameLayout.addView(img6, param5);
	    	}else if(priority.get(i)==R.id.imageView7){
	    		frameLayout.addView(img7, param6);
	    	}
	    }
	}
	
	public class DragViewListener implements OnTouchListener {
		// ドラッグ対象のView
		private ImageView dragView;
		// ドラッグ中に移動量を取得するための変数
		private int oldx;
		private int oldy;
		
		public DragViewListener(ImageView dragView) {
			this.dragView = dragView;
		}

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// タッチしている位置取得
			int x = (int) event.getRawX();
			int y = (int) event.getRawY();
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(WC, WC);
			

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN://タッチイベント開始
				Log.i("test","view="+view.getId());
				if(R.id.imageView1==selectedView){
					img1.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.anborder));
				}else if(R.id.imageView2==selectedView){
					img2.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.anborder));
				}else if(R.id.imageView3==selectedView){
					img3.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.anborder));
				}else if(R.id.imageView4==selectedView){
					img4.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.anborder));
				}else if(R.id.imageView5==selectedView){
					img5.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.anborder));
				}else if(R.id.imageView6==selectedView){
					img6.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.anborder));
				}else if(R.id.imageView7==selectedView){
					img7.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.anborder));
				}
				if(R.id.imageView1==view.getId()){
					if(view.getId()!=selectedView){
						img1.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
						selectedView=view.getId();
					}else{
						selectedView=0;
					}
				}else if(R.id.imageView2==view.getId()){
					if(view.getId()!=selectedView){
						img2.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
						selectedView=view.getId();
					}else{
						selectedView=0;
					}
				}else if(R.id.imageView3==view.getId()){
					if(view.getId()!=selectedView){
						img3.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
						selectedView=view.getId();
					}else{
						selectedView=0;
					}
				}else if(R.id.imageView4==view.getId()){
					if(view.getId()!=selectedView){
						img4.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
						selectedView=view.getId();
					}else{
						selectedView=0;
					}
				}else if(R.id.imageView5==view.getId()){
					if(view.getId()!=selectedView){
						img5.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
						selectedView=view.getId();
					}else{
						selectedView=0;
					}
				}else if(R.id.imageView6==view.getId()){
					if(view.getId()!=selectedView){
						img6.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
						selectedView=view.getId();
					}else{
						selectedView=0;
					}
				}else if(R.id.imageView7==view.getId()){
					if(view.getId()!=selectedView){
						img7.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.border));
						selectedView=view.getId();
					}else{
						selectedView=0;
					}
				}
				
				break;
			case MotionEvent.ACTION_MOVE:
				// 今回イベントでのView移動先の位置
				int left = dragView.getLeft() + (x - oldx);
				int top = dragView.getTop() + (y - oldy);
				// Viewを移動する
				dragView.layout(left, top, left + dragView.getWidth(), top + dragView.getHeight());
				Log.d("1","left="+left+"top"+top);
				break;
			case MotionEvent.ACTION_UP://タッチイベント終了
				int left1 = dragView.getLeft();
				int top1 = dragView.getTop();
				frameLayout.removeView(dragView);
			    params.setMargins(left1, top1, 0, 0);
			    frameLayout.addView(dragView,params);
			    setImageView("");
			    break;
			    
			}

			// 今回のタッチ位置を保持
			oldx = x;
			oldy = y;
			// イベント処理完了
			return true;
		}
	}
}