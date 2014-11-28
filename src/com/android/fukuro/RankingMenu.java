package com.android.fukuro;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RankingMenu extends DialogFragment implements View.OnClickListener,DownloadImageTaskCallback {

	AlertDialog.Builder errorD;
	public TabLayout tabLayout;
	Context context;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO 自動生成されたメソッド・スタブ
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.rankingmenu);
//		errorD = new AlertDialog.Builder(this);
//		//イメージボタンの設定
//		ImageButton btnrank = (ImageButton)findViewById(R.id.btnRank);
//		ImageButton btnlater = (ImageButton)findViewById(R.id.btnLater);
//		btnrank.setOnClickListener(this);
//		btnlater.setOnClickListener(this);
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rankingmenu, container, false);
		return view;
	}
	
	@Override
	public void onResume(){
        super.onResume();
        //context = tabLayout.getC();
        context = getActivity();
        DownloadImage();
        errorD = new AlertDialog.Builder(context);
      //イメージボタンの設定
		ImageButton btnrank =tabLayout.setImageButton(R.id.btnRank);
		ImageButton btnlater = tabLayout.setImageButton(R.id.btnLater);
		btnrank.setOnClickListener(this);
		btnlater.setOnClickListener(this);
    }
	
	public void DownloadImage(){
		DownloadImageTask dit = new DownloadImageTask(context, this);
	    dit.execute("toplater3");
	}
	
	@Override
	public void onSuccessDownloadImage(List<Bitmap> bitmaplist ,List<String> filenames) {
		// TODO 自動生成されたメソッド・スタブ
		//viewの生成
		ImageView oImg;
		ArrayList<Integer> imglist = new ArrayList<Integer>();
		imglist.add(R.id.rank1);
		imglist.add(R.id.rank2);
		imglist.add(R.id.rank3);
		imglist.add(R.id.later1);
		imglist.add(R.id.later2);
		imglist.add(R.id.later3);
		for(int i = 0 ; i < bitmaplist.size(); i++){
			tabLayout.setimage(imglist.get(i),bitmaplist.get(i));
        }
	}

	@Override
	public void onFailedDownloadImage() {
		// TODO 自動生成されたメソッド・スタブ
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){//どのボタンが押されたか判定
		case R.id.btnRank://btnRankが押された
			tabLayout.move(Top30Activity.class,null,null);
			break;
		case R.id.btnLater://btnLaterが押された
			tabLayout.move(Later30Activity.class,null,null);
			break;
		}
	}
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tabLayout = (TabLayout) activity;
    }
}