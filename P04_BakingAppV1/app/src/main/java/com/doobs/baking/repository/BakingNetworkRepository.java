package com.doobs.baking.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;
import com.doobs.baking.util.BakingException;
import com.doobs.baking.util.BakingJsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private Application application;

    /**
     * default constructor
     *
     * @param app
     */
    public BakingNetworkRepository(Application app) {
        this.application = app;
    }

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

    /**
     * test method to load json from file for development
     *
     * @return
     */
    public List<RecipeBean> loadTestRecipesFromFile() {
        // local variables
        BufferedReader reader = null;
        StringBuffer stringBuffer = new StringBuffer();
        List<RecipeBean> recipeBeanList = new ArrayList<RecipeBean>();

        try {
            try {
                reader = new BufferedReader(
                        new InputStreamReader(application.getAssets().open("testJson.json"), "UTF-8"));

                // do reading, usually loop until end of file reading
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    stringBuffer.append(mLine);
                }
            } catch (IOException e) {
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException exception) {
                        //log the exception
                        throw new BakingException("Got IO exception reading test json: " + exception.getMessage());
                    }
                }
            }

            // parse the json
            recipeBeanList = BakingJsonParser.parseRecipeListFromJsonString(stringBuffer.toString());

        } catch (BakingException exception) {
            String message = "Got error loading recipe list: " + exception.getMessage();
            Log.e(TAG, message);
            Toast.makeText(this.application, message, Toast.LENGTH_LONG).show();
        }

        // return
        return recipeBeanList;
    }

}
