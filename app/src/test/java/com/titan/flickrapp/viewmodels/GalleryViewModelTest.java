package com.titan.flickrapp.viewmodels;


import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import com.titan.flickrapp.LiveDataTestUtil;
import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.requests.responses.PhotoListResponse;
import com.titan.flickrapp.requests.responses.UserSearchResponse;
import com.titan.flickrapp.util.ApiResponse;
import com.titan.flickrapp.util.InstantExecutorExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(InstantExecutorExtension.class)
public class GalleryViewModelTest {

    //system under test
    private GalleryViewModel viewModel;

    @Mock
    private FlickrRepository noteRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        viewModel = new GalleryViewModel(noteRepository);
    }


}
