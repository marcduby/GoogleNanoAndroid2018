package com.doobs.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doobs.baking.adapter.RecipeStepRecyclerAdapter;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;
import com.doobs.baking.util.BakingAppConstants;

import java.util.ArrayList;

/**
 * Fragment class to display the recipe step and ingredient lists
 *
 * Created by mduby on 8/26/18.
 */

public class RecipeStepDetailFragment extends Fragment {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeStepBean recipeStepBean;

    /**
     * default constructor
     *
     */
    public RecipeStepDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // if state was saved
        if (savedInstanceState != null) {
            this.recipeStepBean = savedInstanceState.getParcelable(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN);
        }

        // inflate the view
        // get the root view and inflate it
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        // get the text view and how the name
        TextView nameTextView = rootView.findViewById(R.id.recipe_step_detail_name_fragment_tv);
        nameTextView.setText(recipeStepBean.getShortDescription());

        // get the text view and how the description
        TextView descriptionTextView = rootView.findViewById(R.id.recipe_step_detail_description_fragment_tv);
        descriptionTextView.setText(recipeStepBean.getDescription());

        // return the view
        return rootView;
    }

    /**
     * for state changes
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN, this.recipeStepBean);
    }

    public void setRecipeStepBean(RecipeStepBean recipeStepBean) {
        this.recipeStepBean = recipeStepBean;
    }
}
