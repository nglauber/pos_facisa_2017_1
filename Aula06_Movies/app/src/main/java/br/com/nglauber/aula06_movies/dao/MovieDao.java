package br.com.nglauber.aula06_movies.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.nglauber.aula06_movies.model.Movie;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MovieDao {

    @Insert(onConflict = IGNORE)
    void insertMovie(Movie movie);

    @Query("SELECT * FROM Movie ORDER BY title")
    List<Movie> listAllFavorites();

    @Query("SELECT COUNT(*) FROM Movie WHERE imdbId = :imdbId")
    boolean isFavorite(String imdbId);

    @Delete
    void deleteMovies(Movie... movies);
}
