package com.example.uis.facebook_emotions;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.io.IOException;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String s = getIntent().getStringExtra("MARKER_TITLE");

        setContentView(R.layout.webview_layout);
        webView = findViewById(R.id.help_webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.google.com/search?q=" + s.replace(" ", "+"));

        webView.setVisibility(View.GONE);
        webView.setWebViewClient(new WebViewController());

        webView.addJavascriptInterface(new JavaScriptInterface(this), "ChangeSetVisible");
    }

    public class JavaScriptInterface{

        Context mContext;
        private int counter = 0;

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void setVisible(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(counter == 1) {
                        webView.setVisibility(View.VISIBLE);
                    }
                    counter ++;
                }
            });
        }
    }

}
