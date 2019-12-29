package com.titan.flickrapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.flickrapp.models.Picture;
import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.requests.responses.CheckApiResponse;
import com.titan.flickrapp.requests.responses.PhotoListResponse;
import com.titan.flickrapp.requests.responses.PhotoResponse;
import com.titan.flickrapp.util.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
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
    private int pageNumber;

    public GalleryViewModel(FlickrRepository repository) {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
        galleryLiveData = new MutableLiveData<>();
        pageNumber = 1;
    }


    public MutableLiveData<ApiResponse> observeGallery() {
        return galleryLiveData;
    }


    public void searchGallery(String nsid) {

        Timber.d("Searching user " + nsid + " page " + pageNumber + " list of pictures");


        repository.searchPhotoList(nsid, String.valueOf(pageNumber))
                .map(new Function<PhotoListResponse, List<String>>() {
                    @Override
                    public List<String> apply(PhotoListResponse response) throws Exception {

                        List<String> photoIds = new ArrayList<>();

                        for (PhotoListResponse.Photo photo : response.photos.pictures) {
                            photoIds.add(photo.id);
                        }

                        return photoIds; // B.
                    }
                })
                .flatMap(new Function<List<String>, Observable<List<Picture>>>() {
                    @Override
                    public Observable<List<Picture>> apply(List<String> photoIds) throws Exception {
                        return getPicturesObservable(photoIds);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<Picture>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        disposables.add(disposable);
                        galleryLiveData.setValue(ApiResponse.loading());
                    }

                    @Override
                    public void onNext(List<Picture> pictures) {

                        Timber.d("onNext: " + pictures.toString());
                        galleryLiveData.setValue(ApiResponse.success(pictures));
                    }

                    @Override
                    public void onError(Throwable throwable) {

                        Timber.e("Error on serch user: " + throwable.getMessage());
                        galleryLiveData.setValue(ApiResponse.error(throwable.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        disposables.clear();
                    }
                });

    }


    private Observable<List<Picture>> getPicturesObservable(List<String> photoIds){

        List<Observable<PhotoResponse>> requests = new ArrayList<>();

        for(String id : photoIds) {
            requests.add(repository.searchPhoto(id));
        }

        Observable<List<Picture>> observable = Observable.zip(
                requests,
                new Function<Object[], List<Picture>>() {
                    @Override
                    public List<Picture> apply(Object[] photos) throws Exception {

                        Timber.d("apply photo response: " + photos);

                        List<Picture> pictures = new ArrayList<>();

                        //for (PhotoResponse photo : photos) {

                        for (int i = 0; i < photos.length; ++i) {

                            PhotoResponse photo = ((PhotoResponse) photos[i]);
                            pictures.add(new Picture(photo));
                        }
                        return pictures;
                    }
                });

        return observable;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
