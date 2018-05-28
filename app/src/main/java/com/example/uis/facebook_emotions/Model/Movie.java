package com.example.uis.facebook_emotions.Model;

import com.google.gson.annotations.SerializedName;

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
}
