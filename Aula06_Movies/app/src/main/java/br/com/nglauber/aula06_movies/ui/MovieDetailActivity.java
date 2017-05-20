package br.com.nglauber.aula06_movies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.databinding.ActivityMovieDetailBinding;
import br.com.nglauber.aula06_movies.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    public static final String DETAIL_FRAGMENT = "detailFragment";

    ActivityMovieDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_movie_detail);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MOVIE));

        if (savedInstanceState == null) {
            MovieDetailFragment mdf = MovieDetailFragment.newInstance(movie);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_detail, mdf, DETAIL_FRAGMENT)
                    .commit();
        }
    }
}
