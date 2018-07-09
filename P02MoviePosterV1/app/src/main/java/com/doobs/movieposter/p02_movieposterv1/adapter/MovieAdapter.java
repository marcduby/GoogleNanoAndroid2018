package com.doobs.movieposter.p02_movieposterv1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.doobs.movieposter.p02_movieposterv1.bean.MovieBean;
import com.doobs.movieposter.p02_movieposterv1.utils.MovieUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mduby on 7/7/18.
 */

public class MovieAdapter extends BaseAdapter {
    // instance variables
    private Context mContext;
    private List<MovieBean> movieBeanList = new ArrayList<MovieBean>();

    /**
     * default constructor
     *
     * @param context
     */
    public MovieAdapter(Context context) {
        mContext = context;
        this.movieBeanList = MovieUtils.getMoviesByRating();
    }

    public int getCount() {
        return this.movieBeanList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(185, 185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        // use Picasso to load the image
        MovieBean movieBean = this.movieBeanList.get(position);
        Picasso.get()
                .load(movieBean.getImageUrl())
                .resize(185, 185)
                .into(imageView);
//        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }


}
