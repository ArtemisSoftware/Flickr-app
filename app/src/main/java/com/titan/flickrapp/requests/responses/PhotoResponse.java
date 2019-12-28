package com.titan.flickrapp.requests.responses;

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

        @SerializedName("urls")
        public Urls urls;

    }


    public class Title {

        @SerializedName("_content")
        public String description;
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
}
