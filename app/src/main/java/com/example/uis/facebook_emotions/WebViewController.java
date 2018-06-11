package com.example.uis.facebook_emotions;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;



class WebViewController extends WebViewClient {
    private boolean hasFinished = false;
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        //view.setVisibility(View.GONE);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:(function() { " +
                "document.getElementsByClassName('wUrVib')[0].click();" +
                //"window.ChangeSetVisible.setVisible();" +
                "})()");
        if(hasFinished){
            view.loadUrl("javascript:(function() { " +
                    "javascript: window.ChangeSetVisible.setVisible()" +
                    "})()");
        }
        hasFinished = true;

    }
}
