package com.android.fukuro;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Uplist extends Activity implements OnItemClickListener, UploadAsyncTaskCallback , RegisterUserTaskCallback{
	// 要素をArrayListで設定
	private List<String> imgList = new ArrayList<String>();
	private DBHelper dbHelper = new DBHelper(this);
	public static SQLiteDatabase db;
	AlertDialog alertDialog;
	AlertDialog.Builder errorD;
	String filePath;
	String user_id = null;
	String user;
	EditText input;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uplist);
		
		db = dbHelper.getReadableDatabase();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// GridViewのインスタンスを生成
		GridView gridview = (GridView) findViewById(R.id.gridview);
		// BaseAdapter を継承したGridAdapterのインスタンスを生成
		// 子要素のレイアウトファイル grid_items.xml を main.xml に inflate するためにGridAdapterに引数として渡す
		GridAdapter adapter = new GridAdapter(this.getApplicationContext(), R.layout.grid_photo_item, imgList);
		// gridViewにadapterをセット
		gridview.setAdapter(adapter);
		// gridviewにクリックリスナーセット
		gridview.setOnItemClickListener(this);

		// それのパスを取り出す method
		getImagePath();
		
		errorD = new AlertDialog.Builder(this);
		//アラート作成
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // アラートダイアログのタイトルを設定します
        alertDialogBuilder.setTitle("投稿しますか？");
        // アラートダイアログのOKボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	uploadCoordi(user_id,filePath);
                    	System.out.println(filePath);
                    }
                });
        // アラートダイアログのCancelボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // アラートダイアログのキャンセルが可能かどうかを設定します
        alertDialogBuilder.setCancelable(true);
        alertDialog = alertDialogBuilder.create();
        //user_idをデータベースから取得する
        getUserID();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        if(user_id == null){
        	AlertDialog.Builder alertReg = new AlertDialog.Builder(this);
            // アラートダイアログのタイトルを設定します
        	alertReg.setTitle("あなたの名前を入力してください");
            // アラートダイアログのEditTextを設定します
            input = new EditText(this);
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            alertReg.setView(input);
            // アラートダイアログのOKボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
            alertReg.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        	user = input.getText().toString();
                        	if(user == null || user.length() == 0){
                        		 
                        	    // ダイアログの設定
                        	    errorD.setTitle("入力エラー");          //タイトル
                        	    errorD.setMessage("名前を入力してください");      //内容
                        	 
                        	    errorD.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        	        public void onClick(DialogInterface dialog, int which) {
                        	            // TODO 自動生成されたメソッド・スタブ
                        	        }
                        	    });
                        	 
                        	    // ダイアログの作成と表示
                        	    errorD.create();
                        	    errorD.show();
                        	 
                        	}else{
                        		regUser(user);
                        		input.setText("");
                        	}
                        }
                    });
            // アラートダイアログのCancelボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
            alertReg.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        	input.setText("");
                        }
                    });
            // アラートダイアログのキャンセルが可能かどうかを設定します
            alertReg.setCancelable(true);
            alertReg.create();
            alertReg.show();
        }else{
    		// TODO 自動生成されたメソッド・スタブ
    		filePath = imgList.get(position);
    		// アラートダイアログを表示します
            alertDialog.show();
        }
	}

	private void getImagePath() {  //プラス画像とDBに格納してある画像のパスをimgListに挿入
		String destPath = null;

		Cursor cr = db.rawQuery("SELECT * FROM Item WHERE category_id = \"7\"", null);
		cr.moveToFirst();

		for(int cnt = 0; cnt < cr.getCount(); cnt++){
			destPath = Environment.getExternalStorageDirectory()+"/Item/" + cr.getString(1);
			System.out.println(cr.getString(1));
			System.out.println(destPath);
			// List<String> imgList にはファイルのパスを入れる
			imgList.add(cnt,destPath);
			cr.moveToNext();
		}
		
		cr.close();
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

		public GridAdapter(Context context,  int layoutId, List<String> imgList) {
			super();
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layoutId = layoutId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String mFilepath = imgList.get(position);

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
			Bitmap bmp = BitmapFactory.decodeFile(mFilepath);
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

	public void uploadCoordi(String... str){
		//Task生成
	    UploadAsyncTask up = new UploadAsyncTask(this,this);
		up.execute(str[0],str[1]);
	}
	
	@Override
	public void onSuccessUpload(String result) {
		// TODO 自動生成されたメソッド・スタブ
		// ダイアログの設定
	    errorD.setTitle("画像を投稿しました");      //タイトル
	    errorD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            // TODO 自動生成されたメソッド・スタブ
	        }
	    });
	 
	    // ダイアログの作成と表示
	    errorD.create();
	    errorD.show();
	}

	@Override
	public void onFailedUpload() {
		// TODO 自動生成されたメソッド・スタブ
		// ダイアログの設定
	    errorD.setTitle("画像を投稿できませんでした");      //タイトル
	    errorD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            // TODO 自動生成されたメソッド・スタブ
	        }
	    });
	 
	    // ダイアログの作成と表示
	    errorD.create();
	    errorD.show();
	}
	
	public void getUserID(){
		//Userテーブルが空かどうか判定する。空ではない場合user_idを取得する。
		Cursor cr = db.rawQuery("select count(*) from User;", null);
        cr.moveToLast();
		int count = cr.getInt(0);
		if(count != 0){
			Cursor cr2 = db.rawQuery("select * from User;", null);
	        cr2.moveToLast();
			user_id = cr2.getString(0);
			cr2.close();
		}
        cr.close();
	}
	
	public void regUser(String user_name) {
		//Task生成
	    RegisterUserTask ru = new RegisterUserTask(this,this);
		ru.execute(user_name);
	}
	
	@Override
	public void onSuccessRegisterUser(String result) {
		// TODO 自動生成されたメソッド・スタブ
		//端末のDBにuser_idを登録
		//読み書き可能なデータベースをオープン
		// 読み取り専用の場合はgetReadableDatabase()を用いる
		db = dbHelper.getWritableDatabase();
		SQLiteStatement stmt = db.compileStatement("INSERT INTO User(user_id, user_name) VALUES(?, ?)");
		stmt.bindString(1, result);
		stmt.bindString(2, user);
		stmt.executeInsert();
		stmt.close();
		Toast.makeText(getApplicationContext(), "データを登録しました。",
		Toast.LENGTH_SHORT).show();
		//登録されたuser_idを変数に入れる。
		user_id = result;
	}

	@Override
	public void onFailedRegisterUser() {
		// TODO 自動生成されたメソッド・スタブ
		// ダイアログの設定
	    errorD.setTitle("エラーが発生しました");          //タイトル
	    errorD.setMessage("ネットワークエラー");      //内容
	 
	    errorD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            // TODO 自動生成されたメソッド・スタブ
	        }
	    });
	 
	    // ダイアログの作成と表示
	    errorD.create();
	    errorD.show();
	}

	@Override
	public void onFailedFound(String result) {
		// TODO 自動生成されたメソッド・スタブ
		if(result == "unknown"){
			// TODO 自動生成されたメソッド・スタブ
			// ダイアログの設定
			errorD.setTitle("エラーが発生しました");          //タイトル
			errorD.setMessage("原因不明のエラー");      //内容
		 
			errorD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            // TODO 自動生成されたメソッド・スタブ
		        }
		    });
		 
		    // ダイアログの作成と表示
		    errorD.create();
		    errorD.show();
		}else if(result == "404"){
			// TODO 自動生成されたメソッド・スタブ
			// ダイアログの設定
			errorD.setTitle("エラーが発生しました");          //タイトル
			errorD.setMessage("ファイルが見つかりませんでした");      //内容
		 
			errorD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            // TODO 自動生成されたメソッド・スタブ
		        }
		    });
		 
		    // ダイアログの作成と表示
		    errorD.create();
		    errorD.show();
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