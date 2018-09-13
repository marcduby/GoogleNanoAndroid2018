package com.doobs.baking;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.doobs.baking.adapter.RecipeRecyclerAdapter;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.model.RecipeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    // instance variables
    // log tag
    private final String TAG = this.getClass().getName();

    // views
    @BindView(R.id.recipe_list_rv)
    protected RecyclerView recipeRecyclerView;

    // variables
    private RecipeViewModel recipeViewModel;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private LinearLayoutManager recyclerViewLinearLayoutManager;
    private GridLayoutManager recyclerViewGridLayoutManager;
    private final Integer tablet_number_columns = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set Butterknife bindings
        ButterKnife.bind(this);

        // get the recycler view
//        this.recipeRecyclerView = this.findViewById(R.id.recipe_list_rv);
        this.recipeRecyclerView.setHasFixedSize(true);

        // set the manager for the recycler view
        if (this.findViewById(R.id.activity_main_layout_600dp) == null) {
            this.recyclerViewLinearLayoutManager = new LinearLayoutManager(this);
            this.recipeRecyclerView.setLayoutManager(this.recyclerViewLinearLayoutManager);

        } else {
            this.recyclerViewGridLayoutManager = new GridLayoutManager(this, this.tablet_number_columns);
            this.recipeRecyclerView.setLayoutManager(this.recyclerViewGridLayoutManager);
        }

        // create the adapter for the recycler view
        this.recipeRecyclerAdapter = new RecipeRecyclerAdapter();

        // set the adapter on the recycler view
        this.recipeRecyclerView.setAdapter(this.recipeRecyclerAdapter);

        // get the view model
        this.recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        // set the observer on the recipe view model recipe list
        this.recipeViewModel.getRecipeBeanList().observe(this, new Observer<List<RecipeBean>>() {
            @Override
            public void onChanged(@Nullable List<RecipeBean> recipeBeans) {
                // TODO - set the adapter data
                recipeRecyclerAdapter.setRecipeBeanList(recipeBeans);
            }
        });

        // load the data
        this.recipeViewModel.loadRecipeData();

    }
}
