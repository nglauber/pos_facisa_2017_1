package br.com.nglauber.aula06_movies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.parceler.Parcels;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.http.Util;
import br.com.nglauber.aula06_movies.model.Movie;

public class MoviesActivity extends AppCompatActivity
    implements OnMovieClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (getResources().getBoolean(R.bool.smartphone)) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        MoviesPagerAdapter pagerAdapter = new MoviesPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onMovieClick(Movie movie) {
        if (!Util.hasConnection(this)){
            Toast.makeText(this, R.string.msg_no_internet, Toast.LENGTH_SHORT).show();
            return;
        }

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

    class MoviesPagerAdapter extends FragmentPagerAdapter {
        public MoviesPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new MovieListFragment();
            }
            return new MovieFavoritesFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1) return getString(R.string.tab_search);
            return getString(R.string.tab_favorites);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
