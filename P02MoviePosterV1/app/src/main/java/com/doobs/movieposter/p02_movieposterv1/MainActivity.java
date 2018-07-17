package com.doobs.movieposter.p02_movieposterv1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.doobs.movieposter.p02_movieposterv1.adapter.MovieAdapter;
import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieConstants;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieException;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieJsonParser;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * main activity of the movie app
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(this.getClass().getName(), "In onCreate");

        GridView gridview = (GridView) findViewById(R.id.gridview);

        // load the initial movie list
        try {
            // get the URL
            URL movieUrl = MovieUtils.getMovieListSortedUri(true, MovieConstants.MOVIE_DB_API_KEY);

            // execute the async task
            new MovieLoadTask().execute(movieUrl);

        } catch (MovieException exception) {
            Log.e(this.getClass().getName(), "Got error loading the movies: " + exception.getMessage());
            String error = "Error loading movies; please verify network connection";
            this.showToast(error);
        }

        // create the adapter with the movie data
//        List<MovieBean> movieBeanList = MovieUtils.getMoviesByRating();
//        MovieAdapter movieAdapter = new MovieAdapter(this, movieBeanList);

        // set the adapter
//        gridview.setAdapter(movieAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // toast for debugging
//                Toast.makeText(MainActivity.this, "" + position,
//                        Toast.LENGTH_SHORT).show();

                // launch the movie detail activity
                launchMovieDetailActivity(position);
            }
        });

        // test load of movies
//        this.loadMovieList(null, false);
    }

    @Override
    /**
     * inflate the sort menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the main activity menu
        getMenuInflater().inflate(R.menu.main, menu);

        // return true for success
        return true;
    }

    @Override
    /**
     * handle the menu selection
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of the menu item clicked
        int itemThatWasClickedId = item.getItemId();

        // change the ordering if need be
        if (itemThatWasClickedId == R.id.action_order_by_user_rating) {
            Context context = MainActivity.this;
            String textToShow = "List sorted by user rating";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemThatWasClickedId == R.id.action_order_by_most_popular) {
            Context context = MainActivity.this;
            String textToShow = "List sorted by most popular";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * launch the movie detail activity
     *
     * @param position
     */
    private void launchMovieDetailActivity(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }

    /**
     * load the grid view with the movies given a json input string
     *
     * @param jsonInputString
     * @param isIntialLoad
     */
    private void loadMovieList(String jsonInputString, boolean isIntialLoad) {
        // local variables
        List<MovieBean> movieBeanList = new ArrayList<MovieBean>();

        // if json not null
        if (jsonInputString != null) {
            try {
                // get the movie list from the json result
                movieBeanList = MovieJsonParser.getMovieListFromJsonString(jsonInputString);

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

        // create a new adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movieBeanList);

        // get the grid view
        GridView gridview = (GridView) findViewById(R.id.gridview);

        // apply the adapter to the grid view
        gridview.setAdapter(movieAdapter);
    }

    /**
     * async class to make the network call
     *
     */
    public class MovieLoadTask extends AsyncTask<URL, Void, String> {

        @Override
        /**
         * background thread
         *
         */
        protected String doInBackground(URL... urls) {
            // local variables
            URL movieListUrl = null;
            String responseString = null;

            // get the url
            movieListUrl = urls[0];

            // call the REST service
            try {
                responseString = MovieUtils.getResponseFromHttpUrl(movieListUrl);

            } catch (IOException exception) {
                Log.e(this.getClass().getName(), "Got network error: " + exception.getMessage());
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
            loadMovieList(result, false);
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
