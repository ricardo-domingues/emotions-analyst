package com.example.uis.facebook_emotions.Services;

import android.content.Context;

import com.example.uis.facebook_emotions.Model.IBMCloudToneAnalyzerListener;
import com.example.uis.facebook_emotions.R;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.List;


public enum IBMCloudService {
    INSTANCE;

    private ToneAnalyzer initToneAnalyzer(Context context){

        ToneAnalyzer toneAnalyzer = new ToneAnalyzer("2017-07-01");
        toneAnalyzer.setUsernameAndPassword(context.getString(R.string.tone_analyzer_username),
                                            context.getString(R.string.tone_analyzer_password));

        return toneAnalyzer;
    }

    public void analyzeText(String textToAnalyze, final IBMCloudToneAnalyzerListener caller){

        ToneAnalyzer analyzer = initToneAnalyzer(caller.getContext());
        ToneOptions options = new ToneOptions.Builder()
                .addTone(Tone.EMOTION)
                .html(false).build();


        analyzer.getTone(textToAnalyze, options).enqueue(
                new ServiceCallback<ToneAnalysis>() {
                    @Override
                    public void onResponse(ToneAnalysis response) {
                        // More code here
                        List<ToneScore> scores = response.getDocumentTone()
                                .getTones()
                                .get(0)
                                .getTones();
                        caller.onToneAnalyzerSuccess(scores);

                    }
                    @Override
                    public void onFailure(Exception e) {
                        caller.onToneAnalyzerError(e);
                    }
                });
    }
}
