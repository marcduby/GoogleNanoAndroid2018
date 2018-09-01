package com.doobs.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;
import com.doobs.baking.util.BakingAppConstants;

/**
 * Activity class to dsiplay the recipe step details and video
 *
 * Created by mduby on 8/31/18.
 */

public class RecipeStepDetailActivity extends AppCompatActivity {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeStepBean recipeStepBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // get the intent parceable
        recipeStepBean = intent.getParcelableExtra(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN);
        if (recipeStepBean == null) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got null recipe step bean");
            closeOnError();
            return;
        }

        // create the fragment and display
        this.createStepFragment(this.recipeStepBean);
    }

    /**
     * create the fragment
     *
     * @param recipeStepBean
     */
    public void createStepFragment(RecipeStepBean recipeStepBean) {
        // create the fragment
        RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();

        // set the recipe step object on the fragment
        recipeStepDetailFragment.setRecipeStepBean(recipeStepBean);

        // get the fragment manager
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // bind the fragment to its container layout
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step_detail_fragment_container, recipeStepDetailFragment)
                .commit();
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
