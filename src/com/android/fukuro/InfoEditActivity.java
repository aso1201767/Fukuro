package com.android.fukuro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class InfoEditActivity extends Activity implements View.OnClickListener{

	private DBHelper dbHelper = new DBHelper(this);

	public static SQLiteDatabase db;
	private picture_edit picture;

	RelativeLayout mainlayout;
	InputMethodManager inputMethodManager;
	Long str = null;
	String memo = null;
	String path = null;
	String picname = null;
	String category = null;
	String previousview=null;
	private ArrayList<String> ItemList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infoedit);

		Log.d("テスト","05");
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		db = dbHelper.getWritableDatabase();

        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(this);

        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(this);

        EditText text1 = (EditText)findViewById(R.id.editText1);
        mainlayout = (RelativeLayout)findViewById(R.id.RelativeLayout1);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		Intent inte = getIntent();
		path = inte.getStringExtra("Fpath");
		picname = inte.getStringExtra("Fname");
		Log.d("check",path);
		Log.d("check",picname);

        text1.addTextChangedListener(watchHandler);

		FileInputStream in = null;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		BitmapFactory.Options options
			= new BitmapFactory.Options();
		options.inSampleSize =1;
		Bitmap capturedImage
			= BitmapFactory.decodeStream(
				in,
				null,
				options);
		
		capturedImage = Bitmap.createScaledBitmap(capturedImage, 350, 350, false);

		((ImageView)findViewById(R.id.im)).setImageBitmap(capturedImage);

		
		Cursor c = db.query("Category", new String[] {"category_name"}, null, null, null, null, null);

		c.moveToFirst();
		 CharSequence[] list = new CharSequence[c.getCount()];
         for (int i = 0; i < list.length; i++) {
             list[i] = c.getString(0);
             c.moveToNext();
         }
         c.close();

         Spinner spinner = (Spinner)this.findViewById(R.id.Spinner01);
         spinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, list));

         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	    // アイテムが選択された時の動作
        	        public void onItemSelected(AdapterView parent,View view, int position,long id) {
        	        // Spinner を取得
        	        Spinner spinner = (Spinner) parent;
        	        // 選択されたアイテムのテキストを取得
        	        str = spinner.getSelectedItemId();
        	        str = str + 1;

        	    }

        	    // 何も選択されなかった時の動作
        	    public void onNothingSelected(AdapterView parent) {
        	    }
        	    });

	}
	
	public boolean onTouchEvent(MotionEvent event){
		//キーボードを隠す
		inputMethodManager.hideSoftInputFromWindow(mainlayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		//背景にフォーカスを移す
		mainlayout.requestFocus();
		return false;
	}

    private TextWatcher watchHandler = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("textcheck1", "beforeTextChanged() s:" + s.toString() + " start:" + String.valueOf(start) + " count:" + String.valueOf(count) +
                       " after:" + String.valueOf(after));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("textcheck2", "onTextChanged() s:" + s.toString() + " start:" + String.valueOf(start) + " before:" + String.valueOf(before) +
                       " count:" + String.valueOf(count));
            memo = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("textcheck3", "afterTextChanged()");
        }
    };




	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		Intent movein = null;
		switch(v.getId()){

		case R.id.button1:

			category = str.toString();

			this.dbHelper.InsertItem(db,picname,category,memo);

			String sql2 = "select Item_id from Item order by Item_id";

			Cursor c2 = db.rawQuery(sql2, null);

			c2.moveToFirst();

			for(int i = 0; i < c2.getCount(); i++){
				ItemList.add(c2.getString(0));
				Log.d("test", "id=" + ItemList);
				c2.moveToNext();
			}

			ItemList = new ArrayList<String>();

			break;

		case R.id.button2:
			//sd画像削除
			File file = new File(path);
			file.delete();
			Toast t = Toast.makeText(this, "ファイルを削除しました", Toast.LENGTH_LONG);
			t.show();
			//インテントで指定した別の画面に遷移する

			break;
		}
		
		Intent inte = getIntent();
		previousview = inte.getStringExtra("previousview");
		if(previousview.equals("picture")){//前画面が画像編集処理
			//インテントに、この画面と、遷移する別の画面を指定する
			movein = new Intent(InfoEditActivity.this, TabLayout.class);
		}else{
			
		}
		startActivity(movein);
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
		if(id == android.R.id.home){
            finish();  
            return true;  
		}
		return super.onOptionsItemSelected(item);
	}

}
