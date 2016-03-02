package application.buzzmovieselector.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import application.buzzmovieselector.Fragments.ProfileTab;
import application.buzzmovieselector.Fragments.RecentMovieTab;
import application.buzzmovieselector.Fragments.SearchTab;
import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.R;
import application.buzzmovieselector.Service.VolleySingleton;

public class ProfileActivity extends AppCompatActivity implements ActionBar.TabListener {
    MyAdapter adapter;
    ViewPager mViewPager;
    ActionBar actionBar;
    private String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private int MOVIE_PAGE_LIMIT = 10;
    private String response;
    VolleySingleton volleyInstance;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        volleyInstance = VolleySingleton.getInstance();
        queue = volleyInstance.getmRequestQueue();  // request queue for volley

        adapter = new MyAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("Recent");
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("Profile");
        tab2.setTabListener(this);

        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText("Search");
        tab3.setTabListener(this);

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        // reads page change from the tab
        // basically syncing swiping with clicking tab
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //actionBar.setSelectedNavigationItem(position);
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

    }

    private void setUpActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("Recent");
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("Profile");
        tab2.setTabListener(this);

        ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText("Movies");
        tab3.setTabListener(this);

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    // for movie tab
    public void onClickDvdSearch(View view) {
        String movieName = getMovieName();
        if(movieName.isEmpty() || movieName == null) {
            Toast.makeText(this, "invalid movie name", Toast.LENGTH_SHORT).show();
        } else {
            String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/current_releases.json?apikey="+API_KEY+"&page_limit="+MOVIE_PAGE_LIMIT;
            requestMovie(url);
        }
    }

    // for movie tab
    public void onClickMovieSearch(View view) {
        String movieName = getMovieName();
        if(movieName.isEmpty() || movieName == null) {
            Toast.makeText(this, "invalid movie name", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + API_KEY + "&q=" + movieName + "&page_limit=" + MOVIE_PAGE_LIMIT;
            requestMovie(url);
        }
    }
    public Context getContext(){
        return this;
    }
    /**
     * This method changes to our new list view of the states, but we have to pass the
     * state array into the intent so the new screen gets the data.
     *
     * @param movies the list of list of movie objects we created from the JSon response
     */
    private void changeView(ArrayList<Movie> movies) {
        Intent intent = new Intent(this, ItemListActivity.class);
        //this is where we save the info.  note the State object must be Serializable
        intent.putExtra("movies", movies);
        startActivity(intent);
    }

    private String getMovieName() {
        SearchView searchBox = (SearchView) findViewById(R.id.searchView);
        return String.valueOf(searchBox.getQuery());
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
                        ArrayList<Movie> movies = new ArrayList<>();
                        for(int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject jsonObject = array.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setName(jsonObject.optString("title"));
                                movie.setYear(jsonObject.optInt("year"));
                                movies.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        changeView(movies);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);
    }
}

    class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0) {
            fragment = new RecentMovieTab();
        } else if(position == 1) {
            fragment = new ProfileTab();
        } else if(position == 2) {
            fragment = new SearchTab();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
    }