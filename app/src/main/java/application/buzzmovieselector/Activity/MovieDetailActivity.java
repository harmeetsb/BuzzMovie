package application.buzzmovieselector.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.Model.MovieManager;
import application.buzzmovieselector.R;

/**
 * This class represents a MovieDetail activity object
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class MovieDetailActivity extends AppCompatActivity {
    private TextView movieName;
    private TextView mpaaRating;
    private TextView runTime;
    private TextView ratingScore;
    private TextView year;
    private EditText comment;
    RatingBar ratingBar;
    private Button submitButton;
    private Button backButton;
    private Movie movie;
    private static int submitCount;
    private MovieManager manager;
    float updatedRating;
    ArrayList<Movie> moviesRated = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        manager = new MovieManager(this);
        movieName = (TextView) findViewById(R.id.movieTitleView);
        mpaaRating = (TextView) findViewById(R.id.mpaaRatingView);
        year = (TextView) findViewById(R.id.yearView);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        runTime = (TextView) findViewById(R.id.runTimeView);
        ratingScore = (TextView) findViewById(R.id.ratingScoreView);
        comment = (EditText) findViewById(R.id.commentText);
        submitButton = (Button) findViewById(R.id.submitButton);
        backButton = (Button) findViewById(R.id.backButton);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        movie = manager.findMovieById(id);
        movieName.setText(movie.getName());
        mpaaRating.setText(movie.getMpaaRating());
        runTime.setText(String.valueOf(movie.getRunTime()));
        ratingScore.setText(String.valueOf(movie.getRating()));
        year.setText(String.valueOf(movie.getReleaseDate()));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                float curr = movie.getRating();
                updatedRating = (curr + rating)/10 * 5;
            }
        });
    }
    /**
     * Method to handle back clicks
     * @param view View in which back has been clicked
     */
    public void onClickBack(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    /**
     * Method to handle submit clicks
     * @param view View in which submit has been clicked
     */
    public void onClickSubmit(View view) {
            if(!moviesRated.contains(movie)) {
                ratingScore.setText(String.valueOf(updatedRating));
                movie.setRating(updatedRating);
                Toast.makeText(getContext(), String.valueOf(updatedRating), Toast.LENGTH_SHORT).show();
                moviesRated.add(movie);
        } else {
            Toast.makeText(this, "Already Rated", Toast.LENGTH_SHORT).show();
        }
        manager.updateDb(movie);
    }
    /**
     * Method to get the context of the app
     * @return the context of this activity
     */
    public Context getContext() {
        return this;
    }
}
