package com.udacity.sandwichclub.utils;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import dalvik.annotation.TestTarget;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

/**
 * unit test class to test the json parsing
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class JsonUtilsTest {
    // instance variables
    File jsonFile = null;

    // constants
    private final String DESCRIPTION = "test description";

    @Before
    public void setUp() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource("sandwichTest.json");
        this.jsonFile = new File(resource.getPath());
    }

    @Test
    public void testJsonFileNotNull() {
        assertNotNull(this.jsonFile);
    }

    @Test
    public void testParseSandwichJson() {
        // sandwich variable
        Sandwich sandwich = null;
        BufferedReader bufferedReader = null;
        String jsonObjectString =null;

        // json to parse
        jsonObjectString = this.getFileString(this.jsonFile);
//        try {
//
//        } catch (JSONException exception) {
//            fail("Got json error: " + exception.getMessage());
//        }

        // parse
        sandwich = JsonUtils.parseSandwichJson(jsonObjectString);

        // test
        assertNotNull("sandwich object is null", sandwich);
    }

    private String getFileString(File file) {
        BufferedReader reader = null;
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String resultString = null;

        try {
            reader = new BufferedReader( new FileReader (file));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();

            resultString = stringBuilder.toString();

        } catch (FileNotFoundException exception) {
            fail("Got test file error: " + exception.getMessage());

        } catch (IOException exception) {
            fail("Got test file IO exception: " + exception.getMessage());

        }

        // return
        return resultString;
    }

    private JSONObject createTestObject() {
        // local variables
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = null;

        try {
            // add the description
            jsonObject = jsonObject.put(JsonUtils.JSON_DESCRIPTION_KEY, this.DESCRIPTION);

        } catch (JSONException exception) {
            fail("got test json creation error: " + exception.getMessage());
        }

        // add the name

        // return
        return jsonObject;
    }
}