package com.example.uis.facebook_emotions;

import android.content.Context;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

import java.util.List;

public interface IBMCloudToneAnalyzerListener {

    void onToneAnalyzerSuccess(List<ToneScore> scores);
    void onToneAnalyzerError(Exception e);
    Context getContext();
}
