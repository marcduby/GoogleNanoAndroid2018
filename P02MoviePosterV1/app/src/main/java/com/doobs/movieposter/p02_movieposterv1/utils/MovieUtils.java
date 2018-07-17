package com.doobs.movieposter.p02_movieposterv1.utils;

import android.net.Uri;
import android.util.Log;

import com.doobs.movieposter.p02_movieposterv1.BuildConfig;
import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class to manage the movie data retrieval
 *
 * Created by mduby on 7/7/18.
 */

public class MovieUtils {
    // static constants
    public static class MovieService {
        // sample URL: http://api.themoviedb.org/3/movie/popular?api_key=
        public static final String API_KEY                  = BuildConfig.MOVIEDB_KEY;

        public static final String SERVER_NAME              = "http://api.themoviedb.org";
        public static final String ROOT_CONTEXT             = "3/movie";
        public static final String METHOD_POPULAR           = "popular";
        public static final String METHOD_USER_RATING       = "top_rated";
        public static final String PARAMETER_API_KEY        = "api_key";
    }

    public static class MovieImage {
        public static final String SERVER_NAME              = "http://image.tmdb.org/t/p/";
        public static final String IMAGE_SIZE               = "w185";
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
            String errorMessage = "Got url exception: " + exception.getMessage();
            Log.e(MovieUtils.class.getName(), errorMessage);
            throw new MovieException(errorMessage);
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

    /**
     * connect to the REST service and return the results of the query
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        // local vaiables
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        String responseString = null;

        // open the stream
        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            // get the response string
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                responseString = scanner.next();
            }

        } finally {
            httpURLConnection.disconnect();
        }

        // return
        return responseString;
    }

    /**
     * returns the image file url in string format
     *
     * @param imageFileString
     * @return
     */
    public static String getImageUrlString(String imageFileString) {
        return MovieImage.SERVER_NAME + MovieImage.IMAGE_SIZE + imageFileString;
    }

}
