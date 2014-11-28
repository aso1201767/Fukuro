package com.android.fukuro;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class petutorial extends Activity {
	private RadioButton eraser;
	private RadioButton cutting;
	private RadioButton move;
	private String previousview=null;
	private String pen_mode=null;
	Toast toast;
	private String Fpath=null;
	private String Fname=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.petutorial);

		Log.d("picture_edit", "onCreate");
		setTitle("画像編集チュートリアル");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent i=getIntent();
		Fpath=i.getStringExtra("Fpath");
    	Fname=i.getStringExtra("Fname");

		Button btn = (Button)findViewById(R.id.draw_back_btn);
		 btn.setOnClickListener(new View.OnClickListener() {
			 @Override
	        	public void onClick(View v) {

				 //バックボタンでの処理
				 toast = Toast.makeText(petutorial.this, "消しゴム又はハサミボタン選択時に、\n行った処理を一つ戻すことができます。",Toast.LENGTH_LONG);
				 toast.setGravity(Gravity.AXIS_CLIP, 0, -300);
				 toast.show();
			 	}
		 });

		Button btnend = (Button)findViewById(R.id.infotutendbtn);
		 btnend.setOnClickListener(new View.OnClickListener() {
			 @Override
		       	public void onClick(View v) {

					Intent intent = new Intent(petutorial.this, picture_edit.class);
					intent.putExtra("Fpath", Fpath);
		        	intent.putExtra("Fname", Fname);
		        	intent.putExtra("previousview", "camera");
					//インテントで指定した別の画面に遷移する
					startActivity(intent);

					finish();
			 	}
		 });

		ImageView imgbtn = (ImageView)findViewById(R.id.imageseek);
		 imgbtn.setOnClickListener(new View.OnClickListener() {
				 @Override
		        	public void onClick(View v) {

					 toast = Toast.makeText(petutorial.this, "消しゴムのサイズを変更できます。",Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.AXIS_CLIP, 0, -300);
					 toast.show();
				 	}
			 });

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

	                	//消しゴムを押したときの処理
	                    toast = Toast.makeText(petutorial.this, "撮影した写真の不要な部分をなぞることで\n削除することができます。",Toast.LENGTH_LONG);
		   				toast.setGravity(Gravity.AXIS_CLIP, 0, -300);
						toast.show();

	                	pen_mode="eraser";
	                	Log.d("ラジオボタン","消しゴムを押した");

	                }else if(radioButton.getId()==cutting.getId()){

	                	//はさみを押したときの処理
	                	toast = Toast.makeText(petutorial.this, "撮影した写真の必要な部分を囲むことで、\n囲んだ範囲以外を削除することができます。",Toast.LENGTH_LONG);
	                	toast.setGravity(Gravity.AXIS_CLIP, 0, -300);
						toast.show();

	                	pen_mode="cutting";
	                	Log.d("ラジオボタン","ハサミを押した");

	                }else if(radioButton.getId()==move.getId()){

	                	//移動を押したときの処理
	                	toast = Toast.makeText(petutorial.this, "撮影した写真を枠内で移動することができます。",Toast.LENGTH_LONG);
	                	toast.setGravity(Gravity.AXIS_CLIP, 0, -300);
						toast.show();

	                	pen_mode="move";
	                	Log.d("ラジオボタン","移動を押した");

	                }
	            }
	        });
	}


	@Override
	public void onDestroy(){
		super.onDestroy();
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
			 //バックボタンでの処理
			 toast = Toast.makeText(petutorial.this, "ホーム画面に戻ります。",Toast.LENGTH_SHORT);
			 toast.setGravity(Gravity.AXIS_CLIP, 0, -300);
			 toast.show();

            return true;
		}else if (id == R.id.save) {
			// 表
			 //バックボタンでの処理
			 toast = Toast.makeText(petutorial.this, "編集した画像を保存します。",Toast.LENGTH_SHORT);
			 toast.setGravity(Gravity.AXIS_CLIP, 0, -300);
			 toast.show();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
