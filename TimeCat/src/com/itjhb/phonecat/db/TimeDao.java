package com.itjhb.phonecat.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
	

//	SELECT strftime('%s','now') - strftime('%s','2004-01-01 02:34:56');
	/**
	 * 添加
	 */
	public boolean add(String _switch) {
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
	
	public boolean query(){
		boolean result=false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if(db.isOpen()){
			Cursor cursor=db.rawQuery("select _switch from runtimelog;",null);
			if(cursor.getCount()>=1) result=true;
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
	public ArrayList<String> getAllLog(String date1, String date2) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ArrayList<String> timeList = new ArrayList<String>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from "+MyDatabaseHelper.DB_NAME+" where _dateTime>=? AND _dateTime<?", 
					new String[]{date1,date2} );
			while (cursor.moveToNext()) {
				String state=cursor.getString(1);
				String date = cursor.getString(2);
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				try {
					java.util.Date date3=df.parse(date);
					long mills=date3.getTime();
					timeList.add(state+String.valueOf(mills));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
//				timeList.add(state+time); 
				
				
			}
			cursor.close();
			db.close();
		}
		return timeList;
	}
}
