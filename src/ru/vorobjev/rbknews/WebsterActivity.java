package ru.vorobjev.rbknews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class WebsterActivity extends Activity {
	private ListView lv1; // ���� �������� listview - lv1
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 5000; // ����� ������ Splash ��������
	private ImageView splash;
	// ������� ������ �� ���:
	private String lv_arr[] = { "00. �������.", "01. ������������� �����.",
			"02. �������������� �������. ����� ������.",
			"03. ������������� ����.", "04. ������.",
			"05. ����������� ������������",
			"06. ������� ���������� ������� �� ����������.",
			"07. ��������� �� ������� ������������� ����.",
			"08. ��������������, �����������." };
	private Handler splashHandler = new Handler() { // ������� ����� �������
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STOPSPLASH:
				// ������� Splash �������� - ������ ���������
				splash.setVisibility(View.GONE);
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main); // ������ ����
		splash = (ImageView) findViewById(R.id.splashscreen); // �������� �������������� ImageView � Splash ���������
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		lv1 = (ListView) findViewById(R.id.lister); // �������� ������������� ListView
		lv1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lv_arr)); // ������������� ������ � ListView
		lv1.setTextFilterEnabled(true);
		// ������������ ���� �� ������:
		lv1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				// ������� ��������, �� �������� ��������
				String itemname = Integer.valueOf(position).toString();
				// ������� ����� intent
				Intent intent = new Intent();
				intent.setClass(WebsterActivity.this, ViewActivity.class);
				Bundle b = new Bundle();
				b.putString("defStrID", itemname); // defStrID - ���������� ������, �������� itemname � ������ Activity
				intent.putExtras(b);
				startActivity(intent); // ��������� intent
			}
		});
	}
}