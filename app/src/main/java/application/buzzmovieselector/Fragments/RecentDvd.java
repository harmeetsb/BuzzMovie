package application.buzzmovieselector.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
 * This class represents a recent DVD fragment
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class RecentDvd extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    ArrayList<Movie> movies = new ArrayList<>();
    private String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private int MOVIE_PAGE_LIMIT = 10;
    private String response;
    private RequestQueue queue;
    private ListView listView;
    private View rootView;
//    private ArrayAdapter<Movie> resultListAdapter;
//    private RecentListAdapter testAdapter;

    //new
    private RecentListAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    //the recyclerview containing showing all our movies
    private RecyclerView mRecyclerMovies;
    //the TextView containing error messages generated by Volley
    private TextView mTextError;

    public RecentDvd() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        queue = VolleySingleton.getInstance().getmRequestQueue();
        // rootView = inflater.inflate(R.layout.fragment_recent_dvd, container, false);
        rootView = inflater.inflate(R.layout.recent_list_fragment, container, false);

        //list view
       /* listView = (ListView)rootView.findViewById(R.id.list);
        recentDvd();
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Movie entry = (Movie) parent.getAdapter().getItem(position);
                //Intent intent = new Intent(MainActivity.this, SendMessage.class);
                //String message = entry.getMessage();
                //intent.putExtra(EXTRA_MESSAGE, message);
                //startActivity(intent);

            }
        });
        */
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeMovieHits);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerMovies = (RecyclerView) rootView.findViewById(R.id.listMovieHits);
        //set the layout manager before trying to display data
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerMovies.setLayoutManager(layoutManager);
        mAdapter = new RecentListAdapter(getActivity());
        mRecyclerMovies.setAdapter(mAdapter);
        recentDvd();
        return rootView;
    }

    private void recentDvd() {
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey=" + API_KEY + "&page_limit=" + MOVIE_PAGE_LIMIT;
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
                        ArrayList<Movie> dvd = new ArrayList<>();
                        ArrayList<Movie> movies = new ArrayList<>();
                        Movie movie = null;
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject jsonObject = array.getJSONObject(i);
                                movie = new Movie();
                                movie.setName(jsonObject.optString("title"));
                                movie.setYear(jsonObject.optInt("year"));
                                movie.setId(jsonObject.optInt("id"));
                                movie.setReleaseDate(jsonObject.optJSONObject("release_dates").optString("dvd"));
                                movie.setMpaaRating(jsonObject.optString("mpaa_rating"));
                                movie.setRunTime(jsonObject.optInt("runtime"));
                                movie.setImageUrl(jsonObject.optJSONObject("posters").optString("thumbnail"));
                                dvd.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //resultListAdapter = new ArrayAdapter<>(getActivity(),
                        //       android.R.layout.simple_list_item_1,
                        //       dvd);
                        //mAdapter = new RecentListAdapter(getActivity());
                        mAdapter.setMovies(dvd);
                        // TODO: 3/6/16 implement our own adapter
                        //resultListAdapter = new RecentListAdapter(getActivity(), dvd, movies);
                        // listView.setAdapter(resultListAdapter);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = String.valueOf(listView.getItemAtPosition(position));
        Toast.makeText(getActivity(), "Item" + selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
    }
    /*
    public void onClick(View view) {
        switch(view.getId())// on run time get id what button os click and get id
        {
            case R.id.updateButton:        // it mean if button1 click then this work
                listView.setAdapter(resultListAdapter);
                break;
        }
    }
    */
}
