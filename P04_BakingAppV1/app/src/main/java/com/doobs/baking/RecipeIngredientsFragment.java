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

import com.doobs.baking.adapter.RecipeIngredientRecyclerAdapter;
import com.doobs.baking.adapter.RecipeStepRecyclerAdapter;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;
import com.doobs.baking.util.BakingAppConstants;

/**
 * Fragment class to display the recipe step and ingredient lists
 *
 * Created by mduby on 8/26/18.
 */

public class RecipeIngredientsFragment extends Fragment {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeStepBean recipeStepBean;
    private RecipeIngredientRecyclerAdapter recipeIngredientRecyclerAdapter;
    private RecyclerView recipeIngredientRecyclerView;
    private LinearLayoutManager recyclerViewLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the view
        // get the root view and inflate it
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

        // get the recycler view
        this.recipeIngredientRecyclerView = rootView.findViewById(R.id.recipe_ingredients_list_rv);
        this.recipeIngredientRecyclerView.setHasFixedSize(true);

        // set the manager for the recycler view
        this.recyclerViewLayoutManager = new LinearLayoutManager(rootView.getContext());
        this.recipeIngredientRecyclerView.setLayoutManager(this.recyclerViewLayoutManager);

        // create the adapter for the recycler view
        this.recipeIngredientRecyclerAdapter = new RecipeIngredientRecyclerAdapter();

        // set the adapter on the recycler view
        this.recipeIngredientRecyclerView.setAdapter(this.recipeIngredientRecyclerAdapter);

        Intent intent = this.getActivity().getIntent();

        // get the intent parceable
        this.recipeStepBean = intent.getParcelableExtra(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN);
        if (this.recipeStepBean == null) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got null recipe ingredient bean");
        }

        // TODO - set the ingredients on the adapter
        this.recipeIngredientRecyclerAdapter.setRecipeStepBeanList(recipeStepBean.getIngredientBeanList());

        // return the view
        return rootView;
    }

    public void setRecipeStepBean(RecipeStepBean recipeStepBean) {
        this.recipeStepBean = recipeStepBean;
    }
}
