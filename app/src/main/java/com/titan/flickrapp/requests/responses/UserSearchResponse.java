package com.titan.flickrapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSearchResponse {

    @SerializedName("user")
    @Expose()
    private UserResponse user;


    public UserResponse getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserSearchResponse{" +
                "user=" + user.toString() +
                '}';
    }

    public class UserResponse {

        @SerializedName("id")
        public String id;

        @SerializedName("nsid")
        public String nsid;
    }


}