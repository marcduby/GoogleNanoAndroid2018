package com.doobs.androidjokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doobs.baking.androidjokes.R;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
    }
}
