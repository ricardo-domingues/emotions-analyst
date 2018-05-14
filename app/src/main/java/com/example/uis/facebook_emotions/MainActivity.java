package com.example.uis.facebook_emotions;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.example.uis.facebook_emotions.Model.User;
import com.example.uis.facebook_emotions.Services.GooglePlacesService;
import com.google.maps.model.PlaceType;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class MainActivity extends AppCompatActivity /*implements IBMCloudToneAnalyzerListener*/ {

    private TwitterLoginButton twitterLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);
        twitterLoginButton = findViewById(R.id.loginButton);

        //GooglePlacesService.queryNearbyPlaces(this, this, PlaceType.MOVIE_THEATER);

        /*
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("Emotions Analyst");
        toolbar.setBackground(getDrawable(R.drawable.toolbar));
        toolbar.setTitleTextColor(Color.GRAY);
        toolbar.setLogo(R.drawable.tw__composer_logo_blue);*/

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //Do something with result, which provides a TwitterSession for making API call
                User.INSTANCE.setTwitterSession(TwitterCore.getInstance().getSessionManager().getActiveSession());
                User.INSTANCE.setUsername(User.INSTANCE.getTwitterSession().getUserName());

                //Calling login method and passing twitter session
                login(User.INSTANCE.getTwitterSession());
            }
            @Override
            public void failure(TwitterException exception) {
            }
        });
    }


    public void login(TwitterSession twitterSession) {
        Intent i = new Intent(MainActivity.this, HelloActivity.class);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
