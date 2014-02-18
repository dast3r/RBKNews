package ru.vorobjev.rbknews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

public class ViewActivity extends Activity {
	private static final String TAG = "ViewActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �������� ���������
		setContentView(R.layout.view);
		// �������� ���������:
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Bundle bundle = getIntent().getExtras();
		String itemname = "n" + bundle.getString("defStrID"); // �������� ������ � ��������� ��� �������
		Context context = getBaseContext(); // �������� �������� ������ ��������� ���� �� ��������� �� �����
		String text = readRawTextFile(context, getResources().getIdentifier(itemname, "raw", "com.webster"));
		WebView myWebView = (WebView) findViewById(R.id.webview);
		String summary = "" + text + "";
		myWebView.loadDataWithBaseURL(null, summary, "text/html", "utf-8", "rbc.ru"); // ��������� ����� � webview
	}

	public static String readRawTextFile(Context ctx, int resId) {  // ������ ����� �� raw - ��������� �������� � ������������� �������
		InputStream inputStream = ctx.getResources().openRawResource(resId);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line;
		StringBuilder text = new StringBuilder();
		try {
			while ((line = buffreader.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}
		} catch (IOException e) {
			Log.e(TAG, "read data: ������!" + e.getMessage(), e);
		}
		return text.toString();
	}
}
