package com.doobs.moviebrowser.utils;

import com.doobs.moviebrowser.bean.MovieBean;
import com.doobs.moviebrowser.bean.MovieReviewBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to parse json into movie bean and related objects
 *
 * Created by mduby on 7/9/18.
 */

public class MovieJsonParser {

    /**
     * constants class to hold the moviedb API keys
     *
     */
    public static class MovieJsonKeys {
        public static final String USER_RATING          = "vote_average";
        public static final String NAME                 = "title";
        public static final String POLULARITY           = "popularity";
        public static final String IMAGE_PATH           = "poster_path";
        public static final String SYNOPSIS             = "overview";
        public static final String RELEASE_DATE         = "release_date";
    }

    public static class SharedJsonKeys {
        public static final String ID                   = "id";
        public static final String RESULTS              = "results";
    }

    public static class MoviewReviewJsonKeys {
        public static final String AUTHOR               = "author";
        public static final String CONTENT              = "content";
        public static final String URL                  = "url";
    }

    /**
     * get the movie list from a json object string
     *
     * @param inputJsonString
     * @return
     * @throws MovieException
     */
    public static List<MovieBean> getMovieListFromJsonString(String inputJsonString) throws MovieException {
        // local variables
        List<MovieBean> movieBeanList = null;
        JSONObject jsonObject = null;

        // get the json object
        if (inputJsonString == null) {
            throw new MovieException("Got null input json to translate to movie list");

        } else {
            try {
                jsonObject = new JSONObject(inputJsonString);

            } catch (JSONException exception) {
                throw new MovieException("Got json exception translating to movie list: " + exception.getMessage());
            }
        }

        // get the list
        movieBeanList = getMovieListFromJson(jsonObject);

        // return
        return movieBeanList;
    }

    /**
     * get the movie list from a json object
     *
     * @param inputJsonObject
     * @return
     * @throws MovieException
     */
    public static List<MovieBean> getMovieListFromJson(JSONObject inputJsonObject) throws MovieException {
        List<MovieBean> movieBeanList = new ArrayList<MovieBean>();
        MovieBean movieBean = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        // get the result array
        jsonArray = inputJsonObject.optJSONArray(SharedJsonKeys.RESULTS);

        // if there are movies, then parse
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);

                if (jsonObject != null) {
                    movieBean = getMovieFromJson(jsonObject);

                    // add to list
                    movieBeanList.add(movieBean);
                }
            }
        }

        // return
        return movieBeanList;
    }

    /**
     * parse the json movie object
     *
     * @param inputJsonObject
     * @return
     * @throws MovieException
     */
    public static MovieBean getMovieFromJson(JSONObject inputJsonObject) throws MovieException {
        // local variables
        MovieBean movieBean = new MovieBean();
        String tempString = null;
        Double tempDouble = null;
        Integer tempInteger = null;
        Date tempDate = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // get the title
        tempString = inputJsonObject.optString(MovieJsonKeys.NAME);
        movieBean.setName(tempString);

        // get the user rating
        tempDouble = inputJsonObject.optDouble(MovieJsonKeys.USER_RATING);
        movieBean.setRating(tempDouble);

        // get the popularity
        tempDouble = inputJsonObject.optDouble(MovieJsonKeys.POLULARITY);
        movieBean.setPopularity(tempDouble);

        // get the id
        tempInteger = inputJsonObject.optInt(SharedJsonKeys.ID);
        movieBean.setId(tempInteger);

        // get the image location
        tempString = inputJsonObject.optString(MovieJsonKeys.IMAGE_PATH);
        movieBean.setImageUrl(tempString);

        // get the sysnopsis
        tempString = inputJsonObject.optString(MovieJsonKeys.SYNOPSIS);
        movieBean.setPlotSynopsis(tempString);

        // get the release date
        tempString = inputJsonObject.optString(MovieJsonKeys.RELEASE_DATE);
        movieBean.setReleaseDate(tempString);

        // return
        return movieBean;
    }

    /**
     * get the movie review list from a json object string
     *
     * @param inputJsonString
     * @return
     * @throws MovieException
     */
    public static List<MovieReviewBean> getMovieReviewListFromJsonString(String inputJsonString) throws MovieException {
        // local variables
        List<MovieReviewBean> movieReviewBeanList = null;
        JSONObject jsonObject = null;

        // get the json object
        if (inputJsonString == null) {
            throw new MovieException("Got null input json to translate to movie review list");

        } else {
            try {
                jsonObject = new JSONObject(inputJsonString);

            } catch (JSONException exception) {
                throw new MovieException("Got json exception translating to movie review list: " + exception.getMessage());
            }
        }

        // get the list
        movieReviewBeanList = getMovieReviewListFromJson(jsonObject);

        // return
        return movieReviewBeanList;
    }

    /**
     * get the movie list from a json object
     *
     * @param inputJsonObject
     * @return
     * @throws MovieException
     */
    public static List<MovieReviewBean> getMovieReviewListFromJson(JSONObject inputJsonObject) throws MovieException {
        List<MovieReviewBean> movieReviewBeanList = new ArrayList<MovieReviewBean>();
        MovieReviewBean movieReviewBean = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        // get the result array
        jsonArray = inputJsonObject.optJSONArray(SharedJsonKeys.RESULTS);

        // if there are movies, then parse
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);

                if (jsonObject != null) {
                    movieReviewBean = getMovieReviewFromJson(jsonObject);

                    // add to list
                    movieReviewBeanList.add(movieReviewBean);
                }
            }
        }

        // return
        return movieReviewBeanList;
    }

    /**
     * parse the json movie review object
     *
     * @param inputJsonObject
     * @return
     * @throws MovieException
     */
    public static MovieReviewBean getMovieReviewFromJson(JSONObject inputJsonObject) throws MovieException {
        // local variables
        MovieReviewBean movieReviewBean = new MovieReviewBean();
        String tempString = null;
        Integer tempInteger = null;

        // get the title
        tempString = inputJsonObject.optString(MoviewReviewJsonKeys.AUTHOR);
        movieReviewBean.setAuthor(tempString);

        // get the id
        tempInteger = inputJsonObject.optInt(SharedJsonKeys.ID);
        movieReviewBean.setId(tempInteger);

        // get the image location
        tempString = inputJsonObject.optString(MoviewReviewJsonKeys.URL);
        movieReviewBean.setUrl(tempString);

        // get the sysnopsis
        tempString = inputJsonObject.optString(MoviewReviewJsonKeys.CONTENT);
        movieReviewBean.setContent(tempString);

        // return
        return movieReviewBean;
    }
}
