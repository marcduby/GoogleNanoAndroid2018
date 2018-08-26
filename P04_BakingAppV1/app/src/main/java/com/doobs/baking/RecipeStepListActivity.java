package com.doobs.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.util.BakingAppConstants;

public class RecipeStepListActivity extends AppCompatActivity {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeBean recipeBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_list);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // get the intent parceable
        recipeBean = intent.getParcelableExtra(BakingAppConstants.ActivityExtras.RECIPE_BEAN);
        if (recipeBean == null) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got null recipe bean");
            closeOnError();
            return;
        }

        // get the text view and how the name
        TextView textView = this.findViewById(R.id.recipe_name_tv);
        textView.setText(recipeBean.getName());

    }

    /**
     * method to show toast if error
     *
     */
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
