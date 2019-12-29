package com.titan.flickrapp.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.titan.flickrapp.requests.responses.PhotoResponse;

import java.util.ArrayList;
import java.util.List;

public class Picture implements Parcelable {

    private String id;
    private String title;
    private String description;
    private List<String> tags;
    private String url;
    private String date;

    public Picture(PhotoResponse photo) {

        this.id = photo.photo.id;
        this.title = photo.photo.title.description;
        this.description = photo.photo.description.description;
        this.url = photo.photo.urls.links.get(0).content;
        this.date = photo.photo.dates.taken;

        this.tags = new ArrayList<>();

        for (PhotoResponse.Tag tag: photo.photo.tags.tag) {
            tags.add(tag.description);
        }

    }


    protected Picture(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        tags = in.createStringArrayList();
        url = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeStringList(tags);
        dest.writeString(url);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getDate() {
        return date;
    }
}
