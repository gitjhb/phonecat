package com.itjhb.phonecat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class StopWatch extends Activity implements Runnable {
	
	/**
	 * ��ǰ��������ʱ��
	 */
	private long time = 0;
	
	/**
	 * ��ʼʱ��
	 */
	private long startTime;

	private Handler handler;
	
	/**
	 * ������ʾʱ��
	 */
	private TextView timeView;
	
	/**
	 * �����б���ʾ�ּ�ʱ��
	 */
	private ListView listView;
	
	/**
	 * ��ʼ��ť
	 */
	private Button startButton;
	
	/**
	 * ��ͣ��ť
	 */
	private Button pauseButton;
	
	/**
	 * �ּǰ�ť
	 */
	private Button markButton;
	
	/**
	 * ���ð�ť
	 */
	private Button resetButton;
	
	/**
	 * �ּ�ʱ������
	 */
	private List<Long> marks;
	
	/**
	 * ����ĵ�ǰ״̬
	 * ��Ϊ�������С���ͣ��ֹͣ����״̬
	 */
	private int state = 0;
	
	private  static int STATE_RUNNING = 1;
	private  static int STATE_STOP = 0;
	private  static int STATE_PAUSE = 2;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_stopwatch);
        
        //��ȡ������Ϣ��ƫ�� ��
        readEnvironment();
        Toast.makeText(this, "�����Ѷ�ȡ", Toast.LENGTH_LONG).show();
        
        //��ʼ��ť
        startButton = (Button)findViewById(R.id.start);
        startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onStartClick(view);
			}
		});
        
        //��ͣ��ť
        pauseButton = (Button)findViewById(R.id.pause);
        pauseButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View view) {
        		onPauseClick(view);
        	}
        });
        
        //�ּǰ�ť
        markButton = (Button) findViewById(R.id.mark);
        markButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onMarkClick(view);
			}
		});
        
        //���ð�ť
        resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onResetClick(view);
			}
		});
        
        timeView = (TextView) findViewById(R.id.timeView);
        listView = (ListView) findViewById(R.id.ListView01);
        
        //���� handler
        handler = new Handler();
        
        // ���ø���ť
        setButtons();
        
        //����ʱ����ʾ
        if (state == STATE_STOP){
        	timeView.setText("Let's Run!");
        } else if(state == STATE_PAUSE){
        	timeView.setText(getFormatTime(time));
        }
		
        //��ʾ�б�
        refreshMarkList();
    }

    /**
     * ����״̬���ð�ť��ʾ�ڲ���ʾ
     */
	private void setButtons() {
		switch (state) {
		case 1://�����������
			startButton.setVisibility(View.GONE);
			pauseButton.setVisibility(View.VISIBLE);
			markButton.setVisibility(View.VISIBLE);
			resetButton.setVisibility(View.GONE);
			resetButton.setEnabled(false);
			break;
		case 2://�����ͣ��
			startButton.setVisibility(View.VISIBLE);
			pauseButton.setVisibility(View.GONE);
			markButton.setVisibility(View.GONE);
			resetButton.setVisibility(View.VISIBLE);
			resetButton.setEnabled(true);
			break;
		case 0://���ֹͣ��
			startButton.setVisibility(View.VISIBLE);
			pauseButton.setVisibility(View.GONE);
			markButton.setVisibility(View.GONE);
			resetButton.setVisibility(View.VISIBLE);
			resetButton.setEnabled(false);
			break;
		default:
			break;
		}
	}

    /**
     * ��ȡ����
     */
    @SuppressWarnings("unchecked")
	private void readEnvironment() {
    	SharedPreferences sharedPreferences = getSharedPreferences("environment", MODE_PRIVATE);
    	state = sharedPreferences.getInt("state", STATE_STOP);
    	startTime = sharedPreferences.getLong("startTime", 0);
    	time = sharedPreferences.getLong("time", 0);
    	
    	marks = new ArrayList<Long>();
    	SharedPreferences sharedPreferencesMarks = getSharedPreferences("marks", MODE_PRIVATE);
    	Map<String, Long> mapMarks = (Map<String, Long>) sharedPreferencesMarks.getAll();
    	for (int i = 0; i < mapMarks.size(); i++){
    		Long mark = mapMarks.get("" + i);
    		marks.add(mark);
    	}
    	
	}
    /**
     * ���滷��
     */
    private void saveEnvironment() {
    	SharedPreferences sharedPreferences = getSharedPreferences("environment", MODE_PRIVATE);
    	Editor editor = sharedPreferences.edit();
    	editor.putInt("state", state);
    	editor.putLong("time", time);
    	editor.putLong("startTime", startTime);
    	editor.commit();
    	
    	//����ּ�����
    	SharedPreferences sharedPreferencesMarks = getSharedPreferences("marks", MODE_PRIVATE);
    	Editor editorMarks = sharedPreferencesMarks.edit();
    	editorMarks.clear();
    	for(Long mark : marks){
    		editorMarks.putLong(""  + marks.indexOf(mark), mark.longValue());
    	}
    	editorMarks.commit();
    }

	@Override
	protected void onPause() {
		super.onPause();
		if (state == STATE_RUNNING){//�����������
			handler.removeCallbacks(this);
		}
		
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		//���滷��
		saveEnvironment();
		Toast.makeText(this, "��ǰ�����ѱ���", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (state == STATE_RUNNING){//�����������
			handler.post(this);
		}
	}

	/**
     * �������
     * @param view
     */
    protected void onResetClick(View view) {
    	//����״̬Ϊֹͣ
    	state = STATE_STOP;
    	
    	//����ˢ��
    	if (state == STATE_RUNNING){//�����������
			handler.removeCallbacks(this);
		}
    	
    	//��ʼ���ּ�����
		marks = new ArrayList<Long>();
		refreshMarkList();
		
		//����ʱ����ʾ
		time = 0;
		timeView.setText(getFormatTime(time));
		
		// ���ø���ť
        setButtons();
	}

    /**
     * �����ͣ
     * @param view
     */
	protected void onPauseClick(View view) {
		//����ˢ��
		if (state == STATE_RUNNING){//�����������
			handler.removeCallbacks(this);
		}
    	
		//����״̬Ϊ��ͣ
		state = STATE_PAUSE;
		
		// ���ø���ť
        setButtons();
	}

	/**
     * �����ʼ
     * @param view
     */
	protected void onStartClick(View view) {
		startTime = new Date().getTime() - time;
		
		handler.post(this);
		
		//��ʼ���ּ�����
		marks = new ArrayList<Long>();
		
		//����״̬Ϊ��������
		state = STATE_RUNNING;
		
		// ���ø���ť
        setButtons();
	}

	/**
	 * ����ּ�
	 * 
	 * @param view
	 */
	protected void onMarkClick(View view) {
		
		if(time == 0){
			return;
		}
		// ���ӷּ�����, ����ļ�����ǰ��
		marks.add(0, time);
		
		//ˢ���б�
		refreshMarkList();
	}

	/**
	 * ˢ���б�
	 */
	private void refreshMarkList() {
		//��ʾ
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		int no = marks.size();//���
		for(long mark : marks){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mark", getFormatTime(mark));
			map.put("no", this.getString(R.string.mark) + no);
			no--;
			data.add(map);
			
		}
		String[] from = new String[]{"no","mark"};
		int[] to = new int[]{R.id.no, R.id.time};
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
		listView.setAdapter(simpleAdapter);
	}
	@Override
	public void run() {
		handler.postDelayed(this, 50);
		time = new Date().getTime() - startTime;
		timeView.setText(getFormatTime(time));
	}

	/**
	 * �õ�һ����ʽ����ʱ��
	 * 
	 * @param time
	 * 			ʱ�� ����
	 * @return �֣��룺����
	 */
	private String getFormatTime(long time) {
		long millisecond = time % 1000;
		long second = (time / 1000) % 60;
		long minute = time / 1000 / 60;
		
		//�����µ�ֻ��ʾһλ
		String strMillisecond = ("000" + (millisecond / 10)).substring(("000"+(millisecond/10)).length()-2);
		//����ʾ��λ
		String strSecond = ("0" + second).substring(("0" + second).length() - 2);
		//����ʾ��λ
		String strMinute = ("0" + minute).substring(("0" + minute).length() - 2);
		
		return strMinute + ":" + strSecond + ":" + strMillisecond;
	}
}