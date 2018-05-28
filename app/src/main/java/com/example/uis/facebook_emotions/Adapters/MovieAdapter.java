package com.example.uis.facebook_emotions.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uis.facebook_emotions.Model.Movie;
import com.example.uis.facebook_emotions.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>{
    private Context mContext;
    private List<Movie> movieList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, releaseDate, inCinema, genres;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textViewMovieTitle);
            releaseDate = view.findViewById(R.id.textViewReleaseDate);
            inCinema = view.findViewById(R.id.textViewInCinemas);
            genres = view.findViewById(R.id.textViewMovieGenres);
        }
    }


    public MovieAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_movie_poster, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.releaseDate.setText(movie.getReleaseDateToString());
        holder.inCinema.setText(movie.inCinemaToString());
        holder.genres.setText(movie.genresToString());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
