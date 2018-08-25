package com.doobs.baking.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.repository.BakingNetworkRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * View model class to hold the recipe list
 *
 * Created by mduby on 8/24/18.
 */

public class RecipeViewModel extends AndroidViewModel {
    // instance variables
    private final String TAG = this.getClass().getName();
    private MutableLiveData<List<RecipeBean>> recipeBeanList = new MutableLiveData<List<RecipeBean>>();
    private BakingNetworkRepository bakingNetworkRepository;

    /**
     * default constructor
     *
     * @param application
     */
    public RecipeViewModel(Application application) {
        super(application);
        this.bakingNetworkRepository = new BakingNetworkRepository();

        // load the recipe data
        this.recipeBeanList.postValue(this.bakingNetworkRepository.getRecipes());
    }

    /**
     * get the display list
     *
     * @return
     */
    public LiveData<List<RecipeBean>> getRecipeBeanList() {
        return recipeBeanList;
    }

    /**
     * sets the mutable list
     *
     * @param recipeBeanList
     */
    public void setRecipeBeanList(List<RecipeBean> recipeBeanList) {
        this.recipeBeanList.postValue(recipeBeanList);
    }

    public void loadRecipeData() {
        // load dummy data
        List<RecipeBean> recipeBeans = new ArrayList<RecipeBean>();

        for (int i = 0; i < 10; i++) {
            Log.i(TAG, "loading recipe: " + i);
            RecipeBean recipeBean = new RecipeBean();
            recipeBean.setId(i);
            recipeBean.setName("Recipe " + i);
            recipeBeans.add(recipeBean);
        }

        this.setRecipeBeanList(recipeBeans);
    }

    /**
     * called before destroy
     *
     */
    @Override
    protected void onCleared() {
        String almost = "done";
        super.onCleared();
    }

}
