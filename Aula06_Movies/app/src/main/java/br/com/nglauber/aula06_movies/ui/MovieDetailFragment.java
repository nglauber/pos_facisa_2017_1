package br.com.nglauber.aula06_movies.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.parceler.Parcels;

import java.io.IOException;

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
    ShareActionProvider shareActionProvider;

    public MovieDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
        dao = AppDatabase.getInMemoryDatabase(getContext()).movieDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Movie movie = Parcels.unwrap(getArguments().getParcelable(EXTRA_MOVIE));

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        binding.setMovie(movie);
        updateFabIcon(dao.isFavorite(movie.imdbId));
        binding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavorite();
                }
            });

        new MovieByIdTask().execute(movie.imdbId);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getResources().getBoolean(R.bool.smartphone)) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie_detail, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
    }

    public static MovieDetailFragment newInstance(Movie m) {
        Bundle params = new Bundle();
        params.putParcelable(EXTRA_MOVIE, Parcels.wrap(m));

        MovieDetailFragment f = new MovieDetailFragment();
        f.setArguments(params);

        return f;
    }

    public void toggleFavorite() {
        final Movie movie = binding.getMovie();
        boolean isFavorite = dao.isFavorite(movie.imdbId);
        if (isFavorite) {
            dao.deleteMovies(movie);
            Toast.makeText(getActivity(), R.string.msg_favorite_removed, Toast.LENGTH_SHORT).show();
        } else {
            dao.insertMovie(movie);
            Toast.makeText(getActivity(), R.string.msg_favorite_added, Toast.LENGTH_SHORT).show();
        }

        // Animate
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                binding.fab, View.SCALE_X, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                binding.fab, View.SCALE_Y, 0f);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                updateFabIcon(dao.isFavorite(movie.imdbId));
            }
        });
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setRepeatCount(1);
        scaleY.setRepeatCount(1);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.start();
    }

    private void updateFabIcon(boolean isFavorite){
        binding.fab.setImageResource(isFavorite ? R.drawable.ic_clear : R.drawable.ic_check);
    }

    private void setShareIntent(Movie m) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Eu gostei do filme "+ m.title +" veja no IMDB http://www.imdb.com/title/"+ m.imdbId);
        sendIntent.setType("text/plain");
        shareActionProvider.setShareIntent(sendIntent);
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
                setShareIntent(m);
            }
        }
    }

}
