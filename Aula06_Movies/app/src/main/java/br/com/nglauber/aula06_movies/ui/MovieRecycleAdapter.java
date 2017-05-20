package br.com.nglauber.aula06_movies.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.databinding.ItemMovieBinding;
import br.com.nglauber.aula06_movies.model.Movie;

public class MovieRecycleAdapter extends
        RecyclerView.Adapter<MovieRecycleAdapter.VH> {

    List<Movie> movies;
    OnMovieClickListener listener;

    public MovieRecycleAdapter(List<Movie> movies,
                                OnMovieClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                        R.layout.item_movie, parent, false);

        final VH vh = new VH(binding);
        vh.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int pos = vh.getAdapterPosition();
                            Movie movie = movies.get(pos);
                            listener.onMovieClick(movie);
                        }
                    }
                });
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int pos) {
        Movie movie = movies.get(pos);
        holder.binding.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {

        ItemMovieBinding binding;

        public VH(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}