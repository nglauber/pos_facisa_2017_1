package br.com.nglauber.aula06_movies.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.nglauber.aula06_movies.R;
import br.com.nglauber.aula06_movies.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        final VH vh = new VH(v);
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
        Glide.with(holder.imgPoster.getContext())
                .load(movie.poster)
                .into(holder.imgPoster);
        holder.txtTitle.setText(movie.title);
        holder.txtYear.setText(movie.year);
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public static class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.img_poster)
        public ImageView imgPoster;
        @BindView(R.id.txt_title)
        public TextView txtTitle;
        @BindView(R.id.txt_year)
        public TextView txtYear;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}