package com.example.uis.facebook_emotions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;

public class MainActivity extends AppCompatActivity /*implements IBMCloudToneAnalyzerListener*/ {

    private TwitterLoginButton twitterLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);
        twitterLoginButton = findViewById(R.id.loginButton);


        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //Do something with result, which provides a TwitterSession for making API call

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                //Calling login method and passing twitter session
                login(session);

                /*
                UserTimeline userTimeline = new UserTimeline.Builder().maxItemsPerRequest(10).build();
                System.out.println("SOMETHING");

                userTimeline.next(System.currentTimeMillis(), new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        List<Tweet> tweets = result.data.items;
                        for (Tweet t : tweets) {
                            System.out.println(t.text);
                            t.entities.media.get(0);

                        }*/
            }


            @Override
            public void failure(TwitterException exception) {


            }
        });
    }


    public void login(TwitterSession twitterSession) {
        Intent i = new Intent(MainActivity.this, HelloActivity.class);
        i.putExtra("USERNAME", twitterSession.getUserName());
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickPlacesSpike(View view) {
        Intent i = new Intent(this, PlacesActivity.class);
        startActivity(i);
    }

}
