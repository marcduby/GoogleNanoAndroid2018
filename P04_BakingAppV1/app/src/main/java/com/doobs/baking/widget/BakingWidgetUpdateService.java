package com.doobs.baking.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.doobs.baking.R;
import com.doobs.baking.bean.IngredientBean;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.util.BakingAppConstants;

/**
 * Created by mduby on 9/17/18.
 */

public class BakingWidgetUpdateService extends IntentService {
    // instance variables
    private static final String TAG = BakingWidgetUpdateService.class.getName();

    public BakingWidgetUpdateService() {
        super("BakingWidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // local variables
        RecipeBean recipeBean = null;
        Bundle bundle = null;

        // log
        Log.i(TAG, "In onHandleIntent method");

        if (intent != null) {
            String action = intent.getAction();
            Log.i(TAG, "In onHandleIntent method, got action: " + action);

            if (BakingAppConstants.ServiceActions.UPDATE_INGREDIENTS.equals(action)) {
                // get the bundle
                bundle = intent.getExtras();

                if (bundle != null) {
                    // get the recipe
                    recipeBean = (RecipeBean)bundle.get(BakingAppConstants.ActivityExtras.RECIPE_BEAN);

                    // get the names
                    String recipeName = recipeBean.getName();

                    // build an array of strings with the ingredient data
                    String[] ingredientArray = new String[recipeBean.getIngredientBeanList().size()];
                    for (int i = 0; i <  recipeBean.getIngredientBeanList().size(); i++) {
                        IngredientBean ingredient = recipeBean.getIngredientBeanList().get(i);
                        ingredientArray[i] = "[" + ingredient.getAmount() + " " + ingredient.getMeasurement() + "] - " + ingredient.getName();
                    }

                    // get the widgets
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientsWidgetProvider.class));

                    // update the widgets list view
                    // TODO - skip list/grid view for now due to listener events not triggering factory
//                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_widget_list_view);

                    // log
                    Log.i(TAG, "sent widget data changed event");

                    // call the provider update
                    RecipeIngredientsWidgetProvider.updateIngredientWidgets(this, appWidgetManager, appWidgetIds, recipeName, ingredientArray);
                }

            }
        }


    }
}
