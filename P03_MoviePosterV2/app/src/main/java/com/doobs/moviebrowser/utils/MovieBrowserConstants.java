package com.doobs.moviebrowser.utils;

/**
 * Created by mduby on 7/30/18.
 */

public class MovieBrowserConstants {

    /**
     * hold database constants
     *
     */
    public class Database {
        public static final String TABLE_NAME_MOVIE = "mov_movies";

        public static final String DATABASE_NAME = "movie_database";
    }

    public class MovieListSource {
        public static final String MOST_POPULAR         = "most_popular";
        public static final String BEST_RATED           = "best_rated";
        public static final String STORED_FAVORITES     = "stored_favorites";
    }
}
