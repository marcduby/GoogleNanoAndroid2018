package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Class to use to parse a sandwich object from json
 *
 */
public class JsonUtils {

    public final static String JSON_NAME_KEY              = "name";
    public final static String JSON_MAIN_NAME_KEY         = "mainName";
    public final static String JSON_AKA_KEY               = "alsoKnownAs";
    public final static String JSON_PLACE_OF_ORIGIN_KEY   = "placeOfOrigin";
    public final static String JSON_DESCRIPTION_KEY       = "description";
    public final static String JSON_IMAGE_KEY             = "image";
    public final static String JSON_INGREDIENTS_KEY       = "ingredients";

    /**
     * parse the json into an sandwich object
     *
     * @param json                  the input json string to parse
     * @return                      a new sandwich object populated from the string; null if error
     */
    public static Sandwich parseSandwichJson(String json) {
        // local variables
        Sandwich sandwich = new Sandwich();
        String tempString = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        // get the json object
        try {
            jsonObject = new JSONObject(json);

            // set the place of origin
            tempString = jsonObject.getString(JSON_PLACE_OF_ORIGIN_KEY);
            sandwich.setPlaceOfOrigin(tempString);

            // set the description
            tempString = jsonObject.getString(JSON_DESCRIPTION_KEY);
            sandwich.setDescription(tempString);

            // set thre image location
            tempString = jsonObject.getString(JSON_IMAGE_KEY);
            sandwich.setImage(tempString);

            // get the list of ingredients and set them
            jsonArray = jsonObject.getJSONArray(JSON_INGREDIENTS_KEY);
            sandwich.setIngredients(getListStringFromJsonArray(jsonArray));

            // set the name
            jsonObject = jsonObject.getJSONObject(JSON_NAME_KEY);
            tempString = jsonObject.getString(JSON_MAIN_NAME_KEY);
            sandwich.setMainName(tempString);

            // set the aka
            jsonArray = jsonObject.getJSONArray(JSON_AKA_KEY);
            sandwich.setAlsoKnownAs(getListStringFromJsonArray(jsonArray));

        } catch (JSONException exception) {
            String message = "Got json parsing exception: " + exception.getMessage();
            Log.i(JsonUtils.class.getName(), message);
            return null;
        }

        // return
        return sandwich;
    }

    /**
     * populate an array list from the json string array
     *
     * @param jsonArray             the json string array
     * @return                      a string list
     * @throws JSONException        if there is a json parsing exception
     */
    private static List<String> getListStringFromJsonArray(JSONArray jsonArray) throws JSONException {
        // local variables
        List<String> stringList = new ArrayList<String>();
        String tempString = null;

        // parse the json array
        for (int i = 0; i < jsonArray.length(); i++) {
            tempString = jsonArray.getString(i);
            stringList.add(tempString);
        }

        // return
        return stringList;

    }
}
