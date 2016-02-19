package Model;

/**
 * This class represents a generic UserManagementFacade
 * @version 1.0
 */
public interface UserManagementFacade {
    /**
     * This method adds user to the database
     * @param name is the name of the user
     * @param password is the password of the user
     * @param email is the email address of the user
     * @param userName is the username of the user
     * @param major is the major of the user
     * @param isAdmin to see if user is admin
     * @param isBanned to see if user is banned
     * @return user object
     */
    boolean addUser(String name, String password, String email, String userName, String major, boolean isAdmin, boolean isBanned);
    /**
     * Method to find user from database
     * @param id is the username
     * @return the user object
     */
    User findUserById(String id);
}

