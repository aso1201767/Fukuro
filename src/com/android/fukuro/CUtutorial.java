package com.android.fukuro;

import java.io.File;
import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CUtutorial extends Activity implements View.OnClickListener{
	private String picname = null;
	private File picFile;
	private Intent i,intent;
	static final int REQUEST_CAPTURE_IMAGE = 100;
	private static final String PITflg = "PITflg1";
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.cututorial);

	  //カメラ起動の直前の画面に飛ぶ（CameraFragment）
	        Button btn = (Button)findViewById(R.id.shotstartbtn);
	        btn.setOnClickListener(this);

	 }

	
	 public void onClick(View v) {
	  // TODO 自動生成されたメソッド・スタブ

	  //インテントに、この画面と、遷移する別の画面を指定する
//	  Intent intent = new Intent(CUtutorial.this, CameraActivity.class);

	  //インテントで指定した別の画面に遷移する
//	  startActivity(intent);
		 picname = getPicFileName();
			picFile = new File(
				Environment.getExternalStorageDirectory() + "/Item",
				picname);
			SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	        if(defaultSharedPreferences.getBoolean(PITflg,true)){
	        	defaultSharedPreferences.edit().putBoolean(PITflg,false).apply();
	                Log.d("テスト10","一回目");
	                i=new Intent(this,petutorial.class);
	        }else{
	            //二回目以降の処理
	        	Log.d("テスト10","２回目");
	        	i = new Intent(getApplicationContext(),picture_edit.class);
	        }

			 i.putExtra("Fpath", picFile.toString());
			 i.putExtra("Fname", picname);
			 i.putExtra("previousview", "camera");
			
			 
			 startActivity(i);

			 intent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);


			intent.putExtra(
				MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(picFile));

			startActivityForResult(intent,REQUEST_CAPTURE_IMAGE);
	        Log.e("test",picFile.toString());
			Log.e("test",getPicFileName());
			Log.e("test",picname);
	 }
	 
	 protected String getPicFileName(){
			Calendar c = Calendar.getInstance();
			String s = c.get(Calendar.YEAR)
				+ "_" + (c.get(Calendar.MONTH)+1)
				+ "_" + c.get(Calendar.DAY_OF_MONTH)
				+ "_" + c.get(Calendar.HOUR_OF_DAY)
				+ "_" + c.get(Calendar.MINUTE)
				+ "_" + c.get(Calendar.SECOND)
				+ ".png";
			return s;
	}
}