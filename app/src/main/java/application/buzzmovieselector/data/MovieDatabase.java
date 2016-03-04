package application.buzzmovieselector.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.Model.User;

/**
 * Created by harmeetbindra on 3/2/16.
 */
public class MovieDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "MOVIES";
    private static final int DB_VERSION = 1;
    private static final String movieId = "Movie_Id";
    private static final String movieName = "Movie_Name";
    private static final String comments = "Comments";
    private static final String ratings = "Ratings";
    SQLiteDatabase db;
    MovieDatabase helper;
    private Context context;
    /**
     * Makes a DataBase Helper object
     * @param context is the context of the activity
     */
    public MovieDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);   // Users.db is the name of the database and 1 is the version
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+DB_NAME+"("+movieId+ " INTEGER PRIMARY KEY, "+movieName+" TEXT, "+comments+" TEXT, "+ratings+" INTEGER);";
        try {
            db.execSQL(query);
        } catch (SQLException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+DB_NAME+" IF EXISTS");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+DB_NAME+" IF EXISTS");
        onCreate(db);
    }

    public long insertMovie(Movie movie) {
        db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(movieId, movie.getId());
        userValues.put(movieName, movie.getName());
        userValues.put(ratings, movie.getRating());
        long id = db.insert(DB_NAME, null, userValues);
        db.close();
        return id;
    }
}
