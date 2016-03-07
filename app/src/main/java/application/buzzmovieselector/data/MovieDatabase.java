package application.buzzmovieselector.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String movieUrl = "URL";
    private static final String date = "Date";
    private static final String mppaRating = "Mpaa_Rating";
    private static final String runtime = "Runtime";
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
        String query = "CREATE TABLE "+DB_NAME+"("+movieId+ " INTEGER PRIMARY KEY, "+movieName+" TEXT, "+comments+" TEXT, "+ratings+" FLOAT, "
                +movieUrl+" TEXT, "+date+" TEXT, "+mppaRating+" TEXT,"+runtime+" INTEGER);";
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
        userValues.put(movieUrl, movie.getImageUrl());
        userValues.put(date, movie.getReleaseDate().toString());
        userValues.put(mppaRating,movie.getMpaaRating());
        userValues.put(runtime, movie.getRunTime());
        long id = db.insert(DB_NAME, null, userValues);
        db.close();
        return id;
    }

    public Movie findUser(int id) {
        db = this.getReadableDatabase();
        //String query = "SELECT "+movieId+", "+movieName+", "+comments+", "+ratings+", "+
        //        movieUrl+", "+date+", "+mppaRating+", "+runtime+" FROM "+DB_NAME;
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
        if(cursor.moveToFirst()) {
            do {
                mId = cursor.getInt(0);
                if(id == mId) {
                    name = cursor.getString(1);
                    url = cursor.getString(4);
                    mDate = cursor.getString(5);
                    mpRating = cursor.getString(6);
                    runT = cursor.getInt(7);
                    rating = cursor.getInt(3);
                    movie = new Movie(name, id, rating, url, mDate, runT, mpRating);
                }
            } while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return movie;
    }
    public boolean update(Movie movie) {
        int id = movie.getId();
        db = this.getWritableDatabase();
        db.delete(DB_NAME, " "+movieId+" = '"+id+"'", null);
        long sucess = insertMovie(movie);
        if(sucess < 0) return false;
        else return true;
    }
}
