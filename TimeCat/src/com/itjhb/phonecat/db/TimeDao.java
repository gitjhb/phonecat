package com.itjhb.phonecat.db;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TimeDao {
	private Context context;
	MyDatabaseHelper dbHelper;
	
	public TimeDao(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		dbHelper=new MyDatabaseHelper(context);
	}
	
	/**
	 * 查询
	 */
	public boolean find(String number) {
		boolean result = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select number from blacknumber where number=?",
					new String[] { number });
			if (cursor.moveToNext()) {
				result = true;
			}
			cursor.close();

			db.close();
		}
		return result;
	}

	/**
	 * 添加
	 */
	public boolean add(int _switch) {
		boolean result=false;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("insert into "+ MyDatabaseHelper.DB_NAME+" (_switch, _dateTime) values (?,datetime())",
					new Object[] { _switch});
			result=true;
			db.close();
		}
		return result;
	}
	


//	/**
//	 * 删除
//	 */
//	public void delete(String number) {
//
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		if (db.isOpen()) {
//			db.execSQL("delete from blacknumber where number=?",
//					new Object[] { number });
//			db.close();
//		}
//	}

//	public void update(String oldnumber, String newNumber) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		if (db.isOpen()) {
//			db.execSQL("update blacknumber set number=? where number=?  ",
//					new Object[] { newNumber, oldnumber });
//			db.close();
//		}
//	}

	/**
	 * 查找全部记录
	 */
	public List<Long> getAllLog(String date1, String date2) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<Long> timeList = new ArrayList<Long>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from "+MyDatabaseHelper.DB_NAME+" where _dateTime>=? AND _dateTime<?", 
					new String[]{date1,date2} );
			while (cursor.moveToNext()) {
				String date = cursor.getString(2);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date d = (Date) df.parse(date);
					Long time = d.getTime();
					timeList.add(time);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
			}
			cursor.close();
			db.close();
		}
		return timeList;
	}
}
