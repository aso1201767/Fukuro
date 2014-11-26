package com.android.fukuro;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MylistDetails extends Activity implements OnClickListener {

	private DBHelper dbHelper = new DBHelper(this);
	public static SQLiteDatabase db;
	private String stID = null;
	private String hoge = null;
	public Boolean bFavo = false;

	Button favo;
	Button del;
	Button edit;
	String path = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylist_details);

		db = dbHelper.getWritableDatabase();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// 現在のintentを取得する
		Intent vIntent = getIntent();
		// intentから指定キーの文字列を取得する
		int id = vIntent.getIntExtra("ID",-1);
		stID = String.format("%02d", id);
		Log.d("id","id"+stID);

		Cursor cr = db.rawQuery("SELECT * FROM Mylist WHERE mylist_id = '"+ stID + "'", null);

		cr.moveToFirst();

		//画像表示
		String pic = null;
		pic = Environment.getExternalStorageDirectory()+"/Item/"+cr.getString(1);
		System.out.println(pic);
		ImageView iv = (ImageView)findViewById(R.id.im);
		Bitmap bmp = BitmapFactory.decodeFile(pic);
		iv.setImageBitmap(bmp);

		//お気に入り判定
		hoge = cr.getString(3);
		bFavo = Boolean.valueOf(hoge);
		System.out.println(bFavo);
		favo = (Button)findViewById(R.id.btn_favo);
		favo.setOnClickListener(this);

		if(bFavo == true){
			favo.setText("★お気に入り解除");
			favo.setBackgroundColor(0xff888888);
		}

		del = (Button)findViewById(R.id.btn_del);
		del.setOnClickListener(this);

		edit = (Button)findViewById(R.id.btn_del);
		edit.setOnClickListener(this);

	}


	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		String sql = null;
		switch (v.getId()){
		case R.id.btn_favo:
			favo = (Button)findViewById(R.id.btn_favo);
			if(bFavo == true){
				favo.setText("☆お気に入り登録");
				favo.setBackgroundColor(0xffcccccc);
				sql = "UPDATE Mylist SET favorite = 'false' WHERE mylist_id = '"+ stID + "'";
				db.execSQL(sql);
				bFavo = false;
			}else{
				favo.setText("★お気に入り解除");
				favo.setBackgroundColor(0xff888888);
				sql = "UPDATE Mylist SET favorite = 'true' WHERE mylist_id = '"+ stID + "'";
				db.execSQL(sql);
				bFavo = true;
			}
			break;
		case R.id.btn_edit:
			Intent vIntent = new Intent(this, AddMylist.class);
			vIntent.putExtra("ID", stID);
			startActivity(vIntent);
			break;
		case R.id.btn_del:
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setTitle("削除確認");
			adb.setMessage("このマイリストを削除しますか？");
			adb.setPositiveButton("はい", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自動生成されたメソッド・スタブ
					Cursor cr = db.rawQuery("SELECT mylist FROM Mylist WHERE mylist_id = '" + stID + "'", null);
					cr.moveToFirst();
					path = Environment.getExternalStorageDirectory() + "/Item/"+cr.getString(0);
					File file = new File(path);
					file.delete();
					String sql = "DELETE FROM Mylistmaking WHERE mylist_id = '" + stID + "'";
					db.execSQL(sql);
					sql = "DELETE FROM Mylist WHERE mylist_id = '" + stID + "'";
					db.execSQL(sql);
					finish();
				}
			});
			adb.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自動生成されたメソッド・スタブ

				}
			});
			AlertDialog ad = adb.create();
			ad.show();
			break;
		}

	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		dbHelper.close();
		//startActivity(new Intent(this, Mylist.class));
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
		if(id == android.R.id.home){
            finish();  
            return true;  
		}
		return super.onOptionsItemSelected(item);
	}
}