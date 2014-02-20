package ru.vorobjev.rbcnews.activities;

import java.lang.ref.WeakReference;

import ru.vorobjev.rbcnews.db.DatabaseHandler;
import ru.vorobjev.rbcnews.servicies.UpdateNewsService;
import ru.vorobjev.rbknews.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

public class WebsterActivity extends Activity {
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 2000;
	private long INTERVAL = 10000;
	private ImageView splash;
	private Handler splashHandler;
	private AlarmManager alarmManager;

	
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
		showSplash();
		startDB();
		initializeAlarmManager();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		startRefreshingService();
	}

	
	
	private void initializeAlarmManager() {
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	}
	
	private void startDB() {
		DatabaseHandler db = new DatabaseHandler(this);
		db.open();
	}

	private void showSplash() {
		splash = (ImageView) findViewById(R.id.splashscreen);
		splashHandler = new SplashHandler(splash);
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	}
	
	private void startRefreshingService() {
		Intent intent = new Intent(this, UpdateNewsService.class);
		PendingIntent pending = PendingIntent.getService(this, 0, intent, 0);
		alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), INTERVAL, pending);
	}

	public void getNews(View v) {
		Intent newsIntent = new Intent(this, NewsActivity.class);
		startActivity(newsIntent);
	}

	public void getPreferences(View v) {
		Intent settingsActivity = new Intent(this, PreferencesActivity.class);
		startActivity(settingsActivity);
	}

	private static class SplashHandler extends Handler {

		WeakReference<ImageView> splash;

		public SplashHandler(ImageView activity) {
			splash = new WeakReference<ImageView>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STOPSPLASH:
				ImageView splashView = splash.get();
				if (splashView != null) {
					splashView.setVisibility(View.GONE);
				}
				break;
			}
		}
	}
}