package com.doobs.movieposter.p02_movieposterv1;

import android.content.Context;
import android.content.Intent;
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
import com.doobs.movieposter.p02_movieposterv1.utils.MovieUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(this.getClass().getName(), "In onCreate");

        GridView gridview = (GridView) findViewById(R.id.gridview);

        // create the adapter with the movie data
        List<MovieBean> movieBeanList = MovieUtils.getMoviesByRating();
        MovieAdapter movieAdapter = new MovieAdapter(this, movieBeanList);

        // set the adapter
        gridview.setAdapter(movieAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // toast for debugging
//                Toast.makeText(MainActivity.this, "" + position,
//                        Toast.LENGTH_SHORT).show();

                // launch the movie detail activity
                launchMovieDetailActivity(position);
            }
        });
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
}
