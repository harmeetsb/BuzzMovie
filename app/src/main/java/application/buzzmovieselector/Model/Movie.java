package application.buzzmovieselector.Model;

import java.io.Serializable;

/**
 * This class represents Movie object
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class Movie implements Serializable {
    private String name;
    private int year;
    private int id;
    private float rating;
    private String imageUrl;
    private String releaseDate;
    private int runTime;
    private String mpaaRating;
    private String comment;

    /**
     * Makes a Movie object
     *
     * @param name        is the name of the movie
     * @param id          is the unique movie id
     * @param rating      is the rating of the movie
     * @param imageUrl    is the url of the movie poster
     * @param releaseDate is the movie release date
     * @param runTime     is the movie runTime
     * @param mpaaRating  is the mpaaRating of the  movie
     */
    public Movie(String name, int id, int rating, String imageUrl, String releaseDate, int runTime, String mpaaRating) {
        this.name = name;
        this.id = id;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.runTime = runTime;
        this.mpaaRating = mpaaRating;
    }

    /**
     * Makes a Movie object
     */
    public Movie() {

    }

    /**
     * Method to return the movie run time
     *
     * @return runTime
     */
    public int getRunTime() {
        return runTime;
    }

    /**
     * Method to set movie runtime
     *
     * @param runTime is the movie runTime
     */
    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    /**
     * Method to set movie mpaarating
     *
     * @param mpaaRating is the movie mpaaRating
     */
    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     * returns the movie release date
     *
     * @return releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Method to set movie runtime
     *
     * @param releaseDate is the movie releasedate
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Method to set image URL
     *
     * @param imageUrl is the movie poster thumbnail
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * returns the movie rating
     *
     * @return rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Method to set movie rating
     *
     * @param rating is the movie rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Method to return the movie release year
     *
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * sets the year of the movie
     *
     * @param year is the year movie is released
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * returns the name of the movie
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name of the movie
     *
     * @param name is the name of the movie
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Name: " + name + " Year: " + year;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
