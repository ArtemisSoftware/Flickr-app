package com.titan.flickrapp.repository;

import com.titan.flickrapp.requests.FlickrApi;
import com.titan.flickrapp.requests.responses.CheckApiResponse;
import com.titan.flickrapp.requests.responses.PhotoListResponse;
import com.titan.flickrapp.requests.responses.UserSearchResponse;
import com.titan.flickrapp.util.ApiConstants;
import com.titan.flickrapp.util.ApiResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class FlickrRepositoryTest {

    private static final String USER = "eyetwist";


    @Mock
    FlickrApi flickrApi;

    private FlickrRepository flickrRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        flickrRepository = new FlickrRepository(flickrApi);
    }

    //[Name of method under test]_[Conditions of test case]_[Expected Result]
    

    @Test
    void searchUser_ApiSuccessResponse_() throws Exception {

        //Arrange

        Observable<UserSearchResponse> returnedData = Observable.just(searchUserResponse());

        when(flickrApi.searchUser(ApiConstants.USER_METHOD,USER)).thenReturn(returnedData);

        TestObserver<UserSearchResponse> subscriber = new TestObserver <>();
        flickrRepository.searchUser(USER).subscribe(subscriber);

        //Assert

        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Object>> onNextEvents = subscriber.getEvents();
        List<Object> users = onNextEvents.get(0);

        ApiResponse returnedValue = CheckApiResponse.validate((UserSearchResponse) users.get(0));

        //Act

        ApiResponse<UserSearchResponse> result = ApiResponse.success(searchUserResponse());
        //assertEquals(result.status, returnedValue.status);
        assertEquals(result, returnedValue);
        verify(flickrApi).searchUser(ApiConstants.USER_METHOD, USER);
        
    }


    @Test
    void searchPhotolist_ApiError() throws Exception {


        //Arrange
        when(flickrApi.searchPhotoList(ApiConstants.PUBLIC_PHOTOS_METHOD, USER, "1")).thenReturn(get403ForbiddenError());

        //Act
        TestObserver<PhotoListResponse> subscriber = new TestObserver <>();
        flickrRepository.searchPhotoList(USER, "1").subscribe(subscriber);

        //Assert
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        List<List<Object>> onNextEvents = subscriber.getEvents();
        List<Object> users = onNextEvents.get(0);

        verify(flickrApi).searchPhotoList(ApiConstants.PUBLIC_PHOTOS_METHOD, USER, "1");


    }


    private Observable<PhotoListResponse> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }

    private UserSearchResponse searchUserResponse() {

        UserSearchResponse.UserResponse dd = new UserSearchResponse.UserResponse("49191827@N00", "49191827@N00");
        UserSearchResponse userResponse = new UserSearchResponse(dd);
        userResponse.code = 0;

        return userResponse;
    }

}
