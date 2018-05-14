package com.example.uis.facebook_emotions.Model;

import com.google.maps.model.PlacesSearchResult;

public interface GooglePlacesListener {

    void onResponse(PlacesSearchResult[] results);
    void onErrors(Exception e);
}
