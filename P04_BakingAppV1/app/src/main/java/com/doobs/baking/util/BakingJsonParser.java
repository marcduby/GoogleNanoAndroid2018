package com.doobs.baking.util;

import android.util.Log;

import com.doobs.baking.bean.IngredientBean;
import com.doobs.baking.bean.RecipeBean;
import com.doobs.baking.bean.RecipeStepBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse recipes from json
 *
 * Created by mduby on 8/24/18.
 */
public class BakingJsonParser {
    // instance variables
    private static final String TAG = BakingJsonParser.class.getName();

    /**
     * get the recipe list from a json string
     *
     * @param inputJsonString
     * @return
     * @throws BakingException
     */
    public static List<RecipeBean> parseRecipeListFromJsonString(String inputJsonString) throws BakingException {
        // local variables
        List<RecipeBean> recipeBeanList = null;
        JSONArray jsonArray = null;

        // get the json object
        if (inputJsonString == null) {
            throw new BakingException("Got null input json to translate to recipe list");

        } else {
            try {
                jsonArray = new JSONArray(inputJsonString);

            } catch (JSONException exception) {
                throw new BakingException("Got json exception translating to recipe list: " + exception.getMessage());
            }
        }

        // get the list
        recipeBeanList = parseRecipeListFromJsonArray(jsonArray);

        // log
        Log.i(TAG, "Got recipe list of sixe: " + recipeBeanList.size());

        // return
        return recipeBeanList;
    }


    /**
     * get the recipe list from a json array
     * @param inputJsonArray
     * @return
     * @throws BakingException
     */
    public static List<RecipeBean> parseRecipeListFromJsonArray(JSONArray inputJsonArray) throws BakingException {
        List<RecipeBean> movieBeanList = new ArrayList<RecipeBean>();
        RecipeBean movieBean = null;
        JSONObject jsonObject = null;

        // if there are movies, then parse
        if (inputJsonArray != null) {
            for (int i = 0; i < inputJsonArray.length(); i++) {
                jsonObject = inputJsonArray.optJSONObject(i);

                if (jsonObject != null) {
                    movieBean = getRecipeFromJson(jsonObject);

                    // add to list
                    movieBeanList.add(movieBean);
                }
            }
        }

        // return
        return movieBeanList;
    }

    /**
     * parse the recipe from the json object
     *
     * @param jsonObject
     * @return
     */
    public static RecipeBean getRecipeFromJson(JSONObject jsonObject) throws BakingException {
        // local variables
        RecipeBean recipeBean = new RecipeBean();
        String tempString = null;
        Double tempFloat = null;
        Integer tempInteger = null;
        JSONArray jsonArray = null;

        // get the id
        tempInteger = jsonObject.optInt(BakingAppConstants.JsonKeys.ID);
        recipeBean.setId(tempInteger);

        // get the name
        tempString = jsonObject.optString(BakingAppConstants.JsonKeys.NAME);
        recipeBean.setName(tempString);

        // get the ingredient list
        jsonArray = jsonObject.optJSONArray(BakingAppConstants.JsonKeys.INGREDIENTS);
        if (jsonArray == null) {
            throw new BakingException("Recipe: " + recipeBean.getName() + " has no ingredients");

        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    // get the ingredient bean
                    IngredientBean ingredientBean = getIngredientBeanFromJson(jsonArray.getJSONObject(i));

                    // add it to the list
                    recipeBean.addIngredientBean(ingredientBean);

                } catch (JSONException exception) {
                    throw new BakingException("Got json parsing exception: " + exception.getMessage());
                }

            }
        }

        // get the steps
        jsonArray = jsonObject.optJSONArray(BakingAppConstants.JsonKeys.STEPS);
        if (jsonArray == null) {
            throw new BakingException("Recipe: " + recipeBean.getName() + " has no steps");

        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    // get the ingredient bean
                    RecipeStepBean recipeStepBean = getRecipeStepBeanFromJson(jsonArray.getJSONObject(i));

                    // add it to the list
                    recipeBean.addStepBean(recipeStepBean);

                } catch (JSONException exception) {
                    throw new BakingException("Got json parsing exception: " + exception.getMessage());
                }
            }
        }

        // add in a first step which is the ingredient list
        RecipeStepBean recipeStepBean = new RecipeStepBean();
        recipeStepBean.setType(BakingAppConstants.RecipeStepType.INGREDIENT);
        recipeStepBean.setShortDescription("Ingredient List");
        recipeStepBean.setIngredientBeanList(recipeBean.getIngredientBeanList());
        recipeBean.getStepBeanList().add(0, recipeStepBean);

        // get the servings
        tempFloat = jsonObject.optDouble(BakingAppConstants.JsonKeys.SERVINGS);
        recipeBean.setServings(tempFloat);

        // get the image path
        tempString = jsonObject.optString(BakingAppConstants.JsonKeys.IMAGE);
        recipeBean.setImagePath(tempString);

        // log
        Log.i(TAG, "Got recipe with id: " + recipeBean.getId() + " with step size: " + recipeBean.getStepBeanList().size() +
                " and ingredient size: " + recipeBean.getIngredientBeanList().size());

        // return
        return recipeBean;
    }

    /**
     * parse the ingredients from a json object
     *
     * @param jsonObject
     * @return
     * @throws BakingException
     */
    public static IngredientBean getIngredientBeanFromJson(JSONObject jsonObject) throws BakingException {
        // local variables
        IngredientBean ingredientBean = new IngredientBean();
        String tempString = null;
        Double tempDouble = null;

        // get the ingredient
        tempString = jsonObject.optString(BakingAppConstants.JsonKeys.INGREDIENT);
        ingredientBean.setName(tempString);

        // get the measurement
        tempString = jsonObject.optString(BakingAppConstants.JsonKeys.MEASURE);
        ingredientBean.setMeasurement(tempString);

        // get the quantity
        tempDouble = jsonObject.optDouble(BakingAppConstants.JsonKeys.QUANTITY);
        ingredientBean.setAmount(tempDouble);

        // return
        return ingredientBean;
    }

    /**
     * parses the recipe step bean from a json object
     *
     * @param jsonObject
     * @return
     * @throws BakingException
     */
    public static RecipeStepBean getRecipeStepBeanFromJson(JSONObject jsonObject) throws BakingException {
        // local variables
        RecipeStepBean recipeStepBean = new RecipeStepBean();
        Integer tempInteger = null;
        String tempString = null;

        // get the id
        tempInteger = jsonObject.optInt(BakingAppConstants.JsonKeys.ID);
        recipeStepBean.setId(tempInteger);

        // get the description
        tempString = jsonObject.optString(BakingAppConstants.JsonKeys.DESCRIPTION);
        recipeStepBean.setDescription(tempString);

        // get the short description
        tempString = jsonObject.optString(BakingAppConstants.JsonKeys.SHORT_DESCRIPTION);
        recipeStepBean.setShortDescription(tempString);

        // get the video url
        tempString = jsonObject.optString(BakingAppConstants.JsonKeys.VIDEO_URL);
        recipeStepBean.setVideoUrl(tempString);

        // get the thumbnail url
        tempString = jsonObject.optString(BakingAppConstants.JsonKeys.THUMBNAIL_URL);
        recipeStepBean.setThumbnailUrl(tempString);

        // return
        return recipeStepBean;
    }
}
