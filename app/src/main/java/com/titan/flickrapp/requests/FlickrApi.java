package com.titan.flickrapp.requests;

import com.titan.flickrapp.requests.responses.PhotoListResponse;
import com.titan.flickrapp.requests.responses.PhotoResponse;
import com.titan.flickrapp.requests.responses.UserSearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {


    @GET("rest/?")
    Observable<UserSearchResponse> searchUser(@Query("method") String method, @Query("username") String username);

    @GET("rest/?")
    Observable<PhotoListResponse> searchPhotoList(@Query("method") String method, @Query("user_id") String nsid, @Query("page") String page);

    @GET("rest/?")
    Observable<PhotoResponse> searchPhoto(@Query("method") String method, @Query("user_id") String nsid, @Query("page") String page);

}
