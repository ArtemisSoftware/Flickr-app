package com.titan.flickrapp.requests.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoListResponse extends BaseResponse{

    @SerializedName("photos")
    public Photos photos;

    public class Photos {

        @SerializedName("page")
        public int page;

        @SerializedName("pages")
        public int pages;

        @SerializedName("photo")
        public List<Photo> pictures;
    }


    public class Photo {

        @SerializedName("id")
        public String id;

        @SerializedName("secret")
        public String secret;

        @SerializedName("server")
        public String server;


        @SerializedName("title")
        public String title;
    }

}
