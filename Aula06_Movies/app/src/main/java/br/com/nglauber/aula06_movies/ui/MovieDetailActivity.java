package br.com.nglauber.aula06_movies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDetailFragment mdf = (MovieDetailFragment)
                        getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT);
                if (mdf != null) {
                    mdf.toggleFavorite();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MOVIE));
        binding.setMovie(movie);

        if (savedInstanceState == null) {
            MovieDetailFragment mdf = MovieDetailFragment.newInstance(movie);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_detail, mdf, DETAIL_FRAGMENT)
                    .commit();
        }
    }
}
