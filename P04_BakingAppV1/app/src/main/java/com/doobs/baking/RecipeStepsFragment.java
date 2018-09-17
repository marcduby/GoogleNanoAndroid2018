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
import com.doobs.baking.util.BakingAppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    private LinearLayoutManager recyclerViewLayoutManager;
    private Unbinder unbinder;

    // instance views
    @BindView(R.id.recipe_step_list_rv)
    protected RecyclerView recipeStepRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // if state was saved
        if (savedInstanceState != null) {
            this.recipeBean = savedInstanceState.getParcelable(BakingAppConstants.ActivityExtras.RECIPE_BEAN);

        } else {
            Intent intent = this.getActivity().getIntent();

            // get the intent parceable
            this.recipeBean = intent.getParcelableExtra(BakingAppConstants.ActivityExtras.RECIPE_BEAN);
            if (recipeBean == null) {
                // EXTRA_POSITION not found in intent
                Log.e(this.getClass().getName(), "Got null recipe bean");
            }
        }

        // inflate the view
        // get the root view and inflate it
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        // bind Butterknife views
        this.unbinder = ButterKnife.bind(this, rootView);

        // get the recycler view
//        this.recipeStepRecyclerView = rootView.findViewById(R.id.recipe_step_list_rv);
        this.recipeStepRecyclerView.setHasFixedSize(true);

        // set the manager for the recycler view
        this.recyclerViewLayoutManager = new LinearLayoutManager(rootView.getContext());
        this.recipeStepRecyclerView.setLayoutManager(this.recyclerViewLayoutManager);

        // create the adapter for the recycler view
        this.recipeStepRecyclerAdapter = new RecipeStepRecyclerAdapter((RecipeStepRecyclerAdapter.RecipeStepListener)this.getActivity());

        // set the adapter on the recycler view
        this.recipeStepRecyclerView.setAdapter(this.recipeStepRecyclerAdapter);

        // get the text view and how the name
        TextView textView = rootView.findViewById(R.id.recipe_name_fragment_tv);
        textView.setText(recipeBean.getName() + " Recipe");

        // set the stpe list on the adapter
        this.recipeStepRecyclerAdapter.setRecipeStepBeanList(recipeBean.getStepBeanList());

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
        outState.putParcelable(BakingAppConstants.ActivityExtras.RECIPE_BEAN, this.recipeBean);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind Butterknife
        this.unbinder.unbind();
    }
}
