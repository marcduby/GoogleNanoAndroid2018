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

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to manage the recipe recycler view
 *
 * Created by mduby on 8/25/18.
 */
public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    // instance variables
    private final String TAG = this.getClass().getName();
    private List<RecipeBean> recipeBeanList = new ArrayList<RecipeBean>();

    /**
     * binds a vew holder to data
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        // bind the data
        holder.bind(this.recipeBeanList.get(position));

        // log
        Log.i(TAG, "Bound recipe with id: " + this.recipeBeanList.get(position).getId());
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
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // local variables
        Context context = parent.getContext();
        RecipeViewHolder viewHolder = null;

        // get the layout id
        int layoutId = R.layout.list_recipe_item;

        // get the layout inflator and inflate
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);

        // create the view holder
        viewHolder = new RecipeViewHolder(view, this);

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
        return this.recipeBeanList.size();
    }

    /**
     * sets the adapter managed data
     *
     * @param recipeBeanList
     */
    public void setRecipeBeanList(List<RecipeBean> recipeBeanList) {
        // set the data
        this.recipeBeanList = recipeBeanList;

        // notify of change
        this.notifyDataSetChanged();
    }
}
