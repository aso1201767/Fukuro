package com.android.fukuro;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TopActivity extends Activity implements View.OnClickListener{
	private DBHelper dbHelper = new DBHelper(this);
	
	public static SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top);
		
		//読み書き可能なデータベースをオープン
		db = dbHelper.getWritableDatabase();
//
//		File newfile = new File("/data/data/com.android.fukuro/Item");
//		File newfile2 = new File("/data/data/com.android.fukuro/Thambnail");
//
//	    if (newfile.mkdir()){
//	      //System.out.println("ディレクトリの作成に成功しました");
//	      Log.d("ファイル作成","Itemディレクトリの作成に成功しました");
//
//	    }else{
//	      //System.out.println("ディレクトリの作成に失敗しました");
//	      Log.d("ファイル作成","Itemディレクトリの作成に失敗しました");
//	    }
//
//	    if (newfile2.mkdir()){
//		      //System.out.println("ディレクトリの作成に成功しました");
//		      Log.d("ファイル作成","Thambnailディレクトリの作成に成功しました");
//
//		    }else{
//		      //System.out.println("ディレクトリの作成に失敗しました");
//		      Log.d("ファイル作成","Thambnailディレクトリの作成に失敗しました");
//		    }
	}
	
	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		ImageButton top_btn = (ImageButton)findViewById(R.id.top_btn);
		//ボタン変数にリスナーを登録する
		top_btn.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.top_btn:
			Intent intent =new Intent(TopActivity.this, TabLayout.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		dbHelper.close();
	}

}
