package application.buzzmovieselector.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.buzzmovieselector.data.DatabaseHelper;

/**
 * This class represents a UserManager object
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class UserManager implements AuthenticationFacade, UserManagementFacade {
    private static Map<String, User> users = new HashMap<>();
    DatabaseHelper db;
    @Override
    public User findUserById(String userName) {
        return db.findUser(userName);
    }
    @Override
    public boolean addUser(String name, String password, String email, String userName, String major, boolean isAdmin, boolean isBanned) {
        User user = new User(name, password, email, userName,major,isAdmin, false);
        long id = db.insertUser(user);
        if(id < 0) return false;
        else return true;
    }
    @Override
    public boolean handleLoginRequest(String name, String pass) {
        User u = findUserById(name);
        if (u == null) return false;
        return u.checkPassword(pass);
    }
    /**
     * Makes a UserManager object
     * @param context is the context of the activity
     */
    public UserManager(Context context) {
        db = new DatabaseHelper(context);
    }
    /**
     * updates the user information
     * @param user is the user that needs to be updated
     */
    public void updateUser(User user) {
        db.update(user);
    }

    public ArrayList<User> getUsers() {
        return db.getAllUser();
    }
}

