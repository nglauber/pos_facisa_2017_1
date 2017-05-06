package br.com.nglauber.aula06_movies.http;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.nglauber.aula06_movies.model.Movie;

class MovieSearchResult {
    @SerializedName("Search")
    List<Movie> movies;
}
