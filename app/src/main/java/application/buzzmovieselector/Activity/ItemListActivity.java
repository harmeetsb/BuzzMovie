package application.buzzmovieselector.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.ui.RecentListAdapter;
import java.util.ArrayList;

import application.buzzmovieselector.R;


public class ItemListActivity extends AppCompatActivity {

    private RecentListAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    //the recyclerview containing showing all our movies
    private RecyclerView mRecyclerMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie_list);
       // movieList = (ListView) findViewById(R.id.movieList);
        Intent intent = getIntent();
        ArrayList<Movie> resultList = (ArrayList<Movie>) intent.getSerializableExtra("movies");
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeMovieHits);
       // mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerMovies = (RecyclerView) findViewById(R.id.listMovieHits);
        //set the layout manager before trying to display data
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // GridLayoutManager layoutManager =  new GridLayoutManager(getActivity());
        mRecyclerMovies.setLayoutManager(layoutManager);
        mAdapter = new RecentListAdapter(this, resultList);
        mRecyclerMovies.setAdapter(mAdapter);
    }

}

//class MyListAdapter extends ArrayAdapter<Movie> {

  //  public MyListAdapter(Context context, int resource) {
   //     super(context, resource);
  //  }
    
//}