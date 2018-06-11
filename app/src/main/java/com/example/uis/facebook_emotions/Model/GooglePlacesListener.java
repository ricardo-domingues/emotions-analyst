package com.example.uis.facebook_emotions.Model;

import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResult;

public interface GooglePlacesListener {

    void onResponse(PlacesSearchResult[] results, LatLng userLocation);
    void onErrors(Exception e);
}
