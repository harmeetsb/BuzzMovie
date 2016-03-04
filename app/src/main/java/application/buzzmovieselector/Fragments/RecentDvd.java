package application.buzzmovieselector.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class RecentDvd extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<String> movies = new ArrayList<>();
    private String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private int MOVIE_PAGE_LIMIT = 5;
    private String response;
    private RequestQueue queue;
    private ListView listView;
    private View rootView;
    private ArrayAdapter<String> resultListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public RecentDvd() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        queue = VolleySingleton.getInstance().getmRequestQueue();
        rootView = inflater.inflate(R.layout.fragment_recent_dvd, container, false);
        listView = (ListView)rootView.findViewById(R.id.list);
        recentDvd();
        return rootView;
    }
    private void recentDvd() {
        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey="+API_KEY+"&page_limit="+MOVIE_PAGE_LIMIT;
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
                        ArrayList<String> dvd = new ArrayList<>();
                        Movie movie = null;
                        for(int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject jsonObject = array.getJSONObject(i);
                                movie = new Movie();
                                movie.setName(jsonObject.optString("title"));
                                movie.setYear(jsonObject.optInt("year"));
                                dvd.add(movie.getName());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        resultListAdapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_list_item_1,
                                dvd);
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



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selected = String.valueOf(listView.getItemAtPosition(position));
        Toast.makeText(getActivity(), "Item"+selected, Toast.LENGTH_SHORT).show();
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
