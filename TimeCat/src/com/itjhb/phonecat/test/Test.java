package com.itjhb.phonecat.test;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import com.itjhb.phonecat.db.TimeDao;
import com.itjhb.phonecat.db.TotalTimeDao;
import com.itjhb.phonecat.utils.AppConstant;
import com.itjhb.phonecat.utils.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.test.AndroidTestCase;
import junit.framework.TestCase;


public class Test extends AndroidTestCase {
	

	
	
//	public void testAdd() throws Exception{
//		TimeDao dao=new TimeDao(getContext());
//		dao.add("ON");
//		Thread.sleep(500);
//		dao.add("OF");
//		Thread.sleep(500);
//		dao.add("ON");
//		Thread.sleep(500);
//		dao.add("OF");
//		
//	}
	public void testGetAll() throws Exception{
		TimeDao dao=new TimeDao(getContext());
		ArrayList<String> aaArrayList=dao.getAllLog("2014-08-20", "2014-08-23");
		for (String string : aaArrayList) {
			System.out.println(string);
		}
			
		
			
	}
//	
//	public void testDB() throws SQLException{
//		TimeDao dao=new TimeDao(getContext());
////		for(int i=0;i<20;i++){
////			dao.add(i%2);
////			try {
////				Thread.sleep(50);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		}
//		List<Long> list=dao.getAllLog("2014-08-19", "2014-08-21");
//		System.out.println(list);
//	}

//	public void testInsert (){
//		TotalTimeDao dao= new TotalTimeDao(getContext());
//		dao.add("2014-08-20","223412");
//		dao.add("2014-08-21","1341421");
//		dao.add("2014-08-22","235231");
//		
//	}
	
	
	public void testQuery (){
		TotalTimeDao dao= new TotalTimeDao(getContext());
		dao.add("2014-08-21", "2000");
		dao.add("2014-08-21", "3000");
		dao.add("2014-08-21", "4000");
		
		assertEquals(dao.query("2014-08-21"),4000);
	}
//	
//	public void testUpdate(){
//		TotalTimeDao dao= new TotalTimeDao(getContext());
//		dao.update("2014-08-20", "20102220");
//		System.out.print(dao.query("2014-08-20"));
//	}
//	
//	public void testQueryAll(){
//		TotalTimeDao dao= new TotalTimeDao(getContext());
//		HashMap<String, String> map =dao.getAllTotalTime();
//		assertEquals(map.containsKey("2014-08-20"),true);
//		assertEquals(map.containsKey("2014-08-21"),true);
//		assertEquals(map.containsKey("2014-08-22"),true);
//	}

}
