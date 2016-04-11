package application.buzzmovieselector.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import application.buzzmovieselector.Model.Movie;

/**
 * Created by harmeetbindra on 3/2/16.
 */
public class MovieDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "MOVIES";
    private static final int DB_VERSION = 1;
    private static final String MOVIE_ID = "Movie_Id";
    private static final String MOVIE_NAME = "Movie_Name";
    private static final String MOVIE_URL = "URL";
    private static final String DATE = "Date";
    private static final String MPAA_RATING = "Mpaa_Rating";
    private static final String RUNTIME = "Runtime";
    private static final String USER_RATED = "User_Rated";

    SQLiteDatabase db;
    MovieDatabase helper;
    private Context context;

    /**
     * Makes a Movie database object
     *
     * @param context is the context of the activity
     */
    public MovieDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);   // Users.db is the name of the database and 1 is the version
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_NAME + "(" + MOVIE_ID + " INTEGER PRIMARY KEY, " + MOVIE_NAME + " TEXT, "
                + MOVIE_URL + " TEXT, " + DATE + " TEXT, " + MPAA_RATING + " TEXT," + RUNTIME + " INTEGER);";
        try {
            db.execSQL(query);
        } catch (SQLException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + DB_NAME + " IF EXISTS");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + DB_NAME + " IF EXISTS");
        onCreate(db);
    }

    /**
     * insertMovie to the db
     *
     * @param movie is the movie to be inserted
     * @return -1 if movie is already in the db
     */
    public long insertMovie(Movie movie) {
        db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(MOVIE_ID, movie.getId());
        userValues.put(MOVIE_NAME, movie.getName());
        userValues.put(MOVIE_URL, movie.getImageUrl());
        userValues.put(DATE, movie.getReleaseDate().toString());
        userValues.put(MPAA_RATING, movie.getMpaaRating());
        userValues.put(RUNTIME, movie.getRunTime());
        long id = db.insert(DB_NAME, null, userValues);
        db.close();
        return id;
    }

    /**
     * find movie in the database
     *
     * @param id is the movie id to be found
     * @return the movie object
     */
    public Movie findMovie(int id) {
        db = this.getReadableDatabase();
        //String query = "SELECT "+MOVIE_ID+", "+MOVIE_NAME+", "+comments+", "+ratings+", "+
        //        MOVIE_URL+", "+DATE+", "+MPAA_RATING+", "+RUNTIME+" FROM "+DB_NAME;
        String query = "SELECT * FROM MOVIES";
        Cursor cursor = db.rawQuery(query, null);
        String name = null;
        String url = null;
        String mDate = null;
        String mpRating = null;
        int runT = 0;
        int rating = 0;
        int mId = 0;
        Movie movie = null;
        if (cursor.moveToFirst()) {
            do {
                mId = cursor.getInt(0);
                if (id == mId) {
                    name = cursor.getString(1);
                    url = cursor.getString(2);
                    mDate = cursor.getString(3);
                    mpRating = cursor.getString(4);
                    runT = cursor.getInt(5);
                    movie = new Movie(name, id, rating, url, mDate, runT, mpRating);
                }
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return movie;
    }

    /**
     * update movie in the database
     *
     * @param movie is the movie to be updated
     * @return true if update was sucessful
     */
    public boolean update(Movie movie) {
        int id = movie.getId();
        db = this.getWritableDatabase();
        db.delete(DB_NAME, " " + MOVIE_ID + " = '" + id + "'", null);
        long sucess = insertMovie(movie);
        if (sucess < 0) return false;
        else return true;
    }

    public ArrayList<Movie> getMovieByMajor(String major) {
        return new ArrayList<>();
    }


}
