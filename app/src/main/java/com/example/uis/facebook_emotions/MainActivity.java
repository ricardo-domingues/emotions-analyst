package com.example.uis.facebook_emotions;

import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
// import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

public class MainActivity extends AppCompatActivity /*implements IBMCloudToneAnalyzerListener*/{


    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private LoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login With Facebook successful", Toast.LENGTH_LONG).show();
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
        //IBMCloudService.INSTANCE.analyzeText("My sister is a nice woman. I hope she lives someday!", this);
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



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

    @Override
    public void onToneAnalyzerError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public Context getContext() {
        return this;
    }*/
}
