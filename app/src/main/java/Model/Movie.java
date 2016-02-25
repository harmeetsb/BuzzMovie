package Model;

import java.io.Serializable;

/**
 * Created by harmeetbindra on 2/24/16.
 */
public class Movie implements Serializable {
    private String name;
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Name: "+name + " Year: "+year;
    }
}
