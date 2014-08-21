package com.itjhb.phonecat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper{
	
	public static String DB_NAME="runtimelog";
	public static int version=1;
	private SQLiteDatabase db;
	

	public MyDatabaseHelper(Context context) {
		super(context, "runtime.db", null, version);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE "+DB_NAME+" (_id integer primary key autoincrement, _switch integer, _datetime DATETIME)");
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
