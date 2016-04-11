package application.buzzmovieselector.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.Model.User;

/**
 * Created by harmeetbindra on 3/9/16.
 */
public class MovieUserDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "USERMOVIE";
    private static final int DB_VERSION = 1;
    private static final String USER_NAME = "UserName";
    private static final String MOVIE_ID = "MovieId";
    private static final String COMMENTS = "Comments";
    private static final String RATING = "Rating";

    private SQLiteDatabase db;
    private MovieDatabase helper;
    private Context context;

    public SQLiteDatabase getDb() {
        return db;
    }


    public MovieDatabase getHelper() {
        return helper;
    }


    public MovieUserDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);   // Users.db is the name of the database and 1 is the version
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_NAME + "(" + USER_NAME + " TEXT, " + MOVIE_ID + " INTEGER, " + COMMENTS + " TEXT, " + RATING + " TEXT" +
                ", PRIMARY KEY (" + USER_NAME + ", " + MOVIE_ID + "));";
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

    public long insertRatingComment(Movie movie, User user) {
        db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(USER_NAME, user.getUserName());
        userValues.put(MOVIE_ID, movie.getId());
        userValues.put(RATING, movie.getRating());
        userValues.put(COMMENTS, movie.getComment());
        long id = db.insert(DB_NAME, null, userValues);
        db.close();
        return id;
    }

    public float getRating(Movie movie) {
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DB_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int movieId;
        float rating;
        ArrayList<Float> ratings = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                movieId = cursor.getInt(1);
                if (movie.getId() == movieId) {
                    rating = cursor.getInt(3);
                    ratings.add(rating);
                }
            } while (cursor.moveToNext());
        }
        db.close();
        float sum = 0;
        for (Float a : ratings) {
            sum += a;
        }
        return (sum / (ratings.size() * 5)) * 5;
    }
}
