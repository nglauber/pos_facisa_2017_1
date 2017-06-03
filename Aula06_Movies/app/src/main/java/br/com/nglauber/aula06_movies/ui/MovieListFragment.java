package br.com.nglauber.aula06_movies.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.databinding.FragmentMovieListBinding;
import br.com.nglauber.aula06_movies.http.MoviesParser;
import br.com.nglauber.aula06_movies.http.Util;
import br.com.nglauber.aula06_movies.model.Movie;

public class MovieListFragment extends Fragment
        implements SearchView.OnQueryTextListener {

    List<Movie> movies;
    FragmentMovieListBinding binding;
    ConnectionReceiver receiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false);

        if (movies != null) {
            updateList();
        }

        showNoConnectionMessage(!Util.hasConnection(getActivity()));
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        receiver = new ConnectionReceiver();
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (Util.hasConnection(getActivity())) {
            new MovieSearchTask().execute(query);
        } else {
            Toast.makeText(getActivity(), R.string.msg_no_internet, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    class MovieSearchTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            try {
                return MoviesParser.searchMovies(params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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

    class ConnectionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isDisconnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            if (movies == null || movies.isEmpty()) {
                showNoConnectionMessage(isDisconnected);
            }
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

    private void showNoConnectionMessage(boolean show) {
        if (show) {
            binding.txtEmpty.setText(R.string.msg_no_internet);
            binding.txtEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.txtEmpty.setText(null);
            binding.txtEmpty.setVisibility(View.GONE);
        }
    }
}
