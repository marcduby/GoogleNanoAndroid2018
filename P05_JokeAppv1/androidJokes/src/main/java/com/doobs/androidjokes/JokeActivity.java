package com.doobs.androidjokes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.doobs.androidjokes.utils.JokeLibraryConstants;
import com.doobs.baking.androidjokes.R;

/**
 * Activity class to display jokes
 *
 */
public class JokeActivity extends AppCompatActivity {
    // instance variables
    private final String TAG = this.getClass().getName();
    private String jokeString;
    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        // get the text view
        this.jokeTextView = this.findViewById(R.id.joke_text_view);

        if (savedInstanceState != null) {
            this.jokeString = savedInstanceState.getString(JokeLibraryConstants.ExtraKeys.JOKE_KEY);

        } else {
            Intent intent = this.getIntent();

            if (intent != null) {
                this.jokeString = intent.getStringExtra(JokeLibraryConstants.ExtraKeys.JOKE_KEY);
            }
        }

        // if joke is not null, display
        if (this.jokeString != null) {
            this.jokeTextView.setText(this.jokeString);
        }
    }
}
