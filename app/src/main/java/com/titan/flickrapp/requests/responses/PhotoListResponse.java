package com.titan.flickrapp.requests.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoListResponse extends BaseResponse{

    @SerializedName("page")
    public int page;

    @SerializedName("pages")
    public int pages;

    @SerializedName("photo")
    public List<PhotoResponse> photos;

}
