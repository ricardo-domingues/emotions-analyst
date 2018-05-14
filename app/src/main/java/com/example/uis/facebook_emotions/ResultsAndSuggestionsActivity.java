package com.example.uis.facebook_emotions;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.uis.facebook_emotions.Model.User;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.LinkedList;
import java.util.List;

public class ResultsAndSuggestionsActivity extends AppCompatActivity {

    private TextView textViewResults;

    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_and_suggestions);

        List<ToneScore> bests = User.INSTANCE.getTheBestResults();

        textViewResults = findViewById(R.id.textViewResult);
        lottieAnimationView = findViewById(R.id.animationViewResult);



        String string = "";
        for (ToneScore toneScore: bests) {
            if(toneScore.getName().compareToIgnoreCase("joy")==0||toneScore.getName().compareToIgnoreCase("confident")==0){
                lottieAnimationView.setAnimation(R.raw.laughs);
            }else if(toneScore.getName().compareToIgnoreCase("sadness")==0||toneScore.getName().compareToIgnoreCase("anger")==0){
                lottieAnimationView.setAnimation(R.raw.cries);
            }
            string = string+toneScore.getName()+"\n";
        }
        textViewResults.setText(string);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
