package com.example.uis.facebook_emotions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uis.facebook_emotions.Adapters.MovieAdapter;
import com.example.uis.facebook_emotions.Model.Movie;
import com.example.uis.facebook_emotions.Model.MovieGenre;
import com.example.uis.facebook_emotions.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;

public class SuggestionsActivity extends AppCompatActivity {
    private LinkedList<Movie> movies;

    private LinkedList<Movie> movieList;
    private MovieAdapter adapter;
    private LinearLayout toolbarContent;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarContent = findViewById(R.id.toolbarContent);

        initCollapsingToolbar();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_movies);
        movieList = new LinkedList<Movie>();
        adapter = new MovieAdapter(this, movieList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        makeMoviesByGenreRequest();

        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeMoviesByGenreRequest();
                }
            }
        }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.activity_suggestions_title));
                    toolbarContent.setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    toolbarContent.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }

    private void makeMoviesByGenreRequest() {

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
                Log.i("Errors:", error.toString());
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
                    Movie[] movies = gson.fromJson(results.toString(), Movie[].class);
                    movieList.addAll(Arrays.asList(movies));
                    adapter.notifyDataSetChanged();
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

    public void onClickButtonSeeSuggestions(View view) {
        startActivity(new Intent(this, PlacesActivity.class));
    }
}
