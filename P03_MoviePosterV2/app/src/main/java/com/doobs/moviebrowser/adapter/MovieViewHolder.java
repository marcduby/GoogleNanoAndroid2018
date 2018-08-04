package com.doobs.moviebrowser.adapter;

/**
 * Created by mduby on 8/3/18.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.doobs.moviebrowser.R;
import com.doobs.moviebrowser.model.MovieBean;
import com.doobs.moviebrowser.utils.MovieUtils;
import com.squareup.picasso.Picasso;

/**
 * class to hold the individual movie poster items
 *
 */
public class MovieViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
    // instance variables
    private MoviesRecyclerAdapter parent;
    ImageView moviePosterView = null;
//        TextView movieNameTextView;

    /**
     * default constructor
     *
     * @param itemView
     */
    public MovieViewHolder(View itemView, MoviesRecyclerAdapter moviesRecyclerAdapter) {
        super(itemView);

        // get the image view
        this.moviePosterView = (ImageView) itemView.findViewById(R.id.list_item_movie_iv);
        this.parent = moviesRecyclerAdapter;
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
        MovieBean movieBean = this.parent.getMovieBeanList().get(clickedPosition);

        // call the movie item listener with the position
        this.parent.getMovieItemClickListener().onListItemClick(movieBean);

    }
}