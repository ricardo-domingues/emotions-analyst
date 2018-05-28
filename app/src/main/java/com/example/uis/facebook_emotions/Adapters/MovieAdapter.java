package com.example.uis.facebook_emotions.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.uis.facebook_emotions.Model.Movie;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie>{
    public MovieAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }
}
