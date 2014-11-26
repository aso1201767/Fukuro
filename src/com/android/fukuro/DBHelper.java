package com.android.fukuro;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private final static String DB_NAME = "fukuro.db";
	private final static int DB_VERSION = 1;
	
	public DBHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// データベース作成時の処理を記述する。
		
		// テーブル作成
		// ユーザテーブル
		String sql1 = "CREATE TABLE User ( " +
						"user_id TEXT, " +
						"user_name TEXT NOT NULL, " +
						"PRIMARY KEY(user_id))";
		// カテゴリテーブル
		String sql2 = "CREATE TABLE Category ( " +
				"category_id TEXT, " +
				"category_name TEXT NOT NULL, " +
				"PRIMARY KEY(category_id))";
		// 画像テーブル
		String sql3 = "CREATE TABLE Item ( " +
				"item_id TEXT, " +
				"item TEXT NOT NULL, " +
				"category_id TEXT NOT NULL, " +
				"memo TEXT , " +
				"PRIMARY KEY(item_id), " +
				"FOREIGN KEY(category_id)REFERENCES Category(category_id))";
		// マイリストテーブル
		String sql4 = "CREATE TABLE Mylist ( " +
				"mylist_id TEXT, " +
				"mylist TEXT NOT NULL, " +
				"maked TEXT NOT NULL," +
				"favorite TEXT NOT NULL," +
				"PRIMARY KEY(mylist_id))";
		// マイリスト構成テーブル
		String sql5 = "CREATE TABLE Mylistmaking ( " +
				"mylist_id TEXT, " +
				"item_id TEXT, " +
				"item_position_R TEXT NOT NULL, " +
				"item_position_L TEXT NOT NULL, " +
				"item_position_T TEXT NOT NULL, " +
				"item_position_B TEXT NOT NULL, " +
				"item_priority TEXT NOT NULL, " +
				"magni TEXT NOT NULL, " +
				"PRIMARY KEY(mylist_id,item_id), " +
				"FOREIGN KEY(mylist_id)REFERENCES Mylist(mylist_id), " +
				"FOREIGN KEY(item_id)REFERENCES Item(item_id))";
		
		// ランキングテーブル
		String sql6 = "CREATE TABLE Ranking ( " +
				"ranking_id TEXT, " +
				"user_id TEXT NOT NULL, " +
				"ranking_thambnail TEXT NOT NULL, " +
				"ranking_item TEXT NOT NULL, " +
				"date NUMERIC NOT NULL, " +
				"good NUMERIC NOT NULL, " +
				"PRIMARY KEY(ranking_id), " +
				"FOREIGN KEY(user_id)REFERENCES User(user_id))";
		String sql7 = "CREATE TABLE Good ( " +
				"good_name TEXT, " +
				"PRIMARY KEY(good_name))";
		
		//SQL文の実行
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		db.execSQL(sql6);
		db.execSQL(sql7);
		
		//データ挿入
		// カテゴリテーブル
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('1','Tシャツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('2','シャツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('3','ニット・カーディガン')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('4','ジャケット・コート')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('5','パンツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('6','ショートパンツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('7','全身')");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public void InsertItem(SQLiteDatabase db, String name, String category, String memo){
		String picpath = name;
		String categorynum = category;
		String memovalue = memo;

		ArrayList<String> ItemList = new ArrayList<String>();

		String sql2 = "select Item_id from Item order by Item_id";

		Cursor c2 = db.rawQuery(sql2, null);

		c2.moveToFirst();

		for(int i = 0; i < c2.getCount(); i++){
			ItemList.add(c2.getString(0));
			c2.moveToNext();
		}

		int j = 0;
		String ID = null;

		for(j = 0; j < ItemList.size(); j++){

			if(!(ItemList.get(j).equals(String.format("%04d",j + 1)))){
				Log.d("check1",String.format("%04d", j + 1));
				Log.d("check2",ItemList.get(j));

				break;
			}
		}

		ID = String.format("%04d", j + 1);

		ItemList = new ArrayList<String>();

		String sql = "INSERT INTO Item(item_id,item, category_id, memo) VALUES(\""+ ID +"\",\"" + picpath + "\",\"" + categorynum + "\",\"" + memovalue +"\")";
		db.execSQL(sql);
	}
	
	public String InsertMylist(SQLiteDatabase db , String date){
		String MylistID = null;
		String time = date;
	 	ArrayList<String> ItemList = new ArrayList<String>();
 		String sql2 = "select mylist_id from Mylist order by mylist_id";
 		Cursor c2 = db.rawQuery(sql2, null);
	 	c2.moveToFirst();
		for(int i = 0; i < c2.getCount(); i++){
	 		ItemList.add(c2.getString(0));
	 		c2.moveToNext();
	 	}
		
	 	int j = 0;
	 	for(j = 0; j < ItemList.size(); j++){
	 		if(!(ItemList.get(j).equals(String.format("%02d",j + 1)))){
	 			Log.d("check1",String.format("%02d", j + 1));
	 			Log.d("check2",ItemList.get(j));
 				break;
 			}
 		}
	 	
	 	MylistID = String.format("%02d", j + 1);
 		ItemList = new ArrayList<String>();
		String sql = "INSERT INTO Mylist(mylist_id,mylist, maked, favorite) VALUES(\""+ MylistID +"\",\"" + MylistID + ".png\",\"" + time + "\",\"false\")";
	 	db.execSQL(sql);
	 	return MylistID;
	}

	public void InsertMylistmaking(SQLiteDatabase db, String MylistID, String ItemID, Integer a, Integer b, Integer c, Integer d){
		String mylistid = MylistID;
		Integer itemid =  Integer.parseInt(ItemID);
		Integer right = c;
		Integer top = b;
		Integer bottom = d;
		Integer left = a;
	
		String sql ="INSERT INTO Mylistmaking(mylist_id,item_id,item_position_R,item_priority,item_position_T,item_position_B,item_position_L,magni)"
				+ " VALUES(\""+ mylistid +"\",\""+ ItemID + "\",\""+ right + "\",\""+ itemid + "\",\""+ top + "\",\""+ bottom + "\",\""+ left + "\",\"1\")";
		db.execSQL(sql);
	}
}
