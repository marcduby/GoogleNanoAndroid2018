package com.doobs.movieposter.p02_movieposterv1.utils;

import android.net.Uri;
import android.util.Log;

import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to manage the movie data retrieval
 *
 * Created by mduby on 7/7/18.
 */

public class MovieUtils {
    // static constants
    public static class MovieService {
        // sample URL: http://api.themoviedb.org/3/movie/popular?api_key=

        public static final String SERVER_NAME              = "api.themoviedb.org";
        public static final String ROOT_CONTEXT             = "3/movie";
        public static final String METHOD_POPULAR           = "popular";
        public static final String METHOD_USER_RATING       = "top_rated";
        public static final String PARAMETER_API_KEY        = "api_key";

    }

    /**
     * build the movie list URL
     *
     * @param isMostPopularSort
     * @param apiKey
     * @return
     * @throws MovieException
     */
    public static URL getMovieListSortedUri(boolean isMostPopularSort, String apiKey) throws MovieException {
        // local variables
        Uri searchUri = null;
        Uri.Builder uriBuilder = null;
        String serverAndContextString = null;
        URL movieListUrl = null;

        // build the string
        serverAndContextString = MovieService.SERVER_NAME + "/" + MovieService.ROOT_CONTEXT + "/";
        if (isMostPopularSort) {
             serverAndContextString = serverAndContextString + MovieService.METHOD_POPULAR;

        } else {
            serverAndContextString = serverAndContextString + MovieService.METHOD_USER_RATING;
        }

        // build the uri
        searchUri = Uri.parse(serverAndContextString).buildUpon()
                .appendQueryParameter(MovieService.PARAMETER_API_KEY, apiKey)
                .build();

        // build the url
        try {
            movieListUrl = new URL(searchUri.toString());

        } catch (MalformedURLException exception) {
            String errorMessage = "Got url exception" + exception.getMessage();
            Log.e(MovieUtils.class.getName(), errorMessage);
            throw new MovieException("Got url exception" + exception.getMessage());
        }

        // return
        return movieListUrl;
    }

    /**
     * returns the list of movies sorted by rating
     *
     * @return
     */
    public static List<MovieBean> getMoviesByRating() {
        // local variables
        List<MovieBean> movieBeanList = new ArrayList<MovieBean>();

        // create dummy movie list
        for (int i = 0; i < 17; i++) {
            MovieBean movieBean = new MovieBean();
            movieBean.setName(String.valueOf(i));
            movieBean.setImageUrl("http://i.imgur.com/DvpvklR.png");
            movieBeanList.add(movieBean);
            Log.i(MovieUtils.class.getName(), "Loaded movie: " + movieBean.getName() + " and image url: " + movieBean.getImageUrl());
        }

        // return
        return movieBeanList;
    }
}
