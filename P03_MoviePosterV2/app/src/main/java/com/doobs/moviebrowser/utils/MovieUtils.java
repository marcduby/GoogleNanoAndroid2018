package com.doobs.moviebrowser.utils;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.doobs.moviebrowser.BuildConfig;
import com.doobs.moviebrowser.model.MovieBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        public static final String METHOD_REVIEWS           = "reviews";
        public static final String METHOD_TRAILERS          = "trailers";
    }

    public static class MovieImage {
        public static final String SERVER_NAME              = "http://image.tmdb.org/t/p/";
        public static final String POSTER_IMAGE_SIZE        = "w185";
        public static final String THUMBNAILAGE_SIZE        = "w92";
    }

    public static class YouTube {
        public static final String APPLICATION_PATH         = "vnd.youtube:";
        public static final String WEB_URL                  = "http://www.youtube.com/watch?v=";

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
        String serverAndContextString = null;
        URL movieListUrl = null;

        // build the string
        serverAndContextString = getServerAndContextString();
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
     * returns the movie review list url given a movie id
     *
     * @param movieId
     * @param apiKey
     * @return
     * @throws MovieException
     */
    public static URL getMovieReviewURL(int movieId, String apiKey) throws MovieException {
        return getMovieLinkURL(movieId, MovieService.METHOD_REVIEWS, apiKey);
    }

    /**
     * returns the movie review list url given a movie id
     *
     * @param movieId
     * @param apiKey
     * @return
     * @throws MovieException
     */
    public static URL getMovieTrailerURL(int movieId, String apiKey) throws MovieException {
        return getMovieLinkURL(movieId, MovieService.METHOD_TRAILERS, apiKey);
    }

    /**
     * returns the movie list url given a movie id and sub oath method
     *
     * @param movieId
     * @param subPath
     * @param apiKey
     * @return
     * @throws MovieException
     */
    public static URL getMovieLinkURL(int movieId, String subPath, String apiKey) throws MovieException {
        // local variables
        Uri searchUri = null;
        String serverAndContextString = null;
        URL movieReviewListUrl = null;

        // build the string
        serverAndContextString = getServerAndContextString();
        serverAndContextString = serverAndContextString + String.valueOf(movieId) + "/" + subPath;

        // build the uri
        searchUri = Uri.parse(serverAndContextString).buildUpon()
                .appendQueryParameter(MovieService.PARAMETER_API_KEY, apiKey)
                .build();

        // build the url
        try {
            movieReviewListUrl = new URL(searchUri.toString());

        } catch (MalformedURLException exception) {
            String errorMessage = "Got url exception: " + exception.getMessage();
            Log.e(MovieUtils.class.getName(), errorMessage);
            throw new MovieException(errorMessage);
        }

        // return
        return movieReviewListUrl;
    }

    /**
     * returns the movie REST service server and context string
     *
     * @return
     */
    private static String getServerAndContextString() {
        // return the server and context
        return  MovieService.SERVER_NAME + "/" + MovieService.ROOT_CONTEXT + "/";
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
    public static String getImageUrlString(String imageFileString, boolean isThumbnail) {
        // local variables
        String imageSize = (isThumbnail ? MovieImage.THUMBNAILAGE_SIZE : MovieImage.POSTER_IMAGE_SIZE);

        // return the image path
        return MovieImage.SERVER_NAME + imageSize + imageFileString;
    }

    /**
     * test the network connection
     *
     * @throws MovieException
     */
    public static void testNetwork() throws MovieException {
        try {
            int timeoutMilliseconds = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMilliseconds);
            sock.close();

        } catch (IOException exception) {
            String message = "Got network exception: " + exception;
            throw new MovieException(message);
        }

    }

    /**
     * format the date from yyyy-MM-dd format to human readable format
     *
     * @param dateString
     * @return
     * @throws MovieException
     */
    public static String formatDate(String dateString) throws MovieException {
        // local variables
        Date date = null;
        String returnString = null;
        String inputFormat = "yyyy-MM-dd";
        String outputFormat = "E MMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputFormat);

        // parse the date if possible
        try {
            date = simpleDateFormat.parse(dateString);

            // now convert in friendly format
            simpleDateFormat = new SimpleDateFormat(outputFormat);
            returnString = simpleDateFormat.format(date);

        } catch (ParseException exception) {
            throw new MovieException("Got date parsing error: " + exception.getMessage());
        }

        // return
        return returnString;
    }

    /**
     * apply data to the text views; hide them if there is no data
     *
     * @param labelView
     * @param dataView
     * @param dataString
     */
    public static void bindDataToViews(TextView labelView, TextView dataView, String dataString) {
        if (dataString.isEmpty()) {
            dataView.setVisibility(View.GONE);
            labelView.setVisibility(View.GONE);

        } else {
            dataView.setText(dataString);
        }
    }

    /**
     * return the youtube application url given the video source
     *
     * @param source
     * @return
     */
    public static String getYouTubeApplicationUrl(String source) {
        return YouTube.APPLICATION_PATH + source;
    }

    /**
     * return the youtube web url given the video source
     *
     * @param source
     * @return
     */
    public static String getYouTubeWebUrl(String source) {
        return YouTube.WEB_URL + source;
    }
}
