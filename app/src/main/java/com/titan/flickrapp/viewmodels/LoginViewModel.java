package com.titan.flickrapp.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.requests.responses.UserSearchResponse;
import com.titan.flickrapp.util.ApiResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginViewModel extends ViewModel {

    private FlickrRepository repository;
    private final CompositeDisposable disposables;
    private final MutableLiveData<ApiResponse> loginLiveData;

    public LoginViewModel(FlickrRepository repository) {
        this.repository = repository;
        this.disposables = new CompositeDisposable();
        loginLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ApiResponse> observeLogin() {
        return loginLiveData;
    }



    public void loginUser(String userName) {

        Timber.d("Atempt to login user: " + userName);

        disposables.add(repository.searchUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        loginLiveData.setValue(ApiResponse.loading());
                    }
                })
                .subscribe(
                        new Consumer<UserSearchResponse>() {
                            @Override
                            public void accept(UserSearchResponse response) throws Exception {

                                Timber.e("accept: " + response.toString());
                                loginLiveData.setValue(ApiResponse.success(response));
                            }
                        },

                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Timber.e("Error on serch user: " + throwable.getMessage());

                                loginLiveData.setValue(ApiResponse.error(throwable.getMessage()));
                            }
                        }

                ));

    }



    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
