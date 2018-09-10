package com.doobs.baking.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.util.BakingAppConstants;
import com.doobs.baking.util.BakingException;
import com.doobs.baking.util.BakingJsonParser;
import com.doobs.baking.util.BakingNetworkUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
    MutableLiveData<List<RecipeBean>> recipeList = new MutableLiveData<List<RecipeBean>>();

    /**
     * default constructor
     *
     * @param app
     */
    public BakingNetworkRepository(Application app) {
        // set the variables
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

    public MutableLiveData<List<RecipeBean>> getRecipeList() {
        return recipeList;
    }


    /**
     * test method to load json from file for development
     *
     * @return
     */
    public void loadTestRecipesFromNetwork() {
        // local variables
        URL recipeURL = null;
        Uri recipeUri = null;

        try {
            // get the URL
            recipeUri = Uri.parse(BakingAppConstants.WebUrls.RECIPEDB_URL);
            recipeURL = new URL(recipeUri.toString());

        } catch (MalformedURLException exception) {
            String errorMessage = "Got url exception: " + exception.getMessage();
            Log.e(TAG, errorMessage);
            Toast.makeText(application, errorMessage, Toast.LENGTH_LONG).show();
        }

        // load the recipes
        new RecipeNetworkLoadTask().execute(recipeURL);
    }

    /**
     * test method to load json from file for development
     *
     * @return
     */
    public void loadTestRecipesFromFile() {
        // local variables
        BufferedReader reader = null;
        StringBuffer stringBuffer = new StringBuffer();

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

        } catch (BakingException exception) {
            String message = "Got error loading recipe list: " + exception.getMessage();
            Log.e(TAG, message);
            Toast.makeText(this.application, message, Toast.LENGTH_LONG).show();
        }

        // set the recipes on the live data object
//        this.recipeList.postValue(recipeBeanList);
        // return
//        return recipeBeanList;
        this.loadRecipesFromJsonString(stringBuffer.toString());
    }

    /**
     * set the recipes based on the passed in json string
     *
     * @param jsonString
     */
    protected void loadRecipesFromJsonString(String jsonString) {
        // local variables
        List<RecipeBean> recipeBeanList = new ArrayList<RecipeBean>();

        try {
            // parse the json
            recipeBeanList = BakingJsonParser.parseRecipeListFromJsonString(jsonString);

        } catch (BakingException exception) {
            String message = "Got error loading recipe list: " + exception.getMessage();
            Log.e(TAG, message);
            Toast.makeText(this.application, message, Toast.LENGTH_LONG).show();
        }

        // set the recipes on the live data object
        this.recipeList.postValue(recipeBeanList);
    }

    /**
     * async class to make the network call
     *
     */
    public class RecipeNetworkLoadTask extends AsyncTask<URL, Void, String> {

        @Override
        /**
         * background thread
         *
         */
        protected String doInBackground(URL... urls) {
            // local variables
            URL movieListUrl = null;
            String responseString = null;

            try {
                // will get exception if no conection
                BakingNetworkUtils.testNetwork();

                // get the url
                movieListUrl = urls[0];

                // log
                Log.i(this.getClass().getName(), "Calling REST service at url: " + movieListUrl);

                // call the REST service
                responseString = BakingNetworkUtils.getResponseFromHttpUrl(movieListUrl);


            } catch (BakingException exception) {
                Log.e(this.getClass().getName(), "Got exception loading movies: " + exception.getMessage());
            }

            // return
            return responseString;
        }

        @Override
        /**
         * called when background thread is done
         *
         */
        protected void onPostExecute(String result) {
            // load the data
            loadRecipesFromJsonString(result);
        }
    }

}
