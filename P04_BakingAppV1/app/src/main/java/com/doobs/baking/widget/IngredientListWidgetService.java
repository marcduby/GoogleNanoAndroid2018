package com.doobs.baking.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.doobs.baking.R;
import com.doobs.baking.util.BakingAppConstants;

/**
 * Created by mduby on 9/17/18.
 */

public class IngredientListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        // get the ingredients array from the intent
        String[] ingredientArray = intent.getStringArrayExtra(BakingAppConstants.ActivityExtras.INGREDIENTS_ARRAY);

        // create the new factory and return
        return new IngredientsListRemoteViewsFactory(this.getApplicationContext(), ingredientArray);
    }
}

class IngredientsListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    // instance variables
    private final String TAG = this.getClass().getName();
    private Context context;
    private String[] ingredientArray;

    public IngredientsListRemoteViewsFactory(Context con, String[] stringArray) {
        this.context = con;
        this.ingredientArray = stringArray;

        // test
//        this.ingredientArray = new String[40];
//        for (int i = 0; i < 40; i++) {
//            this.ingredientArray[i] = "ddude " + i;
//        }

        // log
        Log.i(TAG, "Got array of size: " + (stringArray == null ? 0 : stringArray.length) + " of value: " + stringArray);
    }


    @Override
    public void onCreate() {

    }

    @Override
    public int getCount() {
        if (this.ingredientArray == null) {
            return 0;

        } else {
            return this.ingredientArray.length;
        }
    }

    @Override
    public void onDataSetChanged() {
        // data already in string array
        Log.i(TAG, "Have ingredients of size: " + (this.ingredientArray == null ? 0 : this.ingredientArray.length));

        if (this.ingredientArray != null) {
            for (int i = 0; i < this.ingredientArray.length; i++) {
                Log.i(TAG, "Got ingredient: " + this.ingredientArray[i]);
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * creates the list view at the given position
     *
     * @param position
     * @return
     */
    @Override
    public RemoteViews getViewAt(int position) {
        // log
        Log.i(TAG, "In getViewAt method");

        // if no strings in the array, return null
        if ((this.ingredientArray == null) || (this.ingredientArray.length < 1)) {
            return null;
        }

        // create the remote view
        RemoteViews remoteViews = new RemoteViews(this.context.getPackageName(), R.layout.ingredient_widget_list_item);

        // log
        Log.i(TAG, "Setting widget list ingredient: " + this.ingredientArray[position]);

        // set the ingredient data on the view
        remoteViews.setTextViewText(R.id.widget_ingredient_textview, this.ingredientArray[position]);

        // return
        return remoteViews;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

