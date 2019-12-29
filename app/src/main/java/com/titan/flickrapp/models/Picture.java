package com.titan.flickrapp.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Picture implements Parcelable {

    private String id;
    private String title;
    private String url;

    public Picture(String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    protected Picture(Parcel in) {
        id = in.readString();
        title = in.readString();
        url = in.readString();
    }



    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }


    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(url);
    }
}
