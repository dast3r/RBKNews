package ru.vorobjev.rbknews;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

public class NewsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.news);
		
		// ��������� ����������� � ��
		DatabaseHandler db = new DatabaseHandler(this);
		db.open();

		// �������� ������
		Cursor cursor = db.getAllData(C.RSS_ITEMS_TABLE);

		// ��������� ������� �������������
		String[] from = new String[] { C.RSS_ITEMS_TABLE_TITLE, C.RSS_ITEMS_TABLE_PUBDATE };
		int[] to = new int[] { R.id.title, R.id.date };

		// �������� ������� � ����������� ������
		SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
		ListView lvData = (ListView) findViewById(R.id.list);
		lvData.setAdapter(scAdapter);
	}

}
