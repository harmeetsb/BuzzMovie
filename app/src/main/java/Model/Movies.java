package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harmeetbindra on 2/24/16.
 */
public class Movies {

    /**
     * An array of Movie objects.
     */
    public static final List<Movie> ITEMS = new ArrayList<>();

    /**
     * A map of states  by Name.
     */
    public static final Map<String, Movie> ITEM_MAP = new HashMap<>();

    public static void addItem(Movie item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getName(), item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Movie: ").append(position);
        for(int i = 0; i <position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
