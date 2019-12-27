package com.titan.flickrapp.requests;

import com.titan.flickrapp.requests.responses.UserSearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {


    @GET("rest/?")
    Observable<UserSearchResponse> searchUser(@Query("method") String method, @Query("username") String username);

}
