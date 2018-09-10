package com.doobs.baking.adapter;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.baking.R;
import com.doobs.baking.RecipeStepDetailActivity;
import com.doobs.baking.RecipeStepListActivity;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;
import com.doobs.baking.util.BakingAppConstants;

/**
 * View holder class for the recipe step list
 *
 * Created by mduby on 8/25/18.
 */
public class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeStepRecyclerAdapter parent;
    private TextView nameTextView;
    private RecipeStepBean recipeStepBean;

    /**
     * default constructor
     *
     * @param view
     * @param adapter
     */
    public RecipeStepViewHolder(View view, RecipeStepRecyclerAdapter adapter) {
        super(view);

        // set the instance variables
        this.parent = adapter;
        this.nameTextView = view.findViewById(R.id.recipe_list_item_name_tv);

        // set the listener
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
//        // TODO - tell adapter listener to launch new activity
//        Toast.makeText(view.getContext(), "Clicked on recipe step: " + this.recipeStepBean.getShortDescription(), Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(view.getContext(), RecipeStepDetailActivity.class);
//        intent.putExtra(BakingAppConstants.ActivityExtras.RECIPE_STEP_BEAN, recipeStepBean);
//        view.getContext().startActivity(intent);

        // call the listener
        if (this.parent.getRecipeStepListener() != null) {
            this.parent.getRecipeStepListener().onRecipeStepClick(this.recipeStepBean);
        }
    }

    /**
     * bind the item data to the view holder
     *
     * @param bean
     */
    protected void bind(RecipeStepBean bean) {
        // set the recipe bean
        this.recipeStepBean = bean;

        // get the text view and set the recipe name
        this.nameTextView.setText(this.recipeStepBean.getShortDescription());

        // if ingredient list, set background
        // TODO - skip for now because if view holder recycled, color not necessarily recycled
//        if (bean.getType().equals(BakingAppConstants.RecipeStepType.INGREDIENT)) {
//            final int sdk = android.os.Build.VERSION.SDK_INT;
//            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                this.nameTextView.setBackgroundDrawable(ContextCompat.getDrawable(this.nameTextView.getContext(), R.drawable.recipe_card) );
//
//            } else {
//                this.nameTextView.setBackground(ContextCompat.getDrawable(this.nameTextView.getContext(), R.drawable.ingredient_card));
//            }
//
//        }
    }
}
