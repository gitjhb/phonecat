package com.itjhb.phonecat;

import javax.crypto.spec.IvParameterSpec;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class DrawActivity extends Activity {

	protected static final int DRAW_CIRCLE = 0;
	private static final float radius = 500;
	private static final float radius2 = 400;
	protected static final float pi = 3.1415926f;
	ImageView im;
	Paint mPaint;
	Paint mLinePaint;
	float angle = 0;
	float speed= (2*pi/360)/6;
	Canvas canvas;
	Bitmap bitmap;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DRAW_CIRCLE:
				for(int i=0;i<=20;i++){
				float x1 = (float) (Math.sin(angle) * radius);
				float y1 = (float) (Math.cos(angle) * radius);
				float x2 = (float) (Math.sin(angle) * radius2);
				float y2 = (float) (Math.cos(angle) * radius2);
				
				canvas.drawLine(599+x2, 959-y2, 599+x1, 959-y1, mLinePaint);
				angle+=speed;
				
				//canvas.drawPoint(x, y, mPaint);
				}
				im.setImageBitmap(bitmap);
				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service);

		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setStrokeWidth(10);
		mPaint.setStyle(Paint.Style.STROKE);
		
		mLinePaint = new Paint();
		mLinePaint.setColor(Color.GREEN);
		mLinePaint.setStrokeWidth(5);
		im = (ImageView) findViewById(R.id.iv_clock);

		bitmap = Bitmap
				.createBitmap(1199, 1919, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
		canvas.drawCircle(599, 959, radius, mPaint);
		canvas.drawCircle(599, 959, radius2, mPaint);
		im.setImageBitmap(bitmap);

		

	}
	
	public void start(View view){
		System.out.println("»­Ïß");
		new Thread() {
			public void run() {
				while (true) {
					// 100- pi/300
					handler.sendEmptyMessage(DRAW_CIRCLE);
					
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();
	}

}
