package br.com.nglauber.aula06_movies.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Movie {
    @SerializedName("Title")
    public String title;
    @SerializedName("Year")
    public String year;
    @SerializedName("imdbID")
    public String imdbId;
    @SerializedName("Poster")
    public String poster;

    @SerializedName("Released")
    public String released;
    @SerializedName("Awards")
    public String awards;
    @SerializedName("Genre")
    public String genre;
    @SerializedName("Director")
    public String director;
    @SerializedName("Actors")
    public String actors;
    @SerializedName("Plot")
    public String plot;
    @SerializedName("imdbRating")
    public String imdbRating;
}
