package com.doobs.movieposter.p02_movieposterv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.doobs.movieposter.p02_movieposterv1.adapter.MovieAdapter;
import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(this.getClass().getName(), "In onCreate");

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MovieAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // toast for debugging
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();

                // launch the movie detail activity
                launchMovieDetailActivity(position);
            }
        });
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
