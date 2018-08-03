package com.doobs.moviebrowser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doobs.moviebrowser.R;
import com.doobs.moviebrowser.model.MovieTrailerBean;
import com.doobs.moviebrowser.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Recycler class to load the movie trailer data
 *
 * Created by mduby on 7/18/18.
 */

public class MoviesTrailerRecyclerAdapter extends RecyclerView.Adapter<MoviesTrailerRecyclerAdapter.MovieTrailerViewHolder> {
    // constants
    private String logClassName = this.getClass().getName();

    // instance variables
    private List<MovieTrailerBean> movieTrailerBeanList = new ArrayList<MovieTrailerBean>();
    private MovieTrailerItemClickListener movieTrailerItemClickListener;

    /**
     * default constructor
     *
     * @param listener
     */
    public MoviesTrailerRecyclerAdapter(MovieTrailerItemClickListener listener) {
        this.movieTrailerItemClickListener = listener;
    }

    @NonNull
    @Override
    /**
     * called when creating new view holder instance
     *
     */
    public MovieTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get the context
        Context context = parent.getContext();
        MovieTrailerViewHolder movieReviewViewHolder = null;

        // get the movie item layout
        int movieLayoutId = R.layout.list_item_trailer;

        // inflate the layout
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(movieLayoutId, parent, false);

        // create the view holder
        movieReviewViewHolder = new MovieTrailerViewHolder(view);

        // log
        Log.i(this.logClassName, "Create movie trailer view holder");

        // return
        return movieReviewViewHolder;
    }

    @Override
    /**
     * called when binding new data from the movie list
     *
     */
    public void onBindViewHolder(@NonNull MovieTrailerViewHolder holder, int position) {
        // populate the view holder with a movie at the position
        holder.bind(this.movieTrailerBeanList.get(position));

        // log
        Log.i(this.logClassName, "Bound movie trailer view holder for position: " + position);
    }

    @Override
    /**
     * returns the number of items in the movie list
     *
     */
    public int getItemCount() {
        return this.movieTrailerBeanList.size();
    }

    /**
     * sets the movie list
     *
     * @param movieTrailerBeanList
     */
    public void setMovieTrailerBeanList(List<MovieTrailerBean> movieTrailerBeanList) {
        // set the movie list
        this.movieTrailerBeanList = movieTrailerBeanList;

        // propogate event that data changed
        this.notifyDataSetChanged();
    }

    /**
     * interface to handle item clicks
     *
     */
    public interface MovieTrailerItemClickListener {
        void onListItemClick(MovieTrailerBean movieTrailerBean);
    }

    /**
     * class to hold the individual movie poster items
     *
     */
    public class MovieTrailerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        // instance variables
        TextView nameTextView;
        TextView nameLabelTextView;
        TextView typeTextView;
        TextView typeLabelTextView;
        Button trailerButton;

        /**
         * default constructor
         *
         * @param itemView
         */
        public MovieTrailerViewHolder(View itemView) {
            super(itemView);

            // get the text views
            this.nameTextView = (TextView) itemView.findViewById(R.id.trailer_name_tv);
            this.nameLabelTextView = (TextView) itemView.findViewById(R.id.trailer_name_label_tv);
            this.typeTextView = (TextView) itemView.findViewById(R.id.trailer_type_tv);
            this.typeLabelTextView = (TextView) itemView.findViewById(R.id.trailer_type_label_tv);
            this.trailerButton = (Button) itemView.findViewById(R.id.trailer_youtube_button);

            // set the listener on the button
            this.trailerButton.setOnClickListener(this);
        }

        /**
         * sets the trailer details data
         *
         * @param movieTrailerBean
         */
        protected void bind(MovieTrailerBean movieTrailerBean) {
            // bind the author
            MovieUtils.bindDataToViews(this.nameLabelTextView, this.nameTextView, movieTrailerBean.getName());

            // bind the content
            MovieUtils.bindDataToViews(this.typeLabelTextView, this.typeTextView, movieTrailerBean.getType());
        }

        @Override
        /**
         * handle clicks on the view holder
         *
         */
        public void onClick(View view) {
            // get the index clicked
            int clickedPosition = this.getAdapterPosition();

            // get the movie
            MovieTrailerBean movieTrailerBean = movieTrailerBeanList.get(clickedPosition);

            // call the movie item listener with the position
            movieTrailerItemClickListener.onListItemClick(movieTrailerBean);

        }
    }
}
