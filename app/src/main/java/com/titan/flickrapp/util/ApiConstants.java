package com.titan.flickrapp.util;

public class ApiConstants {

    public static final String BASE_URL = "https://api.flickr.com/services/";

    public static final String API_KEY = "22df34dbfb05acc69192986065aec6c0";


    public static final String USER_METHOD = "flickr.people.findByUsername";
    public static final String PUBLIC_PHOTOS_METHOD = "flickr.people.getPublicPhotos";
    public static final String PHOTO_INFO_METHOD = "flickr.photos.getInfo";


    public static final String FORMAT = "json";
    public static final String NO_JSON_CALLBACK = "?";
    public static final String PER_PAGE = "7";

    public static final int CONNECTION_TIMEOUT = 10; // 10 seconds
    public static final int READ_TIMEOUT = 20; // 2 seconds
    public static final int WRITE_TIMEOUT = 20; // 2 seconds

    public static final int NETWORK_TIMEOUT = 3000;
}
