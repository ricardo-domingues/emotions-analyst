package com.example.uis.facebook_emotions.Model;
import android.util.Log;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.microsoft.projectoxford.face.contract.Emotion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class EmotionAnalysisResult {

    /* Microsoft Face API :
       Anger
       Disgust
       Fear
       Happiness (Converted to Joy)
       Sadness
       Contempt - Ignored
       Neutral - Ignored
       Suprise - Ignored
     */

    /* IBM Watson Tone Analyzer :
       Anger
       Fear
       Joy
       Sadness
       Disgust
       Analytical
       Confident
       Tentative
     */


    private HashMap<String, Emotion> emotions;

    EmotionAnalysisResult() {

        emotions = new HashMap<>();
        emotions.put(Emotion.ANGER, new Emotion(Emotion.ANGER));
        emotions.put(Emotion.ANALYTICAL, new Emotion(Emotion.ANALYTICAL));
        emotions.put(Emotion.CONFIDENT, new Emotion(Emotion.CONFIDENT));
        emotions.put(Emotion.DISGUST, new Emotion(Emotion.DISGUST));
        emotions.put(Emotion.FEAR, new Emotion(Emotion.FEAR));
        emotions.put(Emotion.JOY, new Emotion(Emotion.JOY));
        emotions.put(Emotion.SADNESS, new Emotion(Emotion.SADNESS));
        emotions.put(Emotion.TENTATIVE, new Emotion(Emotion.TENTATIVE));
    }



    public void calculateResults(LinkedList<TweetToAnalyze> tweets){

        for(TweetToAnalyze tweet : tweets){

            List<ToneScore> textScores = tweet.getEmotionsInTweetText();
            com.microsoft.projectoxford.face.contract.Emotion mediaEmotions = tweet.getEmotionsInTweetMedia();

            emotions.get(Emotion.ANGER).addToScore(mediaEmotions == null ? textScores.get(0).getScore() :
                    getCombinedScore(textScores.get(0).getScore(), mediaEmotions.anger));
            emotions.get(Emotion.DISGUST).addToScore(mediaEmotions == null ?  textScores.get(1).getScore() :
                    getCombinedScore(textScores.get(1).getScore(), mediaEmotions.disgust));
            emotions.get(Emotion.FEAR).addToScore(mediaEmotions == null ? textScores.get(2).getScore() :
                    getCombinedScore(textScores.get(2).getScore(), mediaEmotions.fear));
            emotions.get(Emotion.JOY).addToScore(mediaEmotions == null ? textScores.get(3).getScore() :
                    getCombinedScore(textScores.get(3).getScore(), mediaEmotions.happiness));
            emotions.get(Emotion.SADNESS).addToScore(mediaEmotions == null ? textScores.get(4).getScore() :
                    getCombinedScore(textScores.get(4).getScore(), mediaEmotions.sadness));
            emotions.get(Emotion.ANALYTICAL).addToScore(textScores.get(5).getScore());
            emotions.get(Emotion.CONFIDENT).addToScore(textScores.get(6).getScore());
            emotions.get(Emotion.TENTATIVE).addToScore(textScores.get(7).getScore());
        }

        for(HashMap.Entry<String, Emotion> entry : emotions.entrySet()){
            entry.getValue().calculateAverage(tweets.size());
        }

    }

    private double getCombinedScore(double textScore, double mediaScore){
        return (mediaScore + textScore) / 2;
    }

    public ArrayList<Emotion> getHighestEmotions() {

        ArrayList<Map.Entry<String, Emotion>> auxList = new ArrayList<>(this.emotions.entrySet());
        Collections.sort(auxList, new Comparator<Map.Entry<String, Emotion>>()
        {
            @Override
            public int compare(Map.Entry<String, Emotion> o1,Map.Entry<String, Emotion> o2) {
                return o2.getValue().getScore().compareTo(o1.getValue().getScore());
            }
        });

        //Simplify the results
        ArrayList<Emotion> results = new ArrayList<>(3);
        results.add(auxList.remove(0).getValue());
        results.add(auxList.remove(0).getValue());
        results.add(auxList.remove(0).getValue());
        return results;
    }


    public class Emotion {

        private static final String ANGER = "Anger";
        private static final String ANALYTICAL = "Analytical";
        private static final String CONFIDENT = "Confident";
        private static final String DISGUST = "Disgust";
        private static final String FEAR = "Fear";
        private static final String JOY = "Joy";
        private static final String SADNESS = "Sadness";
        private static final String TENTATIVE = "Tentative";


        private double score;
        private String name;

        Emotion(String name) {
            this.name = name;
            this.score = 0;
        }

        void addToScore(double score){
            this.score += score;
        }

        void calculateAverage(int count){
            this.score = this.score / count;
        }

        public Double getScore(){
            return this.score;
        }

        public String getName() {
            return name;
        }
    }

}
