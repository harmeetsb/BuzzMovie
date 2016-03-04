package application.buzzmovieselector.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
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
import java.util.List;

import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.R;
import application.buzzmovieselector.Service.VolleySingleton;


public class RecentMovieTab extends Fragment {
    ArrayList<String> movies = new ArrayList<>();
    private String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private int MOVIE_PAGE_LIMIT = 5;
    private String response;
    private RequestQueue queue;
    private ListView listView;
    private View rootView;
    public RecentMovieTab() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)rootView.findViewById(R.id.list);
        queue = VolleySingleton.getInstance().getmRequestQueue();
        recentDvd();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recent_movie_tab, container, false);
        return rootView;
    }
    private void recentDvd() {
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey="+API_KEY+"&page_limit="+MOVIE_PAGE_LIMIT;
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
                        ArrayList<String> m = new ArrayList<>();
                        for(int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject jsonObject = array.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setName(jsonObject.optString("title"));
                                movie.setYear(jsonObject.optInt("year"));
                                m.add(movie.getName());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> resultListAdapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_list_item_1,
                                m);
                        listView.setAdapter(resultListAdapter);
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

    public void populateArray(ArrayList<String> m) {
        this.movies = m;
    }
}
