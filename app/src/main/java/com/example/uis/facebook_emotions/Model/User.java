package com.example.uis.facebook_emotions.Model;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public enum User {

    INSTANCE;

    private LinkedList<TweetToAnalyze> tweets;
    private TwitterSession twitterSession;
    private String username;
    private EmotionAnalysisResult emotionResult;

    User(){
        tweets = new LinkedList<>();
        twitterSession = null;
        username = "";
        emotionResult = new EmotionAnalysisResult();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public LinkedList<TweetToAnalyze> getTweets() {
        return tweets;
    }

    public TwitterSession getTwitterSession() {
        return twitterSession;
    }

    public void setTwitterSession(TwitterSession twitterSession) {
        this.twitterSession = twitterSession;
    }

    public void addTweet(TweetToAnalyze tweetToAnalyze){
        if(tweetToAnalyze==null || tweets.contains(tweetToAnalyze)){
            return;
        }else{
            tweets.add(tweetToAnalyze);
        }
    }

    public void aggregateEmotions(){


        //HashMap<ToneScore, Double> scoreDoubleHashMap = new HashMap<>();
        ArrayList<ToneScore> results = new ArrayList<>(5);

        for (ToneScore toneScore:tweets.get(0).getEmotionsInTweetText()) {
            ToneScore tone = new ToneScore();
            tone.setName(toneScore.getName());
            tone.setScore(0.0);
            tone.setId(toneScore.getId());
            results.add(tone);
        }

        for (TweetToAnalyze tweetToAnalyze: tweets) {
            for (int i = 0; i<tweetToAnalyze.getEmotionsInTweetText().size(); i++){
                results.get(i).setScore(results.get(i).getScore()+tweetToAnalyze.getEmotionsInTweetText().get(i).getScore());

            }

        }

        for (int j=0; j<results.size(); j++){
            results.get(j).setScore(results.get(j).getScore()/tweets.size());
        }

        Collections.sort(results, new Comparator<ToneScore>()
        {
            @Override
            public int compare(ToneScore o1, ToneScore o2) {
                return o2.getScore().compareTo(o1.getScore());
            }
        });

        results.remove(7);
        results.remove(6);
        results.remove(5);
        results.remove(4);
        results.remove(3);


    }


    public void calculateEmotionAnalysisResult() {
        this.emotionResult.calculateResults(this.tweets);
    }

    public ArrayList<EmotionAnalysisResult.Emotion> getHighestEmotions() {
        return emotionResult.getHighestEmotions();
    }
}
