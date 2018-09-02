package com.doobs.baking.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doobs.baking.R;
import com.doobs.baking.bean.IngredientBean;

/**
 * View holder class for the recipe ingredient list items
 *
 * Created by mduby on 8/25/18.
 */
public class RecipeIngredientViewHolder extends RecyclerView.ViewHolder {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeIngredientRecyclerAdapter parent;
    private TextView nameTextView;
    private TextView measurementTextView;
    private IngredientBean recipeIngredientBean;

    /**
     * default constructor
     *
     * @param view
     * @param adapter
     */
    public RecipeIngredientViewHolder(View view, RecipeIngredientRecyclerAdapter adapter) {
        super(view);

        // set the instance variables
        this.parent = adapter;
        this.nameTextView = view.findViewById(R.id.recipe_ingredient_item_name_tv);
        this.measurementTextView = view.findViewById(R.id.recipe_ingredient_item_measurement_tv);
    }

    /**
     * bind the item data to the view holder
     *
     * @param bean
     */
    protected void bind(IngredientBean bean) {
        // set the recipe bean
        this.recipeIngredientBean = bean;

        // get the text view and set the recipe ingredient name
        this.nameTextView.setText(this.recipeIngredientBean.getName());

        // get the text view and set the recipe ingredient measurement
        this.measurementTextView.setText(this.recipeIngredientBean.getAmount() + " " + this.recipeIngredientBean.getMeasurement());
    }
}
