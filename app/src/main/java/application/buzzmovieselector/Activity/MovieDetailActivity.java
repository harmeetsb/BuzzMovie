package application.buzzmovieselector.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.Model.MovieManager;
import application.buzzmovieselector.Model.User;
import application.buzzmovieselector.Model.UserManager;
import application.buzzmovieselector.R;

/**
 * This class represents a MovieDetail activity object
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class MovieDetailActivity extends AppCompatActivity {
    private static int submitCount;
    private RatingBar ratingBar;
    private User user;
    private ArrayList<Movie> moviesRated = new ArrayList<>();
    private TextView movieName;
    private TextView mpaaRating;
    private TextView runTime;
    private TextView ratingScore;
    private TextView year;
    private EditText comment;
    private Button submitButton;
    private Button backButton;
    private Movie movie;
    private MovieManager manager;
    private float updatedRating;
    private UserManager userManager;

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public User getUser() {
        return user;
    }


    public ArrayList<Movie> getMoviesRated() {
        return moviesRated;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        userManager = new UserManager(this);
        manager = new MovieManager(this);
        user = userManager.findUserById(ProfileActivity.getUserName());
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
        ratingScore.setText(String.valueOf(manager.getRating(movie)));
        year.setText(String.valueOf(movie.getReleaseDate()));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                updatedRating = rating;
            }
        });
    }

    /**
     * Method to handle back clicks
     *
     * @param view View in which back has been clicked
     */
    public void onClickBack(View view) {
        //       Intent intent = new Intent(this, ProfileActivity.class);
        //      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //    intent.putExtra("userName", user.getUserName());
        //    startActivity(intent);
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    /**
     * Method to handle submit clicks
     *
     * @param view View in which submit has been clicked
     */
    public void onClickSubmit(View view) {
        movie.setRating(updatedRating);
        String comm = String.valueOf(comment.getText());
        movie.setComment(comm);
        boolean rated = !(manager.insertRatingComment(user, movie));
        if (!rated) {
            float rating = manager.getRating(movie);
            ratingScore.setText(String.valueOf(rating));
            movie.setRating(rating);
            Toast.makeText(getContext(), String.valueOf(rating), Toast.LENGTH_SHORT).show();
            moviesRated.add(movie);
        } else {
            //manager.getRating(movie);
            Toast.makeText(this, "Already Rated", Toast.LENGTH_SHORT).show();
        }
        //  manager.updateDb(movie);
    }

    /**
     * Method to get the context of the app
     *
     * @return the context of this activity
     */
    public Context getContext() {
        return this;
    }
}
