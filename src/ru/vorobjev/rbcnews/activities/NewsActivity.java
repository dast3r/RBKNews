package ru.vorobjev.rbcnews.activities;

import ru.vorobjev.rbcnews.constants.C;
import ru.vorobjev.rbcnews.db.DatabaseHandler;
import ru.vorobjev.rbcnews.servicies.UpdateNewsService;
import ru.vorobjev.rbknews.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class NewsActivity extends FragmentActivity implements
		android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

	public final static String REFRESH_COMPLETE = "ru.vorobjev.rbcnews.NewsActivity.broadcast";
	
	DatabaseHandler db;
	PullToRefreshListView lvData;
	SimpleCursorAdapter scAdapter;
	BroadcastReceiver br;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.news);

		db = new DatabaseHandler(this);
		db.open();

		cursor = db.getAllData(C.RSS_ITEMS_TABLE);

		String[] from = new String[] { C.RSS_ITEMS_TABLE_TITLE, C.RSS_ITEMS_TABLE_PUBDATE };
		int[] to = new int[] { R.id.title, R.id.date };

		scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to, 0);
		lvData = (PullToRefreshListView) findViewById(R.id.list);
		lvData.setAdapter(scAdapter);
		lvData.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				Intent intent = new Intent(NewsActivity.this, UpdateNewsService.class);
				startService(intent);
			}
		});
		lvData.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SQLiteCursor item = (SQLiteCursor) scAdapter.getItem(position);
				String link = item.getString(item.getColumnIndex(C.RSS_ITEMS_TABLE_LINK));
				if (link != null) {
					Intent intent = new Intent(NewsActivity.this, ViewActivity.class);
					intent.putExtra(C.LINK, link);
					startActivity(intent);
				} else {
					Toast.makeText(NewsActivity.this, "Bad link", Toast.LENGTH_LONG).show();
				}
			}
		});

		br = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				lvData.onRefreshComplete();
				cursor = db.getAllData(C.RSS_ITEMS_TABLE);
				int status = intent.getIntExtra(C.PARAM_STATUS, 0);
				if (status == C.STATUS_BAD) {
					String exception = intent.getStringExtra(C.PARAM_EXCEPTION);
					Toast.makeText(NewsActivity.this, exception, Toast.LENGTH_LONG).show();
				}
			}
		};
		IntentFilter intentFilter = new IntentFilter(REFRESH_COMPLETE);
		registerReceiver(br, intentFilter);

		getSupportLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(br);
		super.onDestroy();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
		return new RssCursorLoader(this, db);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		scAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	static class RssCursorLoader extends CursorLoader {

		DatabaseHandler db;

		public RssCursorLoader(Context context, DatabaseHandler db) {
			super(context);
			this.db = db;
		}

		@Override
		public Cursor loadInBackground() {
			return db.getAllData(C.RSS_ITEMS_TABLE);
		}

	}

}
