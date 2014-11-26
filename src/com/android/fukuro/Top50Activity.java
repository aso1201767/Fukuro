package com.android.fukuro;

import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import android.widget.Toast;

public class Top50Activity extends Activity implements OnItemClickListener,DownloadImageTaskCallback,GoodAsyncTaskCallback{
	// 要素をArrayListで設定
	private List<Bitmap> imgList;
	private List<String> fnList;
	private DBHelper dbHelper = new DBHelper(this);
	public static SQLiteDatabase db;
	AlertDialog.Builder errorD;
	private int goodPosition;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top50);
		
		db = dbHelper.getReadableDatabase();
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// 画像をダウンロードする
		DownloadImage();
		
		errorD = new AlertDialog.Builder(this);
	}

	public void DownloadImage(){
		DownloadImageTask dit = new DownloadImageTask(this, this);
	    dit.execute("top50");
	}
	
	public void postGood(String filename){
		//Task生成
	    GoodAsyncTask pg = new GoodAsyncTask(this,this);
		pg.execute(filename);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		System.out.println(fnList.get(position));
		goodPosition = position;
		AlertDialog.Builder alertGood = new AlertDialog.Builder(this);
        // アラートダイアログのContentViewを設定します
		//Bitmap image
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(imgList.get(position));
		iv.setScaleType(ImageView.ScaleType.FIT_XY);
		iv.setAdjustViewBounds(true);
		alertGood.setView(iv);
        // アラートダイアログのOKボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		alertGood.setPositiveButton("いいね！",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	Cursor cr = db.rawQuery("SELECT COUNT(*) FROM Good WHERE good_name = \""+fnList.get(goodPosition)+"\"", null);
                		cr.moveToFirst();

                		System.out.println(cr.getInt(0));
                		int exist = cr.getInt(0);
                		cr.close();
                		//いいねしたかどうか判定
                		if(exist == 0){//いいねしていない
                			postGood(fnList.get(goodPosition));
                		}
                		else//いいねしている
                		{
                			Toast.makeText(getApplicationContext(), "既にいいねしています",
                			Toast.LENGTH_SHORT).show();
                		}
                    }
                });
        // アラートダイアログのCancelボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		alertGood.setNegativeButton("キャンセル",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // アラートダイアログのキャンセルが可能かどうかを設定します
		alertGood.setCancelable(true);
		alertGood.create();
		alertGood.show();
	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		dbHelper.close();
	}

	class ViewHolder {
		ImageView imageView;
	}

	class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int layoutId;

		public GridAdapter(Context context,  int layoutId, List<Bitmap> imgList) {
			super();
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layoutId = layoutId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				// main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
				convertView = inflater.inflate(layoutId, parent, false);
				// ViewHolder を生成
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			//プラスボタン以外の画像読み出し
			Bitmap bmp = imgList.get(position);
			bmp = Bitmap.createScaledBitmap(bmp, 120, 160, true);
			holder.imageView.setImageBitmap(bmp);
			
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
	public void onSuccessDownloadImage(List<Bitmap> bitmaplist, List<String> filenames) {
		// TODO 自動生成されたメソッド・スタブ
		imgList = bitmaplist;
		fnList = filenames;
		// GridViewのインスタンスを生成
		GridView gridview = (GridView) findViewById(R.id.gridview);
		// BaseAdapter を継承したGridAdapterのインスタンスを生成
		// 子要素のレイアウトファイル grid_items.xml を main.xml に inflate するためにGridAdapterに引数として渡す
		GridAdapter adapter = new GridAdapter(this.getApplicationContext(), R.layout.grid_photo_item, imgList);
		// gridViewにadapterをセット
		gridview.setAdapter(adapter);
		// gridviewにクリックリスナーセット
		gridview.setOnItemClickListener(this);
	}

	@Override
	public void onFailedDownloadImage() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onSuccessGood(String filename) {
		// TODO 自動生成されたメソッド・スタブ
    	db = dbHelper.getWritableDatabase();
		SQLiteStatement stmt = db.compileStatement("INSERT INTO Good(good_name) VALUES(?)");
		stmt.bindString(1,filename);
		stmt.executeInsert();
		stmt.close();
		
		Toast.makeText(getApplicationContext(), "いいねしました！",
		Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onFailedGood() {
		// TODO 自動生成されたメソッド・スタブ
		Toast.makeText(getApplicationContext(), "いいねできませんでした…",
		Toast.LENGTH_SHORT).show();
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