package com.android.fukuro;

import java.util.ArrayList;
import java.util.List;
import com.android.fukuro.TabLayout.ReturnValue;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Mylist extends Fragment implements OnItemClickListener {
	public TabLayout tabLayout;
	// 要素をArrayListで設定
	private List<String> imgList = new ArrayList<String>();
	private List<Integer> filename = new ArrayList<Integer>();
	private List<String> favoList = new ArrayList<String>();
	public static SQLiteDatabase db;
	private GridAdapter adapter=null;
	Context context;
	Bitmap bmp;
	GridView gridview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mylist, container, false);
		
		return view;
	}
	public void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
		Log.d("Mylist","onStart");
	}
	
	@Override
	public void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();

		imgList = new ArrayList<String>();
		filename = new ArrayList<Integer>();
		favoList = new ArrayList<String>();

		// GridViewのインスタンスを生成
		gridview = tabLayout.setgrid();
		// BaseAdapter を継承したGridAdapterのインスタンスを生成
		// 子要素のレイアウトファイル grid_items.xml を main.xml に inflate するためにGridAdapterに引数として渡す
		context = tabLayout.getC();
		adapter = new GridAdapter(context, R.layout.grid_item, imgList);
		// gridViewにadapterをセット
		gridview.setAdapter(adapter);
		// gridviewにクリックリスナーセット
		gridview.setOnItemClickListener(this);

		// それのパスを取り出す method
		getImagePath();
	}
	@Override
	public void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}
	@Override
	public void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
		Log.d("MyList","onStop");
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 自動生成されたメソッド・スタブ

		if(position == 0){
			tabLayout.move(Coordinate.class,null,null);
		}else{
			tabLayout.move(MylistDetails.class,"ID",filename.get(position));
			Log.i("100","100="+filename.get(position));
		}

	}

	private void getImagePath() {  //プラス画像とDBに格納してある画像のパスをimgListに挿入
		ReturnValue value=tabLayout.mylist_db();
		imgList=value.rImgList;
		filename=value.rFilename;
		favoList=value.rFavoList;
	}

	@Override
	public void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		adapter=null;
		bmp.recycle();
		//dbHelper.close();
	}

	class ViewHolder {
		ImageView imageView;
		ImageView favo;
		ImageView nStar;
	}

	class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int layoutId;

		public GridAdapter(Context context,  int layoutId, List<String> imgList) {
			super();
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layoutId = layoutId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String mFilepath = imgList.get(position);
			String mFavo =favoList.get(position);
			Log.d("image","favo="+favoList);

			ViewHolder holder;
			if (convertView == null) {
				// main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
				convertView = inflater.inflate(layoutId, parent, false);
				// ViewHolder を生成
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
				holder.favo = (ImageView)convertView.findViewById(R.id.favo);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			Resources r = getResources();
			Bitmap bmp2 = BitmapFactory.decodeResource(r,R.drawable.no_star);
			Bitmap bmp3 = BitmapFactory.decodeResource(r,R.drawable.white);

			if(position != 0){  //プラスボタン以外の画像読み出し
				bmp = BitmapFactory.decodeFile(mFilepath);
				bmp = Bitmap.createScaledBitmap(bmp, 120, 160, true);
				holder.imageView.setImageBitmap(bmp);
				if(mFavo.equals("true")){

				}else{
					holder.favo.setImageBitmap(bmp2);
				}

			}else{  //プラスボタンの画像読み出し
				Bitmap bmp = BitmapFactory.decodeResource(r,R.drawable.plus);
				holder.imageView.setImageBitmap(bmp);
				bmp = Bitmap.createScaledBitmap(bmp, 120, 160, true);
				holder.imageView.setImageBitmap(bmp);
				holder.favo.setImageBitmap(bmp3);
			}

			return convertView;
		}

		@Override
		public int getCount() {
			// List<String> imgList の全要素数を返す
			return imgList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tabLayout = (TabLayout) activity;
    }
}
