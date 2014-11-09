package com.android.fukuro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
				"thambnail TEXT NOT NULL, " +
				"maked TEXT NOT NULL," +
				"favorite TEXT NOT NULL," +
				"PRIMARY KEY(mylist_id))";
		// マイリスト構成テーブル
		String sql5 = "CREATE TABLE Mylistmaking ( " +
				"mylist_id TEXT, " +
				"item_id TEXT, " +
				"item_position TEXT NOT NULL, " +
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
		
		//SQL文の実行
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
		db.execSQL(sql5);
		db.execSQL(sql6);
		
		//データ挿入
		// ユーザテーブ
		db.execSQL("INSERT INTO User(user_id,user_name) VALUES('0000001','相沢')");
		db.execSQL("INSERT INTO User(user_id,user_name) VALUES('0000002','宍戸')");
		db.execSQL("INSERT INTO User(user_id,user_name) VALUES('0000003','父さん')");
		db.execSQL("INSERT INTO User(user_id,user_name) VALUES('0000004','笹井')");
		// カテゴリテーブル
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('1','Tシャツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('2','シャツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('3','ニット・カーディガン')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('4','ジャケット・コート')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('5','パンツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('6','ショートパンツ')");
		db.execSQL("INSERT INTO Category(category_id,category_name) VALUES('7','全身')");

		db.execSQL("INSERT INTO Mylist(mylist_id,thambnail,maked,favorite) VALUES('01','item_Tshats1.png','2014/11/04 11:13:21','false')");
		db.execSQL("INSERT INTO Mylist(mylist_id,thambnail,maked,favorite) VALUES('02','item_Tshats2.png','2014/10/04 11:13:22','false')");
		db.execSQL("INSERT INTO Mylist(mylist_id,thambnail,maked,favorite) VALUES('03','item_Tshats3.png','2014/11/04 11:13:23','false')");
		db.execSQL("INSERT INTO Mylist(mylist_id,thambnail,maked,favorite) VALUES('04','item_all1.png','2014/11/02 11:14:23','true')");
		db.execSQL("INSERT INTO Mylist(mylist_id,thambnail,maked,favorite) VALUES('05','item_all2.png','2014/11/04 10:14:23','false')");
		db.execSQL("INSERT INTO Mylist(mylist_id,thambnail,maked,favorite) VALUES('06','item_all3.png','2014/11/04 10:24:23','false')");
		
		db.execSQL("INSERT INTO Item(item_id,item,category_id,memo) VALUES('0001','Testo01.png','7','')");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
