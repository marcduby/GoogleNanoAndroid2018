package com.doobs.movieposter.p02_movieposterv1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.movieposter.p02_movieposterv1.adapter.MoviesRecyclerAdapter;
import com.doobs.movieposter.p02_movieposterv1.adapter.MoviesReviewRecyclerAdapter;
import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;
import com.doobs.movieposter.p02_movieposterv1.bean.MovieReviewBean;
import com.doobs.movieposter.p02_movieposterv1.utils.AbstractDetailActivity;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieException;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieJsonParser;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Detail activity class to display the selected movie details
 *
 */
public class MovieReviewListActivity extends AppCompatActivity implements MoviesReviewRecyclerAdapter.MovieReviewItemClickListener {
    // static cosntants
    public static final String EXTRA_MOVIE_ID = "movie_id_key";

    // text views
    private MoviesReviewRecyclerAdapter moviesReviewRecyclerAdapter;
    private RecyclerView movieReviewRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // log
        Log.i(this.getClass().getName(), "In onCreate");

        // get the recycler view
        this.movieReviewRecyclerView = (RecyclerView) this.findViewById(R.id.movie_list_review_rv);
        this.movieReviewRecyclerView.setHasFixedSize(true);

        // set the layout manager for the recycler view
        LinearLayoutManager reviewListLayoutManager = new LinearLayoutManager(this);
        this.movieReviewRecyclerView.setLayoutManager(reviewListLayoutManager);

        // create the adapter
        this.moviesReviewRecyclerAdapter = new MoviesReviewRecyclerAdapter(this);

        // set the adapter on the recycler view
        this.movieReviewRecyclerView.setAdapter(this.moviesReviewRecyclerAdapter);

        // get the movie id from the intent
        Intent intent = this.getIntent();
        int movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);

        // call the
        // load the initial movie list
        this.callMovieReviewRestApi(movieId);
    }

    /**
     * calls the movie REST service and populate the adapter
     *
     * @param movieId
     */
    private void callMovieReviewRestApi(int movieId) {
        // load the initial movie list
        try {
            // get the URL
            URL movieReviewUrl = MovieUtils.getMoviewReviewURL(movieId, MovieUtils.MovieService.API_KEY);

            // log
            Log.i(this.getClass().getName(), "Starting asyc task wirth url: : " + movieReviewUrl.toString());

            // execute the async task
            new MainActivity.MovieLoadTask().execute(movieReviewUrl);

        } catch (MovieException exception) {
            Log.e(this.getClass().getName(), "Got error loading the movies: " + exception.getMessage());
            String error = "Error loading movies; please verify network connection";
            this.showToast(error);
        }
    }

    @Override
    public void onListItemClick(MovieReviewBean movieReviewBean) {
        // get the movie url

        // call an explicit intent to open the web page

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
     * @param movieReviewBean                  the movie to display
     */
    private void populateUI(MovieReviewBean movieReviewBean) {
        // instance vartiables
        TextView dataView = null;
        TextView labelView = null;
        String dataString = null;

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
     * load the list view with the movie reviews given a json input string
     *
     * @param jsonInputString
     */
    private void loadMovieReviewList(String jsonInputString) {
        // local variables
        List<MovieReviewBean> movieReviewBeanList = new ArrayList<MovieReviewBean>();

        // if json not null
        if (jsonInputString != null) {
            try {
                // get the movie list from the json result
                movieBeanList = MovieJsonParser.getMovieListFromJsonString(jsonInputString);

                // log
                Log.i(this.getClass().getName(), "Got movie list of size: " + movieBeanList.size());

            } catch (MovieException exception) {
                Log.e(this.getClass().getName(), "Got errr loading movies: " + exception.getMessage());
                String textToShow = "Error loading movies; please check network access";
                this.showToast(textToShow);
            }

        } else {
            if (isIntialLoad) {
                // show toast for error in network
                String textToShow = "Error loading movies; please check network access";
                this.showToast(textToShow);
            }
        }

        // get the movie adapter and set the movie list on it
        moviesRecyclerAdapter.setMovieBeanList(movieBeanList);
    }

    /**
     * async class to make the network call to load the movie reviews
     *
     */
    public class MovieReviewLoadTask extends AsyncTask<URL, Void, String> {

        @Override
        /**
         * background thread
         *
         */
        protected String doInBackground(URL... urls) {
            // local variables
            URL movieReviewListUrl = null;
            String responseString = null;

            try {
                // will get exception if no conection
                MovieUtils.testNetwork();

                // get the url
                movieReviewListUrl = urls[0];

                // log
                Log.i(this.getClass().getName(), "Calling REST service at url: " + movieReviewListUrl);

                // call the REST service
                try {
                    responseString = MovieUtils.getResponseFromHttpUrl(movieReviewListUrl);

                } catch (IOException exception) {
                    Log.e(this.getClass().getName(), "Got network error: " + exception.getMessage());
                }

            } catch (MovieException exception) {
                Log.e(this.getClass().getName(), "Got exception loading movie reviews: " + exception.getMessage());
            }

            // return
            return responseString;
        }

        @Override
        /**
         * called when background thread is done
         *
         */
        protected void onPostExecute(String result) {
            loadMovieReviewList(result);
        }
    }

}
