package com.titan.flickrapp.repository;

import com.titan.flickrapp.requests.FlickrApi;
import com.titan.flickrapp.requests.responses.PhotoListResponse;
import com.titan.flickrapp.requests.responses.PhotoResponse;
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


    public Observable<PhotoListResponse> searchPhotoList(String nsid, String page) {
        return api.searchPhotoList(ApiConstants.USER_METHOD, nsid,page);
    }

    public Observable<PhotoResponse> searchPhoto(String photoId) {
        return api.searchPhoto(ApiConstants.USER_METHOD, photoId);
    }

}
