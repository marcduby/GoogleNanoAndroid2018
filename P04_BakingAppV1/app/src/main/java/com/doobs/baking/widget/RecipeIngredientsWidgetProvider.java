package com.doobs.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.doobs.baking.MainActivity;
import com.doobs.baking.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidgetProvider extends AppWidgetProvider {
    // instance variables
    private static final String TAG = RecipeIngredientsWidgetProvider.class.getName();

    /**
     * update the ingredients widget
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String recipeName, String[] ingredientArray) {
        // log
        Log.i(TAG, "Updating widget: " + appWidgetId + " with ingredients for recipe: " + recipeName);

        // get the baking title
        CharSequence widgetText = context.getString(R.string.baking_app_widget_title);
        String widgetTitle = (recipeName == null ? widgetText.toString() : widgetText.toString() + " for " + recipeName);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);
        views.setTextViewText(R.id.appwidget_text, widgetTitle + "dude");

        // create the intent for the click response
        Intent bakingIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, bakingIntent, 0);
        views.setOnClickPendingIntent(R.id.baking_widget_layout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            // update the widgets
            updateAppWidget(context, appWidgetManager, appWidgetId, null, null);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String recipeName, String[] ingredientArray) {
        for (int appWidgetId : appWidgetIds) {
            // update the widgets
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, ingredientArray);
        }
    }
}

