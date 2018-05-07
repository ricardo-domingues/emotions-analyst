package com.example.uis.facebook_emotions;

import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.microsoft.projectoxford.face.contract.Face;
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
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.sql.StatementEvent;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    private TwitterLoginButton loginButton;

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
                        for (Tweet t: tweets){
                            System.out.println(t.text);
                            t.entities.media.get(0);

                        }
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });





            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


        /*loginButton = findViewById(R.id.login_button);

        permissions = new LinkedList<>();

        permissions.add(EMAIL);
        permissions.add("user_posts");
        permissions.add("user_photos");

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(permissions);

        currentProfile = Profile.getCurrentProfile();

        emotionButton = findViewById(R.id.FetchEmotionBtn);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login With Facebook successful", Toast.LENGTH_LONG).show();
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }


   /* @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
*/



    /*
    @Override
    public void onToneAnalyzerSuccess(List<ToneScore> scores) {
        String detectedTones = "";
        for(ToneScore score:scores) {
            if(score.getScore() > 0.5f) {
                detectedTones += score.getName() + " ";
            }
        }
        final String toastMessage =
                "The following emotions were detected:\n\n"
                        + detectedTones.toUpperCase();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG).show();
            }
        });
    }


}
