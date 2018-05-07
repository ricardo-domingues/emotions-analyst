package com.example.uis.facebook_emotions.Model;

public class Post {

    private String body;
    private String multimediaContentUrl;

    public Post(String body, String multimediaContentUrl) {
        this.body = body;
        this.multimediaContentUrl = multimediaContentUrl;
    }

    public String getBody() {
        return body;
    }

    public String getMultimediaContentUrl() {
        return multimediaContentUrl;
    }

    public boolean hasMultimediaContent(){
        return this.multimediaContentUrl != null;
    }
}
