package com.doobs.moviebrowser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doobs.moviebrowser.R;
import com.doobs.moviebrowser.model.MovieBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Recycler class to load the movie data
 *
 * Created by mduby on 7/18/18.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    // constants
    private String logClassName = this.getClass().getName();

    // instance variables
    private List<MovieBean> movieBeanList = new ArrayList<MovieBean>();
    private MovieItemClickListener movieItemClickListener;

    /**
     * default constructor
     *
     * @param listener
     */
    public MoviesRecyclerAdapter(MovieItemClickListener listener) {
        this.movieItemClickListener = listener;
    }

    @NonNull
    @Override
    /**
     * called when creating new view holder instance
     *
     */
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get the context
        Context context = parent.getContext();
        MovieViewHolder movieViewHolder = null;

        // get the movie item layout
        int movieLayoutId = R.layout.list_item_movie;

        // inflate the layout
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(movieLayoutId, parent, false);

        // create the view holder
        movieViewHolder = new MovieViewHolder(view, this);

        // log
        Log.i(this.logClassName, "Create view holder");

        // return
        return movieViewHolder;
    }

    @Override
    /**
     * called when binding new data from the movie list
     *
     */
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // populate the view holder with a movie at the position
        holder.bind(this.movieBeanList.get(position));

        // log
        Log.i(this.logClassName, "Bound view holder for position: " + position);
    }

    @Override
    /**
     * returns the number of items in the movie list
     *
     */
    public int getItemCount() {
        return this.movieBeanList.size();
    }

    /**
     * sets the movie list
     *
     * @param movieBeanList
     */
    public void setMovieBeanList(List<MovieBean> movieBeanList) {
        // set the movie list
        this.movieBeanList = movieBeanList;

        // propogate event that data changed
        this.notifyDataSetChanged();
    }

    public List<MovieBean> getMovieBeanList() {
        return movieBeanList;
    }

    public MovieItemClickListener getMovieItemClickListener() {
        return movieItemClickListener;
    }

    /**
     * interface to handle item clicks
     *
     */
    public interface MovieItemClickListener {
        void onListItemClick(MovieBean movieBean);
    }
}
