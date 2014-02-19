package ru.vorobjev.rbknews;

import java.lang.ref.WeakReference;

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
	private long INTERVAL = 5000;
	private ImageView splash;
	private Handler splashHandler;
	private AlarmManager alarmManager;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		splash = (ImageView) findViewById(R.id.splashscreen);
		splashHandler = new SplashHandler(splash);
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(this, UpdateNewsService.class);
		PendingIntent pending = PendingIntent.getService(this, 0, intent, 0);
		alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), INTERVAL, pending);
		
	}
	
	public void getNews(View v) {
		Intent newsIntent = new Intent(this, NewsActivity.class);
		startActivity(newsIntent);
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