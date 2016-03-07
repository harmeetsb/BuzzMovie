package application.buzzmovieselector.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents Movie object
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

    public Movie(String name, int id, int rating, String imageUrl, String releaseDate, int runTime, String mpaaRating) {
        this.name = name;
        this.id = id;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.runTime = runTime;
        this.mpaaRating = mpaaRating;
    }
    public Movie() {

    }
    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Method to return the movie release year
     * @return year
     */
    public int getYear() {
        return year;
    }
    /**
     * sets the year of the movie
     * @param year is the year movie is released
     */
    public void setYear(int year) {
        this.year = year;
    }
    /**
     * returns the name of the mocie
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Method to set the name of the movie
     * @param name is the name of the movie
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return "Name: "+name + " Year: "+year;
    }
}
