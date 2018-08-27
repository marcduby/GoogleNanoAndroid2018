package com.doobs.baking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doobs.baking.R;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;

import java.util.List;

/**
 * Adapter class to manage the recipe step recycler view
 *
 * Created by mduby on 8/25/18.
 */
public class RecipeStepRecyclerAdapter extends RecyclerView.Adapter<RecipeStepViewHolder> {
    // instance variables
    private final String TAG = this.getClass().getName();
    private List<RecipeStepBean> recipeStepBeanList;

    /**
     * binds a vew holder to data
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, int position) {
        // bind the data
        holder.bind(this.recipeStepBeanList.get(position));

        // log
        Log.i(TAG, "Bound recipe step with id: " + this.recipeStepBeanList.get(position).getId());
    }

    /**
     * creates a new view holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // local variables
        Context context = parent.getContext();
        RecipeStepViewHolder viewHolder = null;

        // get the layout id
        int layoutId = R.layout.list_recipe_item;

        // get the layout inflator and inflate
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);

        // create the view holder
        viewHolder = new RecipeStepViewHolder(view, this);

        // return
        return viewHolder;
    }

    /**
     * return the size of the data list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return this.recipeStepBeanList.size();
    }

    /**
     * sets the adapter managed data
     *
     * @param beanList
     */
    public void setRecipeStepBeanList(List<RecipeStepBean> beanList) {
        // set the data
        this.recipeStepBeanList = beanList;

        // notify of change
        this.notifyDataSetChanged();
    }
}
