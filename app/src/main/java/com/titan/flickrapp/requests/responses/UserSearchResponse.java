package com.titan.flickrapp.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSearchResponse extends BaseResponse {

    @SerializedName("user")
    @Expose()
    public UserResponse user;


    public UserSearchResponse(UserResponse user) {
        this.user = user;
    }

    public UserResponse getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserSearchResponse{" +
                "user=" + user +
                '}';
    }

    public static class UserResponse {

        @SerializedName("id")
        public String id;

        @SerializedName("nsid")
        public String nsid;


        public UserResponse(String id, String nsid) {
            this.id = id;
            this.nsid = nsid;
        }
    }


}
