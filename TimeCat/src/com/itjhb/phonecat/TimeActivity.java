package com.itjhb.phonecat;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CalendarView;
import android.widget.Toast;

public class TimeActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_calender);
		CalendarView calendarView= (CalendarView) findViewById(R.id.calendarView1);
		calendarView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(TimeActivity.this, "a",0).show();
			}
		});
		
		
	}
	
	
	
	
}
