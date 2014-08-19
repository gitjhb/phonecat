package com.itjhb.phonecat.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.R.integer;

public class Utils {

	public static String timeToKey() {
		Calendar date = Calendar.getInstance();
		String year= String.valueOf(date.get(Calendar.YEAR));
		String month=String.valueOf(date.get(Calendar.MONTH) + 1);
		if(month.length()==1) {month="0"+month;}
		String day=String.valueOf(date.get(Calendar.DAY_OF_MONTH));
		if(day.length()==1) {day="0"+day;}
		String timeLabel = String.format("%sY%sM%sD",year,
				month, day);
		return timeLabel;
	}

	public static String timeToKeyTotalTime() {
		return timeToKey() + "Total";
	}

	public static ArrayList<String> timeToDuration(long beginTime, long endTime) {

		long duration = endTime - beginTime;
		
		int nHour = (int) (duration/(1000*60*60));
		int nMinute = (int) ((duration-nHour*60*60*1000)/(1000*60));
		int nSecond = (int) ((duration-nHour*60*60*1000-nMinute*60*1000)/1000);
	
		

		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTimeInMillis(beginTime);
		end.setTimeInMillis(endTime);
		String startString = String.format("From: %d : %d",
				start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE));
		String endString = String.format("To : %d : %d",
				end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE));
		String duraString = String.format("%d h : %d m : %d s",
				nHour, nMinute, nSecond);
		ArrayList<String> res = new ArrayList<String>();
		
		
		res.add(startString);
		res.add(endString);
		res.add(duraString);

		return res;
	}

	public static int add(int a, int b) {
		return a + b;
	}

	/**
	 * initialize the List "listViewData" for inflate the listView in return
	 * total time MainActivity
	 * 
	 * @author JHB
	 */
	public static long calculateData(List<String> timeList,
			ArrayList<ArrayList<String>> res) {
		Iterator<String> iterator = timeList.iterator();
		long totalTime = 0;
		while (iterator.hasNext()) {
			String temp1 = iterator.next();
			if (temp1.length() > 0 && temp1.substring(0, 2).equals("ON")) {
				if (iterator.hasNext()) {
					String temp2 = iterator.next();
					if (temp2.length() > 0
							&& temp2.substring(0, 2).equals("OF")) {
						long beginTime = Long.parseLong(temp1.substring(2));
						long endTime = Long.parseLong(temp2.substring(2));
						ArrayList<String> temp = Utils.timeToDuration(
								beginTime, endTime);
						totalTime += endTime - beginTime;
						res.add(temp);
					}
				}
			}
		}
		return totalTime;
	}

	/**
	 * @author JHB
	 * @param hours
	 * @param mins
	 * @param secs
	 * @return the string format of time
	 */
	public static String timeFormat(int totalSec) {
		
		int hours = (int) (totalSec / 60 / 60);
		int mins = (int) ((totalSec / 60) % 60);
		int secs = (int) (totalSec % 60);
		return String.valueOf(hours) + ":" + String.valueOf(mins) + ":"
				+ String.valueOf(secs);
	}
	
	public static String getFormatTime(int totalSec) {
//		long millisecond = time % 1000;
		int hours = (int) (totalSec / 60 / 60);
		int mins = (int) ((totalSec / 60) % 60);
		int secs = (int) (totalSec % 60);
		
		//秒以下的只显示一位
//		String strMillisecond = "" + (millisecond / 100);
		//秒显示两位
		String strSecond = ("00" + secs).substring(("00" + secs).length() - 2);
		//分显示两位
		String strMinute = ("00" + mins).substring(("00" + mins).length() - 2);
		
		String strHour = ("00" + hours).substring(("00" + hours).length() - 2);
		
		return strHour + ":" + strMinute + ":"+ strSecond;
	}

	public static int timeLongToInt(long time) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int hours = 0;
		int mins = 0;
		int secs = 0;

		hours = calendar.get(Calendar.HOUR_OF_DAY);
		mins = calendar.get(Calendar.MINUTE);
		secs = calendar.get(Calendar.SECOND);
		int totalSec = hours * 60 * 60 + mins * 60 + secs;
		return totalSec;

	}

}
