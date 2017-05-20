package br.com.nglauber.aula06_movies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.model.Movie;

public class MoviesActivity extends AppCompatActivity
    implements OnMovieClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    @Override
    public void onMovieClick(Movie movie) {
        if (getResources().getBoolean(R.bool.smartphone)) {
            Intent it = new Intent(this, MovieDetailActivity.class);
            it.putExtra(MovieDetailActivity.EXTRA_MOVIE, Parcels.wrap(movie));
            startActivity(it);
        } else {
            MovieDetailFragment mdf = MovieDetailFragment.newInstance(movie);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_detail, mdf, "detailFragment")
                    .commit();
        }
    }
}
