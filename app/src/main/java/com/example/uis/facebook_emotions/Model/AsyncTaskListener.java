package com.example.uis.facebook_emotions.Model;

import android.content.Context;

public interface AsyncTaskListener {

    void onSuccess();
    void onError(Exception e);
    Context getContext();
}
