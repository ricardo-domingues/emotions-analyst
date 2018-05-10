package com.example.uis.facebook_emotions.Model;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.twitter.sdk.android.core.models.MediaEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TweetToAnalyze {

    private String text;
    private List<ToneScore> emotionsInTweet;
    private LinkedList<MediaEntity> mediaEntities;



    public TweetToAnalyze(String text) {
        this.text = text;
        emotionsInTweet = new LinkedList<>();
        mediaEntities = new LinkedList<>();
    }

    public String getText() {
        return text;
    }

    public void setEmotionsInTweet(List<ToneScore> emotionsInTweet) {
        this.emotionsInTweet = emotionsInTweet;
    }

    public List<ToneScore> getEmotionsInTweet() {
        return emotionsInTweet;
    }
    
    public LinkedList<ToneScore> getResults(){
        Collections.sort(emotionsInTweet, new Comparator<ToneScore>() {
            @Override
            public int compare(ToneScore o1, ToneScore o2) {
                return o1.getScore().compareTo(o2.getScore());
            }
        });
        LinkedList<ToneScore> bests = new LinkedList<>();
        for (int i=0; i<3; i++){
            bests.add(emotionsInTweet.get(i));
        }
        return bests;
    }
    
    
    

    public LinkedList<MediaEntity> getMediaEntities() {
        return mediaEntities;
    }


}
