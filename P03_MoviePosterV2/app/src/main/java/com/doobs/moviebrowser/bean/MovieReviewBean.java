package com.doobs.moviebrowser.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Bean class to hold a review of a movie
 *
 * Created by mduby on 7/22/18.
 */

public class MovieReviewBean implements Parcelable {
    // instance variables
    private int id;
    private String url;
    private String content;
    private String author;

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(url);
        parcel.writeString(content);
        parcel.writeString(author);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieReviewBean> CREATOR = new Parcelable.Creator<MovieReviewBean>() {
        public MovieReviewBean createFromParcel(Parcel parcel) {
            MovieReviewBean movieReviewBean = new MovieReviewBean();

            // set the data
            movieReviewBean.setId(parcel.readInt());
            movieReviewBean.setUrl(parcel.readString());
            movieReviewBean.setContent(parcel.readString());
            movieReviewBean.setAuthor(parcel.readString());

            // return
            return movieReviewBean;
        }

        public MovieReviewBean[] newArray(int size) {
            return new MovieReviewBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
