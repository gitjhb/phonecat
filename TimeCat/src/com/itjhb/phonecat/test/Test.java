package com.itjhb.phonecat.test;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.itjhb.phonecat.db.TimeDao;
import com.itjhb.phonecat.utils.AppConstant;
import com.itjhb.phonecat.utils.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.test.AndroidTestCase;
import junit.framework.TestCase;


public class Test extends AndroidTestCase {
	
	public void testUtils() throws Exception {
		
		//assertEquals(2, Utils.add(1, 1));
		SharedPreferences sp= getContext().getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE);
		
		
	}
	
	
	public void testAdd() throws Exception{
		TimeDao dao=new TimeDao(getContext());
		assertEquals(dao.add(0), true);
	}
	
	public void testDB() throws SQLException{
		TimeDao dao=new TimeDao(getContext());
//		for(int i=0;i<20;i++){
//			dao.add(i%2);
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		List<String> list=dao.getAllLog("2014-08-19", "2014-08-21");
		System.out.println(list);
	}
	

}
