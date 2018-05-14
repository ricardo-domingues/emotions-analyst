package com.example.uis.facebook_emotions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
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

    public void onClickButtonSeeSuggestions(View view) {
        Intent intent = new Intent(this, SuggestionsActivity.class);
        startActivity(intent);
    }
}
