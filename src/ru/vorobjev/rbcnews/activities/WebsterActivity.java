package ru.vorobjev.rbcnews.activities;

import java.lang.ref.WeakReference;

import ru.vorobjev.rbcnews.db.DatabaseHandler;
import ru.vorobjev.rbcnews.servicies.UpdateNewsService;
import ru.vorobjev.rbcnews.utils.PreferencesHelper;
import ru.vorobjev.rbknews.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

public class WebsterActivity extends Activity {
	
	public static final String UPDATE_INTERVAL = "ru.vorobjev.rbcnews.WebsterActivity.UPDATE_INTERVAL";
	
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 2000;
	private static final int DEFAULT_INTERVAL = 10;
	
	private ImageView splash;
	private Handler splashHandler;
	private AlarmManager alarmManager;
	private BroadcastReceiver br;

	
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
		
		showSplash();
		startDB();
		initializeAlarmManager();
		initializeBroadcastReceiver();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		startRefreshingService();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(br);
	}

	
	
	private void initializeBroadcastReceiver() {
		br = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				startRefreshingService();
			}
		};
		IntentFilter intentFilter = new IntentFilter(UPDATE_INTERVAL);
		registerReceiver(br, intentFilter);
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
		alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), getIntervalMillis(), pending);
	}

	private long getIntervalMillis() {
		SharedPreferences p = PreferencesHelper.getPreferences(this);
		String defaultInterval = Integer.toString(DEFAULT_INTERVAL);
		String interval = p.getString("interval", defaultInterval);
		return Integer.parseInt(interval) * 1000;
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