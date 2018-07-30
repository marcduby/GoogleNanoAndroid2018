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
import com.doobs.moviebrowser.model.MovieReviewBean;
import com.doobs.moviebrowser.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Recycler class to load the movie data
 *
 * Created by mduby on 7/18/18.
 */

public class MoviesReviewRecyclerAdapter extends RecyclerView.Adapter<MoviesReviewRecyclerAdapter.MovieReviewViewHolder> {
    // constants
    private String logClassName = this.getClass().getName();

    // instance variables
    private List<MovieReviewBean> movieReviewBeanList = new ArrayList<MovieReviewBean>();
    private MovieReviewItemClickListener movieReviewItemClickListener;

    /**
     * default constructor
     *
     * @param listener
     */
    public MoviesReviewRecyclerAdapter(MovieReviewItemClickListener listener) {
        this.movieReviewItemClickListener = listener;
    }

    @NonNull
    @Override
    /**
     * called when creating new view holder instance
     *
     */
    public MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get the context
        Context context = parent.getContext();
        MovieReviewViewHolder movieReviewViewHolder = null;

        // get the movie item layout
        int movieLayoutId = R.layout.list_item_review;

        // inflate the layout
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(movieLayoutId, parent, false);

        // create the view holder
        movieReviewViewHolder = new MovieReviewViewHolder(view);

        // log
        Log.i(this.logClassName, "Create moview review view holder");

        // return
        return movieReviewViewHolder;
    }

    @Override
    /**
     * called when binding new data from the movie list
     *
     */
    public void onBindViewHolder(@NonNull MovieReviewViewHolder holder, int position) {
        // populate the view holder with a movie at the position
        holder.bind(this.movieReviewBeanList.get(position));

        // log
        Log.i(this.logClassName, "Bound movie review view holder for position: " + position);
    }

    @Override
    /**
     * returns the number of items in the movie list
     *
     */
    public int getItemCount() {
        return this.movieReviewBeanList.size();
    }

    /**
     * sets the movie list
     *
     * @param movieReviewBeanList
     */
    public void setMovieReviewBeanList(List<MovieReviewBean> movieReviewBeanList) {
        // set the movie list
        this.movieReviewBeanList = movieReviewBeanList;

        // propogate event that data changed
        this.notifyDataSetChanged();
    }

    /**
     * interface to handle item clicks
     *
     */
    public interface MovieReviewItemClickListener {
        void onListItemClick(MovieReviewBean movieReviewBean);
    }

    /**
     * class to hold the individual movie poster items
     *
     */
    public class MovieReviewViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        // instance variables
        TextView authorTextView;
        TextView authorLabelTextView;
        TextView contentTextView;
        TextView contentLabelTextView;
        Button webReviewButton;

        /**
         * default constructor
         *
         * @param itemView
         */
        public MovieReviewViewHolder(View itemView) {
            super(itemView);

            // get the text views
            this.authorTextView = (TextView) itemView.findViewById(R.id.review_author_tv);
            this.authorLabelTextView = (TextView) itemView.findViewById(R.id.review_author_label_tv);
            this.contentTextView = (TextView) itemView.findViewById(R.id.review_content_tv);
            this.contentLabelTextView = (TextView) itemView.findViewById(R.id.review_content_label_tv);
            this.webReviewButton = (Button) itemView.findViewById(R.id.review_url_button);

            // set the listener on the button
            this.webReviewButton.setOnClickListener(this);
        }

        /**
         * sets the review details data
         *
         * @param movieReviewBean
         */
        protected void bind(MovieReviewBean movieReviewBean) {
            // bind the author
            MovieUtils.bindDataToViews(this.authorLabelTextView, this.authorTextView, movieReviewBean.getAuthor());

            // bind the content
            MovieUtils.bindDataToViews(this.contentLabelTextView, this.contentTextView, movieReviewBean.getContent());
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
            MovieReviewBean movieReviewBean = movieReviewBeanList.get(clickedPosition);

            // call the movie item listener with the position
            movieReviewItemClickListener.onListItemClick(movieReviewBean);

        }
    }
}
