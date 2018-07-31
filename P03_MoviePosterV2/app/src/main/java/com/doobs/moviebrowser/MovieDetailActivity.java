package com.doobs.moviebrowser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.moviebrowser.model.MovieBean;
import com.doobs.moviebrowser.model.MovieViewModel;
import com.doobs.moviebrowser.utils.MovieBrowserConstants;
import com.doobs.moviebrowser.utils.MovieException;
import com.doobs.moviebrowser.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Detail activity class to display the selected movie details
 *
 */
public class MovieDetailActivity extends AppCompatActivity {
    // static constants
    public static final String EXTRA_MOVIE = "movie_bean_key";

    // text views
    private TextView nameTextView;
    private TextView plotSynopsisTextView;
    private TextView UserRatingTextView;
    private TextView releaseDateTextView;
    private ImageView movieImageView;
    Button favoritesButton;
    Button reviewButton;

    // instance variables
    private MovieViewModel movieViewModel;

    // 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // local variables
        final MovieBean movieBean;

        // calll super
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // get the movie bean from the itent
        movieBean = intent.getParcelableExtra(EXTRA_MOVIE);
        if (movieBean == null) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got null movie bean");
            closeOnError();
            return;
        }

        // log
        Log.i(this.getClass().getName(), "Got movie with name: " + movieBean.getName());

        // get the movie view model
        this.movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        // set the observer on the Room database movie list
        this.movieViewModel.getDatabaseMovieList().observe(this, new Observer<List<MovieBean>>() {
            @Override
            public void onChanged(@Nullable List<MovieBean> movieBeans) {
                // change the favorites button if the data changed
                if (movieViewModel.getDatabaseMovieList().getValue().contains(movieBean)) {
                    setButtonIfMovieInDatabase(true);

                } else {
                    setButtonIfMovieInDatabase(false);
                }
            }
        });

        // get the buttons
        this.favoritesButton = (Button) findViewById(R.id.movie_detail_favorites_button);
        this.reviewButton = (Button) findViewById(R.id.moview_review_button);

        // get the favorites button and add the on click listener to it
        this.favoritesButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (movieViewModel.getDatabaseMovieList().getValue().contains(movieBean)) {
                    movieViewModel.delete(movieBean);

                    // log
                    Log.i(this.getClass().getName(), "Removing from DB movie: " + movieBean.getName());

                } else {
                    movieViewModel.insert(movieBean);

                    // log
                    Log.i(this.getClass().getName(), "Adding to DB movie: " + movieBean.getName());
                }
            }
        });

        // get the review button and add the on click listener to it
        reviewButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // create the intent
                Intent intent = new Intent(MovieDetailActivity.this, MovieReviewListActivity.class);

                // set the movie id as an extra
                intent.putExtra(MovieReviewListActivity.EXTRA_MOVIE_ID, movieBean.getId());

                // launch the new activity
                startActivity(intent);
            }
        });

        // populate the data
        this.populateUI(movieBean);
    }

    /**
     * method to chow toast if error
     *
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * sets the movie favorites button
     *
     * @param isSavedInFavorites
     */
    private void setButtonIfMovieInDatabase(boolean isSavedInFavorites) {
        if (isSavedInFavorites) {
            this.favoritesButton.setText(R.string.button_movie_remove_favorites);

        } else {
            this.favoritesButton.setText(R.string.button_movie_add_favorites);
        }
    }
    /**
     * main method to add movie details to the detail view
     *
     * @param movieBean                  the movie to display
     */
    private void populateUI(final MovieBean movieBean) {
        // instance vartiables
        TextView dataView = null;
        TextView labelView = null;
        String dataString = null;

        // set the favorites button message
        boolean isMovieSavedInFavorites = false;
        if (this.movieViewModel.getDatabaseMovieList().getValue() != null) {
            isMovieSavedInFavorites = this.movieViewModel.getDatabaseMovieList().getValue().contains(movieBean);
        }
        this.setButtonIfMovieInDatabase(isMovieSavedInFavorites);

        // populate the image
        this.movieImageView = findViewById(R.id.movie_detail_poster_iv);
        String imageUrl = MovieUtils.getImageUrlString(movieBean.getImageUrl(), false);

        // log
        Log.i(this.getClass().getName(), "Displaying image: " + imageUrl);

        // display the thumbnail poster image
        Picasso.get()
                .load(imageUrl)
                .into(movieImageView);


        // add the name
        dataView = findViewById(R.id.movie_detail_title_tv);
        labelView = findViewById(R.id.name_label_tv);
        dataString = movieBean.getName();
        this.bindDataToViews(labelView, dataView, dataString);

        // add the plot synopsis
        dataView = findViewById(R.id.movie_detail_synopsis_tv);
        labelView = findViewById(R.id.synopsis_label_tv);
        dataString = movieBean.getPlotSynopsis();
        this.bindDataToViews(labelView, dataView, dataString);

        // add the user rating
        dataView = findViewById(R.id.movie_detail_user_rating_tv);
        labelView = findViewById(R.id.user_rating_label_tv);
        dataString = (movieBean.getRating() == null ? null : movieBean.getRating().toString());
        this.bindDataToViews(labelView, dataView, dataString);

        // add the release data
        dataView = findViewById(R.id.movie_detail_release_date_tv);
        labelView = findViewById(R.id.release_date_label_tv);
        try {
            dataString = MovieUtils.formatDate(movieBean.getReleaseDate());

        } catch (MovieException exception) {
            dataString = movieBean.getReleaseDate();
        }
        this.bindDataToViews(labelView, dataView, dataString);

    }

    /**
     * apply data to the text views; hide them if there is no data
     *
     * @param labelView
     * @param dataView
     * @param dataString
     */
    private void bindDataToViews(TextView labelView, TextView dataView, String dataString) {
        if (dataString.isEmpty()) {
            dataView.setVisibility(View.GONE);
            labelView.setVisibility(View.GONE);

        } else {
            dataView.setText(dataString);
        }
    }
}
