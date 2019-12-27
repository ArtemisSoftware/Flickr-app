package com.titan.flickrapp.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.util.ApiResponse;

import io.reactivex.disposables.CompositeDisposable;

public class LoginViewModel extends ViewModel {

    private FlickrRepository repository;
    private final CompositeDisposable disposables;
    private final MutableLiveData<ApiResponse> loginLiveData;

    public LoginViewModel(FlickrRepository repository) {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
        loginLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ApiResponse> loginResponse() {
        return loginLiveData;
    }


    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
