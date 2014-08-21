package com.itjhb.phonecat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TotalTimeDatabaseHelper extends SQLiteOpenHelper{
	
	public TotalTimeDatabaseHelper(Context context) {
		super(context, "totalTime.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	public static final String TABLE_TOTAL = "totaltime";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_KEY = "key";
	public static final String COLUMN_TOTAL = "total";
	
	private static final String DATABASE_CREATE = "create table "
		      + TABLE_TOTAL + "(" + COLUMN_ID
		      + " integer primary key autoincrement, " + COLUMN_KEY
		      + " text not null, "+COLUMN_TOTAL+" text not null);";

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		db.execSQL("create table totaltime (_id integer primary key autoincrement, key date(), total varchar(20));");
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOTAL);
	    onCreate(db);
	}


	

}
