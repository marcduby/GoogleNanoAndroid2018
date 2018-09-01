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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.doobs.baking.adapter.RecipeRecyclerAdapter;
import com.doobs.baking.adapter.RecipeStepRecyclerAdapter;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.model.RecipeViewModel;
import com.doobs.baking.util.BakingAppConstants;

/**
 * Fragment class to display the recipe step and ingredient lists
 *
 * Created by mduby on 8/26/18.
 */

public class RecipeStepsFragment extends Fragment {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeBean recipeBean;
    private RecipeStepRecyclerAdapter recipeStepRecyclerAdapter;
    private RecyclerView recipeStepRecyclerView;
    private LinearLayoutManager recyclerViewLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the view
        // get the root view and inflate it
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        // get the recycler view
        this.recipeStepRecyclerView = rootView.findViewById(R.id.recipe_step_list_rv);
        this.recipeStepRecyclerView.setHasFixedSize(true);

        // set the manager for the recycler view
        this.recyclerViewLayoutManager = new LinearLayoutManager(rootView.getContext());
        this.recipeStepRecyclerView.setLayoutManager(this.recyclerViewLayoutManager);

        // create the adapter for the recycler view
        this.recipeStepRecyclerAdapter = new RecipeStepRecyclerAdapter();

        // set the adapter on the recycler view
        this.recipeStepRecyclerView.setAdapter(this.recipeStepRecyclerAdapter);

        Intent intent = this.getActivity().getIntent();

        // get the intent parceable
        recipeBean = intent.getParcelableExtra(BakingAppConstants.ActivityExtras.RECIPE_BEAN);
        if (recipeBean == null) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got null recipe bean");
        }

        // get the text view and how the name
        TextView textView = rootView.findViewById(R.id.recipe_name_fragment_tv);
        textView.setText(recipeBean.getName() + " Recipe");

        // set the stpe list on the adapter
        this.recipeStepRecyclerAdapter.setRecipeStepBeanList(recipeBean.getStepBeanList());

        // return the view
        return rootView;
    }


}
