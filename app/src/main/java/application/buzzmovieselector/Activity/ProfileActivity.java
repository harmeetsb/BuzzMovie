package application.buzzmovieselector.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.R;
import application.buzzmovieselector.Service.VolleySingleton;
import application.buzzmovieselector.ui.TabListAdapter;

/**
 * This class represents a ProfileActivity object
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class ProfileActivity extends AppCompatActivity implements ActionBar.TabListener {
    private static String userName;
    private TabListAdapter adapter;
    private ViewPager mViewPager;
    private ActionBar actionBar;
    private VolleySingleton volleyInstance;
    private String apiKey = "yedukp76ffytfuy24zsqk7f5";
    private int moviePageLimit = 10;
    private String response;
    private RequestQueue queue;
    private ArrayList<Movie> searchMovies;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static void setUserName(String userName) {
        ProfileActivity.userName = userName;
    }

    public TabListAdapter getAdapter() {
        return adapter;
    }


    public ViewPager getmViewPager() {
        return mViewPager;
    }




    public VolleySingleton getVolleyInstance() {
        return volleyInstance;
    }



    public static String getUserName() {
        return userName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        searchMovies = new ArrayList<>();
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        volleyInstance = VolleySingleton.getInstance();
        queue = volleyInstance.getmRequestQueue();  // request queue for volley

        adapter = new TabListAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab0 = actionBar.newTab();
        tab0.setText("Home");
        tab0.setTabListener(this);
        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("Search");
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("Profile");
        tab2.setTabListener(this);

        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText("Movies");
        tab3.setTabListener(this);

        ActionBar.Tab tab4 = actionBar.newTab();
        tab4.setText("DVDs");
        tab4.setTabListener(this);

        actionBar.addTab(tab0);
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        actionBar.addTab(tab4);
        // reads page change from the tab
        // basically syncing swiping with clicking tab
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // when we swipe the screen, keeps track of the changes
                // even tracks the number of pixels been scrolled
            }
        });
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    /**
     * Method to handle Movie search
     *
     * @param view View in which search has been clicked
     */
    public void onClickMovieSearch(View view) {
        String movieName = getMovieName();
        if (movieName.isEmpty() || movieName == null) {
            Toast.makeText(this, "invalid movie name", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + apiKey + "&q=" + movieName + "&page_limit=" + moviePageLimit;
            requestMovie(url);
            // changeView(searchMovies);
        }
    }

    /**
     * Method to return the context of this activity
     *
     * @return the context of this activity
     */
    public Context getContext() {
        return this;
    }

    /**
     * This method changes to our new list view of the states, but we have to pass the
     * state array into the intent so the new screen gets the application.buzzmovieselector.data.
     *
     * @param movies the list of list of movie objects we created from the JSon response
     */
    private void changeView(ArrayList<Movie> movies) {
        Intent intent = new Intent(this, ItemListActivity.class);
        // this is where we save the info.  note the State object must be Serializable
        intent.putExtra("movies", movies);
        startActivity(intent);
    }

    private String getMovieName() {
        SearchView searchBox = (SearchView) findViewById(R.id.searchView);
        String movieName = String.valueOf(searchBox.getQuery());
        movieName = movieName.replaceAll("\\s", "+");
        return movieName;
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
                                movies.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //populateArray(movies);
                        changeView(movies);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Profile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://application.buzzmovieselector.Activity/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Profile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://application.buzzmovieselector.Activity/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}