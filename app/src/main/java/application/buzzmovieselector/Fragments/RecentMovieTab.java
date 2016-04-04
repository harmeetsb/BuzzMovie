package application.buzzmovieselector.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import application.buzzmovieselector.R;
import application.buzzmovieselector.Service.VolleySingleton;
import application.buzzmovieselector.ui.RecentListAdapter;

/**
 * This class represents a movieTab fragment
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class RecentMovieTab extends Fragment {
    ArrayList<String> movies = new ArrayList<>();
    private String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private int MOVIE_PAGE_LIMIT = 10;
    private String response;
    private RequestQueue queue;
    private ListView listView;
    private View rootView;
    private RecentListAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    //the recyclerview containing showing all our movies
    private RecyclerView mRecyclerMovies;

    public RecentMovieTab() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //  listView = (ListView)rootView.findViewById(R.id.list);
        queue = VolleySingleton.getInstance().getmRequestQueue();
        // mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeMovieHits);
        //mSwipeRefreshLayout.setOnRefreshListener();
        mRecyclerMovies = (RecyclerView) rootView.findViewById(R.id.listMovieHits);
        //set the layout manager before trying to display data
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.canScrollVertically();
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // GridLayoutManager layoutManager =  new GridLayoutManager(getActivity());
        mRecyclerMovies.setLayoutManager(layoutManager);
        mAdapter = new RecentListAdapter(getActivity());
        mRecyclerMovies.setAdapter(mAdapter);
        recentDvd();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recent_movie_tab, container, false);
        return rootView;
    }

    private void recentDvd() {
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=" + API_KEY + "&page_limit=" + MOVIE_PAGE_LIMIT;
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
                        Movie movie;
                        ArrayList<Movie> movies = new ArrayList<>();
                        JSONArray array = obj1.optJSONArray("movies");
                        ArrayList<String> m = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject jsonObject = array.getJSONObject(i);
                                movie = new Movie();
                                movie.setName(jsonObject.optString("title"));
                                movie.setYear(jsonObject.optInt("year"));
                                movie.setId(jsonObject.optInt("id"));
                                movie.setReleaseDate(jsonObject.optJSONObject("release_dates").optString("theater"));
                                movie.setMpaaRating(jsonObject.optString("mpaa_rating"));
                                movie.setRunTime(jsonObject.optInt("runtime"));
                                movie.setImageUrl(jsonObject.optJSONObject("posters").optString("thumbnail"));
                                movies.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.setMovies(movies);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);
    }
}
