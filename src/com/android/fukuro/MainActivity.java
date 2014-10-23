package com.android.fukuro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	private DBHelper dbHelper = new DBHelper(this);
	public static SQLiteDatabase db;
	SeekBar seekBar;
	public Canvas mcanvas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setTitle("Main");
		
		Button btn = (Button)findViewById(R.id.btn_picture_edit);
		 btn.setOnClickListener(new View.OnClickListener() {
			 @Override
	        	public void onClick(View v) {
				// インテントのインスタンス生成
				 Intent intent = new Intent(MainActivity.this, picture_edit.class);
				 // 次画面のアクティビティ起動
				 startActivity(intent);
			 	}
		 });
		
		//読み書き可能なデータベースをオープン
		// 読み取り専用の場合はgetReadableDatabase()を用いる
		db = dbHelper.getWritableDatabase();
		
		FileOutputStream fo;

		File newfile = new File("/data/data/com.android.fukuro/Item");
		File newfile2 = new File("/data/data/com.android.fukuro/Thambnail");

		   if (newfile.mkdir()){
		     //System.out.println("ディレクトリの作成に成功しました");
		     Log.d("ファイル作成","ディレクトリの作成に成功しました");
		   }else{
		     //System.out.println("ディレクトリの作成に失敗しました");
		     Log.d("ファイル作成","ディレクトリの作成に失敗しました");
		   }

		   if (newfile2.mkdir()){
		     //System.out.println("ディレクトリの作成に成功しました");
		     Log.d("ファイル作成","ディレクトリの作成に成功しました");

		   }else{
		     //System.out.println("ディレクトリの作成に失敗しました");
		     Log.d("ファイル作成","ディレクトリの作成に失敗しました");
		   }

		   try{
		   File f = new File("/data/data/com.android.fukuro/Item/test2.txt");
		       File parent = f.getParentFile();
		       if (parent != null && parent.canWrite()) { parent.mkdirs(); }
		       fo = new FileOutputStream(f);

		   } catch (IOException e) {
		       Log.e("text作成","text作成に失敗しました");
		   }
        
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		dbHelper.close();
		
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
		if (id == R.id.save) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	
}
