package application.buzzmovieselector.Model;

import android.content.Context;

import application.buzzmovieselector.data.MovieDatabase;

/**
 * This class represents a Movie manager object
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class MovieManager {

    MovieDatabase db;
    /**
     * Makes a Movie manager object
     * @param context is the context of the activity
     */
    public MovieManager(Context context) {
        db = new MovieDatabase(context);
    }
    /**
     * insert movie in database
     * @param movie is the movie to be inserted
     */
    public void insertMovie(Movie movie) {
        long id = db.insertMovie(movie);
    }
    /**
     * finds movie int the db
     * @param id is the movie id
     */
    public Movie findMovieById(int id) {
        return db.findMovie(id);
    }

    /**
     * updates movie in database
     * @param movie is the movie to be updated
     */
    public boolean updateDb(Movie movie) {
        return db.update(movie);
    }

}
