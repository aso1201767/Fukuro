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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class itemDetails extends Activity implements OnClickListener {

	private DBHelper dbHelper = new DBHelper(this);
	public static SQLiteDatabase db;
	private String stID = null;
	private String itempath=null;
	private int id=0;
	private String memo=null;
	private Intent iIntent=null;
	private String category=null;
	private String Fname=null;
	private String catename=null;
	//public Boolean bFavo = false;

	//Button favo;
	Button del;
	Button edit;
	Button imgeditBtn;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_details);

		db = dbHelper.getWritableDatabase();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// 現在のintentを取得する
		Intent vIntent = getIntent();
		// intentから指定キーの文字列を取得する
		stID= vIntent.getStringExtra("ID");
		itempath= vIntent.getStringExtra("itemname");
		memo=vIntent.getStringExtra("memo");
		category=vIntent.getStringExtra("category");
		Fname=vIntent.getStringExtra("Fname");
		//ImageName = imgNmae();
		//stID = String.format("%04d", id);
		
		//画像表示
		String pic = null;
		pic = itempath;
		//pic = "/data/data/" + this.getPackageName() + "/Item/" + cr.getString(0);
		System.out.println(pic);
		ImageView iv = (ImageView)findViewById(R.id.imageView1);
		Bitmap bmp = BitmapFactory.decodeFile(pic);
		iv.setImageBitmap(bmp);

		del = (Button)findViewById(R.id.btn_del);
		del.setOnClickListener(this);
		edit = (Button)findViewById(R.id.btn_edit);
		edit.setOnClickListener(this);
		imgeditBtn = (Button)findViewById(R.id.imgeditBtn);
		imgeditBtn.setOnClickListener(this);
	}

	@Override
	protected void onRestart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onRestart();
	}


	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Log.d("id","stID"+stID);
		TextView tmemo=(TextView)findViewById(R.id.textView2);
		tmemo.setText(memo);
		if(category.equals("1")){
			catename="Tシャツ";
		}else if(category.equals("2")){
			catename="シャツ";
		}else if(category.equals("3")){
			catename="ニット・カーディガン";
		}else if(category.equals("4")){
			catename="ジャケット・コート";
		}else if(category.equals("5")){
			catename="パンツ";
		}else if(category.equals("6")){
			catename="ショートパンツ";
		}else if(category.equals("7")){
			catename="全身";
		}
		
		TextView tcate=(TextView)findViewById(R.id.cateTxt);
		tcate.setText(catename);
	}


	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch (v.getId()){
		case R.id.imgeditBtn:
			//画像編集
			iIntent = new Intent(this, picture_edit.class);
			iIntent.putExtra("Fpath", itempath);
			iIntent.putExtra("Fname",Fname);
			iIntent.putExtra("previousview","itemDetails");
			iIntent.putExtra("memo",memo );
			iIntent.putExtra("category",category);
			startActivity(iIntent);
			Log.e("name1","name1="+Fname);
			break;
		case R.id.btn_edit:
			//コメント編集
			iIntent = new Intent(this, InfoEditActivity.class);
			iIntent.putExtra("Fpath", itempath);
			iIntent.putExtra("Fname",Fname);
			iIntent.putExtra("id", stID);
			iIntent.putExtra("previousview","itemDetails");
			iIntent.putExtra("category",category);
			iIntent.putExtra("memo",memo);
			startActivity(iIntent);
			break;
		case R.id.btn_del:
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			adb.setTitle("削除確認");
			adb.setMessage("このマイリストを削除しますか？");
			adb.setPositiveButton("はい", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自動生成されたメソッド・スタブ
					String sql = "DELETE FROM Item WHERE item_id = '" + stID + "'";
					db.execSQL(sql);
					//sd画像削除
					File file = new File(itempath);
					file.delete();
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