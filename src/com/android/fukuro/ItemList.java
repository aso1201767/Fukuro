package com.android.fukuro;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ItemList extends Activity implements OnItemClickListener {
	// 要素をArrayListで設定
	private List<String> imgList = new ArrayList<String>();
	private List<String> filename = new ArrayList<String>();
	private List<String> favoList = new ArrayList<String>();
	private DBHelper dbHelper = new DBHelper(this);
	private String category_id=null;
	private String Itemid1=null;
	private String Itemid2=null;
	private String Itemid3=null;
	private String Itemid4=null;
	private String Itemid5=null;
	private String Itemid6=null;
	private String Itemid7=null;
	public static SQLiteDatabase db;

	GridView gridview;
	GridAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itemlist);

		db = dbHelper.getReadableDatabase();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();

		imgList = new ArrayList<String>();
		filename = new ArrayList<String>();
		favoList = new ArrayList<String>();


		// GridViewのインスタンスを生成
				gridview = (GridView) findViewById(R.id.gridview);
				// BaseAdapter を継承したGridAdapterのインスタンスを生成
				// 子要素のレイアウトファイル grid_items.xml を main.xml に inflate するためにGridAdapterに引数として渡す
				adapter = new GridAdapter(this.getApplicationContext(), R.layout.grid_items, imgList);
				// gridViewにadapterをセット
				gridview.setAdapter(adapter);
				// gridviewにクリックリスナーセット
				gridview.setOnItemClickListener(this);

				// それのパスを取り出す method
				getImagePath();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 自動生成されたメソッド・スタブ
		Intent vIntent = null;

//		if(position == 0){
//			vIntent = new Intent(this, AddMylist.class);
//		}else{
			vIntent = new Intent(this, AddMylist.class);
			vIntent.putExtra("ID", filename.get(position));
//		}
		//startActivity(vIntent);
			// インテントのインスタンス作成
	Intent data = new Intent();
	// インテントに値をセット
	data.putExtra("filepath", imgList.get(position));
	data.putExtra("MylistID", filename.get(position));
	// 結果を設定
	setResult(RESULT_OK, data);
	// サブ画面の終了
	finish();
	}

	private void getImagePath() {  //プラス画像とDBに格納してある画像のパスをimgListに挿入
		String destPath = null;

		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(Mylist.this, R.id.gridview);
		Intent vintent = getIntent();
		category_id=vintent.getStringExtra("category_id");
		Itemid1=vintent.getStringExtra("Itemid1");
		Itemid2=vintent.getStringExtra("Itemid2");
		Itemid3=vintent.getStringExtra("Itemid3");
		Itemid4=vintent.getStringExtra("Itemid4");
		Itemid5=vintent.getStringExtra("Itemid5");
		Itemid6=vintent.getStringExtra("Itemid6");
		Itemid7=vintent.getStringExtra("Itemid7");
		Cursor cr = db.rawQuery("SELECT * FROM Item WHERE category_id =\""+category_id+"\" and not item_id=\""+Itemid1
				+"\" and not item_id=\""+Itemid2+"\" and not item_id=\""+Itemid3+ "\" and not item_id=\""+Itemid4+ "\" and not item_id=\""+Itemid5
				+ "\" and not item_id=\""+Itemid6+ "\"and not item_id=\""+Itemid7+ "\" ORDER BY item", null);
		cr.moveToFirst();

		//プラスボタン
		//fileList.add("plus");
//		imgList.add("plus"); //リソースから画像をとるのでダミーのパスを指定
//		filename.add("00");
//		favoList.add("plus");
		//プラスボタン画像をfileListに挿入

		for(int cnt = 0; cnt < cr.getCount(); cnt++){
//			destPath = "/data/data/"+this.getPackageName()+"/Item/" + cr.getString(1);
			destPath = Environment.getExternalStorageDirectory() +"/Item/" + cr.getString(1);
			System.out.println(cr.getString(1));

			// List<String> imgList にはファイルのパスを入れる
			imgList.add(destPath);
			filename.add(cnt,cr.getString(0));
//			favoList.add(cr.getString(3));
			cr.moveToNext();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		dbHelper.close();
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
			//String mFavo =favoList.get(position);
			Log.d("image","favo="+favoList);

			ViewHolder holder;
			if (convertView == null) {
				// main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
				convertView = inflater.inflate(layoutId, parent, false);
				// ViewHolder を生成
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
				//holder.favo = (ImageView)convertView.findViewById(R.id.favo);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			Resources r = getResources();
			Bitmap bmp2 = BitmapFactory.decodeResource(r,R.drawable.no_star);
			Bitmap bmp3 = BitmapFactory.decodeResource(r,R.drawable.white);

//			if(position != 0){  //プラスボタン以外の画像読み出し
				Bitmap bmp = BitmapFactory.decodeFile(mFilepath);
				bmp = Bitmap.createScaledBitmap(bmp, 120, 160, true);
				holder.imageView.setImageBitmap(bmp);
//				if(mFavo.equals("true")){
//				}else{
//					holder.favo.setImageBitmap(bmp2);
//				}
//
//			}else{  //プラスボタンの画像読み出し
//				Bitmap bmp = BitmapFactory.decodeResource(r,R.drawable.plus);
//				bmp = Bitmap.createScaledBitmap(bmp, 120, 160, true);
//				holder.imageView.setImageBitmap(bmp);
//
//				//holder.favo.setImageBitmap(bmp3);
//			}

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