package com.doobs.moviebrowser.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.doobs.moviebrowser.dao.MovieDao;
import com.doobs.moviebrowser.database.MovieRoomDatabase;
import com.doobs.moviebrowser.model.MovieBean;

import java.util.List;

/**
 * Repository class to manage the favorite movies
 *
 * Created by mduby on 7/30/18.
 */

public class MovieRepository {
    // instance variables
    private MovieDao movieDao;
    private LiveData<List<MovieBean>> movieList;

    /**
     * default constructor
     *
     * @param application
     */
    public MovieRepository(Application application) {
        MovieRoomDatabase movieRoomDatabase = MovieRoomDatabase.getInstance(application);
        this.movieDao = movieRoomDatabase.getMovieDao();
        this.movieList = this.movieDao.getAllMovies();
    }

    /**
     * movie list getter
     *
     * @return
     */
    public LiveData<List<MovieBean>> getMovieList() {
        return movieList;
    }

    /**
     * insert a movie into the database
     *
     * @param movieBean
     */
    public void insert(MovieBean movieBean) {
        new InsertMovieAsyncTask(this.movieDao).execute(movieBean);
    }

    /**
     * delete a movie from the database
     *
     * @param movieBean
     */
    public void delete(MovieBean movieBean) {
        new DeleteMovieAsyncTask(this.movieDao).execute(movieBean);
    }

    /**
     * async class to insert movie data
     *
     */
    public static class InsertMovieAsyncTask extends AsyncTask<MovieBean, Void, Void> {
        // instance variables
        private MovieDao movieDao;

        /**
         * default constructor
         *
         * @param dao
         */
        public InsertMovieAsyncTask(MovieDao dao) {
            this.movieDao = dao;
        }

        @Override
        protected Void doInBackground(MovieBean... movieBeans) {
            // get the movie to insert
            MovieBean movieBean = movieBeans[0];

            // TODO - maybe add a check that it is not in the list yet?
            // insert the movie
            this.movieDao.insert(movieBean);

            // log
            Log.i(this.getClass().getName(), "Inserted movie with id: " + movieBean.getId() + " and name: " + movieBean.getName());

            // return
            return null;
        }
    }

    /**
     * async class to delete movie data
     *
     */
    public static class DeleteMovieAsyncTask extends AsyncTask<MovieBean, Void, Void> {
        // instance variables
        private MovieDao movieDao;

        /**
         * default constructor
         *
         * @param dao
         */
        public DeleteMovieAsyncTask(MovieDao dao) {
            this.movieDao = dao;
        }

        @Override
        protected Void doInBackground(MovieBean... movieBeans) {
            // get the movie to insert
            MovieBean movieBean = movieBeans[0];

            // insert the movie
            this.movieDao.delete(movieBean);

            // log
            Log.i(this.getClass().getName(), "Deleted movie with id: " + movieBean.getId() + " and name: " + movieBean.getName());

            // return
            return null;
        }
    }
}
