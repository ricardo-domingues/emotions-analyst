package com.example.uis.facebook_emotions.Model;

import android.content.Context;

public interface EmotionAnalysisListener {

    void onSuccess();
    void onError(Exception e);
    Context getContext();
}
