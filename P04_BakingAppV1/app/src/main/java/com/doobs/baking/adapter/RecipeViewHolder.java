package com.doobs.baking.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.baking.R;
import com.doobs.baking.RecipeStepListActivity;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.util.BakingAppConstants;

/**
 * View holder class for the recipe list
 *
 * Created by mduby on 8/25/18.
 */
public class RecipeViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeRecyclerAdapter parent;
    private TextView nameTextView;
    private RecipeBean recipeBean;

    /**
     * default constructor
     *
     * @param view
     * @param adapter
     */
    public RecipeViewHolder(View view, RecipeRecyclerAdapter adapter) {
        super(view);

        // set the instance variables
        this.parent = adapter;
        this.nameTextView = view.findViewById(R.id.recipe_list_item_name_tv);

        // set the listener
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        // TODO - tell adapter listener to launch new activity
        Toast.makeText(view.getContext(), "Clicked on recipe: " + this.recipeBean.getName(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(view.getContext(), RecipeStepListActivity.class);
        intent.putExtra(BakingAppConstants.ActivityExtras.RECIPE_BEAN, recipeBean);
        view.getContext().startActivity(intent);
    }

    /**
     * bind the item data to the view holder
     *
     * @param bean
     */
    protected void bind(RecipeBean bean) {
        // set the recipe bean
        this.recipeBean = bean;

        // get the text view and set the recipe name
        this.nameTextView.setText(this.recipeBean.getName());

    }
}
