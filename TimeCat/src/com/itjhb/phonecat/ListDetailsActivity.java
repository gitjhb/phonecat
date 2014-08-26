package com.itjhb.phonecat;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itjhb.phonecat.db.TimeDao;
import com.itjhb.phonecat.utils.AppConstant;
import com.itjhb.phonecat.utils.Utils;

import android.app.Activity;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListDetailsActivity extends Activity {

	private List<RunningAppProcessInfo> list;
	public PullToRefreshListView appListView = null;
	private List<String> timeList;
	private ArrayList<ArrayList<String>> listViewData;
	private SharedPreferences sp;
	AppListAdapter adapter;
	private String key;
	private TextView tv_date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_main);

		// fetch data
		appListView = (PullToRefreshListView) findViewById(R.id.lv1);
		init();


		appListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						init();
						adapter.addAll(listViewData);
						appListView.onRefreshComplete();
					}

				}.execute();
			}
		});

	}

	private void init() {
		listViewData = new ArrayList<ArrayList<String>>();
		tv_date=(TextView) findViewById(R.id.tv_showdate);
		tv_date.setText("Details of "+Utils.timeToKey()+" :");
		timeList = new TimeDao(this).getAllLog(Utils.timeToKey(), Utils.timeToKeyP1());;
		// Use current date as the key
		key = Utils.timeToKey();
//		sp = getSharedPreferences(AppConstant.SP_NAME, Context.MODE_PRIVATE);

//		String timePeriod = sp.getString(key, null);
//		if (timePeriod != null) {
//			String[] strings = timePeriod.split(",");
//			for (String string : strings) {
//				timeList.add(string);
//			}
//		} else {
//			Toast.makeText(getApplicationContext(), "No log file", 0).show();
//		}

		Utils.calculateData(timeList, listViewData);

		adapter = new AppListAdapter(this, listViewData);
		appListView.setAdapter(adapter);
	}
}
