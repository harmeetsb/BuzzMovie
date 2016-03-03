package application.buzzmovieselector.Model;

import java.io.Serializable;

/**
 * This class represents Movie object
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class Movie implements Serializable {
    private String name;
    private int year;
    private int id;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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
