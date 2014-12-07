package com.android.fukuro;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Cdtutorial extends Activity implements View.OnClickListener{
  private DBHelper dbHelper = new DBHelper(this);

  private SeekBar seekBar;

  Toast toast;
  public static SQLiteDatabase db;

  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.cdtutorial);
   Log.i("coor","onCreate");
         super.onCreate(savedInstanceState);

         getActionBar().setDisplayHomeAsUpEnabled(true);

         ImageButton imgbutton = (ImageButton)findViewById(R.id.addbtn);
         Button btn1 =(Button)findViewById(R.id.savebtn);
         Button upbtn=(Button)findViewById(R.id.upbtn);
         Button downbtn=(Button)findViewById(R.id.downbtn);
         Button delebtn=(Button)findViewById(R.id.delebtn);
         FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
         imgbutton.setOnClickListener(this);
         btn1.setOnClickListener(this);
         upbtn.setOnClickListener(this);
         downbtn.setOnClickListener(this);
         delebtn.setOnClickListener(this);
         frame.setOnClickListener(this);
         seekBar = (SeekBar)findViewById(R.id.seekbar);
         seekBar.setOnClickListener(this);

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

  }



//      imgbutton.setOnClickListener(new OnClickListener() {

//  private void setContentView(SampleView mView2) {
//   // TODO 自動生成されたメソッド・スタブ
 //
//  }

  @Override
  public void onDestroy(){
   super.onDestroy();
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
   if(id == android.R.id.home){
             finish();
             return true;
   }
   return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(View v) {
     switch(v.getId()){

          case R.id.addbtn:
        toast = Toast.makeText(Cdtutorial.this, "選択したカテゴリの洋服一覧を展開し\n一覧から選択した服画像を追加することができます。",Toast.LENGTH_LONG);
     toast.setGravity(Gravity.AXIS_CLIP, 0,0);
     toast.show();

     break;

          case R.id.savebtn:
           toast = Toast.makeText(Cdtutorial.this, "作成したコーディネートを保存することができます。",Toast.LENGTH_LONG);
     toast.setGravity(Gravity.AXIS_CLIP, 0,0);
     toast.show();

     break;

          case R.id.upbtn:
           toast = Toast.makeText(Cdtutorial.this, "選択した服画像を一つ上に。",Toast.LENGTH_LONG);
     toast.setGravity(Gravity.AXIS_CLIP, 0,0);
     toast.show();

     break;

          case R.id.downbtn:
           toast = Toast.makeText(Cdtutorial.this, "選択した服画像を一つ下に。",Toast.LENGTH_LONG);
     toast.setGravity(Gravity.AXIS_CLIP, 0,0);
     toast.show();

     break;

          case R.id.delebtn:
           toast = Toast.makeText(Cdtutorial.this, "選択した服画像を削除することができます。",Toast.LENGTH_LONG);
     toast.setGravity(Gravity.AXIS_CLIP, 0,0);
     toast.show();

     break;

          case R.id.seekbar:
           toast = Toast.makeText(Cdtutorial.this, "選択した服画像のサイズを変更できます。",Toast.LENGTH_LONG);
     toast.setGravity(Gravity.AXIS_CLIP, 0,0);
     toast.show();

     break;

          case R.id.frame:
           toast = Toast.makeText(Cdtutorial.this, "この範囲内でコーディネートを行うことができます。",Toast.LENGTH_LONG);
     toast.setGravity(Gravity.AXIS_CLIP, 0,0);
     toast.show();

     break;

      }
  }
}