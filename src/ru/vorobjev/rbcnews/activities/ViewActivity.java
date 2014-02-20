package ru.vorobjev.rbcnews.activities;

import ru.vorobjev.rbcnews.constants.C;
import ru.vorobjev.rbknews.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewActivity extends Activity {
	
	WebView myWebView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE); // скрываем заголовок
		setContentView(R.layout.view);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);		// скрываем статусбар:
		String link = getIntent().getExtras().getString(C.LINK); 
		myWebView = (WebView) findViewById(R.id.webview);
		myWebView.setWebViewClient(new WebViewClient());
		myWebView.loadUrl(link); 
		myWebView.getSettings().setJavaScriptEnabled(true);
	}

}
