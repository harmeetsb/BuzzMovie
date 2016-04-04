package application.buzzmovieselector.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import application.buzzmovieselector.Activity.MovieDetailActivity;
import application.buzzmovieselector.Model.Movie;
import application.buzzmovieselector.Model.MovieManager;
import application.buzzmovieselector.R;
import application.buzzmovieselector.Service.VolleySingleton;

/**
 * This class represents a RecentMistAdapter object
 *
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class RecentListAdapter extends RecyclerView.Adapter<RecentListAdapter.ViewHolderBoxOffice> {
    MovieManager manager;
    Context context;
    private String title;
    //contains the list of movies
    private ArrayList<Movie> mListMovies = new ArrayList<>();
    private LayoutInflater mInflater;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    //formatter for parsing the dates in the specified format below
    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    //keep track of the previous position for animations where scrolling down requires a different animation compared to scrolling up
    private int mPreviousPosition = 0;

    /**
     * makes a RecentListAdapter object
     *
     * @param context is the context of the app
     */
    public RecentListAdapter(Context context) {
        manager = new MovieManager(context);
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        //this.mListMovies = movies;
        this.context = context;

    }

    /**
     * adds movies to the DB
     *
     * @param movieList is the list of movies to be inserted
     */
    public void addToDb(ArrayList<Movie> movieList) {
        for (Movie movie : movieList) {
            manager.insertMovie(movie);
        }
    }

    public void setMovies(ArrayList<Movie> listMovies) {
        this.mListMovies = listMovies;
        //update the adapter to reflect the new set of movies
        addToDb(listMovies);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_recent_list, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderBoxOffice holder, final int position) {
        final Movie currentMovie = mListMovies.get(position);
        final Movie curr = manager.findMovieById(currentMovie.getId());
        curr.setRating(manager.getRating(curr));
        //one or more fields of the Movie object may be null since they are fetched from the web
        holder.movieTitle.setText(curr.getName());

        holder.movieTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("id", curr.getId());
                context.startActivity(intent);
                //Toast.makeText(context, mListMovies.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        //retrieved date may be null
        String movieReleaseDate = curr.getReleaseDate();

        holder.movieReleaseDate.setText(movieReleaseDate);
        holder.movieTitle.setText(curr.getName());
        holder.movieAudienceScore.setRating(curr.getRating());
        //  int audienceScore = (int)currentMovie.getRating();
        ///  if (audienceScore == -1) {
        //      holder.movieAudienceScore.setRating(0.0F);
        //      holder.movieAudienceScore.setAlpha(0.5F);
        // } else {
        //     holder.movieAudienceScore.setRating(audienceScore / 20.0F);
        //     holder.movieAudienceScore.setAlpha(1.0F);
        // }

        if (position > mPreviousPosition) {
            application.buzzmovieselector.anim.AnimationUtils.animateSunblind(holder, true);
//            AnimationUtils.animateSunblind(holder, true);
//            AnimationUtils.animate1(holder, true);
//            AnimationUtils.animate(holder,true);
        } else {
            application.buzzmovieselector.anim.AnimationUtils.animateSunblind(holder, false);
//            AnimationUtils.animateSunblind(holder, false);
//            AnimationUtils.animate1(holder, false);
//            AnimationUtils.animate(holder, false);
        }
        mPreviousPosition = position;

        String urlThumnail = currentMovie.getImageUrl();
        loadImages(urlThumnail, holder);

    }

    /**
     * loads the image poster as thumbnail
     *
     * @param urlThumbnail is the url of the image
     * @oaram holder is the viewHolder of the content to be displayed
     */
    private void loadImages(String urlThumbnail, final ViewHolderBoxOffice holder) {
        if (!urlThumbnail.equals(null)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListMovies.size();
    }

    /**
     * This class represents a ViewHolderBoxOffice object
     *
     * @author Harmeet S. Bindra
     * @version 1.0
     */
    class ViewHolderBoxOffice extends RecyclerView.ViewHolder {

        ImageView movieThumbnail;
        TextView movieTitle;
        TextView movieReleaseDate;
        RatingBar movieAudienceScore;

        /**
         * Makes viewHolderBoxOffice object
         *
         * @param itemView is the number of items to display
         */
        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
            //title = mListMovies.get(getAdapterPosition()).getName();
        }
    }
}