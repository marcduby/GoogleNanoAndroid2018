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
    public static final String EXTRA_MOVIE = "movie_bean_key";

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
     * main method to add movie details to the detail view
     *
     * @param movieBean                  the movie to display
     */
    private void populateUI(MovieBean movieBean) {
        // populate the image
        this.movieImageView = findViewById(R.id.movie_detail_poster_iv);
        String imageUrl = MovieUtils.getImageUrlString(movieBean.getImageUrl(), false);

        // log
        Log.i(this.getClass().getName(), "Displaying image: " + imageUrl);

        // display the thumbnail poster image
        Picasso.get()
                .load(imageUrl)
//                .fit()
                .into(movieImageView);


        // add the name
        nameTextView = findViewById(R.id.movie_detail_title_tv);
        if (movieBean.getName().isEmpty()) {
//            TextView labelView = findViewById(R.id.name_label_tv);
            nameTextView.setVisibility(View.GONE);
//            labelView.setVisibility(View.GONE);

        } else {
            nameTextView.setText(movieBean.getName());
        }

    }
}
