package com.example.uis.facebook_emotions.Model;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;
import com.microsoft.projectoxford.face.contract.Emotion;

import java.util.LinkedList;
import java.util.List;

public class TweetToAnalyze {

    private List<ToneScore> emotionsInTweetText;
    private Emotion emotionsInTweetMedia;
    private String text;
    private String mediaEntity;
    
    public TweetToAnalyze(String text, String mediaEntity) {
        this.text = text;
        this.mediaEntity = mediaEntity;
        this.emotionsInTweetText = new LinkedList<>();
    }

    public String getText() {
        return text;
    }

    public void setEmotionsInTweetText(List<ToneScore> emotions) {
        this.emotionsInTweetText = emotions;
    }

    public void setEmotionsInTweetMedia(Emotion emotions){
        this.emotionsInTweetMedia = emotions;
    }

    public List<ToneScore> getEmotionsInTweetText() {
        return emotionsInTweetText;
    }

    public Emotion getEmotionsInTweetMedia(){
        return emotionsInTweetMedia;
    }

    public String getMediaEntity() {
        return mediaEntity;
    }
}
