package com.doobs.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.util.BakingAppConstants;

/**
 * Fragment class to display the recipe step and ingredient lists
 *
 * Created by mduby on 8/26/18.
 */

public class RecipeStepsFragment extends Fragment {
    // instance variables
    private final String TAG = this.getClass().getName();
    private RecipeBean recipeBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the view
        // get the root view and inflate it
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        Intent intent = this.getActivity().getIntent();
        if (intent == null) {
//            this.getActivity().closeOnError();
        }

        // get the intent parceable
        recipeBean = intent.getParcelableExtra(BakingAppConstants.ActivityExtras.RECIPE_BEAN);
        if (recipeBean == null) {
            // EXTRA_POSITION not found in intent
            Log.e(this.getClass().getName(), "Got null recipe bean");
  //          closeOnError();
//            return;
        }

        // get the text view and how the name
        TextView textView = rootView.findViewById(R.id.recipe_name_fragment_tv);
        textView.setText(recipeBean.getName() + " dude");

//        // get the recycler view
//        this.imageGridView = (GridView)rootView.findViewById(R.id.image_list_gv);
//
//        // get the adapter
//        this.masterListAdapter = new MasterListAdapter(rootView.getContext(), AndroidImageAssets.getAll());
//
//        // set the adapter on the recycler view
//        this.imageGridView.setAdapter(this.masterListAdapter);
//
//        // set the click listener
//        this.imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                imageClickListener.onImageClick(position);
//            }
//        });

        // return the view
        return rootView;
    }


}
