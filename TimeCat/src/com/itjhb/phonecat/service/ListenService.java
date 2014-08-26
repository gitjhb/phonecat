package com.itjhb.phonecat.service;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.itjhb.phonecat.db.TimeDao;
import com.itjhb.phonecat.db.TotalTimeDao;
import com.itjhb.phonecat.utils.AppConstant;
import com.itjhb.phonecat.utils.Utils;

import android.R.integer;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ListenService extends Service {

	//service is context!
	SharedPreferences sp;
	TimeDao dao;
	TotalTimeDao totalDao;
	Editor myEditor;
	String key;
	String keyTotal;
	private boolean isOn = false;
	private String currentDateString = "";
	TimerTask timerTask = null;
	Timer timer = new Timer();
	int totalSecs = 0;
	public MyBinder myBinder;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i("cat", "onBind");
		return new MyBinder();
	}

	public class MyBinder extends Binder {

		public int getTotalSecs() {
			// TODO Auto-generated method stub
			return totalSecs;
		}

	}
	
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		isOn = true;
		key = Utils.timeToKey();
		dao= new TimeDao(this);
		totalDao=new TotalTimeDao(this);
		totalSecs=totalDao.query(Utils.timeToKey());
		if(!dao.query()){
			dao.add("ON");
		}
		saveSecs();
		Log.i("cat", "监听服务创建");
		Log.i("cat", "读取记录：" + String.valueOf(totalSecs));
		myBinder = new MyBinder();
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(receiver, filter);

		startTimer();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("cat", "线程销毁");
		saveSecs();
		super.onDestroy();
	}

	private void startTimer() {

		if (timerTask == null) {
			timerTask = new TimerTask() {

				@Override
				public void run() {
					if (isOn) {
						totalSecs++;
						// Log.i("cat", Utils.timeFormat(totalSecs));
						if (totalSecs % 100 == 0) {
							saveSecs();
						}
//						dateChangeListener();
					}

				}
			};

			timer.schedule(timerTask, 0, 1000);
		}
	}

	protected void dateChangeListener() {
		// TODO Auto-generated method stub
		

		if (!key.equals(Utils.timeToKey())) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.valueOf(key.substring(0, 4)),
					Integer.valueOf(key.substring(5, 7)),
					Integer.valueOf(key.substring(8, 10)), 23, 59, 59);
			String currentTime = String.valueOf(calendar.getTimeInMillis());
			String currentSP = sp.getString(key, "");
			String flag = "OF";
			myEditor.putString(key, currentSP + "," + flag + currentTime);
			myEditor.commit();
		}
	}

	private void stopTimer() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("cat", "监听服务开启");
		saveSecs();
		return super.onStartCommand(intent, flags, startId);

	}
	
	private void saveSecs(){
		if(totalDao.query(Utils.timeToKey())==-1){
			totalDao.add(Utils.timeToKey(), String.valueOf(totalSecs));
		} else {
			totalDao.update(Utils.timeToKey(), String.valueOf(totalSecs));
		}
	}

//	private void saveSecs() {
//		Log.i("cat", "储存");
//		SharedPreferences sp = getSharedPreferences(AppConstant.SP_NAME,
//				Context.MODE_PRIVATE);
//		Editor myEditor = sp.edit();
//		myEditor.putInt(keyTotal, totalSecs);
//		myEditor.commit();
//	}
//
//	private void loadSecs() {
//		if (secs == -1) {
//			totalSecs = 0;
//			saveSecs();
//		} else {
//			totalSecs = secs;
//		}
//
//	}

	private void saveLog() {
		Calendar calendar = Calendar.getInstance();
		String currentTime = String.valueOf(calendar.getTimeInMillis());
		String currentSP = sp.getString(key, "");
		String flag = (isOn == true) ? "ON" : "OF";
		if (currentSP == "") {
			myEditor.putString(key, flag + currentTime);
		} else {
			myEditor.putString(key, currentSP + "," + flag + currentTime);
		}
		myEditor.commit();
	}

	/**
	 * 服务被创建的时候，或者第一次运行，23：59更改时间，需要调用方法查看是否存在这一天的记录
	 */

	private void loadLog() {
		String currentSP = sp.getString(key, "");
		if (currentSP == "") {
			saveLog();
		}
	}

	final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(final Context context, final Intent intent) {
			// Do your action here
			String action = intent.getAction();

			if (action.equals(Intent.ACTION_SCREEN_OFF)) {
				isOn = false;
				Log.i("cat", "停止计时");
				stopTimer();
				saveSecs();
				dao.add("OF");
			} else if (action.equals(Intent.ACTION_SCREEN_ON)) {
				isOn = true;
				Log.i("cat", "开始计时");
				startTimer();
				dao.add("ON");
				totalSecs=totalDao.query(Utils.timeToKey());
			}

		}

	};

}
