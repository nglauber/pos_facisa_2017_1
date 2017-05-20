package br.com.nglauber.aula06_movies.ui;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.parceler.Parcels;

import java.io.IOException;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.databinding.ActivityMovieDetailBinding;
import br.com.nglauber.aula06_movies.http.MoviesParser;
import br.com.nglauber.aula06_movies.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";

    ActivityMovieDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_movie_detail);

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MOVIE));
        binding.setMovie(movie);

        new MovieByIdTask().execute(movie.imdbId);
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
