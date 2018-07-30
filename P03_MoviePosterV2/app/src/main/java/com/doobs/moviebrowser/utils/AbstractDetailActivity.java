package com.doobs.moviebrowser.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    /**
     * show toast message
     *
     * @param message
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
