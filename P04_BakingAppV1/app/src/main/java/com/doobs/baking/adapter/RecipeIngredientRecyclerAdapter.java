package com.doobs.baking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doobs.baking.R;
import com.doobs.baking.bean.IngredientBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to manage the recipe ingredient recycler view
 *
 * Created by mduby on 8/25/18.
 */
public class RecipeIngredientRecyclerAdapter extends RecyclerView.Adapter<RecipeIngredientViewHolder> {
    // instance variables
    private final String TAG = this.getClass().getName();
    private List<IngredientBean> ingredientBeanList = new ArrayList<IngredientBean>();

    /**
     * default constructor
     *
     */
    public RecipeIngredientRecyclerAdapter() {
        super();
    }

    /**
     * binds a vew holder to data
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder holder, int position) {
        // bind the data
        holder.bind(this.ingredientBeanList.get(position));

        // log
        Log.i(TAG, "Bound recipe ingredient with name: " + this.ingredientBeanList.get(position).getName());
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
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // local variables
        Context context = parent.getContext();
        RecipeIngredientViewHolder viewHolder = null;

        // get the layout id
        int layoutId = R.layout.list_ingredient_item;

        // get the layout inflator and inflate
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);

        // create the view holder
        viewHolder = new RecipeIngredientViewHolder(view, this);

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
        return this.ingredientBeanList.size();
    }

    /**
     * sets the adapter managed data
     *
     * @param beanList
     */
    public void setRecipeStepBeanList(List<IngredientBean> beanList) {
        // set the data
        this.ingredientBeanList = beanList;

        // notify of change
        this.notifyDataSetChanged();
    }
}
