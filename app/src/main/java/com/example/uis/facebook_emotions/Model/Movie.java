package com.example.uis.facebook_emotions.Model;

import com.google.gson.annotations.SerializedName;

import org.joda.time.Months;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Movie {

    private boolean adult;
    @SerializedName("genre_ids")
    private Integer[] genres;
    private Integer id;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String title;
    private String overview;
    @SerializedName("release_date")
    private Date releaseDate;
    @SerializedName("poster_path")
    private String posterPath;


    public String getTitle(){
        return title;
    }

    public String getReleaseDateToString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(releaseDate);
    }

    public String genresToString(){
        StringBuilder builder = new StringBuilder();
        for (Integer id : genres) {

            //Prego
            MovieGenre genre = MovieGenre.getMovieGenreById(id);
            if(genre != null  && genre.getName() != null) {
                builder.append(MovieGenre.getMovieGenreById(id).getName() + ", ");
            }

        }
        return builder.toString().substring(0, builder.toString().length() - 2);
    }

    public String inCinemaToString() {

        Calendar currentDate = Calendar.getInstance();

        Calendar releaseDate = Calendar.getInstance();
        releaseDate.setTime(this.releaseDate);

        int monthsToCurrentDate = ((currentDate.get(Calendar.YEAR) - 1) * 12) + currentDate.get(Calendar.MONTH);
        int monthsToReleaseDate = ((releaseDate.get(Calendar.YEAR) - 1) * 12) + releaseDate.get(Calendar.MONTH);

        int result = monthsToCurrentDate - monthsToReleaseDate;

        return result > 3 ? "Not in Cinemas" : "In Cinemas";
    }

    public String getPosterPath() {
        return posterPath;
    }
}
