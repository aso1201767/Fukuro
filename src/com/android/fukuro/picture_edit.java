package com.android.fukuro;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class picture_edit extends Activity {
	private penView penview;
	private Bitmap _bm;
	private SeekBar seekBar;
	private Canvas mcanvas;
	private RadioButton eraser;
	private RadioButton cutting;
	private RadioButton move;
	private AlertDialog.Builder alertDlg;
	private String path = null;
	private String picname = null;
	private String previousview=null;
	private String pen_mode=null;
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
		Log.d("picture_edit", "onCreate");
		setTitle("画像編集");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		penview.pen_mode="eraser";
		Intent inte = getIntent();
		path = inte.getStringExtra("Fpath");
		picname = inte.getStringExtra("Fname");
		previousview=inte.getStringExtra("previousview");
		penview.previousview=previousview;
		penview.camerapath=path;
		penview.cameraname=picname;
		Log.d("picture","picname="+picname);
		if(previousview.equals("camera")){
			// 確認ダイアログの生成
	        alertDlg = new AlertDialog.Builder(this);
	        alertDlg.setTitle("画像を保存しますか");
	        //alertDlg.setMessage("");
	        alertDlg.setPositiveButton(
	            "OK",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    // 上書き ボタンクリック処理
	                	penview.saveBitmapToSd();
	                	move();
	                }
	            });
	        alertDlg.setNegativeButton(
	        	"キャンセル",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    // キャンセル ボタンクリック処理
	                }
	            });
		}else{
			// 確認ダイアログの生成
	        alertDlg = new AlertDialog.Builder(this);
	        alertDlg.setTitle("画像を保存しますか");
	        //alertDlg.setMessage("");
	        alertDlg.setPositiveButton(
	            "上書き",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    // 上書き ボタンクリック処理
	                	penview.saveBitmapToSd();
	                	move();
	                }
	            });
	        alertDlg.setNeutralButton(
	            "新規",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    // 新規 ボタンクリック処理
	                	move();
	                }
	            });
	        alertDlg.setNegativeButton(
	        	"キャンセル",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    // キャンセル ボタンクリック処理
	                }
	            });
		}   
		File dir = new File("/data/data/com.android.fukuro/Item");
        if(dir.exists()){
            
            File file = new File(dir.getAbsolutePath()+"/Testo01.png");
            if (file.exists()) {
                    Bitmap bm2 = BitmapFactory.decodeFile(file.getPath());
                    Bitmap bm3 = Bitmap.createScaledBitmap(bm2,230,250,false);
                    //((ImageView)findViewById(R.id.imageView2)).setImageBitmap(bm3); 
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
				    if(pen_mode!="move"){
					penview.undo();
				    }
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
	        move = (RadioButton)findViewById(R.id.radioButton3);

	        // ラジオグループのチェック状態変更イベントを登録
	        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	            // チェック状態変更時に呼び出されるメソッド
	            public void onCheckedChanged(RadioGroup group, int checkedId) {
	                // チェック状態時の処理を記述
	                // チェックされたラジオボタンオブジェクトを取得
	                RadioButton radioButton = (RadioButton)findViewById(checkedId);
	                if(radioButton.getId() == eraser.getId()) {
	                	penview.pen_mode="eraser";
	                	pen_mode="eraser";
	                	Log.d("ラジオボタン","消しゴムを押した");
	                }else if(radioButton.getId()==cutting.getId()){
	                	penview.pen_mode="cutting";
	                	pen_mode="cutting";
	                	Log.d("ラジオボタン","ハサミを押した");
	                }else if(radioButton.getId()==move.getId()){
	                	penview.pen_mode="move";
	                	pen_mode="move";
	                	Log.d("ラジオボタン","移動を押した");
	                }
	            }
	        });
	}

	
	@Override
	public void onDestroy(){
		super.onDestroy();
		path = null;
		picname = null;
		previousview=null;
		pen_mode=null;
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
		if(id == android.R.id.home){
            finish();  
            return true;  
		}else if (id == R.id.save) {
			// 表示
		     alertDlg.create().show();
			 //penview.saveBitmapToSd();
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
	public void move(){
		if(previousview.equals("camera")){//前画面がカメラの場合
			Intent intent = new Intent(this, InfoEditActivity.class);
			String view ="picture";
			intent.putExtra("Fpath", path);
			intent.putExtra("Fname", picname);
			intent.putExtra("previousview", view);
			startActivity(intent);
		}else{
			
		}
	}
}
