package com.titan.flickrapp.repository;

import com.titan.flickrapp.requests.FlickrApi;
import com.titan.flickrapp.requests.responses.UserSearchResponse;
import com.titan.flickrapp.util.ApiConstants;

import io.reactivex.Observable;

public class FlickrRepository {

    private FlickrApi api;

    public FlickrRepository(FlickrApi api) {
        this.api = api;
    }


    public Observable<UserSearchResponse> searchUser(String userName) {
        return api.searchUser(ApiConstants.USER_METHOD, userName);
    }

}
