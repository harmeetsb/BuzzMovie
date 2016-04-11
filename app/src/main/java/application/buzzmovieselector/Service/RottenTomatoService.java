package application.buzzmovieselector.Service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import application.buzzmovieselector.Model.Movie;

/**
 * Created by harmeetbindra on 3/3/16.
 */
public final class RottenTomatoService {
    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private static final int MOVIE_PAGE_LIMIT = 5;
    private static String response;
    private static RequestQueue queue;
    private static RottenTomatoService ourInstance;
    private ArrayList<Movie> searchList = new ArrayList<>();

    private RottenTomatoService() {
        queue = VolleySingleton.getInstance().getmRequestQueue();
    }

    public static RottenTomatoService getInstance() {
        if (ourInstance == null) {
            ourInstance = new RottenTomatoService();
        }
        return ourInstance;
    }

    public ArrayList<Movie> getMovies(String url, final Context context) {
        //       searchList.clear();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        response = resp.toString();
                        JSONObject obj1 = null;
                        try {
                            obj1 = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray array = obj1.optJSONArray("movies");
                        //ArrayList<Movie> movies = new ArrayList<>();
                        Movie movie = null;
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject jsonObject = array.getJSONObject(i);
                                movie = new Movie();
                                movie.setName(jsonObject.optString("title"));
                                movie.setYear(jsonObject.optInt("year"));
                                movie.setId(jsonObject.optInt("id"));
                                //movies.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            searchList.add(movie);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);
        return searchList;
    }

}
