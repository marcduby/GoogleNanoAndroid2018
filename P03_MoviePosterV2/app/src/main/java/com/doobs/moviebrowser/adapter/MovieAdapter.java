package com.doobs.moviebrowser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.doobs.moviebrowser.R;
import com.doobs.moviebrowser.model.MovieBean;
import com.doobs.moviebrowser.utils.MovieException;
import com.doobs.moviebrowser.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Test adapter class used to develop the UI w/o network access
 *
 * Created by mduby on 7/7/18.
 */

public class MovieAdapter extends ArrayAdapter<MovieBean> {
    // instance variables
    private Context mContext;
    private List<MovieBean> movieBeanList = new ArrayList<MovieBean>();

    /**
     * default constructor
     *
     * @param context
     */
    public MovieAdapter(Context context, List<MovieBean> movieBeanList) {
        super(context, 0, movieBeanList);
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View listView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        MovieBean movieBean = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
        }

        ImageView imageView = (ImageView) listView.findViewById(R.id.list_item_movie_iv);
        String imageUrl = MovieUtils.getImageUrlString(movieBean.getImageUrl(), false);
        Picasso.get()
                .load(imageUrl)
//                .resize(185, 185)
                .fit()
                .into(imageView);

        // return
        return listView;
    }

    /**
     * return the movie at position
     *
     * @param position
     * @return
     * @throws MovieException
     */
    public MovieBean getMovieAtPosition(int position) throws MovieException {
        // local variables
        MovieBean movieBean = null;

        // make sure the position exists
        if (position < this.movieBeanList.size()) {
            movieBean = this.movieBeanList.get(position);

        } else {
            throw new MovieException("Got incorrect position: " + position + " for movie list of size: " + this.movieBeanList.size());
        }

        // return
        return movieBean;
    }

}
