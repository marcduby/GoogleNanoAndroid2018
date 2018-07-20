package com.doobs.movieposter.p02_movieposterv1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doobs.movieposter.p02_movieposterv1.R;
import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mduby on 7/18/18.
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder> {
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
        movieViewHolder = new MovieViewHolder(view);

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

    /**
     * interface to handle item clicks
     *
     */
    public interface MovieItemClickListener {
        void onListItemClick(MovieBean movieBean);
    }

    /**
     * class to hold the individual movie poster items
     *
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        // instance variables
        ImageView moviePosterView;
//        TextView movieNameTextView;

        /**
         * default constructor
         *
         * @param itemView
         */
        public MovieViewHolder(View itemView) {
            super(itemView);

            // get the image view
            this.moviePosterView = (ImageView) itemView.findViewById(R.id.list_item_movie_iv);
//            this.movieNameTextView = (TextView) itemView.findViewById(R.id.movie_name_tv);

            // set the listener
            itemView.setOnClickListener(this);
        }

        /**
         * sets the poster image
         *
         * @param movieBean
         */
        protected void bind(MovieBean movieBean) {
            // get the image url
            String imageUrl = MovieUtils.getImageUrlString(movieBean.getImageUrl(), false);

            // log
            Log.i(this.getClass().getName(), "Inflating image for url: " + imageUrl);

            // add the image to the image view
            Picasso.get()
                    .load(imageUrl)
                    .into(this.moviePosterView);

            // set the text
//            this.movieNameTextView.setText(movieBean.getName());
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
            MovieBean movieBean = movieBeanList.get(clickedPosition);

            // call the movie item listener with the position
            movieItemClickListener.onListItemClick(movieBean);

        }
    }
}
