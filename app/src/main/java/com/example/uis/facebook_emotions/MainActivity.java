package com.example.uis.facebook_emotions;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IBMCloudToneAnalyzerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IBMCloudService.INSTANCE.analyzeText("My sister is a bitch. I hope she dies someday!", this);
    }


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
    }
}
