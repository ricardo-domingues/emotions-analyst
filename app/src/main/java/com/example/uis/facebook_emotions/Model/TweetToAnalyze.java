package com.example.uis.facebook_emotions.Model;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.microsoft.projectoxford.face.contract.FaceAttribute;

import java.util.LinkedList;
import java.util.List;

public class TweetToAnalyze {

    private String text;
    private List<ToneScore> emotionsInTweet;
    private String mediaEntity;
    
    public TweetToAnalyze(String text, String mediaEntity) {
        this.text = text;
        this.emotionsInTweet = new LinkedList<>();
        this.mediaEntity = mediaEntity;
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
    

    public String getMediaEntities() {
        return mediaEntity;
    }


}
