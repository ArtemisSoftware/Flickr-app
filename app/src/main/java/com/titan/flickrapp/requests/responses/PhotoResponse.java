package com.titan.flickrapp.requests.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoResponse extends BaseResponse{

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public Title title;

    @SerializedName("urls")
    public Urls urls;


    public class Title {

        @SerializedName("_content")
        public String content;
    }


    public class Urls {

        @SerializedName("url")
        public List<Url> urls;

    }


    public class Url {

        @SerializedName("type")
        public String type;

        @SerializedName("_content")
        public String content;

    }
}
