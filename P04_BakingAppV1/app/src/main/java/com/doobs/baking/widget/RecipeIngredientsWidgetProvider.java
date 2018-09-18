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
import com.doobs.baking.util.BakingAppConstants;

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
        views.setTextViewText(R.id.appwidget_text, widgetTitle);

        // log
        Log.i(TAG, "Got ingredients array of size: " + (ingredientArray == null ? 0 : ingredientArray.length));

        // create the widget list view intent
        Intent intent = new Intent(context, IngredientListWidgetService.class);
        intent.putExtra(BakingAppConstants.ActivityExtras.INGREDIENTS_ARRAY, ingredientArray);
        views.setRemoteAdapter(R.id.ingredient_widget_list_view, intent);
//        PendingIntent adapterPendingIntent = PendingIntent.getService(context, 0, intent, 0);
//        views.setPendingIntentTemplate(R.id.ingredient_widget_list_view, adapterPendingIntent);

        // log
        Log.i(TAG, "Added widget list adapter for widget: " + appWidgetId);

        // create the intent for the click response
        Intent bakingIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, bakingIntent, 0);
//        views.setPendingIntentTemplate(R.id.ingredient_widget_list_view, pendingIntent);
        views.setOnClickPendingIntent(R.id.baking_widget_layout, pendingIntent);

        // log
        Log.i(TAG, "Added main activity onClick for widget: " + appWidgetId);

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

        // send the data changeed event
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_widget_list_view);

        // log
        Log.i(TAG, "Sent widget change event from provider");
    }
}

