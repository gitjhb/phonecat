package com.itjhb.phonecat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TotalTimeDatabaseHelper extends SQLiteOpenHelper{
	
	public static String TB_NAME="total_time";

	public TotalTimeDatabaseHelper(Context context) {
		super(context, TB_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE table "+TB_NAME+" (_id integer primary key autoincrement, key TIME, total_time varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}
