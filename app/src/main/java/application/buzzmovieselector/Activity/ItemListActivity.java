package application.buzzmovieselector.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.R;

public class ItemListActivity extends AppCompatActivity {
private ListView movieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        movieList = (ListView) findViewById(R.id.movieList);
        Intent intent = getIntent();
        ArrayList<Movie> resultList = (ArrayList<Movie>) intent.getSerializableExtra("movies");
        ArrayAdapter<Movie> resultListAdapter = new ArrayAdapter<Movie>(this,
                android.R.layout.simple_list_item_1,
                resultList);
        movieList.setAdapter(resultListAdapter);
    }

}

//class MyListAdapter extends ArrayAdapter<Movie> {

  //  public MyListAdapter(Context context, int resource) {
   //     super(context, resource);
  //  }
    
//}