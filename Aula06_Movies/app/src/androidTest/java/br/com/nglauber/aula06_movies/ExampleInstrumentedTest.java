package br.com.nglauber.aula06_movies;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import br.com.nglauber.aula06_movies.dao.AppDatabase;
import br.com.nglauber.aula06_movies.dao.MovieDao;
import br.com.nglauber.aula06_movies.model.Movie;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();

        MovieDao dao = AppDatabase.getInMemoryDatabase(appContext).movieDao();
        Movie movie = new Movie();
        movie.imdbId  = "BBBBB";
        movie.title  = "Teste 1";
        movie.year = "1900";
        movie.poster = "";
        movie.released = "Jun";
        movie.awards = "Oscar";
        movie.genre = "Comedy";
        movie.director = "Eu";
        movie.actors = "Nilson";
        movie.plot = "Historia bosta";
        movie.imdbRating = "aaaaa";

        dao.insertMovie(movie);

        List<Movie> movies = dao.listAllFavorites();
        for (Movie m : movies) {
            Log.d("NGVL", m.imdbId + m.title);
        }

        dao.deleteMovies(movie);

        movies = dao.listAllFavorites();
        Log.d("NGVL", "SIZE: "+ movies.size());

        assertEquals("br.com.nglauber.aula06_movies", appContext.getPackageName());
    }
}
