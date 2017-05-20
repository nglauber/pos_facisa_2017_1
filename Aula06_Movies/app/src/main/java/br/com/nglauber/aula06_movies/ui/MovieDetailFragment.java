package br.com.nglauber.aula06_movies.ui;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.dao.AppDatabase;
import br.com.nglauber.aula06_movies.dao.MovieDao;
import br.com.nglauber.aula06_movies.databinding.FragmentMovieDetailBinding;
import br.com.nglauber.aula06_movies.http.MoviesParser;
import br.com.nglauber.aula06_movies.model.Movie;

import static br.com.nglauber.aula06_movies.ui.MovieDetailActivity.EXTRA_MOVIE;

public class MovieDetailFragment extends Fragment {

    FragmentMovieDetailBinding binding;
    MovieDao dao;

    public MovieDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dao = AppDatabase.getInMemoryDatabase(getContext()).movieDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Movie movie = Parcels.unwrap(getArguments().getParcelable(EXTRA_MOVIE));

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        binding.setMovie(movie);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });

        new MovieByIdTask().execute(movie.imdbId);

        printFavorites();

        return binding.getRoot();
    }

    public static MovieDetailFragment newInstance(Movie m) {
        Bundle params = new Bundle();
        params.putParcelable(EXTRA_MOVIE, Parcels.wrap(m));

        MovieDetailFragment f = new MovieDetailFragment();
        f.setArguments(params);

        return f;
    }

    public void toggleFavorite() {
        Movie movie = binding.getMovie();
        boolean isFavorite = dao.isFavorite(movie.imdbId);
        if (isFavorite) {
            dao.deleteMovies(movie);
        } else {
            dao.insertMovie(movie);
        }
        printFavorites();
    }

    void printFavorites(){
        List<Movie> movies = dao.listAllFavorites();
        for (Movie m : movies) {
            Log.d("NGVL", m.imdbId + m.title);
        }
    }


    class MovieByIdTask extends AsyncTask<String, Void, Movie> {

        @Override
        protected Movie doInBackground(String... params) {
            try {
                return MoviesParser.getMovieDetail(params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie m) {
            super.onPostExecute(m);
            if (m != null) {
                binding.setMovie(m);
            }
        }
    }
}
