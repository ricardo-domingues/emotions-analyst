package com.example.uis.facebook_emotions;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.uis.facebook_emotions.Model.IBMCloudToneAnalyzerListener;
import com.example.uis.facebook_emotions.Services.IBMCloudService;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;

public class HelloActivity extends AppCompatActivity implements IBMCloudToneAnalyzerListener {

    private TextView textViewHello;
    private ProgressBar progressBarAnalyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d4e0f8")));

        textViewHello = findViewById(R.id.textViewHello);
        progressBarAnalyze = findViewById(R.id.progressBarAnalyze);

        textViewHello.setText("Hello, \n" + User.INSTANCE.getUsername());

        UserTimeline userTimeline = new UserTimeline.Builder().maxItemsPerRequest(10).build();
        userTimeline.next(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                List<Tweet> tweets = result.data.items;
                for (Tweet t : tweets) {
                    User.INSTANCE.addTweet(new TweetToAnalyze(t.text));
                }

                IBMCloudService.INSTANCE.analyzeTweets(User.INSTANCE.getTweets(), HelloActivity.this);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    @Override
    public void onTweetsAnalyzed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBarAnalyze.setVisibility(View.GONE);
                
            }
        });
    }

    @Override
    public void onToneAnalyzerError(Exception e) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
