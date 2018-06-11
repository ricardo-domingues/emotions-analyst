package com.example.uis.facebook_emotions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.uis.facebook_emotions.Model.GooglePlacesListener;
import com.example.uis.facebook_emotions.Services.GooglePlacesService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResult;

import java.util.LinkedList;

public class PlacesActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private GoogleMap map;
    private PlacesSearchResult[] m_results;
    private com.google.maps.model.LatLng m_userLocation;
    private static final int ZOOM = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places2);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


        GooglePlacesService.queryNearbyPlaces(this, new GooglePlacesListener() {
            @Override
            public void onResponse(PlacesSearchResult[] results, com.google.maps.model.LatLng userLocation) {
                m_results = results;
                m_userLocation = userLocation;
            }

            @Override
            public void onErrors(Exception e) {
                Log.d("Error", e.getMessage());
            }
        }, PlaceType.MOVIE_THEATER);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LinkedList<LatLng> coordinates = new LinkedList<>();
        if( m_results != null && m_results.length > 0){
            for (PlacesSearchResult placesSearchResult : m_results) {
                double lat = placesSearchResult.geometry.location.lat;
                double lng = placesSearchResult.geometry.location.lng;
                coordinates.add(new LatLng(lat, lng));
                map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(placesSearchResult.name));
            }

            coordinates.add(new LatLng(this.m_userLocation.lat, this.m_userLocation.lng));
            LatLng center = trianguleCoordinates(coordinates);

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, ZOOM));
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
                    i.putExtra("MARKER_TITLE", marker.getTitle());
                    startActivity(i);
                }
            });
        }



    }

    private LatLng trianguleCoordinates(LinkedList<LatLng> coordinates) {
        double medianX = 0;
        double medianY = 0;

        for(LatLng latLng : coordinates){
            medianX += latLng.latitude;
            medianY += latLng.longitude;
        }

        return new LatLng((medianX / coordinates.size()), (medianY / coordinates.size()));
    }
}
