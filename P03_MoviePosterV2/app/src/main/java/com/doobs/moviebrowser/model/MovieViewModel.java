package com.doobs.moviebrowser.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.doobs.moviebrowser.repository.MovieRepository;

import java.util.List;

/**
 * View model class to keep track of movie list data
 *
 * Created by mduby on 7/30/18.
 */
public class MovieViewModel extends AndroidViewModel {
    // instance variables
    private MovieRepository movieRepository;
    private LiveData<List<MovieBean>> databaseMovieList;
    private MutableLiveData<List<MovieBean>> displayMovieList = new MutableLiveData<List<MovieBean>>();
    private MutableLiveData<String> displayOptionSetting = new MutableLiveData<String>();

    /**
     * default constructor
     *
     * @param application
     */
    public MovieViewModel(Application application) {
        super(application);
        this.movieRepository = new MovieRepository(application);
        this.databaseMovieList = this.movieRepository.getMovieList();
    }

    /**
     * insert a movie into the movie database
     *
     * @param movieBean
     */
    public void insert(MovieBean movieBean) {
        this.movieRepository.insert(movieBean);
    }

    /**
     * delete a movie from the movie database
     *
     * @param movieBean
     */
    public void delete(MovieBean movieBean) {
        this.movieRepository.delete(movieBean);
    }

    /**
     * movie list getter (for DB list)
     *
     * @return
     */
    public LiveData<List<MovieBean>> getDatabaseMovieList() {
        return databaseMovieList;
    }

    /**
     * movie list getter for the main activity list view display
     *
     * @return
     */
    public MutableLiveData<List<MovieBean>> getDisplayMovieList() {
        return displayMovieList;
    }

    /**
     * set the movies to display; will modify the observed mutable live data list
     *
     * @param movieList
     */
    public void setDisplayMovieList(List<MovieBean> movieList) {
        this.displayMovieList.postValue(movieList);
    }

    /**
     * returns the dispay option setting
     *
     * @return
     */
    public MutableLiveData<String> getDisplayOptionSetting() {
        return displayOptionSetting;
    }

    /**
     * set the movie list display option
     *
     * @param option
     */
    public void setDisplayOptionSetting(String option) {
        this.displayOptionSetting.postValue(option);
    }

}
