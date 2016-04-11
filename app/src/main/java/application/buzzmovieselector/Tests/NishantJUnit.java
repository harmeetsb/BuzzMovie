package application.buzzmovieselector.Tests;
import android.test.AndroidTestCase;

import junit.framework.TestCase;

import application.buzzmovieselector.Model.User;
import application.buzzmovieselector.Model.UserManager;

/**
 * Created by localadmin on 4/11/16.
 */
public class NishantJUnit extends AndroidTestCase {

    private User user1 = new User("Nishant", "password", "nroy@gatech.edu", "nroy6",
            "Computer Science", false, false, false, false);
    private User user2 = new User("Shane", "password", "nroy@gatech.edu", "nroy6",
            "Computer Science", false, false, false, false);


    public void testFindUser() {
        UserManager manager = new UserManager(mContext);
        boolean added = manager.addUser("Nishant", "password", "nroy@gatech.edu", "nroy6",
                "Computer Science", false, false, false, false);
        assertTrue(added);
        assertEquals(user1, manager.findUserById("Nishant"));

    }




}
