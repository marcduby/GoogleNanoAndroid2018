package com.doobs.baking.repository;

import android.arch.lifecycle.LiveData;

import com.doobs.baking.bean.RecipeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Architecture component repository class
 *
 * Created by mduby on 8/24/18.
 */
public class BakingNetworkRepository {
    // instance variables
    private final String TAG = this.getClass().getName();

    /**
     * returns the recipe bean list
     *
     * @return
     */
    public List<RecipeBean> getRecipes() {
        // local variables
        List<RecipeBean> recipeBeanList = new ArrayList<RecipeBean>();

        // make network call to get recipes

        // return
        return recipeBeanList;
    }
}
