package application.buzzmovieselector.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import application.buzzmovieselector.Model.User;

/**
 * This class represents a DatabaseHelper object
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "USERS";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private DatabaseHelper helper;
    private Context context;

    public SQLiteDatabase getDb() {
        return db;
    }



    public DatabaseHelper getHelper() {
        return helper;
    }



    /**
     * Makes a DataBase Helper object
     *
     * @param context is the context of the activity
     */
    public DatabaseHelper(Context context) {
        super(context, "USERS", null, DB_VERSION);   // Users.db is the name of the database and 1 is the version
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE USERS(Name TEXT, Email TEXT, Username TEXT PRIMARY KEY, " +
                "Password TEXT, Major TEXT, isAdmin NUMERIC, isBanned NUMERIC, isLocked, isActive);";
        try {
            db.execSQL(query);
        } catch (SQLException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE USERS IF EXISTS");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE USERS IF EXISTS");
        onCreate(db);
    }

    /**
     * Method to insert user to the database
     *
     * @param user is the user object that is to be stored in the database
     * @return id that is negative if the user was not inserted
     */
    public long insertUser(User user) {
        db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put("Name", user.getName());
        userValues.put("Email", user.getEmail());
        userValues.put("Username", user.getUserName());
        userValues.put("Password", user.getPassword());
        userValues.put("Major", user.getMajor());
        userValues.put("isAdmin", user.getIsAdmin());
        userValues.put("isBanned", user.getIsBanned());
        userValues.put("isLocked", user.getIsLocked());
        userValues.put("isActive", user.getIsActive());
        long id = db.insert("USERS", null, userValues);
        db.close();
        return id;
    }

    /**
     * Method to find user from the database
     *
     * @param userName is the primary key
     * @return user object
     */
    public User findUser(String userName) {
        db = this.getReadableDatabase();
        String query = "SELECT Name, Email, Username, Password, " +
                "Major, isAdmin, isBanned, isLocked, isActive FROM " + DB_NAME;

        Cursor cursor = db.rawQuery(query, null);
        String name = null, email = null, username = null, password = null, major = null;
        boolean isAdmin = false, isBanned = false, isLocked = false, isActive = false;
        String un = "";
        if (cursor.moveToFirst()) {
            do {
                un = cursor.getString(2);
                if (un.equals(userName)) {
                    name = cursor.getString(0);
                    email = cursor.getString(1);
                    username = cursor.getString(2);
                    password = cursor.getString(3);
                    major = cursor.getString(4);
                    isAdmin = cursor.getInt(5) > 0;
                    isBanned = cursor.getInt(6) > 0;
                    isLocked = cursor.getInt(7) > 0;
                    isActive = cursor.getInt(8) > 0;
                    db.close();
                    cursor.close();
                    return new User(name, password, email, username, major, isAdmin,
                            isBanned, isLocked, isActive);
                }
            } while (cursor.moveToNext());
        }
        return null;
    }

    /**
     * method to update user
     *
     * @param user is the user that has to be updated
     */
    public void update(User user) {
        String name = user.getUserName();
        db = this.getWritableDatabase();
        db.delete("USERS", " Username = '" + user.getUserName() + "'", null);
        insertUser(user);
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();
        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DB_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String name = null, email = null, username = null, password = null, major = null;
        boolean isAdmin = false, isBanned = false, isLocked = false, isActive = false;
        String un = "";
        cursor.moveToFirst();
        do {
            name = cursor.getString(0);
            email = cursor.getString(1);
            username = cursor.getString(2);
            password = cursor.getString(3);
            major = cursor.getString(4);
            isAdmin = cursor.getInt(5) > 0;
            isBanned = cursor.getInt(6) > 0;
            User user = new User(name, password, email, username, major, isAdmin,
                    isBanned, isLocked, isActive);
            users.add(user);
        } while (cursor.moveToNext());
        db.close();
        cursor.close();
        return users;
    }
}
