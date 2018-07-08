package com.doobs.movieposter.p02_movieposterv1.utils;

import android.util.Log;

import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to manage the movie data retrieval
 *
 * Created by mduby on 7/7/18.
 */

public class MovieUtils {

    /**
     * returns the list of movies sorted by rating
     *
     * @return
     */
    public static List<MovieBean> getMoviesByRating() {
        // local variables
        List<MovieBean> movieBeanList = new ArrayList<MovieBean>();

        // create dummy movie list
        for (int i = 0; i < 10; i++) {
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
