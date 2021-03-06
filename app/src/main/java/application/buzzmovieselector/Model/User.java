package application.buzzmovieselector.Model;

/**
 * This class represents a User object
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class User {
    private String name;
    private String password;
    private String email;
    private String userName;
    private boolean isAdmin;
    private boolean isBanned;
    private String major;
    private boolean isLocked;
    private boolean isActive;

    /**
     * Makes a User object
     *
     * @param name     is the name of the user
     * @param password is the password of the user
     * @param email    is the email address of the user
     * @param userName is the username of the user
     * @param major    is the major of the user
     * @param isAdmin  to see if user is admin
     * @param isBanned to see if user is banned
     */
    public User(String name, String password, String email, String userName, String major,
                boolean isAdmin, boolean isBanned, boolean isLocked, boolean isActive) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.isAdmin = isAdmin;
        this.isBanned = isBanned;
        this.major = major;
        this.isLocked = isLocked;
        this.isActive = isActive;
    }

    public User() {

    }

    /**
     * Method to returns isBanned
     *
     * @return isBanned
     */
    public boolean getIsBanned() {
        return isBanned;
    }

    /**
     * Method to returns getMajor
     *
     * @return major
     */

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public String getMajor() {
        return major;
    }

    /**
     * Method to returns name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set name
     *
     * @param name is the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to returns password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to returns email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set email
     *
     * @param email is the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to returns username
     *
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method to returns isAdmin
     *
     * @return isAdmin
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Method to returns check password
     *
     * @param pass is the password
     * @return if password is correct or not
     */
    public boolean checkPassword(String pass) {
        return password.equals(pass);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}

