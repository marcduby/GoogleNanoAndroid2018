package com.doobs.moviebrowser;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.moviebrowser.adapter.MoviesReviewRecyclerAdapter;
import com.doobs.moviebrowser.adapter.MoviesTrailerRecyclerAdapter;
import com.doobs.moviebrowser.model.MovieReviewBean;
import com.doobs.moviebrowser.model.MovieTrailerBean;
import com.doobs.moviebrowser.utils.MovieException;
import com.doobs.moviebrowser.utils.MovieJsonParser;
import com.doobs.moviebrowser.utils.MovieUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Detail activity class to display the selected movie details
 *
 */
public class MovieTrailerListActivity extends AppCompatActivity implements MoviesTrailerRecyclerAdapter.MovieTrailerItemClickListener {
    // static constants
    public static final String EXTRA_MOVIE_ID = "movie_id_key";

    // text views
    private MoviesTrailerRecyclerAdapter moviesTrailerRecyclerAdapter;
    private RecyclerView movieTrailerRecyclerView;
    private TextView movieTrailerCountTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_list);

        // log
        Log.i(this.getClass().getName(), "In onCreate");

        // get the trailer count text view
        this.movieTrailerCountTextView = (TextView)this.findViewById(R.id.movie_trailer_list_count_tv);

        // get the recycler view
        this.movieTrailerRecyclerView = (RecyclerView) this.findViewById(R.id.movie_trailer_list_rv);
        this.movieTrailerRecyclerView.setHasFixedSize(true);

        // set the layout manager for the recycler view
        LinearLayoutManager reviewListLayoutManager = new LinearLayoutManager(this);
        this.movieTrailerRecyclerView.setLayoutManager(reviewListLayoutManager);

        // create the adapter
        this.moviesTrailerRecyclerAdapter = new MoviesTrailerRecyclerAdapter(this);

        // set the adapter on the recycler view
        this.movieTrailerRecyclerView.setAdapter(this.moviesTrailerRecyclerAdapter);

        // get the movie id from the intent
        Intent intent = this.getIntent();
        int movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);

        // log
        Log.i(this.getClass().getName(), "Got movie id: " + movieId);

        // call the
        // load the initial movie list
        this.callMovieTrailerRestApi(movieId);
    }

    /**
     * calls the movie REST service and populate the adapter
     *
     * @param movieId
     */
    private void callMovieTrailerRestApi(int movieId) {
        // load the initial movie list
        try {
            // get the URL
            URL movieTrailerUrl = MovieUtils.getMovieTrailerURL(movieId, MovieUtils.MovieService.API_KEY);

            // log
            Log.i(this.getClass().getName(), "Starting asyc task wirth url: : " + movieTrailerUrl.toString());

            // execute the async task
            new MovieTrailerListActivity.MovieTrailerLoadTask().execute(movieTrailerUrl);

        } catch (MovieException exception) {
            Log.e(this.getClass().getName(), "Got error loading the movies: " + exception.getMessage());
            String error = "Error loading movies; please verify network connection";
            this.showToast(error);
        }
    }

    @Override
    /**
     * will open the movie trailer url
     *
     */
    public void onListItemClick(MovieTrailerBean movieTrailerBean) {
        // get the movie trailer source
        String source = movieTrailerBean.getSource();

        // call an explicit intent to open the youtube app
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MovieUtils.getYouTubeApplicationUrl(source)));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MovieUtils.getYouTubeWebUrl(source)));

        // open youtube video
        try {
            this.startActivity(appIntent);

        } catch (ActivityNotFoundException ex) {
            this.startActivity(webIntent);
        }
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
     * load the list view with the movie reviews given a json input string
     *
     * @param jsonInputString
     */
    private void loadMovieTrailerList(String jsonInputString) {
        // local variables
        List<MovieTrailerBean> movieTrailerBeanList = new ArrayList<MovieTrailerBean>();

        // if json not null
        if (jsonInputString != null) {
            try {
                // get the movie list from the json result
                movieTrailerBeanList = MovieJsonParser.getMovieTrailerListFromJsonString(jsonInputString);

                // log
                Log.i(this.getClass().getName(), "Got movie trailer list of size: " + movieTrailerBeanList.size());

            } catch (MovieException exception) {
                Log.e(this.getClass().getName(), "Got error loading movie trailers: " + exception.getMessage());
                String textToShow = "Error loading movie trailers; please check network access";
                this.showToast(textToShow);
            }

        } else {
            // show toast for error in network
            String textToShow = "Error loading movie trailers; please check network access";
            this.showToast(textToShow);
        }

        // get the movie adapter and set the movie list on it
        moviesTrailerRecyclerAdapter.setMovieTrailerBeanList(movieTrailerBeanList);

        // set the count text view
        if (movieTrailerBeanList.size() > 0) {
            this.movieTrailerCountTextView.setText(movieTrailerBeanList.size() + " movie trailer" + (movieTrailerBeanList.size() > 1 ? "s" : ""));

        } else {
            this.movieTrailerCountTextView.setText("No movie trailers found");
        }
    }

    /**
     * async class to make the network call to load the movie reviews
     *
     */
    public class MovieTrailerLoadTask extends AsyncTask<URL, Void, String> {

        @Override
        /**
         * background thread
         *
         */
        protected String doInBackground(URL... urls) {
            // local variables
            URL movieTrailerListUrl = null;
            String responseString = null;

            try {
                // will get exception if no conection
                MovieUtils.testNetwork();

                // get the url
                movieTrailerListUrl = urls[0];

                // log
                Log.i(this.getClass().getName(), "Calling REST service at url: " + movieTrailerListUrl);

                // call the REST service
                try {
                    responseString = MovieUtils.getResponseFromHttpUrl(movieTrailerListUrl);

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
            loadMovieTrailerList(result);
        }
    }

    /**
     * show toast message
     *
     * @param message
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
