package com.example.uis.facebook_emotions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.uis.facebook_emotions.AsyncTasks.EmotionAnalysisTask;
import com.example.uis.facebook_emotions.Model.EmotionAnalysisListener;
import com.example.uis.facebook_emotions.Model.TweetToAnalyze;
import com.example.uis.facebook_emotions.Model.User;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class HelloActivity extends AppCompatActivity implements EmotionAnalysisListener {

    //private ProgressBar progressBarAnalyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        TextView textViewHello = findViewById(R.id.textViewHello);
        textViewHello.setText("Hello, \n" + User.INSTANCE.getUsername());

        LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.playAnimation();


        UserTimeline userTimeline = new UserTimeline.Builder().maxItemsPerRequest(10).build();
        userTimeline.next(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {

                for (Tweet t : result.data.items) {
                    String mediaUrl = t.entities.media.isEmpty() ? null : t.entities.media.get(0).mediaUrlHttps;
                    User.INSTANCE.addTweet(new TweetToAnalyze(t.text, mediaUrl));
                }
                new EmotionAnalysisTask(HelloActivity.this).execute();
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }

    @Override
    public void onSuccess() {
        Intent i = new Intent(this, ResultsActivity.class);
        startActivity(i);
    }

    @Override
    public void onError(Exception e) {
        Log.e("EMOTION_ANALYSIS_ERROR", "Something went wrong");
        e.printStackTrace();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
