package com.doobs.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;
import com.doobs.baking.util.BakingAppConstants;

import butterknife.BindView;

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

    // instance views
    private Button previousStepButton;
    private Button nextStepButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // build the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        if (savedInstanceState != null) {
            // get the data from the saved state
            this.recipeBean = savedInstanceState.getParcelable(BakingAppConstants.ActivityExtras.RECIPE_BEAN);
            this.recipeStepPosition = savedInstanceState.getInt(BakingAppConstants.ActivityExtras.RECIPE_STEP_POSITION);

        } else {
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
        }

        // get the buttons
        this.previousStepButton = this.findViewById(R.id.ingredient_list_previous_step_button);
        this.nextStepButton = this.findViewById(R.id.ingredient_list_next_step_button);

        // set previous button on click listener
        this.previousStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check that position is correct
                if (recipeStepPosition > 0) {
                    recipeStepPosition = recipeStepPosition - 1;
                    displayRecipeStep();

                } else {
                    Log.e(TAG, "Got error click on first previous button while at first step of list");
                }
            }
        });

        // set next button on click listener
        this.nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check that position is correct
                if (recipeStepPosition < (recipeBean.getStepBeanList().size() - 1)) {
                    recipeStepPosition = recipeStepPosition + 1;
                    displayRecipeStep();

                } else {
                    Log.e(TAG, "Got error click on first next button while at end step of list");
                }
            }
        });

        if (savedInstanceState ==  null) {
            // display the recipe step information
            this.displayRecipeStep();
        }
    }

    /**
     * to handle state changes
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the recicpe bean
        outState.putParcelable(BakingAppConstants.ActivityExtras.RECIPE_BEAN, this.recipeBean);

        // save the position
        outState.putInt(BakingAppConstants.ActivityExtras.RECIPE_STEP_POSITION, this.recipeStepPosition);
    }

    /**
     * display the correct fragment based on the recipe step bean
     *
     */
    protected void displayRecipeStep() {
        // get the recipe step bean
        RecipeStepBean recipeStepBean = this.recipeBean.getStepBeanList().get(this.recipeStepPosition);

        // display correct fragment based on the type of step
        if (BakingAppConstants.RecipeStepType.STEP.equals(recipeStepBean.getType())) {
            // display the recipe step fragment
            // create the fragment and display
            this.createStepFragment(recipeStepBean);

        } else {
            // display the ingredient list fragment
            this.createIngredientListFragment(recipeStepBean);
        }

        // update the button states
        if (this.recipeStepPosition < 1) {
            this.previousStepButton.setEnabled(false);

        } else if (recipeStepPosition >= (this.recipeBean.getStepBeanList().size() - 1)) {
            this.nextStepButton.setEnabled(false);

        } else {
            this.previousStepButton.setEnabled(true);
            this.nextStepButton.setEnabled(true);
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
