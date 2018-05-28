package com.example.uis.facebook_emotions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uis.facebook_emotions.Model.EmotionAnalysisResult;
import com.example.uis.facebook_emotions.Model.Movie;
import com.example.uis.facebook_emotions.Model.MovieGenre;
import com.example.uis.facebook_emotions.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class SuggestionsActivity extends AppCompatActivity {
    private LinkedList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        movies = new LinkedList<>();
        requestMoviesByGenres();
    }

    private void requestMoviesByGenres() {
        //Get the genres we need to suggest from somewhere
        LinkedList<MovieGenre> genresToFetch = User.INSTANCE.getRecommendedMoviesByGenre();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        for(MovieGenre movieGenre : genresToFetch) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, generateURL(movieGenre), null,
                    onRequestMoviesInGenreSuccess(), onRequestMoviesInGenreError());

            requestQueue.add(request);
        }
    }

    private Response.ErrorListener onRequestMoviesInGenreError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        };
    }

    private Response.Listener<JSONObject> onRequestMoviesInGenreSuccess() {

        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray results = null;

                    results = (JSONArray) response.get("results");

                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                    Movie[] moviesArray = gson.fromJson(results.toString(), Movie[].class);
                    movies.addAll(Arrays.asList(moviesArray));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private String generateURL(MovieGenre genre){
        return "https://api.themoviedb.org/3/genre/" + genre.getId() + "/movies?api_key="
                + getString(R.string.themoviedb_api_key)
                + "&language=en-US&include_adult=false&sort_by=created_at.asc";
    }

}
