package com.itjhb.phonecat.recevier;

import com.itjhb.phonecat.service.ListenService;

import android.R.string;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LockReceiver extends BroadcastReceiver {
	
	private static final String action="android.intent.action.BOOT_COMPLETED";

	/**
	 * start the service once the phone start
	 * @author JHB
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(action)) {
			Intent newIntent= new Intent(Intent.ACTION_RUN);
			newIntent.setClass(context, ListenService.class);
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(newIntent);
		}
	}

}
