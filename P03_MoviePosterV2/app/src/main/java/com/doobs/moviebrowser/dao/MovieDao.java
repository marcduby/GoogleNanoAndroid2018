package com.doobs.moviebrowser.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.doobs.moviebrowser.model.MovieBean;
import com.doobs.moviebrowser.utils.MovieBrowserConstants;

import java.util.List;

/**
 * DAO class to manage the persistence of the movie data
 *
 * Created by mduby on 7/30/18.
 */
@Dao
public interface MovieDao {
    @Insert
    public void insert(MovieBean movieBean);

    @Delete
    public void delete(MovieBean movieBean);

    @Update
    public void update(MovieBean movieBean);

    @Query("delete from " + MovieBrowserConstants.Database.TABLE_NAME_MOVIE)
    public void deleteAll();

    @Query("select * from " + MovieBrowserConstants.Database.TABLE_NAME_MOVIE)
    public LiveData<List<MovieBean>> getAllMovies();
}
