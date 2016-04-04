package application.buzzmovieselector.Model;

import android.content.Context;

import java.util.ArrayList;

import application.buzzmovieselector.data.MovieDatabase;
import application.buzzmovieselector.data.MovieUserDB;

/**
 * This class represents a Movie manager object
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class MovieManager {

    MovieDatabase db;
    MovieUserDB movieUserDB;

    /**
     * Makes a Movie manager object
     *
     * @param context is the context of the activity
     */
    public MovieManager(Context context) {
        db = new MovieDatabase(context);
        movieUserDB = new MovieUserDB(context);
    }

    /**
     * insert movie in database
     *
     * @param movie is the movie to be inserted
     */
    public void insertMovie(Movie movie) {
        long id = db.insertMovie(movie);
    }

    /**
     * finds movie int the db
     *
     * @param id is the movie id
     */
    public Movie findMovieById(int id) {
        return db.findMovie(id);
    }

    /**
     * updates movie in database
     *
     * @param movie is the movie to be updated
     */
    public boolean updateDb(Movie movie) {
        return db.update(movie);
    }

    /**
     * sorts movies in descending order by rating
     *
     * @param movieList is the movie to be updated\
     * @return the sorted array of the movie list in the highest rank
     */
    public ArrayList<Movie> highestRatedMovie(ArrayList<Movie> movieList) {
        for (int i = movieList.size() - 1; i > 0; i--) {
            Movie selectedMovie = movieList.get(i);
            int j = i;
            while (j > 0 && this.getRating(movieList.get(j - 1)) < this.getRating(selectedMovie)) {
                movieList.set(j, movieList.get(j - 1));
                j = j - 1;
            }
            movieList.set(j, selectedMovie);
        }
        return movieList;
    }

    /**
     * see if user can comment on the movie and has not already commneted on the movie
     *
     * @param user  user who wants to make comment on movie
     * @param movie movie that is being commented on
     * @return see if user can comment on the movie
     */
    public boolean insertRatingComment(User user, Movie movie) {
        long id = movieUserDB.insertRatingComment(movie, user);
        if (id < 0) return false;
        else return true;
    }

    /**
     * retrieves rating of a movie
     *
     * @param movie movie that database is recieving the ratings from
     * @return the rating of a movie
     */
    public float getRating(Movie movie) {
        return movieUserDB.getRating(movie);
    }
}
