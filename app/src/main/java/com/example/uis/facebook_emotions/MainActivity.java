package com.example.uis.facebook_emotions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TimelineResult;

import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;

public class MainActivity extends AppCompatActivity /*implements IBMCloudToneAnalyzerListener*/{

    private TwitterLoginButton loginButton;

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private LoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Twitter.initialize(this);


        loginButton = findViewById(R.id.login_button);
        loginButton.setEnabled(true);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API call


                UserTimeline userTimeline = new UserTimeline.Builder().maxItemsPerRequest(10).build();
                System.out.println("SOMETHING");

                userTimeline.next(System.currentTimeMillis(), new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        List<Tweet> tweets = result.data.items;
                        for (Tweet t : tweets) {
                            System.out.println(t.text);
                            t.entities.media.get(0);

    public void onClickPlacesSpike(View view) {

        Intent i = new Intent(this, PlacesActivity.class);
        startActivity(i);
    }
}
