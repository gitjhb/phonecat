package com.itjhb.phonecat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.itjhb.phonecat.db.TotalTimeDao;
import com.itjhb.phonecat.service.IService;
import com.itjhb.phonecat.service.ListenService;
import com.itjhb.phonecat.service.ListenService.MyBinder;
import com.itjhb.phonecat.utils.AppConstant;
import com.itjhb.phonecat.utils.Utils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.R.anim;
import android.app.Activity;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	protected static final int MSG_WHAT_TIME_TICK = 0;
	private static final int MSG_WHAT_UPDATE_UI = 1;
	private TextView tvTotalTime;
	com.actionbarsherlock.app.ActionBar actionBar;

	ListenService.MyBinder myBinder;
	ArrayList<String> keys = null;
	HashMap<String, String> map;
	MyConnection conn;
	SlidingMenu slidingMenu = null;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == MSG_WHAT_TIME_TICK) {

				int totalSecs = myBinder.getTotalSecs();
				tvTotalTime.setText(Utils.getFormatTime(totalSecs));

				handler.sendEmptyMessageDelayed(MSG_WHAT_TIME_TICK, 1000);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_main);
		findViewById();
		// 开启服务
		Intent intent = new Intent(getApplicationContext(), ListenService.class);
		startService(intent);
		// 绑定服务

		bind();

	

		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); // 触摸边界拖出菜单
		slidingMenu.setMenu(R.layout.slidingmenu_left);
		slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 将抽屉菜单与主页面关联起来
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

		map = new TotalTimeDao(this).getAllTotalTime();
		keys = new ArrayList<String>();
		Set<String> keyStrings = map.keySet();
		for (String string : keyStrings) {
			keys.add(string);
		}
		View slidView = slidingMenu.getMenu();
		ListView lv_total = (ListView) slidView.findViewById(R.id.lv_totalTime);
		lv_total.setAdapter(new MyTotalAdapter());

		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this));
		// 单击GridView元素的响应
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 弹出单击的GridView元素的位置
				switch (position) {
				case 0:
					Intent intent0 = new Intent(getApplicationContext(),
							TimeActivity.class);
					startActivity(intent0);
					break;
				case 1:
					Intent intent1 = new Intent(getApplicationContext(),
							ListDetailsActivity.class);
					startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent(MainActivity.this,
							DrawActivity.class);
					startActivity(intent2);
					break;
				case 3:
					Intent intent3 = new Intent(MainActivity.this,
							StopWatch.class);
					startActivity(intent3);
					break;
				case 4:
					Intent intent4 = new Intent(MainActivity.this,
							MyCalendar.class);
					startActivity(intent4);
					break;

				}
			}
		});
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showUI();
	}

	private class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context context) {
			this.mContext = context;
		}

		@Override
		public int getCount() {
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int position) {
			return mThumbIds[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 定义一个ImageView,显示在GridView里
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageResource(mThumbIds[position]);
			return imageView;
		}

	}

	// 展示图片
	private Integer[] mThumbIds = { R.drawable.grid1, R.drawable.grid2,
			R.drawable.grid3, R.drawable.grid4, R.drawable.grid5

	};

	private void showUI() {
		new Thread() {
			public void run() {
				try {
					sleep(200);
					handler.sendEmptyMessage(MSG_WHAT_TIME_TICK);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			};
		}.start();

	}

	public void click(View view) {
		handler.sendEmptyMessage(MSG_WHAT_TIME_TICK);
	}

	public void bind() {
		Intent intent = new Intent(getApplicationContext(), ListenService.class);
		// conn: 用来和服务建立联系，一定不能为空，不然就没有意义了。相当于中间代理人，让Activity和Service联系起来.
		conn = new MyConnection();
		bindService(intent, conn, BIND_AUTO_CREATE);

	}

	public void unbind() {
		unbindService(conn);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i("cat", "应用UI销毁");
		try {
			unbind();
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.onDestroy();
	}

	private void findViewById() {
		tvTotalTime = (TextView) findViewById(R.id.tv2);
	}



	public void startService(View view) {
		Intent intent = new Intent(this, ListenService.class);
		startService(intent);
	}

	private class MyConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			myBinder = (MyBinder) service;

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

	}

	private class MyTotalAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return map.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(MainActivity.this,
					R.layout.listview_total_item, null);
			TextView date = (TextView) view.findViewById(R.id.tv_date);
			TextView total = (TextView) view.findViewById(R.id.tv_total);
			String keyString = keys.get(position);
			date.setText(keyString);
			total.setText(Utils.timeFormat(Integer.parseInt(map.get(keyString))));

			return view;
		}

	}

}
