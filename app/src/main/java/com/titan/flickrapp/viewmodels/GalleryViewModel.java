package com.titan.flickrapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.flickrapp.models.Picture;
import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.requests.responses.CheckApiResponse;
import com.titan.flickrapp.requests.responses.PhotoListResponse;
import com.titan.flickrapp.requests.responses.PhotoResponse;
import com.titan.flickrapp.requests.responses.UserSearchResponse;
import com.titan.flickrapp.util.ApiResponse;
import com.titan.flickrapp.util.Status;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GalleryViewModel extends ViewModel {

    public static final String QUERY_EXHAUSTED = "No more results";

    private FlickrRepository repository;
    private final CompositeDisposable disposables;
    private final MutableLiveData<ApiResponse> galleryLiveData;
    private int pageNumber, pages;
    private boolean isPerformingQuery;

    public GalleryViewModel(FlickrRepository repository) {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
        galleryLiveData = new MutableLiveData<>();
        pageNumber = 1;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public MutableLiveData<ApiResponse> observeGallery() {
        return galleryLiveData;
    }

    public void searchNextGallery(String nsid){

        if(pageNumber > pages){

            Timber.d("page limit reached pageNumber:" + pageNumber + " pages:" + pages);
            galleryLiveData.setValue(ApiResponse.error(QUERY_EXHAUSTED));

        }
        else if(!isPerformingQuery){
            pageNumber++;
            searchGallery(nsid);
        }
    }



    public void searchGallery(String nsid) {

        Timber.d("Searching user " + nsid + " page " + pageNumber + " list of pictures");
        isPerformingQuery = true;

        disposables.add(repository.searchPhotoList(nsid, String.valueOf(pageNumber))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        galleryLiveData.setValue(ApiResponse.loading());
                    }
                })
                .subscribe(
                        new Consumer<PhotoListResponse>() {
                            @Override
                            public void accept(PhotoListResponse photoListResponse) throws Exception {

                                Timber.e("accept: " + photoListResponse.toString());

                                ApiResponse response = CheckApiResponse.validate(photoListResponse);

                                if(response.status == Status.SUCCESS) {

                                    pages = photoListResponse.photos.pages;
                                    List<Picture> pictures = new ArrayList<>();

                                    for (PhotoListResponse.Photo photo : photoListResponse.photos.pictures) {
                                        pictures.add(new Picture(photo));
                                    }

                                    galleryLiveData.setValue(ApiResponse.success(pictures));
                                }
                                else{
                                    galleryLiveData.setValue(response);
                                }

                                isPerformingQuery = false;
                            }
                        },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                Timber.e("Error on serch user: " + throwable.getMessage());
                                galleryLiveData.setValue(ApiResponse.error(throwable.getMessage()));
                                isPerformingQuery = false;
                            }
                        }

                ));

    }


    public void searchPicture(String id) {

        Timber.d("Searching picture id: " + id);
        isPerformingQuery = true;

        disposables.add(repository.searchPhoto(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        galleryLiveData.setValue(ApiResponse.loading());
                    }
                })
                .subscribe(
                        new Consumer<PhotoResponse>() {
                            @Override
                            public void accept(PhotoResponse photoResponse) throws Exception {

                                Timber.e("accept: " + photoResponse.toString());

                                ApiResponse response = CheckApiResponse.validate(photoResponse);

                                if(response.status == Status.SUCCESS) {

                                    galleryLiveData.setValue(ApiResponse.success(new Picture(photoResponse)));
                                }
                                else{
                                    galleryLiveData.setValue(response);
                                }

                                isPerformingQuery = false;
                            }
                        },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                Timber.e("Error on serch user: " + throwable.getMessage());
                                galleryLiveData.setValue(ApiResponse.error(throwable.getMessage()));
                                isPerformingQuery = false;
                            }
                        }

                ));

    }


    /*

    public void searchGallery(String nsid) {

        Timber.d("Searching user " + nsid + " page " + pageNumber + " list of pictures");
        isPerformingQuery = true;

        repository.searchPhotoList(nsid, String.valueOf(pageNumber))
                .map(new Function<PhotoListResponse, List<String>>() {
                    @Override
                    public List<String> apply(PhotoListResponse response) throws Exception {

                        List<String> photoIds = new ArrayList<>();

                        pages = response.photos.pages;
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
                        isPerformingQuery = false;
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

    */

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
