package com.titan.flickrapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class BaseResponse {

    @SerializedName("stat")
    @Expose()
    private String status;

    @SerializedName("code")
    @Expose()
    private int code;

    @SerializedName("message")
    @Expose()
    private String message;


    public String getStatus(){
        return status;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
