package br.com.nglauber.aula06_movies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Entity
@Parcel
public class Movie {
    @SerializedName("Title")
    public String title;
    @SerializedName("Year")
    public String year;
    @PrimaryKey
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
