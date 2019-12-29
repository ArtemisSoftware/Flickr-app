package com.titan.flickrapp.requests.responses;

import android.nfc.Tag;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoResponse extends BaseResponse{

    @SerializedName("photo")
    public PhotoInfo photo;


    public class PhotoInfo{

        @SerializedName("id")
        public String id;

        @SerializedName("title")
        public Title title;

        @SerializedName("description")
        public Description description;

        @SerializedName("urls")
        public Urls urls;

        @SerializedName("dates")
        public Dates dates;

        @SerializedName("tags")
        public Tags tags;

    }


    public class Title {

        @SerializedName("_content")
        public String description;
    }

    public class Description {

        @SerializedName("_content")
        public String description;
    }


    public class Dates {

        @SerializedName("posted")
        public long posted;

        @SerializedName("taken")
        public String taken;
    }


    public class Urls {

        @SerializedName("url")
        public List<Url> links;

    }


    public class Url {

        @SerializedName("type")
        public String type;

        @SerializedName("_content")
        public String content;

    }


    public class Tags {

        @SerializedName("tag")
        public List<Tag> tag;

    }

    public class Tag {

        @SerializedName("raw")
        public String description;
    }
}
