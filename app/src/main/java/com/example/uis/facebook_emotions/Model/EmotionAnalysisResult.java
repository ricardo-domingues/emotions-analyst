package com.example.uis.facebook_emotions.Model;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.microsoft.projectoxford.face.contract.Emotion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class EmotionAnalysisResult {

    /* Microsoft Face API :
       Anger
       Fear
       Happiness (Converted to Joy)
       Sadness
       Contempt - Ignored
       Disgust - Ignored
       Neutral - Ignored
       Suprise - Ignored
     */

    /* IBM Watson Tone Analyzer :
       Anger
       Fear
       Joy
       Sadness
       Analytical
       Confident
       Tentative
     */


    private HashMap<String, Emotion> emotions;

    EmotionAnalysisResult() {

        emotions = new HashMap<>();


        emotions.put(Emotion.ANGER, new Emotion(Emotion.ANGER));
        emotions.put(Emotion.ANALYTICAL,new Emotion(Emotion.ANALYTICAL));
        emotions.put(Emotion.CONFIDENT,new Emotion(Emotion.CONFIDENT));
        emotions.put(Emotion.FEAR,new Emotion(Emotion.FEAR));
        emotions.put(Emotion.JOY,new Emotion(Emotion.JOY));
        emotions.put(Emotion.SADNESS,new Emotion(Emotion.SADNESS));
        emotions.put(Emotion.TENTATIVE,new Emotion(Emotion.TENTATIVE));
    }



    public void calculateResults(LinkedList<TweetToAnalyze> tweets){

        for(TweetToAnalyze tweet : tweets){

            List<ToneScore> textScores = tweet.getEmotionsInTweetText();
            com.microsoft.projectoxford.face.contract.Emotion mediaEmotions = tweet.getEmotionsInTweetMedia();

            emotions.get(Emotion.ANGER).addToScore(getCombinedScore());



        }
    }

    private float getCombinedScore(float textScore, float mediaScore){
        if(mediaScore == null){

        }
        return
    }


    private class Emotion {

        private static final String ANGER = "ANGER";
        private static final String ANALYTICAL = "ANALYTICAL";
        private static final String CONFIDENT = "CONFIDENT";
        private static final String FEAR = "FEAR";
        private static final String JOY = "JOY";
        private static final String SADNESS = "SADNESS";
        private static final String TENTATIVE = "TENTATIVE";

        private float score;
        private String name;

        public Emotion(String name) {
            this.name = name;
            this.score = 0;
        }

        public void addToScore(float score){
            this.score += score;
        }

        public String getName() {
            return name;
        }
    }

}
