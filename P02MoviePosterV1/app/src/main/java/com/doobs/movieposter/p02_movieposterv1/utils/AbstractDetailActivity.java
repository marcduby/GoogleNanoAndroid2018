package com.doobs.movieposter.p02_movieposterv1.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.movieposter.p02_movieposterv1.R;
import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;
import com.squareup.picasso.Picasso;

/**
 * Detail activity class to display the selected movie details
 *
 */
public abstract class AbstractDetailActivity extends AppCompatActivity {

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
