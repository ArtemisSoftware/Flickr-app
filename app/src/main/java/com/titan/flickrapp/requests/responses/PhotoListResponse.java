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
        public List<Photo> photos;
    }


    public class Photo {

        @SerializedName("id")
        public String id;
    }

}
