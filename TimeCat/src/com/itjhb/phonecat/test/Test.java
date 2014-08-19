package com.itjhb.phonecat.test;
import com.itjhb.phonecat.utils.AppConstant;
import com.itjhb.phonecat.utils.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;
import junit.framework.TestCase;


public class Test extends AndroidTestCase {
	
	public void testUtils() throws Exception {
		
		//assertEquals(2, Utils.add(1, 1));
		SharedPreferences sp= getContext().getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE);
		
		
	}
	

}
