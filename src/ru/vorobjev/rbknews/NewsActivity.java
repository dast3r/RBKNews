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
		
		// открываем подключение к БД
		DatabaseHandler db = new DatabaseHandler(this);
		db.open();

		// получаем курсор
		Cursor cursor = db.getAllData(C.RSS_ITEMS_TABLE);

		// формируем столбцы сопоставления
		String[] from = new String[] { C.RSS_ITEMS_TABLE_TITLE, C.RSS_ITEMS_TABLE_PUBDATE };
		int[] to = new int[] { R.id.title, R.id.date };

		// создааем адаптер и настраиваем список
		SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
		ListView lvData = (ListView) findViewById(R.id.list);
		lvData.setAdapter(scAdapter);
	}

}
