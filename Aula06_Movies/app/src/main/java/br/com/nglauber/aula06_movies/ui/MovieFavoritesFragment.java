package br.com.nglauber.aula06_movies.ui;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.dao.AppDatabase;
import br.com.nglauber.aula06_movies.dao.MovieDao;
import br.com.nglauber.aula06_movies.databinding.FragmentMovieListBinding;
import br.com.nglauber.aula06_movies.model.Movie;

public class MovieFavoritesFragment extends Fragment {

    List<Movie> movies;
    FragmentMovieListBinding binding;
    MovieDao dao;

    public MovieFavoritesFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dao = AppDatabase.getInMemoryDatabase(getContext()).movieDao();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false);

        if (movies != null) {
            updateList();
        }
        new MovieFavoritesTask().execute();

        return binding.getRoot();
    }

    class MovieFavoritesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            return dao.listAllFavorites();
        }

        @Override
        protected void onPostExecute(List<Movie> m) {
            super.onPostExecute(m);
            movies = m;
            boolean isEmpty = movies == null || movies.size() <= 0;
            binding.txtEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            updateList();
        }
    }

    private void updateList() {
        OnMovieClickListener listener = null;
        if (getActivity() instanceof OnMovieClickListener) {
            listener = (OnMovieClickListener) getActivity();
        }

        MovieRecycleAdapter adapter = new MovieRecycleAdapter(movies, listener);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
    }
}
