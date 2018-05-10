package com.example.uis.facebook_emotions.Model;

import com.example.uis.facebook_emotions.Model.TweetToAnalyze;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.LinkedList;

public enum User {

    INSTANCE;

    private LinkedList<TweetToAnalyze> tweets;
    private TwitterSession twitterSession;
    private String username;


    User(){
        tweets = new LinkedList<>();
        twitterSession = null;
        username = "";

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
}
