package com.itjhb.phonecat.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TotalTimeDao {
	private Context context;
	TotalTimeDatabaseHelper dbHelper;
	
	public TotalTimeDao(Context context){
		this.context=context;
		dbHelper=new TotalTimeDatabaseHelper(context);
	}
	
	public void add(String time) {
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("INSERT INTO "+TotalTimeDatabaseHelper.TB_NAME+ " (key, total_time) values (date(), ?)", new String[]{time});
			db.close();
		}

	}
	
	public void update(String oldnumber, String newNumber) {
	SQLiteDatabase db = dbHelper.getWritableDatabase();
	if (db.isOpen()) {
		db.execSQL("update "+TotalTimeDatabaseHelper.TB_NAME+" set total_time=? where total_time=?  ",
				new Object[] { newNumber, oldnumber });
		db.close();
	}
	}
	public List<String> getTotalTimeByPeriod(String left, String right){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<String> timeList = new ArrayList<String>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from "+TotalTimeDatabaseHelper.TB_NAME+" where key>=? AND key<?", 
					new String[]{left,right} );
			while (cursor.moveToNext()) {
				String key = cursor.getString(0);
			}
			cursor.close();
			db.close();
		}
		return timeList;
	}
	
	public List<String> getAllTotalTime(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<String> timeList = new ArrayList<String>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from "+TotalTimeDatabaseHelper.TB_NAME, 
					null );
			while (cursor.moveToNext()) {
				String key = cursor.getString(0);
			}
			cursor.close();
			db.close();
		}
		return timeList;
	}


}
