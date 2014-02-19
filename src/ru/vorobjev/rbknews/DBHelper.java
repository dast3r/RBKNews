package ru.vorobjev.rbknews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, C.DATABASE, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + C.RSS_ITEMS_TABLE + " ("
				+ "_id integer primary key autoincrement,"
				+ C.RSS_ITEMS_TABLE_TITLE + " text," 
				+ C.RSS_ITEMS_TABLE_DESCRIPTION + "description text,"
				+ C.RSS_ITEMS_TABLE_PUBDATE + "pubDate text,"
				+ C.RSS_ITEMS_TABLE_LINK + "link text"
				+ ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}