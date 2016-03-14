package application.buzzmovieselector.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import application.buzzmovieselector.Activity.LoginActivity;
import application.buzzmovieselector.Activity.ProfileActivity;
import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.Model.MovieManager;
import application.buzzmovieselector.Model.User;
import application.buzzmovieselector.Model.UserManager;
import application.buzzmovieselector.R;
import application.buzzmovieselector.Service.VolleySingleton;
import application.buzzmovieselector.ui.RecentListAdapter;


public class Home extends Fragment implements View.OnClickListener{
    private View rootView;
    UserManager userManager;
    private RecentListAdapter mAdapter;
    User user;
    Spinner spinner;
    Button logoutButton;
    MovieManager movieManager;
    private String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private int MOVIE_PAGE_LIMIT = 10;
    private String response;
    VolleySingleton volleyInstance;
    private RequestQueue queue;
    private RecyclerView mRecyclerMovies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        userManager = new UserManager(getActivity());
        mRecyclerMovies = (RecyclerView) rootView.findViewById(R.id.listMovieHit);
        volleyInstance = VolleySingleton.getInstance();
        queue = volleyInstance.getmRequestQueue();
        mAdapter = new RecentListAdapter(getActivity());
        movieManager = new MovieManager(getActivity());
        user = userManager.findUserById(ProfileActivity.getUserName());
        logoutButton = (Button)rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);
        spinner = (Spinner) rootView.findViewById(R.id.spinnerRecommend);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.recommend, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
<<<<<<< HEAD
        //layoutManager.canScrollVertically();
=======
        layoutManager.canScrollVertically();
>>>>>>> origin/master
        mRecyclerMovies.setLayoutManager(layoutManager);
        mRecyclerMovies.setAdapter(mAdapter);
        setFilter();
        return rootView;
    }
    /**
     * see if user wants to logout of the app
     * @param view the logout button clicked
     */
    public void onClick(View view) {
        System.out.println();
        switch (view.getId())// on run time get id what button os click and get id
        {
            case R.id.logoutButton:        // it mean if button1 click then this work
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
    /**
     * gives users movies based on major
     */
    public void setFilter() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position > 0) {
                    String major = user.getMajor();
                    String[] splitMajor = major.split("\\s+");
                    major = splitMajor[0];
                    String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + API_KEY + "&q=" + major + "&page_limit=" + MOVIE_PAGE_LIMIT;
                    requestMovie(url);
                }
            }


            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }
    private void requestMovie(String url) {
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
                        Movie movie;
                        ArrayList<Movie> movies = new ArrayList<>();
                        for(int i = 0; i < array.length(); i++) {
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
                                movies.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
<<<<<<< HEAD
                        if(spinner.getSelectedItemPosition() == 2){
                            movies = movieManager.highestRatedMovie(movies);
                        }
=======
                         if(spinner.getSelectedItemPosition() == 2){
                             movies = movieManager.highestRatedMovie(movies);
                         }
>>>>>>> origin/master

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
<<<<<<< HEAD
=======

}
>>>>>>> origin/master

}