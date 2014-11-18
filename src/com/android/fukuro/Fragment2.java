package com.android.fukuro;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Fragment2 extends Fragment {
	private Timer mainTimer;					//タイマー用
			//タイマタスククラス
	private TextView countText;					//テキストビュー
	private int count = 0;						//カウント
	private Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ
	private int count1=0;
	private TabLayout tablayout;
	private TabLayout parent;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		Log.d("Fragment","Create1");
		
	}
	

	@Override
	  public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
		//テキストビュー
				this.countText = (TextView)view.findViewById(R.id.tv1);
				Log.d("fragment","create");
				Button btnMove = (Button) view.findViewById(R.id.btn_picture_edit);
		        btnMove.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View v) {
		            	parent.move2();
		            }
		        });
	    return view;
	  }

}
