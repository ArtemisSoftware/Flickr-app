package com.titan.flickrapp.repository;

import com.titan.flickrapp.requests.FlickrApi;
import com.titan.flickrapp.requests.responses.BaseResponse;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class FlickrRepositoryTest {

    private static final String USER = "eyetwist";


    @Mock
    FlickrApi githubUserRestService;

    private FlickrRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userRepository = new FlickrRepository(githubUserRestService);
    }

    //[Name of method under test]_[Conditions of test case]_[Expected Result]
    

    @Test
    void searchUser_ApiSuccessResponse_() throws Exception {

        //Arrange

        Observable<UserSearchResponse> returnedData = Observable.just(githubUserList());

        when(githubUserRestService.searchUser(ApiConstants.USER_METHOD,USER)).thenReturn(returnedData);

        TestObserver<UserSearchResponse> subscriber = new TestObserver <>();
        userRepository.searchUser(USER).subscribe(subscriber);

        //Assert

        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Object>> onNextEvents = subscriber.getEvents();
        List<Object> users = onNextEvents.get(0);

        ApiResponse returnedValue = CheckApiResponse.validate((UserSearchResponse) users.get(0));

        //Act

        assertEquals(ApiResponse.success(githubUserList()), returnedValue);
        verify(githubUserRestService).searchUser(ApiConstants.USER_METHOD, USER);
        
    }


    @Test
    void name() throws Exception {


        //Given
        when(githubUserRestService.searchPhotoList(ApiConstants.PUBLIC_PHOTOS_METHOD, USER, "1")).thenReturn(get403ForbiddenError());

        //When
        TestObserver<PhotoListResponse> subscriber = new TestObserver <>();
        userRepository.searchPhotoList(USER, "1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        List<List<Object>> onNextEvents = subscriber.getEvents();
        List<Object> users = onNextEvents.get(0);

        verify(githubUserRestService).searchPhotoList(ApiConstants.PUBLIC_PHOTOS_METHOD, anyString(), anyString());


        //Arrange

        //Act

        //Assert

    }


    private Observable<PhotoListResponse> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }

    private UserSearchResponse githubUserList() {

        UserSearchResponse.UserResponse dd = new UserSearchResponse.UserResponse("49191827@N00", "49191827@N00");
        UserSearchResponse userResponse = new UserSearchResponse(dd);
        userResponse.code = 0;

        return userResponse;
    }

}
