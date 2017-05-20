package br.com.nglauber.aula06_movies.ui;


import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.io.IOException;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.databinding.FragmentMovieDetailBinding;
import br.com.nglauber.aula06_movies.http.MoviesParser;
import br.com.nglauber.aula06_movies.model.Movie;

import static br.com.nglauber.aula06_movies.ui.MovieDetailActivity.EXTRA_MOVIE;

public class MovieDetailFragment extends Fragment {

    FragmentMovieDetailBinding binding;

    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Movie movie = Parcels.unwrap(getArguments().getParcelable(EXTRA_MOVIE));

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        binding.setMovie(movie);

        new MovieByIdTask().execute(movie.imdbId);

        return binding.getRoot();
    }

    public static MovieDetailFragment newInstance(Movie m) {
        Bundle params = new Bundle();
        params.putParcelable(EXTRA_MOVIE, Parcels.wrap(m));

        MovieDetailFragment f = new MovieDetailFragment();
        f.setArguments(params);

        return f;
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
