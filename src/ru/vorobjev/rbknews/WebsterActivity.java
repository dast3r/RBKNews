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
	private ListView lv1; // даем название listview - lv1
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 5000; // Время показа Splash картинки
	private ImageView splash;
	// Создаем массив из тем:
	private String lv_arr[] = { "00. Формулы.", "01. Электрический заряд.",
			"02. Взаимодействие зарядов. Закон Кулона.",
			"03. Электрическое поле.", "04. Диполь.",
			"05. Поляризация диэлектриков",
			"06. Условие равновесия зарядов на проводнике.",
			"07. Проводник во внешнем электрическом поле.",
			"08. Электроемкость, конденсатор." };
	private Handler splashHandler = new Handler() { // создаем новый хэндлер
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STOPSPLASH:
				// убираем Splash картинку - меняем видимость
				splash.setVisibility(View.GONE);
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main); // ставим слой
		splash = (ImageView) findViewById(R.id.splashscreen); // получаем индентификатор ImageView с Splash картинкой
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		lv1 = (ListView) findViewById(R.id.lister); // получаем идентификатор ListView
		lv1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lv_arr)); // устанавливаем массив в ListView
		lv1.setTextFilterEnabled(true);
		// Обрабатываем клик по пункту:
		lv1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				// Позиция элемента, по которому кликнули
				String itemname = Integer.valueOf(position).toString();
				// Создаем новый intent
				Intent intent = new Intent();
				intent.setClass(WebsterActivity.this, ViewActivity.class);
				Bundle b = new Bundle();
				b.putString("defStrID", itemname); // defStrID - уникальная строка, отправим itemname в другое Activity
				intent.putExtras(b);
				startActivity(intent); // запускаем intent
			}
		});
	}
}