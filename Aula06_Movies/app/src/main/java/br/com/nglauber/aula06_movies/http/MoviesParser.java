package br.com.nglauber.aula06_movies.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.nglauber.aula06_movies.model.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MoviesParser {

    public static List<Movie> searchMovies(String q) throws IOException {

        String url = String.format("http://www.omdbapi.com/?s=%s", q);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        String json = response.body().string();

        Gson gson = new Gson();
        MovieSearchResult result = gson.fromJson(json, MovieSearchResult.class);
        return result.movies;
    }
}
