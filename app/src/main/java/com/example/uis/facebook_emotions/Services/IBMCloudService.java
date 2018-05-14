package com.example.uis.facebook_emotions.Services;

import android.content.Context;

import com.example.uis.facebook_emotions.Model.IBMCloudToneAnalyzerListener;
import com.example.uis.facebook_emotions.R;
import com.example.uis.facebook_emotions.Model.TweetToAnalyze;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.SentenceTone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneCategory;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.LinkedList;
import java.util.List;


public enum IBMCloudService {
    INSTANCE;

    private ToneAnalyzer initToneAnalyzer(Context context){

        ToneAnalyzer toneAnalyzer = new ToneAnalyzer("2017-07-01");
        toneAnalyzer.setUsernameAndPassword(context.getString(R.string.tone_analyzer_username),
                                            context.getString(R.string.tone_analyzer_password));

        return toneAnalyzer;
    }

    public void analyzeTweets(final List<TweetToAnalyze> tweetsToAnalyze, final IBMCloudToneAnalyzerListener caller){

        ToneAnalyzer analyzer = initToneAnalyzer(caller.getContext());
        ToneOptions options = new ToneOptions.Builder()
                .addTone(Tone.EMOTION)
                .addTone(Tone.LANGUAGE)
                .html(false).build();

        final int[] counter = {0};

        for (final TweetToAnalyze tweetToAnalyze: tweetsToAnalyze) {

            analyzer.getTone(tweetToAnalyze.getText(), options).enqueue(
                    new ServiceCallback<ToneAnalysis>() {
                        @Override
                        public void onResponse(ToneAnalysis response) {
                            // More code here
                            List<ToneScore> scores = new LinkedList<>();

                            for (ToneCategory toneCategory:response.getDocumentTone().getTones()){
                                scores.addAll(toneCategory.getTones());
                            }

                            tweetToAnalyze.setEmotionsInTweet(scores);

                            counter[0]++;

                            if(counter[0]==tweetsToAnalyze.size()){
                                caller.onTweetsAnalyzed();
                            }



                        }
                        @Override
                        public void onFailure(Exception e) {
                            caller.onToneAnalyzerError(e);
                        }
                    });
        }



    }
}
