package com.doobs.moviebrowser.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.doobs.moviebrowser.dao.MovieDao;
import com.doobs.moviebrowser.model.MovieBean;
import com.doobs.moviebrowser.utils.MovieBrowserConstants;

/**
 * Room database class to manage store the movie data storing
 *
 * Created by mduby on 7/30/18.
 */
@Database(entities = {MovieBean.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {
    // static variables
    private static MovieRoomDatabase movieRoomDatabase;

    /**
     * singleton method to return the movie database
     *
     * @param context
     * @return
     */
    public static MovieRoomDatabase getInstance(final Context context) {
        if (movieRoomDatabase == null) {
            synchronized (MovieRoomDatabase.class) {
                movieRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        MovieRoomDatabase.class,
                        MovieBrowserConstants.Database.DATABASE_NAME).build();
            }
        }

        // return
        return movieRoomDatabase;
    }

    /**
     * returns the associated DAO
     *
     * @return
     */
    public abstract MovieDao getMovieDao();
}
