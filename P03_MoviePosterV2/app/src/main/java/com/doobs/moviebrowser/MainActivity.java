package com.doobs.moviebrowser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.moviebrowser.adapter.MoviesRecyclerAdapter;
import com.doobs.moviebrowser.model.MovieBean;
import com.doobs.moviebrowser.model.MovieViewModel;
import com.doobs.moviebrowser.utils.MovieBrowserConstants;
import com.doobs.moviebrowser.utils.MovieException;
import com.doobs.moviebrowser.utils.MovieJsonParser;
import com.doobs.moviebrowser.utils.MovieUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Main activity of the movie app
 *
 */
public class MainActivity extends AppCompatActivity implements MoviesRecyclerAdapter.MovieItemClickListener {
    // instance variables
    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private RecyclerView movieRecyclerView;
    private TextView movieListOptionTextView;
    private MovieViewModel movieViewModel;

    // constants
    private final int numberOfColumns = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // log
        Log.i(this.getClass().getName(), "In onCreate");

        // get the movie option text view
        this.movieListOptionTextView = (TextView)this.findViewById(R.id.movie_list_sorting_tv);

        // get the recycler view
        this.movieRecyclerView = (RecyclerView) this.findViewById(R.id.movies_rv);
        this.movieRecyclerView.setHasFixedSize(true);

        // set the layout manager for the recycler view
        GridLayoutManager movieListLayoutManager = new GridLayoutManager(this, this.numberOfColumns);
        this.movieRecyclerView.setLayoutManager(movieListLayoutManager);

        // create the adapter
        this.moviesRecyclerAdapter = new MoviesRecyclerAdapter(this);

        // set the adapter on the recycler view
        this.movieRecyclerView.setAdapter(this.moviesRecyclerAdapter);

        // get the movie view model
        this.movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        // set the observer on the mutable live data movie list used for display
        this.movieViewModel.getDisplayMovieList().observe(this, new Observer<List<MovieBean>>() {
            @Override
            public void onChanged(@Nullable List<MovieBean> movieBeanList) {
                // set the data on the adapter
                moviesRecyclerAdapter.setMovieBeanList(movieBeanList);
            }
        });

        // set the observer on the Room database movie list
        this.movieViewModel.getDatabaseMovieList().observe(this, new Observer<List<MovieBean>>() {
            @Override
            public void onChanged(@Nullable List<MovieBean> movieBeans) {
                // only update view model display list if the setting option is the favrites option
                if (MovieBrowserConstants.MovieListSource.STORED_FAVORITES.equals(movieViewModel.getDisplayOptionSetting().getValue())) {
                    movieViewModel.setDisplayMovieList(movieBeans);
                }
            }
        });

        // set the observer for the display option setting
        this.movieViewModel.getDisplayOptionSetting().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String option) {
                // load the movies
                callMovieRestApi(option);

                // set the text view
                if (MovieBrowserConstants.MovieListSource.STORED_FAVORITES.equals(option)) {
                    movieListOptionTextView.setText(R.string.movie_list_favorites_message);

                } else if (MovieBrowserConstants.MovieListSource.BEST_RATED.equals(option)) {
                    movieListOptionTextView.setText(R.string.movie_list_rating_message);

                } else {
                    movieListOptionTextView.setText(R.string.movie_list_popular_message);
                }

            }
        });

        // set the default movie list option
        this.movieViewModel.setDisplayOptionSetting(MovieBrowserConstants.MovieListSource.MOST_POPULAR);

        // load the initial movie list
//        this.callMovieRestApi(MovieBrowserConstants.MovieListSource.MOST_POPULAR);
    }

    /**
     * calls the movie REST service and populate the adapter
     *
     * @param sourceId
     */
    private void callMovieRestApi(String sourceId) {
        // local variables
        boolean isMostPopularSort = false;

        // log
        Log.i(this.getClass().getName(), "Loading movies with option: " + sourceId);

        // check if should load favorites
        if (MovieBrowserConstants.MovieListSource.STORED_FAVORITES.equals(sourceId)) {
            // set the movie option text view
//            this.movieListOptionTextView.setText(R.string.movie_list_favorites_message);

        } else {
            if (MovieBrowserConstants.MovieListSource.MOST_POPULAR.equals(sourceId)) {
                isMostPopularSort = true;

            } else {
                isMostPopularSort = false;
            }

            // load the initial movie list
            try {
                // get the URL
                URL movieUrl = MovieUtils.getMovieListSortedUri(isMostPopularSort, MovieUtils.MovieService.API_KEY);

                // log
                Log.i(this.getClass().getName(), "Starting asyc task with url: : " + movieUrl.toString());

                // execute the async task
                new MovieLoadTask().execute(movieUrl);

                // set the movie option text view
//                if (isMostPopularSort) {
//                    this.movieListOptionTextView.setText(R.string.movie_list_popular_message);
//
//                } else {
//                    this.movieListOptionTextView.setText(R.string.movie_list_rating_message);
//                }

            } catch (MovieException exception) {
                Log.e(this.getClass().getName(), "Got error loading the movies: " + exception.getMessage());
                String error = "Error loading movies; please verify network connection";
                this.showToast(error);
            }
        }
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

            // set the display list option
            this.movieViewModel.setDisplayOptionSetting(MovieBrowserConstants.MovieListSource.BEST_RATED);

//            // load the movies
//            this.callMovieRestApi(MovieBrowserConstants.MovieListSource.BEST_RATED);

            // return
            return true;

        } else if (itemThatWasClickedId == R.id.action_order_by_most_popular) {
            Context context = MainActivity.this;
            String textToShow = "List sorted by most popular";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

            // set the display list option
            this.movieViewModel.setDisplayOptionSetting(MovieBrowserConstants.MovieListSource.MOST_POPULAR);

//            // load the movies
//            this.callMovieRestApi(MovieBrowserConstants.MovieListSource.MOST_POPULAR);

            // return
            return true;

        } else if (itemThatWasClickedId == R.id.action_order_my_favorites) {
            Context context = MainActivity.this;
            String textToShow = "Loading my favorites";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

            // set the display list option
            this.movieViewModel.setDisplayOptionSetting(MovieBrowserConstants.MovieListSource.STORED_FAVORITES);

//            // load the movies
//            this.callMovieRestApi(MovieBrowserConstants.MovieListSource.STORED_FAVORITES);

            // return
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * click handler to launch the movie detail activity
     *
     * @param movieBean
     */
    public void onListItemClick(MovieBean movieBean) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieBean);
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

        // get the view model and set the new list on it
        this.movieViewModel.setDisplayMovieList(movieBeanList);
        // get the movie adapter and set the movie list on it
//        moviesRecyclerAdapter.setMovieBeanList(movieBeanList);
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

            try {
                // will get exception if no conection
                MovieUtils.testNetwork();

                // get the url
                movieListUrl = urls[0];

                // log
                Log.i(this.getClass().getName(), "Calling REST service at url: " + movieListUrl);

                // call the REST service
                try {
                    responseString = MovieUtils.getResponseFromHttpUrl(movieListUrl);

                } catch (IOException exception) {
                    Log.e(this.getClass().getName(), "Got network error: " + exception.getMessage());
                }

            } catch (MovieException exception) {
                Log.e(this.getClass().getName(), "Got exception loading movies: " + exception.getMessage());
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
            loadMovieList(result, true);
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
