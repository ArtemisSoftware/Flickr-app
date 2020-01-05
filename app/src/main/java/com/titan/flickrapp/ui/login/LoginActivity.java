package com.titan.flickrapp.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.titan.flickrapp.App;
import com.titan.flickrapp.R;
import com.titan.flickrapp.requests.responses.UserSearchResponse;
import com.titan.flickrapp.ui.BaseActivity;
import com.titan.flickrapp.ui.gallery.GalleryActivity;
import com.titan.flickrapp.util.ApiResponse;
import com.titan.flickrapp.util.AppConstants;
import com.titan.flickrapp.util.ViewModelFactory;
import com.titan.flickrapp.viewmodels.LoginViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.txt_input_user)
    TextInputLayout txt_input_user;

    @Inject
    ViewModelFactory viewModelFactory;

    LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Timber.d("Init app");

        ((App) getApplication()).getAppComponent().doInjection(this);

        loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        subscribeObservers();
    }


    private void subscribeObservers(){

        loginViewModel.observeLogin().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

                Timber.d("onChanged: " + apiResponse.toString());

                switch (apiResponse.status){


                    case LOADING:

                        showProgressBar(true);
                        break;

                    case SUCCESS:


                        showProgressBar(false);
                        initGalley((UserSearchResponse) apiResponse.data);
                        break;

                    case ERROR:

                        Toast.makeText(getApplicationContext(),"Error: " + apiResponse.message,Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        break;
                }
            }
        });
    }

    private void initGalley(UserSearchResponse data) {

        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra(AppConstants.NSID, data.getUser().nsid);
        startActivity(intent);

    }


    @OnClick(R.id.btn_login)
    public void btn_login__onButtonClick(View view) {
        loginViewModel.loginUser(txt_input_user.getEditText().getText().toString());
    }


}
