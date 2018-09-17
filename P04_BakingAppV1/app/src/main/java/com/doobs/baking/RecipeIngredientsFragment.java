package com.doobs.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doobs.baking.adapter.RecipeIngredientRecyclerAdapter;
import com.doobs.baking.bean.RecipeStepBean;
import com.doobs.baking.util.BakingAppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    private LinearLayoutManager recyclerViewLayoutManager;
    private Unbinder unbinder;

    // instance views
    @BindView(R.id.recipe_ingredients_list_rv)
    protected RecyclerView recipeIngredientRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // if state was saved
        if (savedInstanceState != null) {
            this.recipeStepBean = savedInstanceState.getParcelable(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN);
        }

        // inflate the view
        // get the root view and inflate it
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

        // bind Butterknife views
        this.unbinder = ButterKnife.bind(this, rootView);

        // get the recycler view
//        this.recipeIngredientRecyclerView = rootView.findViewById(R.id.recipe_ingredients_list_rv);
        this.recipeIngredientRecyclerView.setHasFixedSize(true);

        // set the manager for the recycler view
        this.recyclerViewLayoutManager = new LinearLayoutManager(rootView.getContext());
        this.recipeIngredientRecyclerView.setLayoutManager(this.recyclerViewLayoutManager);

        // create the adapter for the recycler view
        this.recipeIngredientRecyclerAdapter = new RecipeIngredientRecyclerAdapter();

        // set the adapter on the recycler view
        this.recipeIngredientRecyclerView.setAdapter(this.recipeIngredientRecyclerAdapter);

//        Intent intent = this.getActivity().getIntent();
//
//        // get the intent parceable
//        this.recipeStepBean = intent.getParcelableExtra(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN);
//        if (this.recipeStepBean == null) {
//            // EXTRA_POSITION not found in intent
//            Log.e(this.getClass().getName(), "Got null recipe ingredient bean");
//        }

        // TODO - set the ingredients on the adapter
        this.recipeIngredientRecyclerAdapter.setRecipeStepBeanList(recipeStepBean.getIngredientBeanList());

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind Butterknife
        this.unbinder.unbind();
    }
}
