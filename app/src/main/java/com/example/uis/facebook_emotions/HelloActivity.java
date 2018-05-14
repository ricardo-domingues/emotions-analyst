package com.example.uis.facebook_emotions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.uis.facebook_emotions.Model.IBMCloudToneAnalyzerListener;
import com.example.uis.facebook_emotions.Model.TweetToAnalyze;
import com.example.uis.facebook_emotions.Model.User;
import com.example.uis.facebook_emotions.Services.IBMCloudService;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;

public class HelloActivity extends AppCompatActivity implements IBMCloudToneAnalyzerListener {

    private TextView textViewHello;
    //private ProgressBar progressBarAnalyze;

    private  LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);


        textViewHello = findViewById(R.id.textViewHello);
        //progressBarAnalyze = findViewById(R.id.progressBarAnalyze);

        textViewHello.setText("Hello, \n" + User.INSTANCE.getUsername());

        lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.playAnimation();


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

   /* public void animate(View v) {
        if (lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
        } else {
            lottieAnimationView.playAnimation();
        }
    }*/

    @Override
    public void onTweetsAnalyzed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //progressBarAnalyze.setVisibility(View.GONE);
                Intent intent = new Intent(HelloActivity.this, ResultsAndSuggestionsActivity.class);
                startActivity(intent);
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
