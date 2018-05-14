package com.example.uis.facebook_emotions.Services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.example.uis.facebook_emotions.Model.GooglePlacesListener;
import com.example.uis.facebook_emotions.R;
import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

public abstract class GooglePlacesService {


    private static final int DEFAULT_RADIUS = 2000;

    private static GeoApiContext getGeoApiContext(Context context) {
        return new GeoApiContext.Builder().apiKey(context.getString(R.string.google_places_api_key)).build();
    }

    @SuppressLint("MissingPermission")
    private static LatLng getUserLocation(Context context) {
        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location locationBasedOnGPS = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Location finalLocation = null;

        if(locationBasedOnGPS != null){
            finalLocation = locationBasedOnGPS;
        }
        else{
            finalLocation =  locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        return finalLocation != null ? new LatLng(finalLocation.getLatitude(), finalLocation.getLongitude()) : null;
    }

    public static void queryNearbyPlaces(Context context, GooglePlacesListener caller, PlaceType placeType){
        if((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            caller.onErrors(new Exception("Missing Permissions."));
        }
        else{

            GeoApiContext geoApiContext = getGeoApiContext(context);
            LatLng currentLocation = getUserLocation(context);

            if(currentLocation == null){
                caller.onErrors(new Exception("Could not get user's location"));
            }
            else{
                NearbySearchRequest request = PlacesApi.nearbySearchQuery(geoApiContext, currentLocation);
                request.radius(DEFAULT_RADIUS);
                request.type(placeType);

                try {
                    PlacesSearchResponse response = request.await();
                    PlacesSearchResult[] results = response.results;
                    caller.onResponse(results);

                } catch (Exception e) {
                    caller.onErrors(e);
                }
            }
        }
    }
}
