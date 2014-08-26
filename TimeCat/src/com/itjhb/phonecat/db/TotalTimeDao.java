package com.itjhb.phonecat.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TotalTimeDao {
	private static final String tag = "com.itjhb.phonecat.db";
	TotalTimeDatabaseHelper dbHelper;


	public TotalTimeDao(Context context) {
		dbHelper = new TotalTimeDatabaseHelper(context);
	}

	public void add(String date, String time) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();
			values.put(TotalTimeDatabaseHelper.COLUMN_KEY, date);
			values.put(TotalTimeDatabaseHelper.COLUMN_TOTAL, time);
			if(db.insert(TotalTimeDatabaseHelper.TABLE_TOTAL, null, values)==-1){
				Log.i(tag, "Add colomn failed, update!");
				update(date, time);
			}
//			db.execSQL("INSERT INTO totaltime (key, total) values (date(),?)",new String[]{time});
			Log.i(tag, "Add colomn successfully!");
			db.close();
		}

	}

	/**
	 * 
	 * @param date
	 * @return a Integer value which represent the total secs device used of this day.
	 */
	public int query(String date) {
		int result=-1;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
//			Cursor cursor =db.query(TotalTimeDatabaseHelper.TABLE_TOTAL,
//					null, TotalTimeDatabaseHelper.COLUMN_KEY+" = "+date,null, null, null, null);
			Cursor cursor =db.rawQuery("SELECT total from totaltime where key=?", new String[]{date});
			if(cursor.getCount()>=1) {
			if (cursor.moveToNext()) {
				
				System.out.println(cursor.getString(0));
				 result=Integer.valueOf(cursor.getString(0));
			}
			cursor.close();
			db.close();
			
			}
		}
		return result;
		
	}
	
	/**
	 * 
	 * @param date  date as the key words e.g. "2014-08-20", stored in database
	 * @param newtotal : total time updated in certain "date" e.g. "12381031" (long time)
	 */
	public void update(String date, String newtotal) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();
			values.put(TotalTimeDatabaseHelper.COLUMN_TOTAL, newtotal);
			db.update(TotalTimeDatabaseHelper.TABLE_TOTAL, values, 
					TotalTimeDatabaseHelper.COLUMN_KEY+"=?", new String[]{date});
			db.close();
		}
	}

	public List<String> getTotalTimeByPeriod(String left, String right) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<String> timeList = new ArrayList<String>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from "
					+ TotalTimeDatabaseHelper.TABLE_TOTAL
					+ " where key>=? AND key<?", new String[] { left, right });
			while (cursor.moveToNext()) {
				String key = cursor.getString(0);
			}
			cursor.close();
			db.close();
		}
		return timeList;
	}

	public HashMap<String,String> getAllTotalTime() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		HashMap<String,String> timeMap = new HashMap<String,String>();
		if (db.isOpen()) {
			Cursor cursor = db.query(TotalTimeDatabaseHelper.TABLE_TOTAL,
					new String[]{TotalTimeDatabaseHelper.COLUMN_ID, TotalTimeDatabaseHelper.COLUMN_KEY, TotalTimeDatabaseHelper.COLUMN_TOTAL},
					null, null, null, null, null);
			while (cursor.moveToNext()) {
				String key = cursor.getString(1);
				String value=cursor.getString(2);
				timeMap.put(key, value);
			}
			cursor.close();
			db.close();
		}
		return timeMap;
	}

	public void drop() {
		dbHelper.onUpgrade(null, 1, 2);
	}

}
