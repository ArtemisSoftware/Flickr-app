package com.titan.flickrapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.requests.responses.CheckApiResponse;
import com.titan.flickrapp.requests.responses.PhotoListResponse;
import com.titan.flickrapp.util.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GalleryViewModel extends ViewModel {

    private FlickrRepository repository;
    private final CompositeDisposable disposables;
    private final MutableLiveData<ApiResponse> galleryLiveData;

    public GalleryViewModel(FlickrRepository repository) {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
        galleryLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<ApiResponse> observeGallery() {
        return galleryLiveData;
    }


    public void searchGallery(String nsid, String page) {

        Timber.d("Searching user " + nsid + " page " + page + " list of pictures");

        repository.searchPhotoList(nsid, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PhotoListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PhotoListResponse photoListResponse) {

                        galleryLiveData.setValue(CheckApiResponse.validate(photoListResponse));
                    }

                    @Override
                    public void onError(Throwable throwable) {

                        galleryLiveData.setValue(ApiResponse.error(throwable.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
