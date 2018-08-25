package com.doobs.baking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.doobs.baking.R;
import com.doobs.baking.bean.RecipeBean;

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
        Toast.makeText(view.getContext(), "Clicked on recipe", Toast.LENGTH_LONG).show();
    }

    /**
     * bind the item data to the view holder
     *
     * @param recipeBean
     */
    protected void bind(RecipeBean recipeBean) {
        // get the text view and set the recipe name
        this.nameTextView.setText(recipeBean.getName());

    }
}
