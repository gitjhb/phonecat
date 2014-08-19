package com.itjhb.phonecat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Inflater;

import com.itjhb.phonecat.utils.Utils;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter {

	private Context context;
	private List<ArrayList<String>> listViewData;

	public AppListAdapter(Context context, ArrayList<ArrayList<String>> list) {
		this.context = context;
		this.listViewData = list;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listViewData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listViewData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void addAll(ArrayList<ArrayList<String>> list) {
		// TODO Auto-generated method stub
		listViewData=list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;

		if (convertView == null) {
			// Log.i(TAG,"通过资源文件 创建view对象");
			view = View.inflate(context, R.layout.listview_item, null);
		} else {
			// Log.i(TAG,"使用历史缓存view对象");
			view = convertView;
		}

		ImageView iv = (ImageView) view.findViewById(R.id.list_imageView);
		TextView tv1 = (TextView) view.findViewById(R.id.list_tv1);
		TextView tv2 = (TextView) view.findViewById(R.id.list_tv2);
		TextView tv3 = (TextView) view.findViewById(R.id.list_tv3);
		iv.setImageResource(R.drawable.ic_listitem);
		// tv1.setText(list.get(position).processName);
		tv1.setText(listViewData.get(position).get(0));
		tv2.setText(listViewData.get(position).get(1));
		tv3.setText("duration: " + listViewData.get(position).get(2));
		return view;
	}



}
