package com.example.uis.facebook_emotions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.uis.facebook_emotions.Model.EmotionAnalysisResult;
import com.example.uis.facebook_emotions.Model.User;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private TextView textViewResults;

    private LottieAnimationView lottieAnimationView;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        textViewResults = findViewById(R.id.textViewResult);
        lottieAnimationView = findViewById(R.id.animationViewResult);
        String emotionsText = "";

        for (EmotionAnalysisResult.Emotion emotion: User.INSTANCE.getHighestEmotions()) {
            if(emotion.getName().compareToIgnoreCase("joy")==0||emotion.getName().compareToIgnoreCase("confident")==0){
                lottieAnimationView.setAnimation(R.raw.laughs);
            }else if(emotion.getName().compareToIgnoreCase("sadness")==0||emotion.getName().compareToIgnoreCase("anger")==0){
                lottieAnimationView.setAnimation(R.raw.cries);
            }
            emotionsText += emotion.getName()+"\n";
        }
        textViewResults.setText(emotionsText);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void onClickButtonSeeSuggestions(View view) {
        Intent intent = new Intent(this, SuggestionsActivity.class);
        startActivity(intent);
    }
}
