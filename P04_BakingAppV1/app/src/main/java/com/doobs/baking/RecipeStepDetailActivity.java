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
    private RecipeBean recipeBean;
    private int recipeStepPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // instance variables
        RecipeStepBean recipeStepBean = null;
        int position = 0;

        // build the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }


        // get the recipe step at the position
        this.recipeBean = intent.getParcelableExtra(BakingAppConstants.ActivityExtras.RECIPE_BEAN);
        if (this.recipeBean == null) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got null recipe step bean");
            closeOnError();
            return;
        }

        // get the intent parceable
        if (!intent.getExtras().containsKey(BakingAppConstants.ActivityExtras.RECIPE_STEP_POSITION)) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got null recipe step bean position");
            closeOnError();
            return;

        } else {
            this.recipeStepPosition = intent.getIntExtra(BakingAppConstants.ActivityExtras.RECIPE_STEP_POSITION, 0);
        }

        // get the recipe step bean
        recipeStepBean = this.recipeBean.getStepBeanList().get(this.recipeStepPosition);

        // display the recipe step information
        if (BakingAppConstants.RecipeStepType.STEP.equals(recipeStepBean.getType())) {
            // display the recipe step fragment
            // create the fragment and display
            this.createStepFragment(recipeStepBean);

        } else {
            // display the ingredient list fragment
            this.createIngredientListFragment(recipeStepBean);
        }
    }

    /**
     * create the recipe step fragment
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
     * create the ingredient list fragment
     *
     * @param recipeStepBean
     */
    // TODO - see if these two functions can be consolidated
    public void createIngredientListFragment(RecipeStepBean recipeStepBean) {
        // create the fragment
        RecipeIngredientsFragment recipeIngredientsFragment = new RecipeIngredientsFragment();

        // set the recipe step on it
        recipeIngredientsFragment.setRecipeStepBean(recipeStepBean);

        // get the fragment manager
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // bind the fragment
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step_detail_fragment_container, recipeIngredientsFragment)
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
