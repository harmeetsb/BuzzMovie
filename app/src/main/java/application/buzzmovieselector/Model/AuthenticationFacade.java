package application.buzzmovieselector.Model;

/**
 * This class represents a generic AuthenticationFacade
 *
 * @version 1.0
 */
public interface AuthenticationFacade {
    /**
     * This method handles the login request
     *
     * @param name     is the username
     * @param password is the user password
     * @return if login was successful of not
     */
    boolean handleLoginRequest(String name, String password);
}

