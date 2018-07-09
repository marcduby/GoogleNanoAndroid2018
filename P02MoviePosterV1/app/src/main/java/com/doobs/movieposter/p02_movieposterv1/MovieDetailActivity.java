package com.doobs.movieposter.p02_movieposterv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    // static cosntants
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // text views
    private TextView nameTextView;
    private TextView plotSynopsisTextView;
    private TextView UserRatingTextView;
    private TextView releaseDateTextView;
    private ImageView movieImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // local variables
        MovieBean movieBean = null;

        // calll super
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // get the index of the movie form the list
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got default index: " + position);
            closeOnError();
            return;

        } else {
        }

        // get the movie
        movieBean = MovieUtils.getMoviesByRating().get(position);

        // check again just in case
        if (movieBean == null) {
            // Movie data unavailable
            Log.e(this.getClass().getName(), "Can't find movie bean with list index: " + position);
            closeOnError();
            return;

        } else {
            Log.i(this.getClass().getName(), "Loading movie bean with list index: " + position + " and name: " + movieBean.getName());
        }

        // populate the data
        this.populateUI(movieBean);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * main method to add movie details to the detail view
     *
     * @param movieBean                  the movie to display
     */
    private void populateUI(MovieBean movieBean) {
        // populate the image
        this.movieImageView = findViewById(R.id.image_iv);
        Picasso.get()
                .load(movieBean.getImageUrl())
                .resize(400, 486)
                .error(R.drawable.no_movie_picture)
                .into(this.movieImageView);


        // add the name
        nameTextView = findViewById(R.id.main_name_tv);
        if (movieBean.getName().isEmpty()) {
            TextView labelView = findViewById(R.id.name_label_tv);
            nameTextView.setVisibility(View.GONE);
            labelView.setVisibility(View.GONE);

        } else {
            nameTextView.setText(movieBean.getName());
        }

    }
}
